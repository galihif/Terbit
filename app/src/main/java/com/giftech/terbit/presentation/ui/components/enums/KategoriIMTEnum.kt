package com.giftech.terbit.presentation.ui.components.enums

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import com.giftech.terbit.presentation.ui.theme.CustomColor1
import com.giftech.terbit.presentation.ui.theme.CustomColor3
import com.giftech.terbit.presentation.ui.theme.light_CustomColor2
import com.giftech.terbit.presentation.ui.theme.light_onCustomColor2
import com.giftech.terbit.presentation.ui.theme.light_onCustomColor3

enum class KategoriIMTEnum(val title: String, val desc: (String) -> String) {
    NORMAL(
        title = "Normal",
        desc = { skor ->
            "Hasil perhitungan IMT kamu mendapatkan skor $skor berada dalam kategori Normal, hasil ini cukup baik, namun harus disesuaikan dengan kegiatan kamu sehari - hari, yuk coba kita lihat status gizi kamu!"
        }
    ),
    KURUS(
        title = "Kurus",
        desc = { skor ->
            "Hasil perhitungan IMT kamu mendapatkan skor $skor berada dalam kategori Kurus, perlu diperhatikan ya, pastikan kamu mengkonsumsi makanan yang bernutrisi setiap hari!"
        }
    ),
    SANGAT_KURUS(
        title = "Sangat Kurus",
        desc = { skor ->
            "Hasil perhitungan IMT kamu mendapatkan skor $skor berada dalam kategori Sangat Kurus, perlu diperhatikan ya, pastikan kamu mengkonsumsi makanan yang bernutrisi setiap hari!"
        }
    ),
    GEMUK(
        title = "Gemuk",
        desc = { skor ->
            "Hasil perhitungan IMT kamu mendapatkan skor $skor berada dalam kategori Gemuk, wah pastikan kamu mengkonsumsi makanan bernutrisi dan menghindari makanan tinggi gula, garam, dan lemak ya, jangan lupa untuk terus aktif dengan olahraga yang kamu suka!"
        }
    ),
    OBESITAS(
        title = "Obesitas",
        desc = { skor ->
            "Hasil perhitungan IMT kamu mendapatkan skor $skor berada dalam kategori Obesitas, wah pastikan kamu mengkonsumsi makanan bernutrisi dan menghindari makanan tinggi gula, garam, dan lemak ya, jangan lupa untuk terus aktif dengan olahraga yang kamu suka!"
        }
    );
    
    val bgColor: Color
        @Composable
        @ReadOnlyComposable
        get() = when (this) {
            NORMAL -> light_CustomColor2
            KURUS -> CustomColor1
            SANGAT_KURUS -> MaterialTheme.colorScheme.inverseSurface
            GEMUK -> CustomColor3
            OBESITAS -> MaterialTheme.colorScheme.secondary
        }
    
    val accentColor: Color
        @Composable
        @ReadOnlyComposable
        get() = when (this) {
            NORMAL -> light_onCustomColor2
            KURUS -> Color.Black
            SANGAT_KURUS -> MaterialTheme.colorScheme.inverseOnSurface
            GEMUK -> light_onCustomColor3
            OBESITAS -> MaterialTheme.colorScheme.onSecondary
        }
    
    companion object {
        
        fun fromTitle(title: String): KategoriIMTEnum {
            return values().firstOrNull { it.title == title }
                ?: NORMAL
        }
        
    }
    
}