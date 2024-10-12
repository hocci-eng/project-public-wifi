<%@ page import="project_wifi.WifiApiFetcher"%>
<%@ page import="project_wifi.WifiDataParser"%>
<%@ page import="project_wifi.WifiSpot"%>
<%@ page import="project_wifi.WifiInfoResponse"%>
<%@ page import="project_wifi.ConnectDB"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>와이파이 정보 구하기</title>
</head>
<body>
	<%
		WifiDataParser parser = new WifiDataParser();

		String userLatParam = request.getParameter("userLat");
		String userLntParam = request.getParameter("userLnt");
     	
     	// 요청한 api 데이터가 db에 저장되어 있는지 확인
        if (ConnectDB.isExistData("wifi_spots") > 0) {
            int insertResult = ConnectDB.insertInputHistory(userLatParam, userLntParam);
            if (insertResult > 0) {
            	// 데이터가 저장되어있으면 사용자가 입력한 위치 정보를 history 테이블에 저장하고 list.jsp로 리다이렉션
            	response.sendRedirect("list.jsp?userLat=" + userLatParam + "&userLnt=" + userLntParam);
            	return;
            }
            
            System.err.println("history 테이블에 데이터 저장 실패");
            return;
        }
		
        // API 자료 요청
        int savedCount = 0;
		if (userLatParam != null && userLntParam != null) {
			savedCount = ConnectDB.insertWifiData(parser.requestApi()); // db에 저장된 데이터 갯수를 return
            
        } else {
            out.println("<p class='text-danger'>위도와 경도를 입력해 주세요.</p>");
        }
	%>
		<div class="alert alert-info" style="text-align: center; margin-top: 20px; font-size: 1.5em; font-weight: bold;">
    <%
    	// dbdㅔ 저장된 데이터 갯수를 출력
        if (savedCount != 0) {
            out.println(savedCount + "개의 WIFI 정보를 정상적으로 저장하였습니다.");
        } else {
            out.println("와이파이 정보가 없습니다.");
        }
    %>
	    	<div class="btn-container" style="text-align: center; margin-top: 20px;">
				<a href="index.jsp" class="btn btn-primary">홈으로 돌아가기</a>
			</div> 
		</div>       
</body>
</html>