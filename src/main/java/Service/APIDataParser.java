package project_wifi;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class WifiDataParser {
	private static final Gson gson = new Gson();
	
	// 요청한 api의 json을 객체로 반환
	private WifiInfoResponse parseWifiInfoResponse(String jsonData) {
		try {
			// JSON 데이터 파싱
			return gson.fromJson(jsonData, WifiInfoResponse.class);
		} catch (JsonSyntaxException e) {
			System.err.println("JSON 파싱 오류: " + e.getMessage());
			return null; // 파싱 실패 시 null 반환
		} catch (Exception e) {
			e.printStackTrace();
			return null; // 일반 예외 발생 시 null 반환
		}
	}

	// API 자료 요청
	public List<WifiSpot> requestApi() {	
		WifiApiFetcher wifi = new WifiApiFetcher();
        List<WifiSpot> wifiSpots = new ArrayList<>();
        
	    try {
	    	// api의 전체 데이터 갯수를 확인하기 위한 요청
	    	WifiInfoResponse initialResponse = this.parseWifiInfoResponse(wifi.getWifiJson(1, 1));
	    	
	    	// 데이터가 없으면 빈 list return
	    	if (initialResponse == null || initialResponse.getTbPublicWifiInfo() == null) {
	    		System.err.println("초기 API 응답에서 정보를 가져오는데 실패했습니다.");
	    		return wifiSpots;
	    	}
	    	
	    	// 요청한 api의 전체 데이터 갯수
	        int totalCount = initialResponse.getTbPublicWifiInfo().getList_total_count();
	        					
	        // 전체 갯수 만큼 데이터 베이스에 저장 
	        for (int start = 1; start <= totalCount; start += 1000) {
	        	int end = Math.min(start + 999, totalCount);
	        	String jsonResponse = wifi.getWifiJson(start, end);
	            
	            WifiInfoResponse wifiInfoResponse = this.parseWifiInfoResponse(jsonResponse);
	            
	            // 요청한 정보 null 체크
	            if (wifiInfoResponse != null && wifiInfoResponse.getTbPublicWifiInfo() != null) {
	            	// 요청한 api의 row 항목에 해당하는 결과를 wifiSpots에 저장
	            	wifiSpots.addAll(new ArrayList<WifiSpot>(wifiInfoResponse.getTbPublicWifiInfo().getRow()));     
	            } else {
	            	System.err.println("와이파이 정보를 가져오는 데 실패했습니다.");
	            }
	        }
	        
	         // 저장된 데이터 베이스 전체 갯수 
	        } catch (Exception e) {
	        	System.err.println("API 요청 중 오류 발생: " + e.getMessage());
	        }
	    return wifiSpots;
	}
}
