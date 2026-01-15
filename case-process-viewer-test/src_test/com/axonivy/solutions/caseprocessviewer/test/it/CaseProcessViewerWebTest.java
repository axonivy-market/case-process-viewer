package com.axonivy.solutions.caseprocessviewer.test.it;

import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.switchTo;

import org.junit.jupiter.api.Test;

import com.axonivy.ivy.webtest.IvyWebTest;

@IvyWebTest
public class CaseProcessViewerWebTest extends WebBaseTest {

  private static final String TASK_START_ELEMENT_ID = "#sprotty_19A52DA5B971F827-f0";
  private static final String USER_TASK_ELEMENT_ID = "#sprotty_19A52DA5B971F827-f3";
  private static final String PASSED_CSS_CLASS = "passed";
  private static final String ACTIVE_CSS_CLASS = "active";

  @Test
  void testCaseProcessViewerOnProcessTest() {
    openLoginPage();
    loginWithUser("Developer", "Developer");
    startProcessTest();
    openTasksPage();
    startFirstTask();
    $("span#iFrameForm\\:frameTaskName").shouldBe(visible).shouldHave(text("Task Test"));
    $("#iFrame").shouldBe(visible);
    switchTo().frame("iFrame");
    $("#case-process-viewer").shouldBe(visible);
    switchTo().frame("case-process-viewer");
    $(TASK_START_ELEMENT_ID).shouldBe(visible).shouldHave(cssClass(PASSED_CSS_CLASS));
    $(USER_TASK_ELEMENT_ID).shouldBe(visible).shouldHave(cssClass(ACTIVE_CSS_CLASS));
  }
}
