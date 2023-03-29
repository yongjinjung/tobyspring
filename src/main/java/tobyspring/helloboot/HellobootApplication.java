package tobyspring.helloboot;


import org.apache.catalina.LifecycleException;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class HellobootApplication {
    public static void main(String[] args) throws LifecycleException {

        HelloControllerV2 helloCtr = new HelloControllerV2();

        HttpServlet servlet = new HttpServlet() {
                                                    @Override
                                                    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                                                        //인증, 보안, 다국어, 공통 기능
                                                        if(req.getRequestURI().equals("/hello")){

                                                            String name = req.getParameter("name");
                                                            String result = helloCtr.hello(name);
                                                            resp.setStatus(HttpStatus.OK.value());
                                                            resp.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE + ";charset=" + StandardCharsets.UTF_8);
                                                            resp.getWriter().println(result);
                                                        }
                                                        else if(req.getRequestURI().equals("/user")){

                                                        }
                                                        else{
                                                            resp.setStatus(HttpStatus.NOT_FOUND.value());
                                                        }
                                                    }
                                                };

        ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();


        HellobootApplication hellApp = new HellobootApplication();
        WebServer webServer = hellApp.addServlet(serverFactory, "hello", servlet);
        webServer.start();
    }

    public WebServer addServlet(ServletWebServerFactory serverFactory, String servletName, HttpServlet servlet){

        return serverFactory.getWebServer(servletContext -> {
            servletContext.addServlet(servletName, servlet)
                    .addMapping("/*");
        });

    }

}
