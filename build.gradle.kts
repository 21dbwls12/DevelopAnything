// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.3.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.23" apply false
    // Room에서 ksp사용하기 위해 추가
    // 공식 페이지에서 나온 버전이 너무 옛날꺼라고 오류났었음. 참고 사이트는 README.md 확인
    // 앱 수준의 그래들에 플러그 인에도 하나 추가해야 함
//    id("com.google.devtools.ksp") version "1.9.23-1.0.19" apply false
}