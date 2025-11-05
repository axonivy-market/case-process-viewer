package com.axonivy.solutions.caseprocessviewer.test.ut;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;

import org.junit.jupiter.api.Test;

import com.axonivy.solutions.caseprocessviewer.core.internal.ProcessViewerBuilder;

import ch.ivyteam.ivy.environment.IvyTest;

@IvyTest
public class ProcessViewerBuilderTest extends BaseTest {

  @Test
  void test_buildBpmnIFrameSourceUrl() {
    var builder = new ProcessViewerBuilder();
    builder.pmv(MODULE_URL);
    builder.projectPath(PROJECT_PATH);
    assertThat(builder.toURI().toString()).isEqualTo(URI.create(TEST_IFRAME_SOURCE_URL).toString());
  }
}
