package io.paulbaker.cowclicker.selenium;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpObject;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.littleshoot.proxy.HttpFilters;
import org.littleshoot.proxy.HttpFiltersAdapter;
import org.littleshoot.proxy.HttpFiltersSourceAdapter;
import org.littleshoot.proxy.HttpProxyServer;
import org.littleshoot.proxy.impl.DefaultHttpProxyServer;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import java.net.BindException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by paul on 9/28/15.
 */
@Configuration
public class SeleniumConfig {

  @Autowired
  private ApplicationContext applicationContext;

  @Autowired
  private ResourceLoader resourceLoader;

  @Autowired
  private HttpProxyServer proxyServer;

  @Bean
  public DesiredCapabilities capabilities() {
    Proxy proxy = new Proxy();
    String httpProxy = proxyServer.getListenAddress().getHostName() + ":" + proxyServer.getListenAddress().getPort();
    proxy.setHttpProxy(httpProxy);
    proxy.setSslProxy(httpProxy);
    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities = DesiredCapabilities.firefox();
    capabilities.setCapability(CapabilityType.PROXY, proxy);
    return capabilities;
  }

  @Bean
  public WebDriver webDriver(DesiredCapabilities capabilities) {
    WebDriver webDriver = new FirefoxDriver(capabilities);
    webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    EventFiringWebDriver eventFiringWebDriver = new EventFiringWebDriver(webDriver);
    applicationContext.getBeansOfType(WebDriverEventListener.class).values().forEach(eventFiringWebDriver::register);
    return eventFiringWebDriver;
  }

}
