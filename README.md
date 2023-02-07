
# 프로젝트

# 목표
처음에는 개발지식 공유 SNS서비스를 구현하자는 목적이였으나 지금까지 단순 이론만 공부한 내용을 직접 활용해보고 그때 그때 학습한 새롭게 알게된 기술이나 테크닉을 녹여 진행하는 프로젝트입니다. <br>
* 유용한 기능을 개발하는 것보다 새롭게 알게된 기술들로 재구현 초점을 두었습니다.
* 기획력이나 설계능력을 기르는 것보다 지금까지 배운기술을 정리하는데 집중
* Git branch 전략 사용
* 프로젝트 진행사항 Github Issue 관리
## 목차
* ###  [1. 요구 사항](#1-요구-사항)
* ### [2. 사용했던 기술스택](#2-기술-스택)
* ### [3. 프로젝트하면서 있었던 Issue와 고민점](#3-프로젝트하면서-있었던-issue와-고민점-1)
* ### [4. 개선 할 점 or 배운점](#4-개선-할-점-or-배운점-1)
* ### [5. erd](#5-erd-1)
* ### [6. api명세](#6-api-명세)

# 1. 요구 사항

## 주요 도메인
1. 회원 : 게시물 조회 및 등록, 수정, 삭제를 할 수 있는 회원
2. 게시물 : 게시물과 게시물의 정보를 등록하고 관리
## 1. 회원 관리
* 회원가입

  ➡️ 이메일을 ID로 사용합니다.

* 회원 로그인 및 인증

  ➡️ JTW 토큰을 발급받으며, 이를 추후 사용자 인증으로 사용합니다.

* 로그아웃은 프론트엔드에서 처리.

## 2. 게시물 관리
* 게시물 생성

  ➡️ 제목, 내용, 해시태그 등을 입력하여 생성합니다. <br>
  ➡️ 제목, 내용, 해시태그는 필수 입력사항이며, 작성자 정보는 request body에 존재하지 않고, 해당 API 를 요청한 인증정보에서 추출하여 등록 합니다. <br>
  ➡️ 해시태그는 1개이상 10개 이하로 입력받을 수 있으며 리스트 형태로 데이터를 받는다. <br>


* 게시물 수정

  ➡️ 작성자만 수정 할 수 있습니다.

* 게시물 목록

  ➡️ 모든 사용자는 모든 게시물에 보기권한이 있습니다.<br>
  ➡️ 게시글 목록에는 제목, 작성자, 작성일, 좋아요 수, 조회수 가 포함됩니다.<br>
  - **아래 기능은 쿼리 파라미터로 구현. ex) ?search=..&orderBy=..**  (예시이며 해당 변수는 직접 설정)
  - 아래 4가지 동작은 각각 동작 할 뿐만 아니라, 동시에 적용될 수 있어야 합니다.

  ➡️ Searching (= 검색)

  사용자는 입력한 키워드로 해당 키워드를 포함한 게시물을 조회할 수 있습니다.<br>
  **Like 검색, 해당 키워드가 문자열 중 포함 된 데이터 검색** <br>
  검색 방법 1.  some-url?search=후기 >>  “후기" 가 제목에 포함된 게시글 목록.<br>
  [ex. 후기 검색 시 > 00 방문후기 입니다. (검색 됨)]

  ➡️ Pagination (= 페이지 기능)

  사용자는 1 페이지 당 게시글 수를 조정할 수 있습니다. (default: 10건)

* 게시물 상세보기

  ➡️ 모든 사용자는 모든 게시물에 보기권한이 있습니다.<br>
  ➡️ 작성자를 포함한 사용자는 본 게시글에 좋아요를 누를 수 있습니다.
  좋아요된 게시물에 다시 좋아요를 누르면 취소됩니다. <br>
  ➡️ 작성자 포함한 사용자가 게시글을 상세보기 하면 조회수가 1 증가합니다. (횟수 제한 없음)

* 게시물 삭제

  ➡️ 작성자만 삭제 할 수 잇습니다.<br>
  ➡️ 작성자는 삭제된 게시물을 다시 복구 할 수 있습니다

- 본 요구사항에 명시 되지 않은 내용은 자유롭게 진행하였습니다.

# 2. 기술 스택

#### Spring Boot

* Spring Web
* Spring Data JPA
* Spring Security
* JUnit5
* Lombok
* H2 Database
* MySQL Driver
#### etc
* QueryDsl
* Docker

