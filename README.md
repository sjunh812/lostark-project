# 🗡 모코코우 - 로스트아크 캐릭터 검색 앱
![ic_launcher](https://user-images.githubusercontent.com/79048895/232505420-9c47bcc8-398c-4615-bdbd-5892552ae1c7.png)
<br>

## 📷 스크린샷
<img src="https://user-images.githubusercontent.com/79048895/232504519-fc00382e-830e-462a-be0d-d2d652311e10.png" width="250" height="500" /> <img src="https://user-images.githubusercontent.com/79048895/232504536-d52b1d24-e55a-4ee2-b39c-874c0148857d.png" width="250" height="500" />
<img src="https://user-images.githubusercontent.com/79048895/232504553-1ad1d99d-5cf9-49be-a103-b62738c188a9.png" width="250" height="500" /><img src="https://user-images.githubusercontent.com/79048895/232504570-a3420ea2-4119-4ac7-b8a1-8995cd114fc1.png" width="250" height="500" />
<br>
<br>

## 📝 프로젝트 소개
### 모코코우는 로스트아크 게임 캐릭터 검색 서비스입니다.

**LostArk Open API** 에서 데이터를 받아와 캐릭터 검색을 구현하였습니다.  
검색한 캐릭터에 대한 프로필, 특성, 각인, 장비 및 장신구, 보석 그리고 카드 정보를 제공하고 있습니다.   
이외에도 `Room` 을 이용하여 검색 기록을 구현하여 보다 간편한 검색을 제공하고 있습니다.

사용된 기술스택은 아래와 같습니다.
- `Kotlin`
- `MVVM` `ViewDataBinding` `Gradle Kotlin DSL` `Clean Architecture`
- `Coroutine` `Flow`  `Hilt` `Retrofit2` `Moshi` `Lottie`
<br>

## ✍ 느낀점
`Gradle Kotlin DSL` 을 사용하여 100% `Kotlin` 언어로 개발해볼 수 있었습니다.  
또한 `Clean Arhitecture`을 적용한 presentation, data, domain 계층을 멀티 모듈로 분리하여 의존성을 줄여볼 수 있었습니다.  

**LostArk Open API** 에서 데이터를 가져왔기 때문에, 제가 원하는 구조로 데이터를 새로 변환할 필요가 있었습니다.  
그래서 **Mapper** 클래스를 따로 선언하여, 데이터를 매핑해볼 수 있었습니다.  
예를 들어, API에서는 장신구에 부여된 각인 효과 데이터를 내려주지 않기 때문에,  
tooltip 속성으로 내려오는 데이터에서 각인 효과와 관련된 데이터를 찾아 매핑하였습니다.  

프로필, 특성, 각인, 장비 및 장신구, 보석 그리고 카드와 같이 비교적 복잡한 데이터를 사용자가 한눈에 알아보기 쉽게 표현하기 위해서  
**Material Design** 의 `TextInputLayout` 과 `TextInputEditText` 그리고 `Chip` 을 적극 활용해볼 수 있었던 프로젝트였습니다.
