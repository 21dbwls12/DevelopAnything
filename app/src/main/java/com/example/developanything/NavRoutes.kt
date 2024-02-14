package com.example.developanything

sealed class NavRoutes(val route: String) {
    data object Greeting : NavRoutes("greeting")
    data object Home : NavRoutes("home")
    data object First : NavRoutes("first/{number}")
    // class 사용해서 route를 정의할 수도 있음.
    // 여러 값을 넘길 때 사용할 수 있을 듯.
    // 근데 위에처럼 object로 정의하면 프로그램이 실행되는 동안 값이 바꿀 수 없고 아래처럼 class로 정의하면 바뀔 수 있다는데 그건 무슨 말인지 모르겠음. 명확히 이해되지 않았음.
//    class First(val number: Int) : NavRoutes("first/{number}") {
//        fun createRoute() = "first/$number"
//    }
    data object Second : NavRoutes("second/{number}")
}