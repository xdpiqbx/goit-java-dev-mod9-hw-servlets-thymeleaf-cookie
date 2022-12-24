package com.dpiqb.servlets;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.servlet.JavaxServletWebApplication;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@WebServlet(value = "/time")
public class TimeServlet extends HttpServlet {
  // http://localhost:8080/time
  private TemplateEngine engine;
  public void init() throws ServletException {
    ServletContext servletContext = getServletContext();
    JavaxServletWebApplication application = JavaxServletWebApplication.buildApplication(servletContext);
    WebApplicationTemplateResolver resolver = new WebApplicationTemplateResolver(application);

    resolver.setPrefix("/WEB-INF/templates/");
    resolver.setSuffix(".html");
    resolver.setTemplateMode("HTML5");
    resolver.setCacheable(false);

    engine = new TemplateEngine();
    resolver.setOrder(engine.getTemplateResolvers().size());
    engine.addTemplateResolver(resolver);
  }
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.setContentType("text/html; charset=utf-8");
    req.setCharacterEncoding("UTF-8");

    ZoneId zoneId = null;

    String timezone = req.getParameter("timezone");

    if(timezone == null || timezone.equals("")){
      zoneId = ZoneId.of("Z");
    }else{
      zoneId = ZoneId.of(timezone.replace(' ', '+'));
    }

    String currentUtc = ZonedDateTime.now(zoneId)
      .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss O"))
      .replace("GMT", "UTC");

    Context context = new Context(
      req.getLocale(),
      Map.of("time", currentUtc)
    );

    engine.process("time", context, resp.getWriter());
    resp.getWriter().close();
  }
}
