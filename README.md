<div align="center">
🔥
<h2>EMPIEZO BOARD</h2>
EMPIEZO의 동사원형인 'EMPEZAR'는 스페인어로 '시작하다'라는 뜻입니다.
  
SPRING BOOT + JPA + THYMELEAF를 사용해 게시판 서비스를 구현해 보았습니다.
</div>

## 📖 개요
- 프로젝트 이름 : EMPIEZO BOARD 
- 프로젝트 개발 기간 : 2023.08 ~ 2023.09
- 프로젝트 주요 개발 언어 및 프레임워크 : JAVA, SPRING BOOT
- 프로젝트 멤버 : 전현근 (1인 개발)

---

## ⚙️ 개발 환경
| TOOL NAME | VERSION |
| --- | ---- |
| JAVA | 17 |
| SPRING BOOT ( Gradle ) | 3.1.2 |
| IntelliJ | 2022.01 |

---

## 📕 기술 스택
### BackEnd
- Java & Spring Framework
- JPA (ORM)
- MariaDB

### FrontEnd
- HTML & CSS & JavaScript
- jQuery
- Thymeleaf

### Cloud & Deployment
- AWS EC2

### Version Control
- Git

---

## 🖋️ 주요 기능

### 💡 Spring Security Session & OAuth2 방식의 사용자 인증
- 일련의 Validate 과정을 거친 후 회원가입 / 로그인 기능
- Social Login (구글) 기능
- 회원 정보 수정 기능

### 💡 게시글 기능
- 기본적인 CRUD 기능
- Check 유무를 통해 비밀글 설정 기능
- 게시글 추천/추천 취소 기능 (중복 추천은 불가능하며 한 게시물 당 한 번만 추천 가능)
- 게시글 조회 시 게시글 조회수 카운팅 기능

### 💡 게시판 기능
- 페이징 기능 (한 페이지 당 10개의 게시글 페이징)
- 검색 기능 (게시글 제목 및 작성자 닉네임)
- 비밀글 기능 (관리자나 작성자 본인이 아닌 경우 클릭 비활성화)

### 💡 댓글 기능
- 기본적인 CRUD 기능
- 댓글 작성 시 AJAX를 사용한 비동기 콘텐츠 로딩 기능

### 💡 관리자 기능
- 관리자만 접속 가능한 USER 삭제 기능 

### ⚠️ 추후 다양한 기능 및 성능 개선 예정 ⚠️ 

---

## 📕 도메인 모델 다이어그램
![](../../Downloads/Domain Model Diagram.png)