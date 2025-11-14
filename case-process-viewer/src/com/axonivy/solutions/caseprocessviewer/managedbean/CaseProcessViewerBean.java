package com.axonivy.solutions.caseprocessviewer.managedbean;

import java.io.Serializable;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.PF;

import com.axonivy.solutions.caseprocessviewer.bo.ProcessMiningData;
import com.axonivy.solutions.caseprocessviewer.constants.ColorConstants;
import com.axonivy.solutions.caseprocessviewer.core.bo.Process;
import com.axonivy.solutions.caseprocessviewer.core.constants.CaseProcessViewerConstants;
import com.axonivy.solutions.caseprocessviewer.core.internal.ProcessViewerBuilder;
import com.axonivy.solutions.caseprocessviewer.core.util.ProcessUtils;
import com.axonivy.solutions.caseprocessviewer.utils.JacksonUtils;
import com.axonivy.solutions.caseprocessviewer.utils.NodeUtils;

import ch.ivyteam.ivy.application.IApplication;
import ch.ivyteam.ivy.cm.ContentObject;
import ch.ivyteam.ivy.cm.exec.ContentManagement;
import ch.ivyteam.ivy.environment.Ivy;

@ManagedBean
@ViewScoped
public class CaseProcessViewerBean implements Serializable {

  private static final long serialVersionUID = -2589140797903853427L;
  private String bpmnIframeSourceUrl;
  private String processUrl;
  private ContentObject processMiningDataJsonFile;
  private ProcessMiningData processMiningData;

  @PostConstruct
  public void init() {
    processMiningDataJsonFile = ContentManagement.cms(IApplication.current()).root().child()
        .folder(CaseProcessViewerConstants.CASE_PROCESS_VIEWER_CMS_PATH).child()
        .file(CaseProcessViewerConstants.DATA_CMS_PATH, CaseProcessViewerConstants.JSON_EXTENSION);
    processUrl = processMiningDataJsonFile.uri();

    var processStart = Ivy.wfCase().getProcessStart();
    var pmv = Ivy.wfCase().getProcessModelVersion();
    var process = ProcessUtils.getProcessByPMVAndProcessStartElementId(pmv, processStart.getProcessElementId());
    initializingProcessMiningData(process);
    String jsonString = JacksonUtils.convertObjectToJSONString(processMiningData);
    processMiningDataJsonFile.value().get(CaseProcessViewerConstants.EN_CMS_LOCALE).write().string(jsonString);

    var builder = new ProcessViewerBuilder();
    builder.pmv(pmv.getName());
    builder.projectPath(process.getProjectRelativePath());
    bpmnIframeSourceUrl = builder.toURI().toString();
    PF.current().executeScript(CaseProcessViewerConstants.UPDATE_IFRAME_SOURCE_METHOD_CALL);
  }

  private void initializingProcessMiningData(Process process) {
    Optional.ofNullable(process).ifPresent(selectedProcess -> {
      processMiningData = new ProcessMiningData();
      processMiningData.setProcessId(selectedProcess.getId());
      processMiningData.setProcessName(selectedProcess.getName());
      processMiningData.setPassedColor(ColorConstants.PASSED_COLOR);
      processMiningData.setActiveColor(ColorConstants.ACTIVE_COLOR);
      var processElements = ProcessUtils.getProcessElementsOfFirstLayerFrom(process.getId(), process.getPmv());
      var allProcessElements = ProcessUtils.getAllProcessElements(processElements);
      processMiningData.setNodes(NodeUtils.buildNodes(allProcessElements));
      String targetElementId = Ivy.wfTask().getStart().getTaskElement().getProcessElementId();
      processMiningData
          .setActiveElementIds(ProcessUtils.getAllElementIdsContainElementId(targetElementId, processElements));
    });
  }

  public boolean shouldRenderBpmnFrame() {
    return StringUtils.isNoneBlank(bpmnIframeSourceUrl);
  }

  public String getBpmnIframeSourceUrl() {
    return bpmnIframeSourceUrl;
  }

  public void setBpmnIframeSourceUrl(String bpmnIframeSourceUrl) {
    this.bpmnIframeSourceUrl = bpmnIframeSourceUrl;
  }

  public String getProcessUrl() {
    return processUrl;
  }

  public void setProcessUrl(String processUrl) {
    this.processUrl = processUrl;
  }
}
