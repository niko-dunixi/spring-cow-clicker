package io.paulbaker.cowclicker;

import io.paulbaker.cowclicker.selenium.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;

/**
 * Created by paul on 9/28/15.
 */
@PageObject
public class CowHomePage {

  @Autowired
  private WebDriver webDriver;

  @Value("${cow.host}")
  private String hostUrl;

  @FindBy(xpath = "//div[@id='loader']/button")
  private WebElement startGameButton;

  @Autowired
  private CowGamePage cowGamePage;

  @PostConstruct
  public void postConstruct() {
    webDriver.get(hostUrl);
  }

  public CowGamePage clickStartGameButton() {
    startGameButton.click();
    return cowGamePage;
  }
}
