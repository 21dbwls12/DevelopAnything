# 안드로이드 

## [서버 통신] Open API 앱

### 목적
Room, ViewModel, Flow, Compose, Camera

### 제출 일자

2024년 6월 10일 02:08:28

### 문제 설명

 <p>공공데이터 Open API, GitHub API 같은 Open API를 이용하여 서버 통신을 구현해보세요.</p>
 <br/>
 <p>2주 프로젝트로 학습하며 해보세요 🙂</p>
 <p>commit을 지속적으로 관리해보세요</p>

### 조건

 <p>선택: Ktor, Retrofit, 기타 서버 통신 Lib</p>
 <p>Open API 접근을 위한 Key 값은 GitHub에 올리지 않아야합니다.</p>
 <p>중요한 Key 값을 감추는 방법도 고민해보세요.</p>

### 사용한 함수

```kotlin
// 앱 수준 그래들
plugins{
    // ktor-serialization
    id("kotlinx-serialization")
}
dependencies {
    //Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.google.code.gson:gson:2.10.1")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    // ktor
    implementation("io.ktor:ktor-server-core:2.3.11")
    implementation("io.ktor:ktor-server-netty:2.3.11")
    implementation("io.ktor:ktor-client-core:2.3.11")
    implementation("io.ktor:ktor-client-cio:2.3.11")
    implementation("io.ktor:ktor-client-logging:2.3.11")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.11")
    testImplementation("io.ktor:ktor-client-mock:2.3.11")
    // ktor-serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.11")
}
```
[Karlo 2.1 관련 kakao developers 공식 사이트](https://developers.kakao.com/docs/latest/ko/karlo/rest-api#text-to-image-response)  
[Ktor 관련 코틀린 공식 사이트](https://ktor.io/docs/client-serialization.html#send_data)  
[Retrofit 관련 공식 사이트](https://square.github.io/retrofit/)  

### 피드백

### 사진
#### Retrofit2
![Screenshot_20240612_184212_DevelopAnything](https://github.com/21dbwls12/DevelopAnything/assets/139525941/0ea3748d-6829-4118-9845-54f6da9d2d31) |![Screenshot_20240612_184147_DevelopAnything](https://github.com/21dbwls12/DevelopAnything/assets/139525941/e3630737-f4ae-4c99-a99f-41cb90dd17a8)
--- | --- |

#### Ktor
![Screenshot_20240610_011033_DevelopAnything](https://github.com/21dbwls12/DevelopAnything/assets/139525941/268c9660-6e05-49e0-9af7-6c778f371d13) |![Screenshot_20240610_010154_DevelopAnything](https://github.com/21dbwls12/DevelopAnything/assets/139525941/e2ae6dfd-f5cb-478c-9675-0d9e0460bd4d)  |![Screenshot_20240610_005654_DevelopAnything](https://github.com/21dbwls12/DevelopAnything/assets/139525941/c29fd7a4-dcf9-479a-ab67-f12127f731ac)
--- | --- | --- |


