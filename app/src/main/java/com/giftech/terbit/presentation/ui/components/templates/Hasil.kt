@file:OptIn(ExperimentalMaterial3Api::class)

package com.giftech.terbit.presentation.ui.components.templates

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.giftech.terbit.presentation.ui.components.atoms.PrimaryButton
import com.giftech.terbit.presentation.ui.components.enums.HeroEnum
import com.giftech.terbit.presentation.ui.components.molecules.HeroColumn

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Hasil(
    onNext: () -> Unit = {},
    onBack: () -> Unit = {},
    hero: HeroEnum,
    bgColor: Color,
    accentColor: Color,
    nama: String,
    skorTitle: String,
    skor: String,
    kategoriTitle: String,
    kategori: String,
    desc: String,
    buttonText: String,
) {
    BackHandler() {
        onBack()
    }
    Scaffold(
        containerColor = bgColor,
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = bgColor,
                    navigationIconContentColor = accentColor,
                )
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            HeroColumn(
                title = hero.title,
                description = hero.description,
                imageRes = hero.image,
                titleColor = accentColor,
                descColor = accentColor,
                imageHeight = 200
            )
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
                shape = RoundedCornerShape(
                    topStart = 12.dp,
                    topEnd = 12.dp
                )
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(24.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = nama,
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Divider(thickness = 1.dp)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = skorTitle,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    text = skor,
                                    style = MaterialTheme.typography.labelLarge
                                )
                            }
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = kategoriTitle,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Badge(
                                    containerColor = bgColor,
                                ) {
                                    Text(
                                        text = kategori,
                                        color = accentColor,
                                        style = MaterialTheme.typography.labelLarge,
                                        modifier = Modifier.padding(
                                            horizontal = 8.dp,
                                            vertical = 4.dp
                                        )
                                    )
                                }
                            }
                        }
                        Divider(thickness = 1.dp)
                    }
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Text(
                            text = "Penjelasan",
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = desc,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    PrimaryButton(
                        text = buttonText,
                        onClick = onNext,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}