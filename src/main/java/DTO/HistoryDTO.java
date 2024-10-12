package project_wifi;


public class History {
	private String id;
	private String latitude; // x 좌표
	private String longitude; // y 좌표
	private String searchTime; // 조회 시간
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getLatitude() {
		return latitude;
	}
	
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	public String getLongitude() {
		return longitude;
	}
	
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	public String getSearchTime() {
		return searchTime;
	}
	
	public void setSearchTime(String searchTime) {
		this.searchTime = searchTime;
	}
}
