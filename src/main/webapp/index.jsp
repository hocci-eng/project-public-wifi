<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>서울시 공공 와이파이 정보</title>
	<!-- Bootstrap CSS -->
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
	<style>
		body {
			background-color: #f8f9fa;
			color: #343a40;
		}
	
		h1,h4 {
			text-align: center;
			margin: 20px 0;
		}
		
		.form-container {
			max-width: 800px;
			margin: 0 auto;
			padding: 20px;
			background-color: white;
			border-radius: 5px;
			box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
		}
		
		.form-group {
			text-align: center;
		}
		
		.btn-container {
			display: flex;
			justify-content: center;
			gap: 10px;
		}
	</style>
	<script>
	
		/* 위도와 경도에 맞는 값만 입력받게 검증 */
		function validateTypeInput(input) {
			const regex = /^-?\d*\.?\d*$/;
			if (!regex.test(input.value)) {
				input.value = input.value.slice(0, -1);
			}
		}
			
		/* 내 위치 불러오기 */
		function loadTemporaryLocation() {
			const tempLat = "37.5665";
			const tempLnt = "126.978";
			document.getElementById('userLat').value = tempLat;
			document.getElementById('userLnt').value = tempLnt;
		}
		
	</script>	
</head>
<body>
	<h1> 서울시 공공 와이파이 정보 </h1>
	<div class="form-container">
		<form id="wifiForm" action="load-wifi.jsp" method="POST" onsubmit="return validateTypeInput(this)">
			<h4>내 위치 입력</h4>
			<div class="form-group" style="display: flex; align-items: center; justify-content: center; margin-bottom: 10px;">
				<label for="userLat" style="margin-right: 10px;">위도:</label>
				<input type="text" id="userLat" name="userLat" required oninput="validateTypeInput(this)" style="width: 100px; margin-right: 20px;">
				
				<label for="userLnt" style="margin-right: 10px;">경도:</label>
				<input type="text" id="userLnt" name="userLnt" required oninput="validateTypeInput(this)" style="width: 100px;">
			</div>
			<div class="btn-container">
				<button type="button" class="btn btn-secondary" onclick="loadTemporaryLocation()">내 위치 불러오기</button>
				<button type="submit" class="btn btn-primary" onclick="submitLocation()">조회 하기</button>
			</div>
		</form>	
	</div>

</body>
</html>