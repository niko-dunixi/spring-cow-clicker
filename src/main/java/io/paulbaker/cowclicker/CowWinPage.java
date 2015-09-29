package io.paulbaker.cowclicker;

import io.paulbaker.cowclicker.selenium.PageObject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by paul on 9/28/15.
 */
@PageObject
public class CowWinPage {

  @Autowired
  private CowGamePage cowGamePage;

  @FindBy(tagName = "select")
  private Select select;

  @FindBy(linkText = "Find Another One")
  private WebElement findAotherButton;

  public void selectMostAnimal() {
    int size = select.getOptions().size();
    if (size > 1)
      select.selectByIndex(size - 1);
  }

  public CowGamePage clickFindAnother() {
    findAotherButton.click();
    return cowGamePage;
  }

}
