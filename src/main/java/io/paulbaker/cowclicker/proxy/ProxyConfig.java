package io.paulbaker.cowclicker.proxy;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import org.apache.log4j.Logger;
import org.littleshoot.proxy.HttpFilters;
import org.littleshoot.proxy.HttpFiltersAdapter;
import org.littleshoot.proxy.HttpFiltersSource;
import org.littleshoot.proxy.HttpFiltersSourceAdapter;
import org.littleshoot.proxy.HttpProxyServer;
import org.littleshoot.proxy.impl.DefaultHttpProxyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.net.BindException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;


/**
 * Created by paul on 9/28/15.
 */
@Configuration
public class ProxyConfig {

  private Logger logger = Logger.getLogger(this.getClass());

  @Autowired
  private Random random;

  @Value("${cow.host}")
  private String gameHost;

  private Set<String> whitelist;

  public ProxyConfig() {
    whitelist = new HashSet<>();
    whitelist.add("http://ocsp.digicert.com");
    whitelist.add("http://fonts.googleapis.com");
//    whitelist.add("http://s7.addthis.com");
    whitelist.add("api.mongolab.com");
    whitelist.add("http://fonts.gstatic.com");
//    whitelist.add("http://www.google-analytics.com");
  }

  @PostConstruct
  public void postConstruct() {
    whitelist.add(gameHost);
  }

  @Bean
  public HttpFiltersSource httpFiltersSource() {
    return new HttpFiltersSourceAdapter() {
      @Override
      public HttpFilters filterRequest(HttpRequest originalRequest, ChannelHandlerContext ctx) {
        String uri = originalRequest.getUri();
        if (whitelist.stream().anyMatch(uri::startsWith)) {
          logger.info(String.format("ALLOWED: %s %s", originalRequest.getMethod(), uri));
          return new HttpFiltersAdapter(originalRequest, ctx);
        } else {
          logger.info(String.format("BLOCKED: %s %s", originalRequest.getMethod(), uri));
          return new DummyFilterAdapter();
        }
      }
    };
  }

  @Bean(destroyMethod = "stop")
  public HttpProxyServer httpProxyServer(HttpFiltersSource httpFiltersSource) {
    HttpProxyServer proxyServer = null;
    do {
      try {
        int portNumber = random.nextInt(1000) + 8080;
        proxyServer = DefaultHttpProxyServer.bootstrap()
          .withPort(portNumber)
          .withFiltersSource(httpFiltersSource)
          .start();
      } catch (Exception e) {
        // The BindException is swallowed somewhere, which means we cannot explicitly catch it.
        // We can only catch the general exception, and then check if it is a BindException.
        if (e instanceof BindException) {
          // If it is a bind exception we just ignore it and try to get a new random port.
          logger.error("Port was already taken. Reattempting.");
        } else {
          // If it is not a BindException, rethrow it.
          throw e;
        }
      }
    } while (proxyServer == null);
    return proxyServer;
  }

  private class DummyFilterAdapter extends HttpFiltersAdapter {

    public DummyFilterAdapter() {
      super(null, null);
    }

    /**
     * This is the only method that needs to be overwritten. It returns nothing.
     *
     * @param httpObject
     * @return
     */
    @Override
    public HttpResponse clientToProxyRequest(HttpObject httpObject) {
      return new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
    }

    @Override
    public HttpResponse proxyToServerRequest(HttpObject httpObject) {
      return null;
    }

    @Override
    public HttpObject proxyToClientResponse(HttpObject httpObject) {
      return null;
    }
  }
}
