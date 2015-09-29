package io.paulbaker.cowclicker;

import io.paulbaker.cowclicker.selenium.PageObject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by paul on 9/28/15.
 */
@PageObject
public class CowWinPage {

  @Autowired
  private CowGamePage cowGamePage;

  @FindBy(xpath = "//div[@id='modal-congratulations']/button")
  private WebElement findAnotherButton;

  public CowGamePage clickFindAnother() {
    findAnotherButton.click();
    return cowGamePage;
  }

}
