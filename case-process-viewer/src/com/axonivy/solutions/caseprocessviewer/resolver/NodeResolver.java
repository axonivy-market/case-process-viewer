package com.axonivy.solutions.caseprocessviewer.resolver;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.axonivy.solutions.caseprocessviewer.bo.Node;
import com.axonivy.solutions.caseprocessviewer.core.constants.CaseProcessViewerConstants;
import com.axonivy.solutions.caseprocessviewer.core.util.ProcessUtils;
import com.axonivy.solutions.caseprocessviewer.enums.NodeType;

import ch.ivyteam.ivy.process.model.connector.SequenceFlow;
import ch.ivyteam.ivy.process.model.element.ProcessElement;
import ch.ivyteam.ivy.process.model.element.event.start.RequestStart;
import ch.ivyteam.ivy.process.model.element.gateway.TaskSwitchGateway;

@SuppressWarnings("restriction")
public class NodeResolver {

  public static List<Node> convertToNodes(List<ProcessElement> processElements, List<SequenceFlow> sequenceFlows) {
    return Stream.concat(
        processElements.stream().flatMap(processElement -> convertProcessElementToNode(processElement).stream()),
        sequenceFlows.stream().map(flow -> convertSequenceFlowToNode(flow))).collect(Collectors.toList());
  }

  /**
   * Convert process element to Node base on its class type
   **/
  public static List<Node> convertProcessElementToNode(ProcessElement element) {
    Node node = createNode(element.getPid().toString(), NodeType.ELEMENT);
    node.setOutGoingPathIds(element.getOutgoing().stream().map(ProcessUtils::getElementPid).toList());

    return switch (element) {
      case TaskSwitchGateway taskSwitchGateway -> {
        node.setInCommingPathIds(taskSwitchGateway.getIncoming().stream().map(ProcessUtils::getElementPid).toList());
        node.setTaskSwitchGateway(true);
        String elementId = taskSwitchGateway.getPid().toString();
        List<Node> taskNodes = taskSwitchGateway.getAllTaskConfigs().stream()
            .map(task -> createNode(
                elementId + CaseProcessViewerConstants.SLASH + task.getTaskIdentifier().getTaskIvpLinkName(),
                NodeType.ELEMENT))
            .collect(Collectors.toList());
        taskNodes.add(0, node);
        yield taskNodes;
      }
      case RequestStart requestStart -> {
        node.setRequestPath(requestStart.getRequestPath().getLinkPath());
        yield List.of(node);
      }
      default -> {
        node.setInCommingPathIds(element.getIncoming().stream().map(ProcessUtils::getElementPid).toList());
        yield List.of(node);
      }
    };
  }

  public static Node convertSequenceFlowToNode(SequenceFlow flow) {
    Node node = createNode(ProcessUtils.getElementPid(flow), NodeType.ARROW);
    node.setTargetNodeId(flow.getTarget().getPid().toString());
    node.setSourceNodeId(flow.getSource().getPid().toString());
    return node;
  }

  private static Node createNode(String id, NodeType type) {
    Node node = new Node();
    node.setId(id);
    node.setType(type);
    return node;
  }
}