# 3. 프로젝트하면서 있었던 Issue와 고민점
* JPA 페이징과 N + 1 문제
  * 문제
    * 게시물(Article Entity)에서 다수의 OneToMany관계 존재
    * OneToMany 관계의 Entity가 필요할 경우 fetch join을 사용
    * 게시물 조회시 페이징네이션 상황이 필요
    * 쿼리에서 limit이 생략되는 문제가 발생

  * 원인
    * OneToMany관계에서 fetch Join과 페이징네이션을 함 계 사용할 경우 어플리케이션에서 limit을 수행 함 만약 데이터가 많을 경우 oom(OutOfMemoryError)가 발생

  * 해결
    * hibernate.default_batch_fetch_size설정을 통해 in 쿼리로 리펙토링 및 성능 향상  
    * 두 개의 쿼리로 N+1 문제 해결
* 해시태그 다중 조회 쿼리 개선
  * 문제, 원인
    * 게시물 생성 시 태그 조회 쿼리 다수 발생
  * 해결
    * 하나 씩 조회쿼리에서 in절로 조회된 태그와 생성 할 태그 비교 후 없는 태그 생성하도록 변경하였습니다.

* 클라이언트에서 넘어온 요청 데이터는 controller에서 모두 검증하는게 맞을까?
# 4. 개선 할 점 or 배운점
* ### 개선 할 점
  * rest API 문서화
    * Spring Rest Docs 적용
  * CI/CD를 적용하여 배포자동화
  * 일정 수준이상 테스트커버리지 유지하기
* ### 배운점
  * 코드에 대한 유지보수 및 안정감을 높이기 위해 테스트코드를
    작성했습니다. 그러인해 그전 프로젝트에 오류를 찾는 비율이 테스트코드 작성 후 현저히 낮아지고 개발 생상선이 증가한 경험을 하였습니다.
  *
