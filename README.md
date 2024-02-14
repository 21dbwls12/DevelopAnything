# 안드로이드 


## Navigation Compose 를 이용한 화면 전환

### 목적
Navigation Compose 이용한 화면 전환

### 제출 일자

2024년 2월 14일 21:08:28

### 문제 설명

 <p>버튼을 누르면 화면을 이동하는 동작을 구현해주세요.</p>

### 조건

 <p>Kotlin, Compose, Navigation Compose, Single Activity</p>

### 사용한 함수

```kotlin
// navigation 그래들 추가
val navVersion = "2.7.7"

implementation("androidx.navigation:navigation-compose:$navVersion")

val navController = rememberNavController()
NavHost(
    navController = navController,
    startDestination = NavRoutes.Greeting.route
) {
    composable(NavRoutes.Greeting.route) { GreetingScreen(navController) }

    composable(NavRoutes.First.route) {
        FirstScreen(
            it.arguments?.getString("number") ?: ""
        )
    }
    composable(NavRoutes.Third(0).route) {
        ThirdScreen(
            it.arguments?.getString("number") ?: ""
        )
    }
}

sealed class NavRoutes(val route: String) {
    data object Greeting : NavRoutes("greeting")
    class Third(private val number: Int) : NavRoutes("third/{number}") {
        fun createRoute()  = "third/$number"
    }
}
// 뒤로가기 눌렀을 때 현재 화면으로는 더이상 돌아오지 않게 하기 위해 popUpTo 사용.
navController.navigate("home") {
    popUpTo("greeting") {
        inclusive = true
    }
}
```

### 사진
![스크린샷 2024-02-14 오후 9 18 38](https://github.com/21dbwls12/DevelopAnything/assets/139525941/bee9a135-e015-4f79-bf85-808ebde1711d)|![스크린샷 2024-02-14 오후 9 14 09](https://github.com/21dbwls12/DevelopAnything/assets/139525941/f76d059a-eaf3-4a8c-964b-473b556feefb)

![스크린샷 2024-02-14 오후 9 15 20](https://github.com/21dbwls12/DevelopAnything/assets/139525941/e57131bf-902b-465d-86b6-98c7ce46d29c) |![스크린샷 2024-02-14 오후 9 17 17](https://github.com/21dbwls12/DevelopAnything/assets/139525941/c53cbecf-9a28-4626-868c-b73996d8589b) |![스크린샷 2024-02-14 오후 9 15 59](https://github.com/21dbwls12/DevelopAnything/assets/139525941/ebf74517-0a77-4f48-89d9-6b2c287fb31b)
--- | --- | --- |
