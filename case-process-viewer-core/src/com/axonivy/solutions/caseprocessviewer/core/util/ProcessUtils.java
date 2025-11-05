package com.axonivy.solutions.caseprocessviewer.core.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;

import com.axonivy.solutions.caseprocessviewer.core.bo.Process;
import com.axonivy.solutions.caseprocessviewer.core.constants.CaseProcessViewerConstants;

import ch.ivyteam.ivy.application.IProcessModelVersion;
import ch.ivyteam.ivy.process.model.BaseElement;
import ch.ivyteam.ivy.process.model.ProcessKind;
import ch.ivyteam.ivy.process.model.connector.SequenceFlow;
import ch.ivyteam.ivy.process.model.element.EmbeddedProcessElement;
import ch.ivyteam.ivy.process.model.element.ProcessElement;
import ch.ivyteam.ivy.process.model.element.activity.SubProcessCall;
import ch.ivyteam.ivy.process.model.element.event.end.EmbeddedEnd;
import ch.ivyteam.ivy.process.model.element.event.end.TaskEnd;
import ch.ivyteam.ivy.process.model.element.event.intermediate.TaskSwitchEvent;
import ch.ivyteam.ivy.process.model.element.event.start.CallSubStart;
import ch.ivyteam.ivy.process.model.element.event.start.EmbeddedStart;
import ch.ivyteam.ivy.process.model.element.gateway.Alternative;
import ch.ivyteam.ivy.process.model.element.gateway.TaskSwitchGateway;
import ch.ivyteam.ivy.process.model.value.PID;
import ch.ivyteam.ivy.process.rdm.IProcess;
import ch.ivyteam.ivy.process.rdm.IProcessManager;
import ch.ivyteam.ivy.process.rdm.IProjectProcessManager;

@SuppressWarnings("restriction")
public class ProcessUtils {

  private ProcessUtils() {}

  public static String getElementPid(BaseElement baseElement) {
    return Optional.ofNullable(baseElement).map(BaseElement::getPid).map(PID::toString).orElse(StringUtils.EMPTY);
  }

  public static String getProcessPidFromElement(String elementId) {
    return StringUtils.defaultString(elementId).split(CaseProcessViewerConstants.HYPHEN_SIGN)[0];
  }

  public static boolean isEmbeddedElementInstance(Object element) {
    return EmbeddedProcessElement.class.isInstance(element);
  }

  public static boolean isAlternativeInstance(Object element) {
    return Alternative.class.isInstance(element);
  }

  public static boolean isTaskSwitchInstance(Object element) {
    return TaskSwitchEvent.class.isInstance(element);
  }

  public static boolean isTaskEndInstance(Object element) {
    return TaskEnd.class.isInstance(element);
  }

  public static boolean isTaskSwitchGatewayInstance(Object element) {
    return TaskSwitchGateway.class.isInstance(element);
  }

  public static boolean isSubProcessCallInstance(Object element) {
    return SubProcessCall.class.isInstance(element);
  }

  public static boolean isEmbeddedEndInstance(Object element) {
    return EmbeddedEnd.class.isInstance(element);
  }

  public static List<ProcessElement> getNestedProcessElementsFromSub(Object element) {
    return switch (element) {
      case EmbeddedProcessElement embeddedElement -> getEmbbedProcessElements(embeddedElement).stream()
          .flatMap(e -> Stream.concat(Stream.of(e), getEmbbedProcessElements(e).stream())).collect(Collectors.toList());
      case SubProcessCall subProcessCall -> getProcessElementsFromCallableSubProcessPath(
          subProcessCall.getCallTarget().getProcessName().getName());
      default -> Collections.emptyList();
    };
  }

  /*
   * Get nested process elements inside the sub (we only support 2 nested layer)
   */
  public static List<ProcessElement> getEmbbedProcessElements(ProcessElement processElement) {
    if (processElement instanceof EmbeddedProcessElement embeddedElement) {
      return embeddedElement.getEmbeddedProcess().getProcessElements();
    }
    return Collections.emptyList();
  }

  public static ProcessElement getStartElementFromSubProcessCall(Object element) {
    if (!isSubProcessCallInstance(element)) {
      return null;
    }
    String targetName = SubProcessCall.class.cast(element).getCallTarget().getSignature().getName();
    return getNestedProcessElementsFromSub(element).stream().filter(CallSubStart.class::isInstance)
        .map(CallSubStart.class::cast).filter(start -> Strings.CS.equals(start.getSignature().getName(), targetName))
        .findAny().orElse(null);
  }

  public static List<ProcessElement> getProcessElementsOfFirstLayerFrom(String processId, IProcessModelVersion pmv) {
    if (StringUtils.isBlank(processId)) {
      return Collections.emptyList();
    }

    String processRawPid = getProcessPidFromElement(processId);
    IProjectProcessManager manager = IProcessManager.instance().getProjectDataModelFor(pmv);
    IProcess foundProcess = manager.findProcess(processRawPid, true);
    if (foundProcess == null) {
      return Collections.emptyList();
    }

    return foundProcess.getModel().getProcessElements();
  }

