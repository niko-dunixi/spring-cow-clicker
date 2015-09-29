package io.paulbaker.cowclicker;

import io.paulbaker.cowclicker.selenium.PageObject;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * Created by paul on 9/28/15.
 */
@PageObject
public class CowGamePage {

  private Logger logger = Logger.getLogger(this.getClass());

  @Autowired
  private CowWinPage cowWinPage;

  @Autowired
  private WebDriver webDriver;

  public void hoverCow() {
    if (!(webDriver instanceof JavascriptExecutor)) {
      throw new IllegalStateException("Can't execute javascript to find cow!");
    }
    JavascriptExecutor javascriptExecutor = (JavascriptExecutor) this.webDriver;
    String javascript = "return find.animal.pos;";
    Map<String, Long> position = (Map<String, Long>) javascriptExecutor.executeScript(javascript);
    logger.info("Moving mouse to " + position.toString());
    Actions actions = new Actions(webDriver);
    actions.moveToElement(webDriver.findElement(By.tagName("body")),
      position.get("x").intValue(), position.get("y").intValue());
    actions.perform();
  }

  public CowWinPage clickCow() {
    Actions actions = new Actions(webDriver);
    actions.click();
    actions.perform();
    return cowWinPage;
  }

}
