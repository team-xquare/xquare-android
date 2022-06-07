package com.xquare.xquare_android.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.semicolon.design.Body3
import com.semicolon.design.color.primary.gray.gray900
import com.xquare.xquare_android.navigation.BottomNavigationItem

@Composable
fun BottomNavigation(
    navController: NavController,
    items: List<BottomNavigationItem>
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    Row(Modifier.fillMaxWidth()) {
        items.forEach { screen ->
            val selected =
                currentDestination?.hierarchy?.any { it.route == screen.route } == true
//            val color = TODO()
            Column(
                Modifier
                    .height(56.dp)
                    .weight(1f)
                    .clickable {
                        navController.navigate(screen.route) { popUpTo(0) }
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = screen.icon(),
                    contentDescription = null,
//                    tint = color,
                    modifier = Modifier.size(24.dp)
                )
                Body3(text = screen.label, color = gray900)
            }
        }
    }
}