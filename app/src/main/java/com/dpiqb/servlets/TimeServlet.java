package com.dpiqb.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;

@WebServlet(value = "/time")
public class TimeServlet extends HttpServlet {
  // http://localhost:8080/time
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

    resp.getWriter().write("<h4>" + currentUtc + "</h4>");
    resp.getWriter().close();
  }
}
