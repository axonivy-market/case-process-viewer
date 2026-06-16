package com.axonivy.utils.caseprocessviewer.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum NodeType {
  @JsonProperty("element") ELEMENT,
  @JsonProperty("arrow") ARROW;
}
