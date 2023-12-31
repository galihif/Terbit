package com.giftech.terbit.presentation.ui.pages.onboarding

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.giftech.terbit.domain.util.Constants
import com.giftech.terbit.presentation.ui.components.enums.HeroEnum
import com.giftech.terbit.presentation.ui.components.templates.Onboarding
import com.giftech.terbit.presentation.ui.route.Screen

@Composable
fun FfqOnboardingScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    FfqOnboardingContent(
        navController = navController,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FfqOnboardingContent(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    Onboarding(
        hero = HeroEnum.FfqOnboard1,
        onNext = {
            navController.navigate(
                Screen.FfqMain.createRoute(programId = Constants.ProgramId.FIRST_FFQ)
            )
        },
        onBack = {
            navController.popBackStack()
        },
        modifier = modifier,
    )
}