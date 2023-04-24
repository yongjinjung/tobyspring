package tobyspring.helloboot;


import org.apache.catalina.LifecycleException;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebApplicationContext;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.http.HttpServlet;

@Configuration
@ComponentScan
public class HellobootApplicationV3 {

    @Bean
    public ServletWebServerFactory servletWebServerFactory(){

        return new TomcatServletWebServerFactory();
    }

    @Bean
    public DispatcherServlet dispatcherServlet(){
        return new DispatcherServlet();
    }

    public static void main(String[] args) throws LifecycleException {

        AnnotationConfigServletWebApplicationContext applicationContext = new AnnotationConfigServletWebApplicationContext(){
            @Override
            protected void onRefresh() {
                super.onRefresh();

                HttpServlet servlet = new DispatcherServlet(this);

                ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
                HellobootApplicationV3 hellApp = new HellobootApplicationV3();
                WebServer webServer = hellApp.addServlet(serverFactory, "dispatcherServlet", servlet);
                webServer.start();
            }
        };
        //applicationContext.registerBean(HelloController.class);
        applicationContext.registerBean(HellobootApplicationV3.class);
        applicationContext.refresh();


    }

    public WebServer addServlet(ServletWebServerFactory serverFactory, String servletName, HttpServlet servlet){

        return serverFactory.getWebServer(servletContext -> {
            servletContext.addServlet(servletName, servlet)
                    .addMapping("/*");
        });

    }

}
