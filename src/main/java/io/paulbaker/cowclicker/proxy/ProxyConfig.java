package io.paulbaker.cowclicker.proxy;

import org.apache.log4j.Logger;
import org.littleshoot.proxy.HttpProxyServer;
import org.littleshoot.proxy.impl.DefaultHttpProxyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.BindException;
import java.util.Random;


/**
 * Created by paul on 9/28/15.
 */
@Configuration
public class ProxyConfig {

  private Logger logger = Logger.getLogger(this.getClass());

  @Autowired
  private Random random;

  @Bean(destroyMethod = "stop")
  public HttpProxyServer httpProxyServer() {
    HttpProxyServer proxyServer = null;
    do {
      try {
        proxyServer = DefaultHttpProxyServer.bootstrap()
          .withPort(random.nextInt(1000) + 8080)
          .start();
      } catch (Exception e) {
        // The BindException is swallowed somewhere, which means we cannot explicitly catch it.
        // We can only catch the general exception, and then check if it is a BindException.
        if (e instanceof BindException) {
          // If it is a bind exception we just ignore it and try to get a new random port.
        } else {
          // If it is not a BindException, rethrow it.
          throw e;
        }
      }
    } while (proxyServer == null);
    return proxyServer;
  }
}
