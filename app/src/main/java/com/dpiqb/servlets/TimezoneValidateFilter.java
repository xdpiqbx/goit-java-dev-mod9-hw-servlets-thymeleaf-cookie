package com.dpiqb.servlets;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZoneId;

@WebFilter(value = "/time")
public class TimezoneValidateFilter extends HttpFilter {
  @Override
  protected void doFilter(
    HttpServletRequest req, HttpServletResponse res,
    FilterChain chain) throws IOException, ServletException {

    String timezone = req.getParameter("timezone");

    if(timezone == null || timezone.equals("")){
      chain.doFilter(req, res);
    }

    try{
      ZoneId.of(timezone.replace(' ', '+'));
    } catch (Exception e) {
      res.setStatus(400);
      res.setContentType("text/html");
      res.getWriter().write("<h4>Invalid timezone</h4>");
      res.getWriter().close();
    }

    chain.doFilter(req, res);
  }
}
