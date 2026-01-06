package com.axonivy.solutions.caseprocessviewer.constants;

import ch.ivyteam.ivy.environment.Ivy;

public class ColorConstants {
//  public static final String PASSED_COLOR = "#47C46B";
  public static final String PASSED_COLOR = Ivy.var().get("caseProcessViewer.passedColor");
  public static final String ACTIVE_COLOR = "#3B82F6";
  public static final String FREQUENCY_COLOR = "#47C46B";
  public static final String FREQUENCY_TEXT_COLOR = "#000000";
}