## 5. ERD
* [ERD](https://www.erdcloud.com/d/sqgFoRnp3xtw2jCbi)
#### 데이터베이스 테이블 설계시 고려사항
* 데이터 무결성
* 적절한 연관관계
* 객체지향 vs 데이터 중심

## 프로젝트 구조
패키지 구조는 도메인 단위로 디렉토리를 다음과 같이 구성하였습니다.
- 도메인(게시물, 회원 등)
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


# 6. API 명세
### 1. 회원관리
* POST `/api/v1/auth/login'
  request
    ```json
    {
        "email":"cksqhr4961@naver.com",
        "password":"book1234!"
    }
    ```
  response
    ```json
        {
    "status": "OK",
    "message": "로그인 후 토큰 발급",
    "data": {
        "accessToken":      "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJja3NxaHI0OTYxQG5hdmVyLmNvbSIsInJvbGUiOiJST0xFX01FTUJFUiIsIm1lbWJlcklkIjoxLCJpYXQiOjE2NzE5NjI3MTksImV4cCI6MTY3MTk2NjMxOX0.z4YlIvnV0vzc9E8Qz8CzQAuAQesdyoS7K4vEYcni_vtQZZ5k09B_hapBGPLVR9ozmbZqV10HAhslkR2UjN-XGA",
        "refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJja3NxaHI0OTYxQG5hdmVyLmNvbSIsInJvbGUiOiJST0xFX01FTUJFUiIsIm1lbWJlcklkIjoxLCJpYXQiOjE2NzE5NjI3MTksImV4cCI6MTY3MjU2NzUxOX0.T8jAl6OmSeXxt4kCDcShnIloznxcQXOPwFpWaYmEM8fyuufe7tMro0LeqXulfPkImubDr5S52DdTUyY3zRuVIg"
        }
    }
    ```
* POST `/api/v1/members/signup` <br>
  request
    ```json
    {
        "email":"test1234@test.com",
        "password":"test1234!@",
        "nickname":"Java"
    }
    ```
  response
    ```json
    {
        "status": "CREATED",
        "message": "회원생성 API입니다.",
        "data": 1
    }
    ```

* GET `/api/v1/members/{id}`<br>
  response
    ```json
    {
    "status": "OK",
    "message": "회원 조회",
    "data": {
        "id": 1,
        "email": "cksqhr4961@naver.com",
        "nickname": "hello6",
        "role": "ROLE_MEMBER",
        "createAt": "2022-12-19 19:05:24"
        }
    }
    
    ```
* PUT `/api/v1/memebers` <br>
  request
    ```json
    Authorization: Bearer {{jwtToken}}
    {
        "password": "test1236!",
        "nickname": "hello"
    }
    ```
  response
    ```json
    {
        "status": "OK",
        "message": "회원 수정"
        "data": {
            "id": 1,
            "email": "test1234@test.com",
            "nickname": "hello",
            "role": "ROLE_MEMBER"
        }
    }
    ```
* *DELETE* `/api/v1/members` <br>
  request
    ```
     Authorization: Bearer {{jwtToken}}
    ```
  response
    ```json
    {
    "status": "OK",
    "message": "회원 탈퇴"
    }
    ```

### 2. 게시물 관리

* POST `/api/v1/articles` <br>
  request
    ```json
     Authorization: Bearer {{jwtToken}}
    {
        "title":"안녕하세요",
        "content":"핑크베놈핑크베놈핑크베놈핑크베놈",
        "tags": [
                "hel123lo","kor123eaduo"
        ]
    }
    ```
  response
    ```json
    {
        "status": "CREATED",
        "message": "게시물 생성",
        "data": 22
    }
    ```
* GET `/api/v1/articles/{id}` <br>
    ```json
    {
        "id": 1,
        "title": "안녕하세요",
        "content": "핑크베놈핑크베놈핑크베놈핑크베놈",
        "view": 2,
        "nickname": "hello",
        "likeCount": 0,
        "tags": [
            "hello",
            "koreaduo"
        ]
    }
    ```
* GET `/api/v1/articles/` <br>
    ```json
    GET /api/v1/articles?keyword=안녕&optionType=title_content&size=10
   {
        "status": "OK",
        "message": "게시물 전체 및 검색 조회",
        "data": [
            {
                "articleId": 20,
                "title": "안녕하세요",
                "content": "codkasdfo",
                "viewCount": 0,
                "memberResponse": {
                     "id": 1,
                    "email": "cksqhr4961@naver.com",
                    "nickname": "hello6",
                    "role": "ROLE_MEMBER",
                    "createAt": "2022-12-19 19:05:24"
                },
                "createAt": "2022-12-20 11:09:48"
            },
            {
                "articleId": 19,
                "title": "안녕하세요19",
                "content": "JAVAJAVAJAVAJAVAJAVAJAVAJAVAJAVAJAVAJAVA",
                "viewCount": 0,
                "memberResponse": {
                     "id": 1,
                    "email": "cksqhr4961@naver.com",
                    "nickname": "hello6",
                    "role": "ROLE_MEMBER",
                    "createAt": "2022-12-19 19:05:24"
                },
                "createAt": "2022-12-20 11:09:11"
            },
            ... data
         ]
    }
    ```
* PUT `/api/v1/articles/{articleId}` <br>
  request
    ```
    Authorization: Bearer {{jwtToken}}
    ```
  response
    ```json
    {
        "status": "NO_CONTENT",
        "message": "게시물 수정"
    }
    ```
* DELETE `/api/v1/articles/{articleId}` <br>
  request
    ```
    Authorization: Bearer {{jwtToken}}
    ```
  response
    ```json
    {
        "status": "NO_CONTENT",
        "message": "게시물 삭제"
    }
    ```

### 3. 좋아요

* POST `/api/v1/articles/{articleId}/like<br>
  request
    ```
    Authorization: Bearer {{jwtToken}}
    ```
    ```json
    {
        "status": "OK",
        "message": "좋아요",
        "data": true
    }
    or
    
    {
        "status": "OK",
        "message": "좋아요 취소",
        "data": false
    }
    ```

### 4. 댓글관리
* POST `/api/v1/comments` <br>
  request
    ```json
     Authorization: Bearer {{jwtToken}}
    ```
  response
    ```json
    {
        "status": "CREATED",
        "message": "댓글 생성",
        "data": 1
    }
    ```
* GET `/api/v1/comments/{articleId}` <br>
  response
    ```json
    {
        "status": "OK",
        "message": "게시물 댓글 조회",
        "data": 1
    }
    ```
* PATCH `/api/v1/comments/{commentId}` <br>
  request
    ```
    Authorization: Bearer {{jwtToken}}
    ```

  response
    ```json
    {
        "status": "OK",
        "message": "댓글 수정"
    }
    ```
* DELETE `/api/v1/comments/{commentId}` <br>
  request
    ```
    Authorization: Bearer {{jwtToken}}
    ```
  response
    ```json
    {
        "status": "NO_CONTENT",
        "message": "댓글 삭제"
    }
    ```    
