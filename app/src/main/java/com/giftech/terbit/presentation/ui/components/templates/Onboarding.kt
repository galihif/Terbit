package com.giftech.terbit.presentation.ui.components.templates

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.giftech.terbit.presentation.ui.components.atoms.PrimaryButton
import com.giftech.terbit.presentation.ui.components.enums.HeroEnum
import com.giftech.terbit.presentation.ui.components.molecules.HeroColumn
import kotlinx.coroutines.delay

@ExperimentalMaterial3Api
@Composable
fun Onboarding(
    modifier: Modifier = Modifier,
    onBack: (() -> Unit)? = null,
    onNext: () -> Unit,
    hero:HeroEnum,
) {
    Scaffold(
        topBar = {
            if (onBack != null) {
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                )
            }
        },
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            HeroColumn(
                title = hero.title,
                description = hero.description,
                imageRes = hero.image
            )
            PrimaryButton(
                text = "Selanjutnya",
                onClick = onNext,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}@ExperimentalMaterial3Api
@Composable
fun Onboarding(
    modifier: Modifier = Modifier,
    onBack: (() -> Unit)? = null,
    onNext: () -> Unit,
    hero:HeroEnum,
    imageHeight: Int? = null,
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            if (onBack != null) {
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                )
            }
        },
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            HeroColumn(
                title = hero.title,
                description = hero.description,
                imageRes = hero.image,
                imageHeight = imageHeight
            )
            content()
            PrimaryButton(
                text = "Selanjutnya",
                onClick = onNext,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


@ExperimentalMaterial3Api
@Composable
fun OnboardLoading(
    onNext: () -> Unit,
    hero: HeroEnum
) {
    LaunchedEffect(key1 = true) {
        delay(3000)
        onNext()
    }
    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HeroColumn(
                title = hero.title,
                description = hero.description,
                imageRes = hero.image,
                modifier = Modifier.weight(1f)
            )
            CircularProgressIndicator(
                modifier = Modifier.size(48.dp),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}