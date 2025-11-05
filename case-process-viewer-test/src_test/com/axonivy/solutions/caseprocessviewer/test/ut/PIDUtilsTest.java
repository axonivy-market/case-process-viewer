package com.axonivy.solutions.caseprocessviewer.test.ut;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.axonivy.solutions.caseprocessviewer.core.util.PIDUtils;

import ch.ivyteam.ivy.environment.IvyTest;
import ch.ivyteam.ivy.process.model.value.PID;

@IvyTest
public class PIDUtilsTest extends BaseTest {

  @Test
  void test_equalsPID() {
    PID pIDSource = PID.of(TEST_PROCESS_RAW_PID);
    PID pIDTarget = PID.of(TEST_PROCESS_RAW_PID);
    assertThat(PIDUtils.equalsPID(pIDSource, pIDTarget)).isTrue();
  }

  @Test
  void test_getId() {
    PID pId = PID.of(TEST_PROCESS_RAW_PID, TEST_EXT_PID);
    assertThat(PIDUtils.getId(pId, false)).isEqualTo(TEST_PROCESS_RAW_PID + PID.SEPARATOR + TEST_EXT_PID);
    assertThat(PIDUtils.getId(pId, true)).isEqualTo(TEST_PROCESS_RAW_PID + PID.SEPARATOR + TEST_PROCESS_RAW_PID);
  }
}
