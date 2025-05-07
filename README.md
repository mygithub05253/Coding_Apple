# Coding_Apple
# 🛍️ Spring Boot Shop Project

이 프로젝트는 Spring Boot, JPA, Spring Security, Thymeleaf를 활용한 간단한 쇼핑몰 백엔드 애플리케이션입니다.  
사용자 등록, 상품 CRUD, 보안 설정 등의 핵심 기능을 학습하고 구현하는 데 목적이 있습니다.

## 📁 프로젝트 구조

- src/main/java/com/apple/shop
  - ShopApplication.java – 메인 어플리케이션 실행 파일
  - BasicController.java – 테스트용 기본 페이지 매핑
  - SecurityConfig.java – Spring Security 설정

  - member/ 사용자 도메인
    - Member.java – 사용자 Entity
    - MemberRepository.java – 사용자 JPA 리포지토리
    - MemberService.java – 사용자 로직 서비스 계층
    - MemberController.java – 사용자 회원가입 처리

  - item/ 상품 도메인
    - Item.java – 상품 Entity
    - ItemRepository.java – 상품 JPA 리포지토리
    - ItemService.java – 상품 관련 비즈니스 로직
    - ItemController.java – 상품 CRUD API

## 🚀 실행 방법

1. 의존성 설치

    ```bash
    ./gradlew build
    ```

2. 애플리케이션 실행

    ```bash
    ./gradlew bootRun
    ```

3. 브라우저 접속

    ```
    http://localhost:8080
    ```

---

## 🔐 보안 설정 요약 (SecurityConfig.java)

- 모든 URL 기본 허용 (`permitAll`)
- 사용자 정의 로그인 페이지 사용 (`/login`)
- JWT 인증 필터 연결 (`JwtFilter`)
- CSRF 설정 및 무시 경로 등록 (`/login` 등)
- 세션 비활성화 (`SessionCreationPolicy.STATELESS`)

---

## 📄 주요 기능

- ✅ 회원가입 (`/register`, `/member`)
- ✅ 로그인 (Spring Security + JWT + BCrypt 암호화)
- ✅ 상품 목록/등록/상세 조회/수정/삭제 (템플릿 기반 UI)
- ✅ 에러 처리 (`400`, `404`, `500`) 및 커스텀 error.html
- ✅ ControllerAdvice를 통한 글로벌 예외 핸들링

---

## 📁 각 프로젝트 폴더 설명
## 👤 member/ 사용자 인증 도메인

- Member.java  
  - 사용자 정보를 저장하는 JPA 엔티티 클래스입니다.  
  - 필드: `id`, `username(고유)`, `displayName`, `password`  
  - `@Entity`, `@Id`, `@Column(unique = true)` 어노테이션으로 DB 테이블과 매핑됩니다.

- MemberRepository.java  
  - `JpaRepository`를 확장한 사용자 저장소 인터페이스입니다.  
  - `findByUsername(String username)` 메서드로 사용자 조회 가능 (Spring Data JPA 지원)

- MemberService.java  
  - 사용자 가입 비즈니스 로직 담당.  
  - 중복 ID 확인, 비밀번호 길이 검증, 암호화 저장(BCrypt)을 수행합니다.  
  - 예외 발생 시 메시지로 반환 (`존재하는아이디`, `너무짧음`)

- MemberController.java  
  - 사용자 등록/로그인 관련 HTTP 요청을 처리하는 컨트롤러입니다.  
  - `/register`, `/login`, `/member`, `/login/jwt`, `/my-page/jwt` 등의 라우팅을 정의  
  - JWT 로그인 구현과 쿠키 저장까지 포함된 복합 기능 처리

- MyUserDetailsService.java  
  - Spring Security의 `UserDetailsService` 구현체  
  - 로그인 시 DB에서 사용자를 조회하고 `CustomUser` 객체로 반환  
  - 사용자 권한 부여 및 `displayName`, `id` 필드 확장

- CustomUser.java  
  - Spring Security의 `User` 클래스를 상속받은 커스텀 사용자 객체  
  - 기본 필드 외 `displayName`, `id` 등을 추가해 인증 후 사용자 정보 접근 용이

