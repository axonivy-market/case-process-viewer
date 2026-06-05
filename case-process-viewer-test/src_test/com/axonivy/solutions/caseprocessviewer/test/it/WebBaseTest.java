package com.axonivy.solutions.caseprocessviewer.test.it;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;

import java.time.Duration;

import com.axonivy.ivy.webtest.engine.EngineUrl;

public class WebBaseTest {
  protected static final String PROCESS_TEST_PATH = "/case-process-viewer-test/19A52DA5B971F827/start.ivp";
  protected static final String PROCESS_DEMO_PATH = "/case-process-viewer-demo/199DB82E409AE3B0/start.ivp";

  protected void startProcessTest() {
    open(EngineUrl.createProcessUrl(PROCESS_TEST_PATH));
  }

  protected void startProcessDemo() {
    open(EngineUrl.createProcessUrl(PROCESS_DEMO_PATH));
  }

  protected void openTasksPage() {
    open(EngineUrl.base() + "/dev-workflow-ui/faces/tasks.xhtml");
  }

  protected void openLoginPage() {
    open(EngineUrl.base() + "/dev-workflow-ui/faces/login.xhtml");
  }

  protected void loginWithUser(String username, String password) {
    openLoginPage();
    $("#loginForm\\:userName").shouldBe(visible).setValue(username);
    $("#loginForm\\:password").shouldBe(visible).setValue(password);
    $("#loginForm\\:login").shouldBe(enabled).click();
  }

  protected void startTask(String taskName) {
    sleep(2000);
    openTasksPage();
    $("#tasksForm\\:tasks_data").$$("tr td span").findBy(exactText(taskName))
        .shouldBe(visible, Duration.ofSeconds(5)).shouldBe(enabled, Duration.ofSeconds(2)).click();
    $("#actionMenuForm\\:taskStartBtn").shouldBe(enabled, Duration.ofSeconds(2))
        .shouldBe(enabled, Duration.ofSeconds(2)).click();
  }
}
