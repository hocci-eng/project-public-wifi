<%@ page import="project_wifi.WifiSpot"%>
<%@ page import="project_wifi.ConnectDB"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Collections"%>
<%@ page import="java.util.Comparator"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>서울시 공공 와이파이 정보</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style type="text/css">
    	body {
            background-color: #f8f9fa;
            color: #343a40;
        }
        h1 {
            text-align: center;
            margin: 20px 0;
            font-size: 2.5em;
        }
        table {
            margin: 0 auto;
            width: 90%;
            border-collapse: collapse;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
            overflow: hidden;
        }
        td {
        	padding: 12px 15px;
            text-align: left;

        }
        th{
        	padding: 5px;
        	text-align: center;
        	vertical-align: middle;
        	height: 15px; /* 고정 높이 설정 */
            line-height: 15px; /* 수직 중앙 정렬을 위한 줄 높이 */
            border: 1px solid #dee2e6; /* 경계선 추가 */
            
        }
        thead {
            background-color: #343a40;
            color: #ffffff;
        }
        tbody tr {
            background-color: #ffffff;
            transition: background-color 0.3s;
        }
        tbody tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        tbody tr:hover {
            background-color: #e9ecef;
        }
        .btn-container {
            text-align: center;
            margin-top: 20px;
        }
        .btn {
            margin: 5px;
        }
    </style>
</head>
<body>
    <h1>서울시 공공 와이파이 정보</h1>
    <%
    
        double userLat = Double.parseDouble(request.getParameter("userLat"));
        double userLnt = Double.parseDouble(request.getParameter("userLnt"));

        List<WifiSpot> wifiSpots = ConnectDB.selectWifiData(userLat, userLnt);
	%>
	
    <table class="table table-bordered table-striped">
        <thead class="thead-dark">
            <tr>
                <th>거리 (KM)</th>
                <th>관리번호</th>
                <th>자치구</th>
                <th>와이파이명</th>
                <th>도로명 주소</th>
                <th>상세주소</th>
                <th>설치위치(층)</th>
                <th>설치유형</th>
                <th>설치기관</th>
                <th>서비스구분</th>
                <th>망종류</th>
                <th>설치년도</th>
                <th>실내외 구분</th>
                <th>WIFI접속환경</th>
                <th>X좌표</th>
                <th>Y좌표</th>
                <th>작업일자</th>
            </tr>
        </thead>
        <tbody>
            <%
                for (WifiSpot spot : wifiSpots) {
                    double distance = spot.getDISTANCE() / 1000; // km로 변환
                    String manageNo = spot.getX_SWIFI_MGR_NO();
                    String district = spot.getX_SWIFI_WRDOFC();
                    String mainName = spot.getX_SWIFI_MAIN_NM();
                    String address1 = spot.getX_SWIFI_ADRES1();
                    String address2 = spot.getX_SWIFI_ADRES2();
                    String installFloor = spot.getX_SWIFI_INSTL_FLOOR();
                    String installType = spot.getX_SWIFI_INSTL_TY();
                    String installAgency = spot.getX_SWIFI_INSTL_MBY();
                    String serviceType = spot.getX_SWIFI_SVC_SE();
                    String networkInfo = spot.getX_SWIFI_CMCWR();
                    String installYear = spot.getX_SWIFI_CNSTC_YEAR();
                    String inOutDoor = spot.getX_SWIFI_INOUT_DOOR();
                    String remarks = spot.getX_SWIFI_REMARS3();
                    String latitude = spot.getLAT();
                    String longitude = spot.getLNT();
                    String workDatetime = spot.getWORK_DTTM();
            %>
                    <tr>
                        <td><%= String.format("%.4f", distance) %></td>
                        <td><%= manageNo %></td>
                        <td><%= district %></td>
                        <td><%= mainName %></td>
                        <td><%= address1 %></td>
                        <td><%= address2 %></td>
                        <td><%= installFloor %></td>
                        <td><%= installType %></td>
                        <td><%= installAgency %></td>
                        <td><%= serviceType %></td>
                        <td><%= networkInfo %></td>
                        <td><%= installYear %></td>
                        <td><%= inOutDoor %></td>
                        <td><%= remarks %></td>
                        <td><%= latitude %></td>
                        <td><%= longitude %></td>
                        <td><%= workDatetime %></td>
                    </tr>
            <%
                }
            %>
        </tbody>
    </table>
    <div class="btn-container" style="text-align: center; margin-top: 20px;">
		<a href="index.jsp"class="btn btn-primary">홈으로 이동</a>
        <a href="history.jsp" class="btn btn-primary">히스토리로 이동</a>
    </div>
</body>
</html>