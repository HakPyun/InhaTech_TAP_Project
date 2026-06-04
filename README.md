# Travel Along the Peninsula (TAP_Project)
<img width="1024" height="386" alt="image" src="https://github.com/user-attachments/assets/3771e9fc-b175-49fa-8d6e-f3ec0d21b0ae" />

> '반도를 따라 여행하다'라는 뜻으로 인하대 후문의 맛집을 등록하고 리뷰를 남길 수 있는 프로그램.


## 목차  
- [프로젝트 소개](#프로젝트-소개)  
- [기능](#기능)   
- [사용 기술](#사용-기술)  
- [프로젝트 구조](#프로젝트-구조)  
- [기여 방법](#기여-방법)
- [주요 화면](#주요-화면)  

## 프로젝트 소개  


이 프로젝트는 사람들이 맛집을 이용한 후, 후기를 남겨 해당 가게를 이용하고자 하는 사람들이 정보를 얻을 수 있도록 하기 위해 만들어졌습니다.
특정 인증을 받은 사람만 '등록자' 권한을 얻어 가게를 등록할 수 있습니다.
- 주요 문제 해결 대상: `인하대 후문 거리 이용자들`  
- 기대 효과: `맛집에 대한 정보를 편히 얻고 이용한다. 또한, 사용자들로 하여금 후기를 보고 오고 싶게 하여 인하대 후문 거리 활성화를 도모한다.`

## 기능  

- ✅ 회원가입/회원탈퇴
- ✅ 로그인/로그아웃
- ✅ 가게 정보 관련 CRUD
- ✅ 카카오지도와의 상호작용

## 사용 기술

**Back-end**
<p align="left">
  <img src="https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white">
  <img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white">
  <img src="https://img.shields.io/badge/Spring_Data_JPA-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
  <img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
</p>

**Front-end**
<p align="left">
  <img src="https://img.shields.io/badge/Thymeleaf-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white">
  <img src="https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white">
  <img src="https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white">
  <img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black">
</p>

**External APIs**
<p align="left">
  <img src="https://img.shields.io/badge/Kakao%20Maps%20API-FFCD00?style=for-the-badge&logo=kakao&logoColor=black">
  <img src="https://img.shields.io/badge/JSON-000000?style=for-the-badge&logo=json&logoColor=white">
</p>

**Deployment & Network**
<p align="left">
  <img src="https://img.shields.io/badge/Local_Server-333333?style=for-the-badge&logo=home-assistant&logoColor=white">
  <img src="https://img.shields.io/badge/Port_Forwarding-FF6600?style=for-the-badge&logo=airplay&logoColor=white">
</p>

**Tools**
<p align="left">
  <img src="https://img.shields.io/badge/IntelliJ_IDEA-000000?style=for-the-badge&logo=intellij-idea&logoColor=white">
  <img src="https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white">
  <img src="https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white">
</p>

## 프로젝트 구조

- DB엔티티
<img width="749" height="643" alt="image" src="https://github.com/user-attachments/assets/d7625234-f96e-4076-bf96-56a0ed821ef9" />

### 🌐 System Architecture & Flow

현재 서비스는 로컬 개발 환경을 포트포워딩하여 외부 사용자가 접속할 수 있도록 구축되었습니다.

| 컴포넌트 | 역할 및 기술 스택 | 포트(Port) | 비고 |
| :--- | :--- | :--- | :--- |
| **Client** | 외부 사용자 요청 (Browser / App) | - | 공인 IP로 접속 |
| **Router** | 공유기 및 포트포워딩 설정 | 외부 포트 ➔ `8080` | 내부 PC로 요청 라우팅 |
| **Backend** | Spring Boot (IntelliJ IDEA) | `8080` | 내장 톰캣(Tomcat) 구동 |
| **Database** | MySQL | `3306` | 로컬 데이터 저장소 |

#### 🔄 Data Flow
1. **요청 접수**: 외부 사용자가 공인 IP 주소를 통해 공유기로 접속합니다.
2. **포트포워딩**: 공유기가 외부 요청을 받아 Local PC의 Spring Boot 포트(`8080`)로 전달합니다.
3. **비즈니스 로직**: Spring Boot(IntelliJ) 서버가 요청을 처리하며, 필요한 데이터는 로컬에 구동 중인 MySQL(`3306`)과 통신하여 주고받습니다.

## 기여 방법

| 성함/역할 | 주요 구현 항목 | 사용 기술 |
| :--- | :--- | :--- |
| **김정인** | **MVC 패턴 기반 위치 데이터 시각화 및 서비스 배포**<br><br>• **[Back-end]** Spring MVC `Model` 객체를 활용한 DB 주소 데이터 동적 바인딩<br>• **[Front-end]** Kakao Maps SDK 연동 (주소-좌표 실시간 변환 및 마커 렌더링)<br>• **[Infra]** 포트포워딩 설정을 통한 로컬 서버 외부 배포 환경 구축 | Java, Spring Boot,<br>MySQL,<br>Kakao Maps API,<br>Thymeleaf |
| **박범준** | • **[Back-end]** 스프링 시큐리티를 이용한 로그인/로그아웃 및 회원관련 CRUD<br>• **[Front-end]** 회원가입, 권한 변경 페이지| Java, Spring Boot,<br>MySQL,<br>Kakao Maps API,<br>Thymeleaf |
| **최필묵** | • **[Back-end]** 이미지 파일 DB 주소화 저장<br>• **[Front-end]** 가게 등록 페이지| Java, Spring Boot<br>MySQL<br>Thymeleaf |

## 주요 화면
### 메인
<img width="687" height="784" alt="메인 화면" src="https://github.com/user-attachments/assets/0307b57f-c16c-4bea-91c2-1e454be81e6a" />

### GIS 데이터 시각화 및 목록화
<img width="687" height="578" alt="GIS 데이터 시각화" src="https://github.com/user-attachments/assets/7ebe42c8-f081-4dce-a4bf-ca46e43dd8a8" />

### 가게 등록
<img width="691" height="691" alt="가게 등록 화면" src="https://github.com/user-attachments/assets/c5088ec9-269c-4881-a72a-5299d96f1876" />

### 가게 상세 및 리뷰
<img width="488" height="764" alt="가게 상세" src="https://github.com/user-attachments/assets/6de83646-1468-4841-8554-81e32b928e86" />
<img width="484" height="752" alt="리뷰 화면" src="https://github.com/user-attachments/assets/d174159b-7fc3-4400-951a-dcd02ca18462" />

## 🚀 Key Achievements (기술적 성과)

본 프로젝트는 로컬 구동 환경의 한계를 극복하고 실제 서비스 환경과 유사한 네트워크 레이어를 구축하는 데 집중했습니다.
* **Spring Security 활용**
  * 스프링 시큐리티를 이용하여 로그인/로그아웃 기능을 구현했습니다.
* **네트워크 환경 최적화 (Port Forwarding)**
  * 외부 클라우드 인프라를 사용하지 않고, 공유기 내 포트포워딩 환경 설정을 통해 외부 유저 및 조원들이 로컬 스프링 부트 서버(:8080)에 실시간 접속할 수 있는 테스트 환경을 구축했습니다.
* **GIS 위치 데이터 시각화**
  * 카카오 맵 API를 연동하여 위치 기반 GIS 데이터를 지도 위에 동적으로 시각화하고, 비즈니스 로직과 매핑하여 리스트화하는 화면 가독성을 확보했습니다.
 
## 🔗 More Info
- [📦 김정인의 프로젝트 개인 회고 (Notion)](https://catnip-memory-4f5.notion.site/TAP-364c4f59f2f98017892af6eda24c6096)
