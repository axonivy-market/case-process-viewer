package com.axonivy.solutions.caseprocessviewer.test.it;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

import com.axonivy.ivy.webtest.engine.EngineUrl;

public class WebBaseTest {
  protected static final String PROCESS_TEST_PATH = "/case-process-viewer-test/19A52DA5B971F827/start.ivp";

  protected void startProcessTest() {
    open(EngineUrl.createProcessUrl(PROCESS_TEST_PATH));
  }

  protected void openTasksPage() {
    open(EngineUrl.base() + "/dev-workflow-ui/faces/tasks.xhtml");
  }

  protected void startFirstTask() {
    $("button#tasksForm\\:tasks\\:0\\:taskActionsBtn").shouldBe(enabled).click();
    $("a#tasksForm\\:tasks\\:0\\:startTaskBtn").shouldBe(enabled).click();
  }
}
