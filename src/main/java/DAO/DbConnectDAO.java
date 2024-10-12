package project_wifi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectDB {

	private static final String DB_URL = "jdbc:mariadb://192.168.0.49:3306/public_wifi_db";
	private static final String DB_ID = "wifi";
	private static final String DB_PW = "wifi";

	private ConnectDB() {}
	
	// 드라이버 로드
	private static void loadDriver() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println("드라이버 로드 실패: " + e.getMessage());
		}
	}
	
	// 테이블에 데이터가 존재하는지 확인
	public static int isExistData(String table) {
		loadDriver();
		
		int result = 0;
		
		String seleteSQL = " SELECT * FROM " + table;
		
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_ID, DB_PW);
			 PreparedStatement pstmt = conn.prepareStatement(seleteSQL);
			 ResultSet rs = pstmt.executeQuery()) {

			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	// 테이블 지우면 auto_increment값 1로 수정
	public static int alterTable(String table) {
		loadDriver();
		
		String alterQuery = " ALTER TABLE " + table + " AUTO_INCREMENT = 1 " ;
		
	    int result = 0;
	    try {
	        Connection conn = DriverManager.getConnection(DB_URL, DB_ID, DB_PW);
	        PreparedStatement pstmt = conn.prepareStatement(alterQuery);
	        result = pstmt.executeUpdate();
	        pstmt.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return result;
	}
	
	// 요청한 api의 전체 결과를 wifi_spots 테이블에 데이터 저장
	public static int insertWifiData(List<WifiSpot> wifiSpots) {
	 	loadDriver();
	 	
        String insertSQL = " INSERT INTO wifi_spots (manage_no, wrdofc, wifi_nm, address1, address2, ist_floor, ist_ty, " +
                           " ist_mby, svc_se, cmcwr, cnstc_year, inout_door, remars, latitude, longitude, work_dttm) " +
                           " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

        int savedCount = 0;

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_ID, DB_PW);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            for (WifiSpot spot : wifiSpots) {
                pstmt.setString(1, spot.getX_SWIFI_MGR_NO());
                pstmt.setString(2, spot.getX_SWIFI_WRDOFC());
                pstmt.setString(3, spot.getX_SWIFI_MAIN_NM());
                pstmt.setString(4, spot.getX_SWIFI_ADRES1());
                pstmt.setString(5, spot.getX_SWIFI_ADRES2());
                pstmt.setString(6, spot.getX_SWIFI_INSTL_FLOOR());
                pstmt.setString(7, spot.getX_SWIFI_INSTL_TY());
                pstmt.setString(8, spot.getX_SWIFI_INSTL_MBY());
                pstmt.setString(9, spot.getX_SWIFI_SVC_SE());
                pstmt.setString(10, spot.getX_SWIFI_CMCWR());
                pstmt.setString(11, spot.getX_SWIFI_CNSTC_YEAR());
                pstmt.setString(12, spot.getX_SWIFI_INOUT_DOOR());
                pstmt.setString(13, spot.getX_SWIFI_REMARS3());
                pstmt.setString(14, spot.getLAT());
                pstmt.setString(15, spot.getLNT());
                pstmt.setString(16, spot.getWORK_DTTM());
                pstmt.addBatch(); // 배치에 추가
            }

            int[] results = pstmt.executeBatch(); // 배치 실행
            savedCount = results.length; // 저장된 개수
        } catch(SQLException e) {
        	System.err.println("DB 오류 발생: " + e.getMessage());
        }

        return savedCount;
    }
	
	// wifi_spots 테이블에서 데이터 select
	public static List<WifiSpot> selectWifiData(double userLat, double userLnt) {
	 	loadDriver();
	 	
	 	List<WifiSpot> wifiSpots = new ArrayList<>();
	 	
	 	String query = " SELECT manage_no, wrdofc, wifi_nm, address1, address2, " +
                " ist_floor, ist_ty, ist_mby, svc_se, cmcwr, cnstc_year, " +
                " inout_door, remars, latitude, longitude, work_dttm, " +
                " ST_Distance_Sphere(POINT(longitude, latitude), POINT(?, ?)) AS distance " +
                " FROM wifi_spots " +
                " ORDER BY distance LIMIT 20 ";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_ID, DB_PW);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

        	 pstmt.setDouble(1, userLnt); // 경도
             pstmt.setDouble(2, userLat); // 위도
             
             ResultSet rs = pstmt.executeQuery();
        	while (rs.next()) {
                WifiSpot spot = new WifiSpot();
                spot.setX_SWIFI_MGR_NO(rs.getString("manage_no")); // 관리 번호
                spot.setX_SWIFI_WRDOFC(rs.getString("wrdofc")); // 구역 이름
                spot.setX_SWIFI_MAIN_NM(rs.getString("wifi_nm")); // 와이파이 이름
                spot.setX_SWIFI_ADRES1(rs.getString("address1")); // 주소 1
                spot.setX_SWIFI_ADRES2(rs.getString("address2")); // 주소 2
                spot.setX_SWIFI_INSTL_FLOOR(rs.getString("ist_floor")); // 설치 층
                spot.setX_SWIFI_INSTL_TY(rs.getString("ist_ty")); // 설치 유형
                spot.setX_SWIFI_INSTL_MBY(rs.getString("ist_mby")); // 설치 기관
                spot.setX_SWIFI_SVC_SE(rs.getString("svc_se")); // 서비스 유형
                spot.setX_SWIFI_CMCWR(rs.getString("cmcwr")); // 망 정보
                spot.setX_SWIFI_CNSTC_YEAR(rs.getString("cnstc_year")); // 설치 연도
                spot.setX_SWIFI_INOUT_DOOR(rs.getString("inout_door")); // 실내/실외 여부
                spot.setX_SWIFI_REMARS3(rs.getString("remars")); // 비고
                spot.setLAT(rs.getString("latitude")); // 위도
                spot.setLNT(rs.getString("longitude")); // 경도
                spot.setWORK_DTTM(rs.getString("work_dttm")); // 작업 일시
                spot.setDISTANCE(rs.getDouble("distance")); // 거리
                
                wifiSpots.add(spot);
            }
        	
        } catch(SQLException e) {
        	System.err.println("DB 오류 발생: " + e.getMessage());
        }
        return wifiSpots;
    }
	
	// 입력받은 위치 정보 정보 history 테이블에 저장 
	public static int insertInputHistory(String userLatParam, String userLntParam) {
		loadDriver();
		
		int result = 0;
		
		String query = " INSERT INTO history (latitude, longitude, search_time) VALUES (?, ?, NOW()) ";
		
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_ID, DB_PW);
	         PreparedStatement pstmt = conn.prepareStatement(query)){
			
			pstmt.setDouble(1, Double.parseDouble(userLatParam));
			pstmt.setDouble(2, Double.parseDouble(userLntParam));
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.err.println("DB 오류 발생: " + e.getMessage());
		}
		return result;
	}
	
	// history 테이블 데이터 select
	public static List<History> selectHistoryData() {
	 	loadDriver();
	 	
	 	List<History> historyData = new ArrayList<>();
	 	
	 	String query = " SELECT id, latitude, longitude, search_time FROM history";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_ID, DB_PW);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

             ResultSet rs = pstmt.executeQuery();
             
        	 while (rs.next()) {
        		 History history = new History();
        		 history.setId(rs.getString("id"));
        		 history.setLatitude(rs.getString("latitude"));
        		 history.setLongitude(rs.getString("longitude"));
        		 history.setSearchTime(rs.getString("search_time"));
        		 
        		 historyData.add(history);
            }
        	
        } catch(SQLException e) {
        	System.err.println("DB 오류 발생: " + e.getMessage());
        }
        return historyData;
    }
	
	// 입력 받은 위치 정보에 해당하는 데이터를 history 테이블에서 삭제 
	public static int deleteHistoryItem(String latitude, String longitude) {
		loadDriver();
		
		String deleteSQL = " DELETE FROM history where latitude = ? AND longitude = ? ";
		
	    int result = 0;
	    try (Connection conn = DriverManager.getConnection(DB_URL, DB_ID, DB_PW);
		     PreparedStatement pstmt = conn.prepareStatement(deleteSQL);) {
	    	
	    	pstmt.setDouble(1, Double.parseDouble(latitude));
	    	pstmt.setDouble(2, Double.parseDouble(longitude));
	    	
	        result = pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return result;
	}
}
