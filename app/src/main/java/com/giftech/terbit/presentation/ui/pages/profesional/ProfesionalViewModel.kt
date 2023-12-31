package com.giftech.terbit.presentation.ui.pages.profesional

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.net.URLEncoder
import javax.inject.Inject

@HiltViewModel
class ProfesionalViewModel
@Inject constructor(

) : ViewModel() {

    fun contactProfesionalByWhatsapp(context:Context) {
        val number = "6281906118253"
        val message = "Halo ahli gizi, ada yang ingin saya tanyakan"
        val url = "https://wa.me/${number}?text=${URLEncoder.encode(message,"UTF-8")}"
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(browserIntent)
    }

}