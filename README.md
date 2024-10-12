# 📚프로젝트 - Public Wifi

- 서울 공공 와이파이 API를 이용해 자신의 위치나 특정 위치를 기반으로 가장 가까운 거리에 있는 20개의 와이파이 목록을 보여주는 프로젝트
- Project to show 20 nearest Wi-Fi lists based on one's location or specific location using Seoul Public Wi-Fi API
<br>

# 🛠️ 기술 스택
- Language : `Java`
- Database : `MariaDB`
- Server : Tomcat 8.5`
- JDK : `JDK 1.8`
- Web : `HTML5`, `Javascript`, `CSS`, `JSP`
- Library : `mariadb-java-client`, `gson`
<br>

# 👀 프로젝트 기능
- 서울시 공공 와이파이 API를 활용하여 서울시의 공공 와이파이 정보를 가져옵니다.
- 사용자가 입력한 위치 좌표를 기반으로 주변에 있는 공공 와이파이 정보 20개를 보여줍니다.
- 사용자가 입력한 위치 정보를 기반으로 API를 요청해서 모든 데이터를 DB에 저장하고, 저장된 정보를 조회할 수 있습니다.
- 사용자가 입력한 위치 정보와 조회 시점 기준의 시간이 히스토리에 저장되고, 이후 히스토리 내역을 볼 수 있습니다.
- 사용자는 히스토리에서 조회할 때 입력했던 위치 정보를 삭제할 수 있습니다.
<br>

# 👀 ERD
![ERD](https://github.com/user-attachments/assets/cda12d9e-965f-4de4-9113-a5b5634db1a7)
<br>

# 👀 프로젝트 기능 구현 영상
https://github.com/user-attachments/assets/67ca86f8-e24b-45a3-8da0-a2c8b3ac2817
