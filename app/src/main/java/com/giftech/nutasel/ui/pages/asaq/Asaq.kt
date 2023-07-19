package com.giftech.nutasel.ui.pages.asaq

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.giftech.nutasel.ui.components.atoms.MyFilterChips
import com.giftech.nutasel.ui.components.atoms.PrimaryButton
import com.giftech.nutasel.ui.components.enums.HariEnum
import com.giftech.nutasel.ui.components.enums.HeroEnum
import com.giftech.nutasel.ui.components.enums.TingkatAktivitasEnum
import com.giftech.nutasel.ui.components.molecules.HeroColumn

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AsaqScreen(
    onBack: () -> Unit,
) {
    var tingkatHariKerja by remember {
        mutableStateOf(TingkatAktivitasEnum.DEFAULT)
    }
    var tingkatHariLibur by remember {
        mutableStateOf(TingkatAktivitasEnum.DEFAULT)
    }
    var selectedHari by remember {
        mutableStateOf(HariEnum.HARI_KERJA)
    }
    var modalSheetOpen by remember {
        mutableStateOf(false)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "")
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            HeroColumn(
                hero = HeroEnum.AsaqOnboard3,
                imageHeight = 200
            )
            MyFilterChips(
                selected = tingkatHariKerja != TingkatAktivitasEnum.DEFAULT,
                onSelectedChange = {
                    selectedHari = HariEnum.HARI_KERJA
                    modalSheetOpen = true
                },
                text = HariEnum.HARI_KERJA.title,
                modifier = Modifier.fillMaxWidth()
            )
            MyFilterChips(
                selected = tingkatHariLibur != TingkatAktivitasEnum.DEFAULT,
                onSelectedChange = {
                    selectedHari = HariEnum.HARI_LIBUR
                    modalSheetOpen = true
                },
                text = HariEnum.HARI_LIBUR.title,
                modifier = Modifier.fillMaxWidth()
            )
            PrimaryButton(
                text = "Selesai",
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                enabled = tingkatHariKerja != TingkatAktivitasEnum.DEFAULT &&
                        tingkatHariLibur != TingkatAktivitasEnum.DEFAULT
            )
        }
        if (modalSheetOpen) {
            ModalBottomSheet(onDismissRequest = {
                modalSheetOpen = false
            }) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    MyFilterChips(
                        selected = when (selectedHari) {
                            HariEnum.HARI_KERJA -> tingkatHariKerja == TingkatAktivitasEnum.RENDAH
                            HariEnum.HARI_LIBUR -> tingkatHariLibur == TingkatAktivitasEnum.RENDAH
                            else -> false
                        },
                        onSelectedChange = {
                            when (selectedHari) {
                                HariEnum.HARI_KERJA -> tingkatHariKerja =
                                    TingkatAktivitasEnum.RENDAH

                                HariEnum.HARI_LIBUR -> tingkatHariLibur =
                                    TingkatAktivitasEnum.RENDAH

                                else -> Unit
                            }
                        },
                        text = TingkatAktivitasEnum.RENDAH.title,
                        modifier = Modifier.fillMaxWidth()
                    )
                    MyFilterChips(
                        selected = when (selectedHari) {
                            HariEnum.HARI_KERJA -> tingkatHariKerja == TingkatAktivitasEnum.SEDANG
                            HariEnum.HARI_LIBUR -> tingkatHariLibur == TingkatAktivitasEnum.SEDANG
                            else -> false
                        },
                        onSelectedChange = {
                            when (selectedHari) {
                                HariEnum.HARI_KERJA -> tingkatHariKerja =
                                    TingkatAktivitasEnum.SEDANG

                                HariEnum.HARI_LIBUR -> tingkatHariLibur =
                                    TingkatAktivitasEnum.SEDANG

                                else -> Unit
                            }
                        },
                        text = TingkatAktivitasEnum.SEDANG.title,
                        modifier = Modifier.fillMaxWidth()
                    )
                    MyFilterChips(
                        selected = when (selectedHari) {
                            HariEnum.HARI_KERJA -> tingkatHariKerja == TingkatAktivitasEnum.TINGGI
                            HariEnum.HARI_LIBUR -> tingkatHariLibur == TingkatAktivitasEnum.TINGGI
                            else -> false
                        },
                        onSelectedChange = {
                            when (selectedHari) {
                                HariEnum.HARI_KERJA -> tingkatHariKerja =
                                    TingkatAktivitasEnum.TINGGI

                                HariEnum.HARI_LIBUR -> tingkatHariLibur =
                                    TingkatAktivitasEnum.TINGGI

                                else -> Unit
                            }
                        },
                        text = TingkatAktivitasEnum.TINGGI.title,
                        modifier = Modifier.fillMaxWidth()
                    )
                    PrimaryButton(
                        text = "Selanjutnya",
                        onClick = {
                            modalSheetOpen = false
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}