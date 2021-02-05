<%@page import="org.springframework.web.context.request.RequestScope"%>
<%@ page language="java" contentType="image/jpeg" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import = "org.jfree.chart.*" %>
<%@ page import = "tech3g.fk.app.statis.actions.Tech3gStatisSpbGraphBean" %>

<%

Tech3gStatisSpbGraphBean viewBean = (Tech3gStatisSpbGraphBean)request.getAttribute("viewBean");
ChartUtilities.writeChartAsPNG(response.getOutputStream(), viewBean.getChat() , 600, 500);

%>