  public static List<ProcessElement> getAllProcessElements(List<ProcessElement> processElements) {
    return processElements.stream()
        .flatMap(element -> Stream.concat(Stream.of(element), getNestedProcessElementsFromSub(element).stream()))
        .collect(Collectors.toList());
  }

  public static List<String> getAllElementIdsContainElementId(String targetElementId,
      List<ProcessElement> processElements) {
    List<String> result = new ArrayList<>();
    collectAllElementsContainElementId(targetElementId, processElements, result);
    return result;
  }

  private static void collectAllElementsContainElementId(String targetElementId, List<ProcessElement> processElements,
      List<String> elementIds) {
    for (ProcessElement pe : processElements) {
      if (containsElementId(targetElementId, pe, elementIds)) {
        return;
      }
    }
  }

  private static boolean containsElementId(String targetElementId, ProcessElement processElement,
      List<String> elementIds) {
    elementIds.add(PIDUtils.getId(processElement.getPid()));
    if (processElement instanceof EmbeddedProcessElement embeddedElement) {
      List<ProcessElement> processElements = embeddedElement.getEmbeddedProcess().getProcessElements();
      if (hasElement(targetElementId, processElements, elementIds)) {
        return true;
      }
    } else if (processElement instanceof SubProcessCall subProcessCall) {
      List<ProcessElement> processElements =
          getProcessElementsFromCallableSubProcessPath(subProcessCall.getCallTarget().getProcessName().getName());
      if (hasElement(targetElementId, processElements, elementIds)) {
        return true;
      }
    } else if (Strings.CI.equals(targetElementId, PIDUtils.getId(processElement.getPid()))) {
      return true;
    }

    elementIds.remove(elementIds.size() - 1);
    return false;
  }

  private static boolean hasElement(String targetElementId, List<ProcessElement> processElements,
      List<String> elementIds) {
    for (ProcessElement pe : processElements) {
      if (containsElementId(targetElementId, pe, elementIds)) {
        return true;
      }
    }
    return false;
  }

  /*
   * Get nested process elements inside the call-able sub by these steps: 1) find the process path which is called
   * inside the sub 2) find all of process element from this process 3) (Optional) find nested embedded process inside
   * the BPMN sub (if exist)
   */
  private static List<ProcessElement> getProcessElementsFromCallableSubProcessPath(String subProcessPath) {
    return IProcessManager.instance().getProjectDataModels().stream()
        .map(model -> model.getProcessByPath(subProcessPath)).filter(Objects::nonNull).findAny()
        .map(process -> process.getModel().getProcessElements().stream()
            .flatMap(pe -> Stream.concat(Stream.of(pe), getNestedProcessElementsFromSub(pe).stream()))
            .collect(Collectors.toList()))
        .orElse(Collections.emptyList());
  }

  public static List<SequenceFlow> getSequenceFlowsFrom(List<ProcessElement> elements) {
    return elements.stream().flatMap(element -> element.getOutgoing().stream()).collect(Collectors.toList());
  }

  private static Process convertIProcessToProcess(IProcessModelVersion iPmv, IProcess iProcess) {
    var process = new Process(iProcess.getIdentifier(), iProcess.getName(), new ArrayList<>());
    process.setPmvId(iPmv.getId());
    process.setPmvName(iPmv.getName());
    process.setPmv(iPmv);
    process.setProjectRelativePath(iProcess.getResource().getProjectRelativePath().toString());
    return process;
  }

  public static Process getProcessByPMVAndProcessStartElementId(IProcessModelVersion pmv,
      String processStartElementId) {
    IProcess iProcess = IProcessManager.instance().getProjectDataModelFor(pmv).getProcesses().stream()
        .filter(process -> (process.getKind() == ProcessKind.NORMAL || process.getKind() == ProcessKind.WEB_SERVICE)
            && processStartElementId.contains(process.getIdentifier()))
        .toList().getFirst();

    return iProcess != null ? convertIProcessToProcess(pmv, iProcess) : null;
  }

  public static boolean isProcessPathEndElement(ProcessElement processElement) {
    return Optional.ofNullable(processElement).map(element -> CollectionUtils.isEmpty(element.getOutgoing()))
        .orElse(false);
  }

  public static boolean isEmbeddedStartConnectToSequenceFlow(ProcessElement element, String targetSequenceFlowId) {
    if (element instanceof EmbeddedStart embeddedStart) {
      String connectedOutterFlowId = embeddedStart.getConnectedOuterSequenceFlow().getPid().toString();
      return StringUtils.defaultString(targetSequenceFlowId).equals(connectedOutterFlowId);
    }
    return false;
  }

  public static ProcessElement getEmbeddedStartConnectToFlow(ProcessElement processElement, String outerFlowId) {
    if (isEmbeddedElementInstance(processElement)) {
      processElement = getNestedProcessElementsFromSub(processElement).stream()
          .filter(element -> isEmbeddedStartConnectToSequenceFlow(element, outerFlowId)).findAny().orElse(null);
    }
    return processElement;
  }
}
