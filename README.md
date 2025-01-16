# 안드로이드 

## 습관 인증 앱

### 목적
Room, ViewModel, Flow, Compose, Camera

### 제출 일자

2024년 5월 28일 07:29:28

### 문제 설명

 <p>한 일을 사진 혹은 음성과 함께 저장 한다.</p>
 <p>앱을 껐다 켜도 유지가 되고, 완료 된 할일을 관 리하고 삭제할 수 있다.</p>
 <p>ex) 오운완, 미라클모닝, 1일 1노래,</p>
 <br/>
 <p>2주 프로젝트로 학습하며 해보세요 🙂</p>
 <p>commit을 지속적으로 관리해보세요</p>

### 조건

 <p>Room을 사용한 데이터 저장, ViewModel, Compose 이해, 사진 촬영 및 사진 데이터 가져오기, 결과물 git repo</p>

### 사용한 함수

```kotlin
// 프로젝트 수준 그래들
plugins {
  // Room ksp
  id("com.google.devtools.ksp") version "1.9.23-1.0.19" apply false
}
// 앱 수준 그래들
plugins{
  // Room ksp
    id("com.google.devtools.ksp")
}
dependencies {
// Navigation Compose
    val navVersion = "2.7.7"

    implementation("androidx.navigation:navigation-compose:$navVersion")

    // View Model
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")

    // Room
    val roomVersion = "2.6.1"

    implementation("androidx.room:room-runtime:$roomVersion")

    ksp("androidx.room:room-compiler:$roomVersion")

    implementation("androidx.room:room-ktx:$roomVersion")
}
```
[Navigation 관련 안드로이드 스튜디오 공식 사이트](https://developer.android.com/develop/ui/compose/navigation?hl=ko)  
[View Model 관련 안드로이드 스튜디오 공식 사이트](https://developer.android.com/topic/libraries/architecture/viewmodel?hl=ko)  
[View Model 구현 참고 사이트](https://developer.android.com/codelabs/basic-android-kotlin-compose-viewmodel-and-state?hl=ko#4)  
[Room 관련 안드로이드 스튜디오 공식 사이트](https://developer.android.com/training/data-storage/room?hl=ko)  
[Room 구현 참고 링크](https://github.com/21dbwls12/DevelopAnything/tree/005.todoList)  
[Pager 관련 안드로이드 스튜디오 공식 사이트](https://developer.android.com/develop/ui/compose/layouts/pager?hl=ko)
[Pager 이동 관련 애니메이션(크기, 위치)](https://medium.com/@mangbaam/android-compose-horizontalpager-animations-w-%EB%B6%88%ED%8B%B0-e18ab02458d8)
[Pager 이동 관련 애니메이션(부채꼴)](https://jizard.tistory.com/507)
[Pager endless scroll](https://jizard.tistory.com/508#google_vignette)

### 피드백
기기별로 화면 크기 정보 받아오기
```kotlin
//기기 높이
val density = LocalDensity.current
// Height 대신 Width 사용하면 너비
// ..Scale 사용하면 크기 전체 다 가져올 수 있을라나??(확실치 않아서 실험해봐야함)
val screenHeight = with(density) { LocalConfiguration.current.screenHeightDp.dp }

// 이렇게도 됐었는데 잘못되기도 해서 위가 더 안정적임
val screenHeight = LocalConfiguration.current.screenHeightDp.dp
```

### 사진
