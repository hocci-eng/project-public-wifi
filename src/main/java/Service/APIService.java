package project_wifi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;

public class WifiApiFetcher {
	 private static final String API_KEY = "484577475a686f6b366544414f65";
	 private static final String API_URL = "http://openapi.seoul.go.kr:8088/" + API_KEY + "/json/TbPublicWifiInfo";
	 
	 public String getWifiJson (int start, int end) {
		String parameter = API_URL + "/" + start + "/" + end + "/";
		
		StringBuilder response = new StringBuilder();
		HttpURLConnection conn = null;
		
		try {
			URI uri = new URI(parameter);
			URL url = uri.toURL();
			conn = (HttpURLConnection) url.openConnection();
			
			// 요청 방식 설정
			try {
				conn.setRequestMethod("GET");
			} catch (ProtocolException e) {
				System.err.println("잘못된 요청 방식입니다: " + e.getMessage());
			}
			
			// 응답 코드 확인
			int responseCode = conn.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
					String inputLine;
					
					while ((inputLine = in.readLine()) != null) {
						response.append(inputLine);
					}
				} catch (IOException e) {
					System.err.println("응답을 읽는 도중 오류 발생: " + e.getMessage());
				}
			} else {
				System.err.println("HTTP 오류 코드: " + responseCode);
			}
		} catch (MalformedURLException e) {
			System.err.println("잘못된 URL 형식입니다: " + e.getMessage());
		} catch (IOException e) {
			System.err.println("네트워크 오류 발생: " + e.getMessage());
		} catch (SecurityException e) {
			System.err.println("보안 오류 발생:" + e.getMessage());
		} catch (Exception e) {
			System.err.println("예상치 못한 오류 발생: " + e.getMessage());
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		
		return response.toString();
	}
}
