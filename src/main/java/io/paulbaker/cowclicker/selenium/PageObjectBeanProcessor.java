package io.paulbaker.cowclicker.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * Created by paul on 9/28/15.
 */
@Component
public class PageObjectBeanProcessor implements BeanPostProcessor {

  @Autowired
  private WebDriver webDriver;

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
    if (bean.getClass().isAnnotationPresent(PageObject.class)) {
      PageFactory.initElements(webDriver, bean);
    }
    return bean;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    return bean;
  }
}
