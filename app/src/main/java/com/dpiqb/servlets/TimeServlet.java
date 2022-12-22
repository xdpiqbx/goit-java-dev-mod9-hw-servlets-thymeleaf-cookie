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
    ZoneId zoneId = ZoneId.of("Z").normalized();
    String currentUtc = ZonedDateTime.now(zoneId).format(DateTimeFormatter.ofPattern("Дата: yyyy.MM.dd, час: HH:mm:ss"));

    resp.getWriter().write("<h4>"+currentUtc+"</h4>");
    resp.getWriter().close();
  }
}
