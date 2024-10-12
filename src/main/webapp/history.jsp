<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="project_wifi.History"%>
<%@ page import="project_wifi.ConnectDB"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>history</title>
	<!-- Bootstrap CSS -->
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
	<style>
		thead th {
			text-align: center; /* 테이블 헤더를 가운데 정렬 */
		}
	</style>	
	<script>
		// 사용자가 데이터를 삭제할건지 확인
		function deleteItem(latitude, longitude) {
			if (confirm("정말로 해당 데이터를 삭제하시겠습니까?")) {
				window.location.href = "deleteData.jsp?latitude=" + latitude + "&longitude=" + longitude;
			}
		}
	</script>
</head>
<body>
	<%  
		// history 테이블에 데이터가 존재하는지 확인
		if (ConnectDB.isExistData("history") <= 0) {
			// 데이터가 존재하지 않으면 테이블의 id 컬럼에 대한 auto_increment값 1로 수정
			int alterResult = ConnectDB.alterTable("history");
		}
		
		// history 테이블에 대한 select 정보를 리스트에 저장
		List<History> historyData = ConnectDB.selectHistoryData();
	%>
	<h1 class="text-center"> history </h1>
	<div>
		<table class ="table table-bordered table-striped">
			<thead class="thead-dark">
				<tr>
					<th>ID</th>
					<th>X좌표</th>
					<th>Y좌표</th>
					<th>조회 일자</th>
					<th>비고</th>
				</tr>
			</thead>
			<tbody>
				<%
					int idCounter = 1;
					for (History history : historyData) {
						String latitude = history.getLatitude(); // 위도
						String longitude = history.getLongitude(); // 경도
						String time = history.getSearchTime(); // 조회 시간
				%>
					<tr>
						<td><%=idCounter++%></td>
						<td><%=latitude%></td>
						<td><%=longitude%></td>
						<td><%=time%></td>
						<td style="display: flex; justify-content: center;">
            				<button class="btn btn-danger" onclick="deleteItem('<%=latitude%>', '<%=longitude%>')">삭제</button>
        				</td>
					</tr>
				<%
					}
				%>
			</tbody>
		</table>
	</div>
	<div style="text-align: center;">
		<a href="index.jsp" class="btn btn-primary">홈으로 이동</a>
	</div>
</body>
</html>