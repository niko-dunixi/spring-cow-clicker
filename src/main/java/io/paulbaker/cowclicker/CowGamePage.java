package io.paulbaker.cowclicker;

import io.paulbaker.cowclicker.selenium.PageObject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by paul on 9/28/15.
 */
@PageObject
public class CowGamePage {

  @Autowired
  private CowWinPage cowWinPage;

  @FindBy(id = "animal")
  private WebElement animal;

  public CowWinPage clickCow() {
    animal.click();
    return cowWinPage;
  }

}
