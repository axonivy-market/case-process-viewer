package com.axonivy.solutions.caseprocessviewer.test.ut;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.axonivy.solutions.caseprocessviewer.core.util.ProcessUtils;

import ch.ivyteam.ivy.environment.IvyTest;

@IvyTest
public class ProcessUtilsTest extends BaseTest {

  @BeforeAll
  static void setUp() {
    prepareData();
  }

  @Test
  void test_getProcessPidFromElement() {
    assertThat(ProcessUtils.getProcessPidFromElement(TEST_PROCESS_ELEMENT_START_PID)).isEqualTo(TEST_PROCESS_RAW_PID);
  }
}