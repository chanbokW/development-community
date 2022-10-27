## 프로젝트 목적
처음에는 개발지식 공유 sns 서비스를 구현하자는 목적이였나 
지금까지 단순 이론만 공부한 내용들을 직접 활용해 보고
그때 그때 학습한 새로운 기술이나 테크닉을 녹여 진행하는 프로젝트입니다.
- 유용한 기능을 개발하는것보다 새롭게 알게된 기술들로 재구현 초점
- 기획력이나 설계능력을 기르는 것보다 지금까지 배운기술을 정리하는데 집중


## 기술 스택
<p dir="auto"><a target="_blank" rel="noopener noreferrer nofollow" href="https://camo.githubusercontent.com/6cfe4fd1c1ac1558a4d0b6e568698e9d4b504b9e2965524a1477329b3a694592/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f4a4156412031312d3030373339363f7374796c653d666f722d7468652d6261646765266c6f676f3d4a617661266c6f676f436f6c6f723d7768697465"><img src="https://camo.githubusercontent.com/6cfe4fd1c1ac1558a4d0b6e568698e9d4b504b9e2965524a1477329b3a694592/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f4a4156412031312d3030373339363f7374796c653d666f722d7468652d6261646765266c6f676f3d4a617661266c6f676f436f6c6f723d7768697465" data-canonical-src="https://img.shields.io/badge/JAVA 11-007396?style=for-the-badge&amp;logo=Java&amp;logoColor=white" style="max-width: 100%;"></a>
<a target="_blank" rel="noopener noreferrer nofollow" href="https://camo.githubusercontent.com/78899826193229f92c7b89740dd215da047eceebac8fe96190b08e72ef2b3391/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f537072696e6720426f6f742d3644423333463f7374796c653d666f722d7468652d6261646765266c6f676f3d537072696e67266c6f676f436f6c6f723d7768697465"><img src="https://camo.githubusercontent.com/78899826193229f92c7b89740dd215da047eceebac8fe96190b08e72ef2b3391/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f537072696e6720426f6f742d3644423333463f7374796c653d666f722d7468652d6261646765266c6f676f3d537072696e67266c6f676f436f6c6f723d7768697465" data-canonical-src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&amp;logo=Spring&amp;logoColor=white" style="max-width: 100%;"></a>
<a target="_blank" rel="noopener noreferrer nofollow" href="https://camo.githubusercontent.com/7423032ac861b754d0b8e79affdb741dc61ad45299643f887952e3d8d85bc806/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f537072696e672044617461204a50412d3644423333463f7374796c653d666f722d7468652d6261646765266c6f676f436f6c6f723d7768697465"><img src="https://camo.githubusercontent.com/7423032ac861b754d0b8e79affdb741dc61ad45299643f887952e3d8d85bc806/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f537072696e672044617461204a50412d3644423333463f7374796c653d666f722d7468652d6261646765266c6f676f436f6c6f723d7768697465" data-canonical-src="https://img.shields.io/badge/Spring Data JPA-6DB33F?style=for-the-badge&amp;logoColor=white" style="max-width: 100%;"></a>
<a target="_blank" rel="noopener noreferrer nofollow" href="https://camo.githubusercontent.com/655b6af93db1fbe85aa14df34c36a1f8028860f091c3fbfb6a87adf62de8cc35/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f5265737420446f63732d3644423333463f7374796c653d666f722d7468652d6261646765"><img src="https://camo.githubusercontent.com/655b6af93db1fbe85aa14df34c36a1f8028860f091c3fbfb6a87adf62de8cc35/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f5265737420446f63732d3644423333463f7374796c653d666f722d7468652d6261646765" data-canonical-src="https://img.shields.io/badge/Rest Docs-6DB33F?style=for-the-badge" style="max-width: 100%;"></a>

[//]: # (<a target="_blank" rel="noopener noreferrer nofollow" href="https://camo.githubusercontent.com/4e6b25950396e9a3709b56fe4f2b7c8a5ffedde00dd4c40e61d3c9f6488a7a71/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f537072696e672053656375726974792d3644423333463f7374796c653d666f722d7468652d6261646765266c6f676f3d537072696e675365637572697479266c6f676f436f6c6f723d7768697465"><img src="https://camo.githubusercontent.com/4e6b25950396e9a3709b56fe4f2b7c8a5ffedde00dd4c40e61d3c9f6488a7a71/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f537072696e672053656375726974792d3644423333463f7374796c653d666f722d7468652d6261646765266c6f676f3d537072696e675365637572697479266c6f676f436f6c6f723d7768697465" data-canonical-src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&amp;logo=SpringSecurity&amp;logoColor=white" style="max-width: 100%;"></a>)


## 🤔 Contribution

## Spring Boot(API SERVER)
    API SERVER 개발
패키지 구조는 도메인 단위로 디렉토리를 다음과 같이 구성하였습니다.
- 도메인(게시물)
  - controller
    - API 관리한다.
  - service
    - 비즈니스 로직을 관리한다.
  - repository
    - JPA + QueryDSL 관리한다.
  - dto
    - request + response dto 관리한다.
  - 도메인(엔티티)
    - 도메인의 Entity를 관리한다.

- common
  - 공통 작업들이 들어가는 클래스와 유틸리티 기능을 관리한다.
- config
  - 프로젝트의 Configuration 관리한다.

## Spring Security
    Security 설정을 추가해 인가된 사용자만 특정 API를 접근 할 수 있도록 제한한다
구조는 다음과 같이 구성하였습니다.
- Session Creation Policy : STATELESS
- CSRF : disable
- FormLogin : disable
- httpBasic : disable
- AuthenticationEntryPoint : JwtAuthenticationEntryPoint.class
- AccessDeniedHandler : JwtAccessDeniedHandler.class

## JPA / QueryDSL
    객체 중심 도메인 설계 및 반복적인 CRUD작업을 대체 및 비즈니스로직 집중한다.
- JPA : 반복적인 CRUD 작업을 대체해 간단히 DataBase 데이터를 조회한다.
- QueryDSL : Join 등 JPA에서 발생한 N + 1 문제 등 SQL은 QueryDSL로 작성한다.
  - ArticleRepository(JPA Interface)
  - ArticleCustomRepository(QueryDSL Interface)
  - ArticleCustomRepository(QueryDSL Implements Class)

- QueryDSL로 작성한 SQL문은 JunitTest 후 작성하였습니다.
```java
@DataJpaTest
public class ArticleRepositoryTest {
    ...
}
```

## JUnit
    레이어 별로 테스트를 진행하였고 로직에 집중해 테스트를 수행한다.
- domain Test
  - 비즈니스 로직에서 중요한 부분이기 때문에 테스트코드를 작성하였습니다.
    ```java
    public class TagsTest {
        ...
    }
    ```
- Service Test
  - 스프링부트 테스트로 작성하였습니다. 
    ```java
      @SpringBootTest
      @Transactional
      public class ArticleServiceTest {
            ...  
      }
    ```
- Repository Test
  - 주로 커스텀하게 작성한 쿼리 메소드나 QueryDSL로 작성한 SQL을 테스트 및 실제 쿼리가 어떻게 출력되는 지 확인하였습니다.
  ```java
    @DataJpaTest
    public class ArticleRepositoryTest {
    ...
    }
    ```
- controller
  - 진행 중

## DB 설계 및 Entity 설계 
![스크린샷](https://user-images.githubusercontent.com/82792464/198165515-f8651588-fb2d-499f-a632-dca6908ee4d7.png)

진행중인 사항
- 테스트코드 작성 진행중

