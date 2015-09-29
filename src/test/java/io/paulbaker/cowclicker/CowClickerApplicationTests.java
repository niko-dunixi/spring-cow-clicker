package io.paulbaker.cowclicker;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CowClickerApplication.class)
public class CowClickerApplicationTests {

  @Autowired
  private CowHomePage cowHomePage;

  @Test
  public void contextLoads() {
    CowGamePage cowGamePage = cowHomePage.clickStartGameButton();
    do {
      cowGamePage.hoverCow();
      CowWinPage cowWinPage = cowGamePage.clickCow();
      cowGamePage = cowWinPage.clickFindAnother();
    } while (true);

//    try {
//      cowHomePage.wait(1000);
//    } catch (InterruptedException e) {
//      e.printStackTrace();
//    }
  }

}
