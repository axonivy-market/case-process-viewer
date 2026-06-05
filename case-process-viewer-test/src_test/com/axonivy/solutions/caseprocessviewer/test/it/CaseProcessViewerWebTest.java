package com.axonivy.solutions.caseprocessviewer.test.it;

import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.switchTo;

import java.time.Duration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.axonivy.ivy.webtest.IvyWebTest;
import com.codeborne.selenide.ScrollIntoViewOptions;
import com.codeborne.selenide.ScrollIntoViewOptions.Block;

@IvyWebTest
public class CaseProcessViewerWebTest extends WebBaseTest {

  /** Elements in Test Process **/
  private static final String TASK_START_ELEMENT_ID = "#sprotty_19A52DA5B971F827-f0";
  private static final String USER_TASK_ELEMENT_ID = "#sprotty_19A52DA5B971F827-f3";

  /** Elements in Demo Process **/
  private static final String DEMO_SUMMARY_USER_TASK_ELEMENT_ID = "#sprotty_199DB82E409AE3B0-f14";
  private static final String DEMO_FIRST_TASK_START_ELEMENT_ID = "#sprotty_199DB82E409AE3B0-f0";
  private static final String DEMO_FIRST_USER_DIALOG_ELEMENT_ID = "#sprotty_199DB82E409AE3B0-S10";
  private static final String DEMO_FIRST_CIRCLE_IN_FIRST_PATH = "#sprotty_199DB82E409AE3B0-f7 circle";
  private static final String DEMO_PRODUCT_NAME_INPUT_ID = "#form\\:product-name";
  private static final String DEMO_PRODUCT_PRICE_INPUT_ID = "#form\\:product-price_input";
  private static final String DEMO_PRODUCT_QUANTITY_INPUT_ID = "#form\\:product-quantity_input";
  private static final String DEMO_SUBMIT_ELEMENT_ID = "#form\\:submit";
  private static final String DEMO_PROCEED_ELEMENT_ID = "#form\\:proceed";
  private static final String DEMO_PAYMENT_ELEMENT_ID = "#form\\:payment";
  private static final String DEMO_CLOSE_ELEMENT_ID = "#form\\:close";

  private static final String FIT_TO_SCREEN_BUTTON_ID = "button#fitToScreenBtn";

  private static final String PASSED_CSS_CLASS = "passed";
  private static final String ACTIVE_CSS_CLASS = "active";
  private static final String TEST_DATA = "1";

  @BeforeEach
  void setup() {
    loginWithUser("Developer", "Developer");
  }

  @Test
  void testCaseProcessViewerOnTestProcess() {
    startProcessTest();
    startTaskAfterSeconds("Task Test", 5);
    switchToCaseProcessViewerFrame();
    $(TASK_START_ELEMENT_ID).shouldBe(visible).shouldHave(cssClass(PASSED_CSS_CLASS));
    $(USER_TASK_ELEMENT_ID).shouldBe(visible).shouldHave(cssClass(ACTIVE_CSS_CLASS));
  }

  @Test
  void testCaseProcessViewerOnDemoProcess() {
    startProcessDemo();
    startTaskAfterSeconds("Purchase Request", 5);
    switchToCaseProcessViewerFrame();
    $(DEMO_FIRST_TASK_START_ELEMENT_ID).shouldBe(visible, Duration.ofSeconds(2)).shouldHave(cssClass(PASSED_CSS_CLASS));
    $(DEMO_FIRST_CIRCLE_IN_FIRST_PATH).shouldBe(visible);
    $(DEMO_FIRST_USER_DIALOG_ELEMENT_ID).shouldBe(visible).shouldHave(cssClass(ACTIVE_CSS_CLASS));
    switchTo().parentFrame();
    $(DEMO_PRODUCT_NAME_INPUT_ID).shouldBe(visible, Duration.ofSeconds(2)).shouldBe(enabled).type(TEST_DATA);
    $(DEMO_PRODUCT_PRICE_INPUT_ID).shouldBe(visible).shouldBe(enabled).type(TEST_DATA);
    $(DEMO_PRODUCT_QUANTITY_INPUT_ID).shouldBe(visible).shouldBe(enabled).type(TEST_DATA);
    $(DEMO_SUBMIT_ELEMENT_ID).shouldBe(visible).shouldBe(enabled).click();
    $(DEMO_PROCEED_ELEMENT_ID).shouldBe(visible, Duration.ofSeconds(2)).shouldBe(enabled).click();
    startTaskAfterSeconds("Purchasing Department Approval", 5);
    $(DEMO_PROCEED_ELEMENT_ID).shouldBe(visible, Duration.ofSeconds(2)).shouldBe(enabled).click();
    startTaskAfterSeconds("Accounting Approval", 5);
    $(DEMO_PROCEED_ELEMENT_ID).shouldBe(visible, Duration.ofSeconds(2)).shouldBe(enabled).click();
    startTaskAfterSeconds("Payment", 10);
    $(DEMO_PAYMENT_ELEMENT_ID).shouldBe(visible, Duration.ofSeconds(2)).shouldBe(enabled).click();
    startTaskAfterSeconds("Summary", 5);
    switchToCaseProcessViewerFrame();
    $(FIT_TO_SCREEN_BUTTON_ID).shouldBe(visible, Duration.ofSeconds(2)).shouldBe(enabled).click();
    $(DEMO_FIRST_USER_DIALOG_ELEMENT_ID).shouldBe(visible, Duration.ofSeconds(2))
        .shouldNotHave(cssClass(ACTIVE_CSS_CLASS));
    $(DEMO_SUMMARY_USER_TASK_ELEMENT_ID).shouldBe(visible).shouldHave(cssClass(ACTIVE_CSS_CLASS));
    switchTo().parentFrame();
    $(DEMO_CLOSE_ELEMENT_ID).shouldBe(visible, Duration.ofSeconds(2)).shouldBe(enabled).click();
  }

  @Override
  protected void startTaskAfterSeconds(String taskName, int seconds) {
    super.startTaskAfterSeconds(taskName, seconds);
    $("span#iFrameForm\\:frameTaskName").shouldBe(visible).shouldHave(text(taskName));
    switchToTaskFrame();
    $("#content").shouldBe(visible, Duration.ofSeconds(2))
        .scrollIntoView(ScrollIntoViewOptions.instant().block(Block.end));
  }

  private void switchToTaskFrame() {
    $("#iFrame").shouldBe(visible);
    switchTo().frame("iFrame");
  }

  private void switchToCaseProcessViewerFrame() {
    $("#case-process-viewer").shouldBe(visible);
    switchTo().frame("case-process-viewer");
  }

}