- JwtUtil.java  
  - JWT 생성 및 추출 유틸 클래스  
  - `createToken(Authentication auth)`는 사용자 정보를 담은 JWT 생성  
  - `extractToken(String token)`은 JWT에서 `Claims` 추출  
  - HMAC-SHA 키 기반으로 서명 처리

- JwtFilter.java  
  - HTTP 요청 시 `jwt` 쿠키를 검사하여 유효한 JWT일 경우 인증 정보를 설정  
  - `SecurityContextHolder`에 사용자 정보를 넣어 Spring Security 인증 처리 수행  
  - 인증 성공 시 사용자 정보를 `CustomUser`로 구성하여 자동 로그인 상태 유지

## 📦 item/ 상품 도메인

- Item.java  
  - 상품 정보를 저장하는 JPA 엔티티 클래스입니다.  
  - 필드: `id`, `title`, `price`, `count`  
  - `@Entity`, `@Id`, `@GeneratedValue`로 DB 테이블과 자동 매핑됩니다.

- ItemRepository.java  
  - `JpaRepository<Item, Long>`를 상속받은 상품 저장소 인터페이스입니다.  
  - 제목에 특정 문자열이 포함된 상품 목록을 가져오는 `findAllByTitleContains(String title)` 제공  
  - MySQL `FULLTEXT` 검색을 위한 `fullTextSearch(String text)` 쿼리 메서드 포함

- ItemService.java  
  - 상품 등록 및 수정 비즈니스 로직을 담당하는 서비스 클래스입니다.  
  - `saveItem(...)`은 상품을 신규로 저장하며, 기본 수량(count)은 10으로 설정  
  - `editItem(...)`은 동일 ID를 가진 상품의 정보를 갱신하여 저장

- ItemController.java  
  - 상품 관련 요청을 처리하는 Spring MVC 컨트롤러입니다.  
  - 주요 라우팅:
    - `GET /list`: 전체 상품 조회 및 리스트 페이지 렌더링  
    - `GET /write`: 상품 등록 폼 렌더링  
    - `POST /add`: 상품 등록 처리  
    - `GET /detail/{id}`: 특정 상품 상세 페이지  
    - `GET /edit/{id}`, `POST /edit`: 상품 수정 폼 및 처리  
    - `DELETE /item`: 상품 삭제 처리  
    - `POST /search`: 제목 기반 텍스트 검색 기능  
    - `GET /presigned-url`: S3 pre-signed URL 생성 (이미지 업로드)

- S3Service.java  
  - AWS S3에 presigned URL을 생성해주는 유틸리티 서비스입니다.  
  - `createPresignedUrl(String path)` 메서드를 통해 특정 파일 업로드용 URL을 생성  
  - `signatureDuration = 3분`, 키 경로는 `"test/" + filename` 형식으로 지정  
  - 내부적으로 `S3Presigner`를 사용하며, `application.properties`에 S3 설정 필요

## 💬 comment/ 댓글 도메인

- Comment.java  
  - 댓글 데이터를 저장하는 JPA 엔티티 클래스입니다.  
  - 필드: `id`, `username`, `content`, `parentId`  
  - `parentId`는 해당 댓글이 달린 게시글(Item)의 ID와 매핑됩니다.  
  - `@Entity`, `@Id`, `@GeneratedValue` 어노테이션으로 자동 매핑 처리됨

- CommentRepository.java  
  - `JpaRepository<Comment, Long>`를 상속받은 댓글 리포지토리 인터페이스입니다.  
  - 특정 게시글의 댓글 목록을 가져오는 `findAllByParentId(Long parentId)` 메서드 제공

- CommentController.java  
  - 댓글 POST 요청을 처리하는 Spring MVC 컨트롤러입니다.  
  - 주요 라우팅:
    - `POST /comment`: 로그인된 사용자의 이름과 댓글 내용을 받아 저장  
  - `Authentication` 객체를 통해 로그인된 사용자의 정보를 받아 `CustomUser`로 캐스팅  
  - 주의: 현재 `content`에 `setUsername(...)`를 잘못 덮어씌우는 버그 있음 → 수정 필요

- 버그 주의 사항
data.setContent(content);            // ✅ 댓글 본문 저장
data.setContent(user.getUsername()); // ❌ 잘못 덮어씀 (실제로는 setUsername 사용해야 함)

## 💰 sales/ 주문 및 매출 도메인

