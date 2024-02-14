# 안드로이드 


## Navigation Compose를 이용한 화면 전환

### 목적
Navigation Compose 이용한 화면 전환

### 제출 일자

2024년 2월 10일 16:12:28

### 문제 설명

 <p>버튼을 누르면 화면을 이동하는 동작을 구현해주세요.</p>

### 조건

 <p>Kotlin, Compose, Navigation Compose, Single Activity</p>

### 사용한 함수

```kotlin

```

### 사용한 파일

- navigation
  - NavigationActivity.kt

### 사진

## 로또번호 생성기

### 목적
기능 요구사항 수행

### 제출 일자

2024년 2월 10일 16:12:28

### 문제 설명

 <p>버튼을 누르면 매번 새로운 로또 번호 조합을 생성해주는 동작을 구현해주세요.</p>

### 조건

 <p>Kotlin, Compose, 애니메이션</p>

### 사용한 함수

```kotlin
val imageLoader = ImageLoader.Builder(context)
    .components {
        if (SDK_INT >= 28) {
            add(ImageDecoderDecoder.Factory())
        } else {
            add(GifDecoder.Factory())
        }
    }
    .build()
AsyncImage(
    model = R.raw.colorballbump,
    imageLoader = imageLoader,
    contentDescription = null,
    modifier = Modifier
        .fillMaxSize()
        .alpha(0.5f),
    contentScale = ContentScale.FillHeight
)

LaunchedEffect(key1 = true) {)

val random = (1..45).random()
Random.nextInt(20, 100)
```

### 사용한 파일

- lotto
  - LottoActivity.kt

### 사진
https://github.com/21dbwls12/DevelopAnything/assets/139525941/5b28b261-8a80-481b-8e03-6970b9c4a100

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
