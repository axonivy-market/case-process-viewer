package com.axonivy.solutions.caseprocessviewer.core.enums;

public enum ViewerParam {
  FACES("faces"), VIEW("view"), SERVER("server"), APP("app"), PMV("pmv"), FILE("file"), HIGHLIGHT("highlight"),
  SELECT("select"), ZOOM("zoom"), CASE_PROCESS_VIEWER("case-process-viewer"),
  CASE_PROCESS_VIEWER_FILE("case-process-viewer.xhtml");

  private String value;

  private ViewerParam(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

}
