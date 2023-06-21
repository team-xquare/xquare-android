package com.xquare.xquare_android.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.semicolon.design.Body3
import com.semicolon.design.color.primary.gray.gray300
import com.semicolon.design.color.primary.gray.gray800
import com.semicolon.design.color.primary.white.white
import com.xquare.xquare_android.navigation.BottomNavigationItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun BottomNavigation(
    navController: NavController,
    items: List<BottomNavigationItem>,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Row(Modifier.fillMaxWidth()) {
        items.forEach { screen ->
            val selected =
                currentDestination?.hierarchy?.any { it.route == screen.route } == true
            val color = if (selected) gray800 else gray300
            Column(
                Modifier
                    .height(56.dp)
                    .weight(1f)
                    .background(color = white)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(radius = 28.dp),
                    ) {
                        navController.navigate(screen.route) {
                            popUpTo(0) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = screen.icon(),
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(24.dp)
                )
                Body3(text = screen.label, color = color)
            }
        }
    }
}