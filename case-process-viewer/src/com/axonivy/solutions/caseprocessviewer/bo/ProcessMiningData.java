package com.axonivy.solutions.caseprocessviewer.bo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProcessMiningData {
  @JsonIgnore
  private String processId;
  private String processName;
  private List<Node> nodes;
  private String activeColor;
  private String passedColor;
  private String frequencyColor;
  private String frequencyTextColor;
  private List<String> activeElementIds;

  public ProcessMiningData() {}

  public String getProcessId() {
    return processId;
  }

  public void setProcessId(String processId) {
    this.processId = processId;
  }

  public String getProcessName() {
    return processName;
  }

  public void setProcessName(String processName) {
    this.processName = processName;
  }

  public List<Node> getNodes() {
    return nodes;
  }

  public void setNodes(List<Node> nodes) {
    this.nodes = nodes;
  }

  public String getActiveColor() {
    return activeColor;
  }

  public void setActiveColor(String activeColor) {
    this.activeColor = activeColor;
  }

  public String getPassedColor() {
    return passedColor;
  }

  public void setPassedColor(String passedColor) {
    this.passedColor = passedColor;
  }

  public String getFrequencyColor() {
    return frequencyColor;
  }

  public void setFrequencyColor(String frequencyColor) {
    this.frequencyColor = frequencyColor;
  }

  public String getFrequencyTextColor() {
    return frequencyTextColor;
  }

  public void setFrequencyTextColor(String frequencyTextColor) {
    this.frequencyTextColor = frequencyTextColor;
  }

  public List<String> getActiveElementIds() {
    return activeElementIds;
  }

  public void setActiveElementIds(List<String> activeElementIds) {
    this.activeElementIds = activeElementIds;
  }
}
