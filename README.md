## :sunny: SOMS :sunny:
Sun Operation management Solution 사이트

## 🧾프로젝트 소개
썬 회사의 경영관리를 위해 사내 자체 이메일 구축 및 간편 전산 결재 시스템 제작. 사내 게시판과 사원 정보 및 근태 관리 시스템을 구현한 사이트입니다.

## 🗓개발기간
2023 . 02 . 01 . ~ 2023 . 02 . 03 .  
● 요구사항정의서 작성 및 프로젝트 설계 전반 작업  
● GitRepository 생성 및 계정 연결  
● 페이지 디자인 규격화 및 와이어 프레임 제작 완료  
● 기능별 업무 분장 및 기능 정의 완료  
● DB 설계 및 테이블 명세서 작성 완료  

2023 . 02 . 04 . ~ 2023 . 02 . 07 .  
2023 . 02 . 08 . ~ 2023 . 02 . 21 .  
2023 . 02 . 22 . ~ 2023 . 03 . 03 .  

### 👪멤버구성
- 연태양 - 진행 총괄 및 요구사항 분석, 작업 설계, 간편 전산 결재 시스템 기능
- 정우민 - GIT 관리 및 게시판(CRUD) 기능 및 화면 담당, 공통UI 기능 및 화면 담당.
- 김희용 - 페이지 디자인 가이드, 회원 관리(마이페이지) 기능 및 로그인,회원가입 기능
- 박호민 - 페이지 디자인 가이드, 사내 이메일 기능 구현

### 🛠개발환경
- <img src="https://img.shields.io/badge/windows 10-0078D6?style=flat&logo=Windows Chrome&logoColor=white"/> <img src="https://img.shields.io/badge/macOS-000000?style=flat&logo=macOS&logoColor=white"/>
- <img src="https://img.shields.io/badge/Google Chrome-4285F4?style=flat&logo=Google Chrome&logoColor=white"/>
- <img src="https://img.shields.io/badge/java11-222324?style=flat&logoColor=white"/>
- <img src="https://img.shields.io/badge/JDK 11-2C2255?style=flat&logoColor=white"/>
- server: <img src="https://img.shields.io/badge/apache tomcat-9.0-2C2255?style=flat&logo=Apache Tomcat&logoColor=white"/>
- Framework: <img src="https://img.shields.io/badge/Eclipse-2C2255?style=flat&logo=Eclipse IDE&logoColor=white"/>
- Database: <img src="https://img.shields.io/badge/MySQL 8.0.31-4479A1?style=flat&logo=MySQL&logoColor=white"/>
<img src="https://img.shields.io/badge/HeidiSQL-1B72BE?style=flat&logoColor=white"/> <img src="https://img.shields.io/badge/MySQLWorkbench-02458D?style=flat&logoColor=white"/>
- 협업툴: <img src="https://img.shields.io/badge/GitHub-181717?style=flat&logo=GitHub&logoColor=white"/> <img src="https://img.shields.io/badge/Sourcetree-0052CC?style=flat&logo=Sourcetree&logoColor=white"/> <img src="https://img.shields.io/badge/Slack-4A154B?style=flat&logo=Slack&logoColor=white"/> <img src="https://img.shields.io/badge/Trello-0052CC?style=flat&logo=Trello&logoColor=white"/> <img src="https://img.shields.io/badge/typed-2C5BB4?style=flat&logoColor=white"/>

### 💎주요기능
#### 사용자
- 회원
아이디 중복확인 기능 및 회원 정보 확인 후 회원가입 기능
회원의 개인 정보 확인 및 수정, 회원 탈퇴 기능
사용자 정보와 데이터를 대조하여 해당 아이디와 비밀번호 조회 및 응답
사용자 및 관리자 로그인 후 회원번호와 권한 등급 세션 저장
- 마이페이지
로그인 된 회원의 잔여 포인트 연산 및 포인트 사용 / 적립 내역 화면에 표시
세션 또는 비회원의 연락처, 비밀번호로 모든 예약 내역 조회, 예약 완료 건에 대해서 리뷰 작성 기능
회원의 문의 내역 조회 및 답변 상태 확인 기능
- 객실 예약
예약건 조회하여 해당 결제 방식, 예약 상태 구분 후 포인트로 결제 기능
객실 별 리뷰 내역 화면 구성
회원, 비회원에 맞춰 날짜, 객실 등 조건으로 객실 배정 및 관련 내용 확인 후 맞춰 예약 진행
사용자 입실, 퇴실에 맞춰 해당 객실의 이용 상태 및 직원의 청소상태 등 화면 구성
- 다이닝 예약
식당 별 정보, 리뷰 화면 구성 및 예약 페이지 연결
회원, 비회원에 맞춰 날짜, 식사시간 등 예약 정보 확인 후 예약 진행

#### 관리자
- 회원
회원 조회 및 회원 정보 수정, 회원의 이용 내역 및 포인트 내역 조회, 관리자 등급 부여
- 문의 사항 및 리뷰
미답변된 문의 정보 상단 배치
문의 내역 조회 후 문의 정보 확인, 답변 등록, 수정 및 삭제 기능 구현
- 객실 예약
관리자가 직접 날짜, 객실 등 조건으로 객실 조회 기능 구현
사용자 이름 및 기간을 조건으로 다이닝 예약 건 전체 조회
일별, 객실별 예약 현황 비교 가능하도록 캘린더 화면 구성
식당별, 기간별로 예약 현황 비교 가능하도록 차트화하여 화면 구성
- 다이닝 예약
사용자 이름 및 기간을 조건으로 다이닝 예약 건 전체 조회
식당별, 기간별로 예약 현황 비교 가능하도록 차트화하여 화면 구성
- 매출
매출, 지출 관련 데이터 차트화하여 화면 구성(당일 기준), 지출 내역 조회 및 신규 지출 입력


