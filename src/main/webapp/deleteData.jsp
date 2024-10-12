<%@ page import="project_wifi.ConnectDB" %>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.DriverManager"%>
<%@ page import="java.sql.PreparedStatement"%>
<%@ page import="java.sql.SQLException"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// history.jsp에서 전달 받은 파라미터("X좌표", "Y좌표")에 해당하는 데이터 삭제
	int deleteResult = ConnectDB.deleteHistoryItem(request.getParameter("latitude"), request.getParameter("longitude"));

	if (deleteResult <= 0) {
		out.print("삭제할 데이터가 없습니다.");
	}
	
	// 삭제가 완료되면 history.jsp로 리다이렉션 
	response.sendRedirect("history.jsp");
%>