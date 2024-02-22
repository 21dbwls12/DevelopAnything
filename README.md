# 안드로이드 


## 기본 화면 전환

### 목적

Activity를 이용한 화면 전환

### 제출 일자

2024년 1월 31일 18:30:28

### 문제 설명

 <p>버튼을 누르면 화면을 이동하는 동작을 구현해주세요.</p>

### 조건 

 <p>Kotlin, Compose, 최소 3개의 Activity</p>

### 사용한 함수

```kotlin
val context = LocalContext.current
val intent = Intent(context, MainAcitivity::class.java)
context.startActivity(intent)
```

### 피드백

 <p>접근제어자 신경쓰기</p>

### 사용한 파일

- basicchangingactivity
  - MiddleActivity.kt
  - LeftActivity.kt
  - RightActivity.kt

### 사진
![스크린샷 2024-02-09 오후 9 33 27](https://github.com/21dbwls12/DevelopAnything/assets/139525941/a8dedd5c-78cf-44c1-adfb-261f0506298d) |![스크린샷 2024-02-09 오후 9 36 59](https://github.com/21dbwls12/DevelopAnything/assets/139525941/893123e4-7220-4564-a907-1e67300092bb) |![스크린샷 2024-02-09 오후 9 37 11](https://github.com/21dbwls12/DevelopAnything/assets/139525941/af142cc1-40a1-4379-bcc1-f4592219f1e8)
--- | --- | --- |
