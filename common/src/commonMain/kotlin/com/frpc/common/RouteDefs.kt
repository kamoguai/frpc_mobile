package com.frpc.common

import androidx.compose.runtime.Composable
import com.frpc.common.pages.login.AddServer
import com.frpc.common.pages.main.MainPage
import com.frpc.common.pages.splash.SplashPage
import moe.tlaster.precompose.navigation.BackStackEntry
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.RouteBuilder

public class Route(
    public val route: String,
    public val page: (@Composable (BackStackEntry) -> Unit)
) {

    public fun registerRoute(builder: RouteBuilder) {
        builder.scene(route, content = page)
    }

}


public val SPLASH: Route = Route("splash") {
    SplashPage()
}
public val ADD_SERVER: Route = Route("addServer") {
    AddServer()
}

public val MAIN: Route = Route("main") {
    MainPage()
}


public object Router {

    private var navigator: Navigator? = null

    public fun initNavigation(navigator: Navigator) {
        this.navigator = navigator
    }

    public fun navigateTo(route: Route, navOptions: NavOptions? = null) {
        val nav = navigator ?: return
        val routeStr = route.route
        nav.navigate(routeStr, navOptions)
    }

    fun popback() {
        navigator?.popBackStack()
    }

}