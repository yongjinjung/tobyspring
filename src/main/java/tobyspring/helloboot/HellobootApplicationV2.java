package tobyspring.helloboot;


import org.apache.catalina.LifecycleException;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebApplicationContext;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
//@Configuration
public class HellobootApplicationV2 {

    //@Bean
/*
    public HelloController helloController(HelloService helloService){
        return new HelloController(helloService);
    }
*/

    //@Bean
    public HelloService helloService(){
        return new SimpleHelloService();
    }
    public static void main(String[] args) throws LifecycleException {

        AnnotationConfigServletWebApplicationContext applicationContext = new AnnotationConfigServletWebApplicationContext(){
            @Override
            protected void onRefresh() {
                super.onRefresh();

                HttpServlet servlet = new DispatcherServlet(this);

                ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
                HellobootApplicationV2 hellApp = new HellobootApplicationV2();
                WebServer webServer = hellApp.addServlet(serverFactory, "dispatcherServlet", servlet);
                webServer.start();
            }
        };
        //applicationContext.registerBean(HelloController.class);
        applicationContext.registerBean(HellobootApplicationV2.class);
        applicationContext.refresh();


    }

    public WebServer addServlet(ServletWebServerFactory serverFactory, String servletName, HttpServlet servlet){

        return serverFactory.getWebServer(servletContext -> {
            servletContext.addServlet(servletName, servlet)
                    .addMapping("/*");
        });

    }

}