- Sales.java  
  - 하나의 주문(결제)을 나타내는 JPA 엔티티입니다.  
  - 필드: `id`, `itemName`, `price`, `count`, `memberId`, `created`  
  - `@ManyToOne` 관계를 통해 구매자(Member)와 연관  
  - 생성 시 자동으로 생성 시각(`@CreationTimestamp`)을 저장  
  - 잘못된 `@OneToMany` 설정 존재: `sales` 필드는 자기 자신을 참조하고 있어 순환 문제가 있음 → 제거 필요

- SalesRepository.java  
  - `JpaRepository<Sales, Long>` 이어야 하나, 현재는 `JpaRepository<Member, Long>`로 잘못 정의되어 있음 → 수정 필요  
  - 사용자 정보를 포함한 매출 정보를 한번에 조회하는 커스텀 쿼리 `customFindAll()` 정의됨

- SalesService.java  
  - 주문 로직을 처리하는 서비스 클래스입니다.  
  - `addSales(...)` 메서드는 다음 두 단계로 구성됨:
    1. `itemId` 기반으로 재고 차감
    2. 주문 정보 생성 후 Sales 테이블에 저장  
  - 현재는 `Authentication`을 통해 로그인 사용자의 정보를 추출해 `Sales.member`로 연결

- SalesController.java  
  - `/order`, `/order/all` 라우트를 처리하는 컨트롤러입니다.  
  - `POST /order`: 주문을 저장하며 재고를 줄이고, Sales에 사용자 연결  
    - 이때 `Member` 객체를 임시 생성하여 ID만 설정한 후 관계 매핑  
    - 주의: 재고 차감 시 사용하는 `id` 변수가 정의되지 않음 → `@RequestParam Long id` 누락되어 있음  
  - `GET /order/all`: 모든 주문 정보 콘솔 출력 (뷰에는 아직 출력되지 않음)

- SalesDto (내부 클래스)  
  - `Sales` 엔티티에서 사용자 이름, 상품명, 가격만 추출해 보여주기 위한 DTO 클래스  
  - 아직 실제 매핑에는 사용되지 않음 (출력용 예비 클래스)

---

## ⚙️ application.properties 주요 설정

- DB: Azure MySQL 연결
- JPA: `ddl-auto=update`, SQL 로그 출력
- 세션: JDBC 세션 저장소, TTL 5초 (테스트용)
- AWS S3: `spring.cloud.aws.*` 설정 포함 (키는 외부 주입 필요)

---

## 🔨 빌드 설정 (build.gradle 주요 내용)

- Spring Boot, Spring Web, JPA, Security
- Thymeleaf, Lombok, H2, MySQL Driver
- JWT 관련 의존성 포함 (`jjwt`, `jwt-impl`, `jwt-jackson`)
- AWS SDK (`aws-java-sdk-s3`, `spring-cloud-starter-aws-v2`)

---

## ✍️ 개발 중 추가 기능 (계속 추가 예정)

- [ ] 회원정보 수정 / 탈퇴
- [ ] 인증된 사용자만 상품 등록 가능
- [ ] 장바구니 / 결제 기능
- [ ] 실제 MySQL 및 AWS 연동
- [ ] 테스트 코드 작성 (JUnit + MockMvc)

---

## 👨‍💻 사용 기술 스택

- **Spring Boot 3.x**
- **Spring Web, Spring Data JPA, Spring Security**
- **H2 / MySQL**
- **Lombok**
- **Thymeleaf**
- **Gradle**

---

## ⚠️ 개발자 참고 사항

- `@Service`, `@Repository`, `@Controller` 어노테이션을 빠뜨리면 Spring이 Bean으로 인식하지 못합니다.
- Spring Data JPA는 `findByUsername()`, `findById()` 같은 규칙 기반 메서드를 자동으로 지원하지만, 반드시 `Repository` 인터페이스에 명시해야 합니다.
- `/detail/{id}`에서 `@PathVariable Long`을 사용할 때, 문자열을 전달하면 400 에러가 발생합니다. 이럴 땐 `@ControllerAdvice`를 활용해 글로벌 예외 처리를 구성하는 것이 좋습니다.
- Spring Security는 기본적으로 모든 경로를 차단하므로, `SecurityConfig.java`에서 명시적으로 경로를 허용해야 합니다.

---

## 📜 License

This project is licensed under the MIT License.
