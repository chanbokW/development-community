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

### Why JPA?
- JdbcTemplate, mybatis를 사용했을 때 sql 중복이 발생 및 수정하기 어려움
- Database 중심적으로 프로그램을 짜게됨
- 반복적인 crud 작업을 진행

앞으로 진행하면서 적용해볼 계획  
  - QueryDSL

## 🤔 Contribution
### DB 설계 및 Entity 설계 
- 진행 중
### Spring Data JPA
- 진행 중
### Session && JWT
