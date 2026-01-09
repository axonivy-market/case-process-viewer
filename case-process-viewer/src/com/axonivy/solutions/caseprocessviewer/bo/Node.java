package com.axonivy.solutions.caseprocessviewer.bo;

import java.util.ArrayList;
import java.util.List;

import com.axonivy.solutions.caseprocessviewer.enums.NodeType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Node {
  private NodeType type;
  private String id;
  private boolean passed;
  private String labelValue;
  @JsonIgnore
  private List<String> outGoingPathIds = new ArrayList<>();
  @JsonIgnore
  private List<String> inCommingPathIds = new ArrayList<>();
  @JsonIgnore
  private String targetNodeId;
  @JsonIgnore
  private String sourceNodeId;
  @JsonIgnore
  private boolean isTaskSwitchGateway;
  @JsonIgnore
  private String requestPath;
  @JsonIgnore
  private String label;
  @JsonIgnore
  private int frequency;

  public String getSourceNodeId() {
    return sourceNodeId;
  }

  public void setSourceNodeId(String sourceNodeId) {
    this.sourceNodeId = sourceNodeId;
  }

  public Node() {}

  public NodeType getType() {
    return type;
  }

  public void setType(NodeType type) {
    this.type = type;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public boolean isPassed() {
    return passed;
  }

  public void setPassed(boolean passed) {
    this.passed = passed;
  }

  public List<String> getOutGoingPathIds() {
    return outGoingPathIds;
  }

  public void setOutGoingPathIds(List<String> outGoingPathIds) {
    this.outGoingPathIds = outGoingPathIds;
  }

  public List<String> getInCommingPathIds() {
    return inCommingPathIds;
  }

  public void setInCommingPathIds(List<String> inCommingPathIds) {
    this.inCommingPathIds = inCommingPathIds;
  }

  public String getTargetNodeId() {
    return targetNodeId;
  }

  public void setTargetNodeId(String targetNodeId) {
    this.targetNodeId = targetNodeId;
  }

  public boolean isTaskSwitchGateway() {
    return isTaskSwitchGateway;
  }

  public void setTaskSwitchGateway(boolean isTaskSwitchGateway) {
    this.isTaskSwitchGateway = isTaskSwitchGateway;
  }

  public String getRequestPath() {
    return requestPath;
  }

  public void setRequestPath(String requestPath) {
    this.requestPath = requestPath;
  }

  public String getLabelValue() {
    return labelValue;
  }

  public void setLabelValue(String labelValue) {
    this.labelValue = labelValue;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public int getFrequency() {
    return frequency;
  }

  public void setFrequency(int frequency) {
    this.frequency = frequency;
  }
  
  @Override
  public String toString() {
    final var pattern =
        "Node: { id: %s / inCommingPathIds: %s / outGoingPathIds: %s / isTaskSwitchGateway: %s / targetNodeId: %s / passed: %s }";
    return pattern.formatted(id, inCommingPathIds, outGoingPathIds, isTaskSwitchGateway, targetNodeId, passed);
  }
}
