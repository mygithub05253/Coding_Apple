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
