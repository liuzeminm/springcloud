//package com.ctrip.framework.apollo.common.customize;
//
//import org.apache.catalina.connector.Connector;
//import org.apache.coyote.ProtocolHandler;
//import org.apache.coyote.http11.Http11NioProtocol;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
//import org.springframework.core.env.Environment;
//import org.springframework.stereotype.Component;
//
///**
// * @author Jason Song(song_s@ctrip.com)
// */
//@Component
//public class TomcatContainerCustomizer extends TomcatServletWebServerFactory {
//  private static final Logger logger = LoggerFactory.getLogger(TomcatContainerCustomizer.class);
//  private static final String TOMCAT_ACCEPTOR_COUNT = "server.tomcat.accept-count";
//
//  @Autowired
//  private Environment environment;
//  @Override
//  public void customizeConnector(Connector connector) {
//    ProtocolHandler handler = connector.getProtocolHandler();
//    if (handler instanceof Http11NioProtocol) {
//      Http11NioProtocol http = (Http11NioProtocol) handler;
//      int acceptCount = Integer.parseInt(environment.getProperty(TOMCAT_ACCEPTOR_COUNT));
//      http.setBacklog(acceptCount);
//      logger.info("Setting tomcat accept count to {}", acceptCount);
//    }
//  }
//}
