package com.giftech.terbit.presentation.ui.components.molecules

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.giftech.terbit.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@ExperimentalMaterial3Api
@Composable
fun KTRDialog(
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
    ) {
        Surface(
            modifier = Modifier.wrapContentSize(),
            shape = MaterialTheme.shapes.large
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(
                    text = "KTR Nutrisionis"
                )
                Image(
                    painter = painterResource(id = R.drawable.ktr),
                    contentDescription = "",
                    modifier = Modifier.fillMaxWidth(),
                )
                Text(
                    text = "Tenaga Kesehatan"
                )
                Text(
                    text = "Berlaku sampai 17 Maret 2028"
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDatePickerDialog(
    initialDate: String,
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit,
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = dateStringToMillis(initialDate),
    )
    
    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""
    
    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(onClick = {
                onDateSelected(selectedDate)
                onDismiss()
            }
            
            ) {
                Text(text = "OK")
            }
        },
        dismissButton = {
            Button(onClick = {
                onDismiss()
            }) {
                Text(text = "Cancel")
            }
        }
    ) {
        DatePicker(
            state = datePickerState,
            dateValidator = { timestamp ->
                timestamp <= System.currentTimeMillis()
            }
        )
    }
}

private fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

private fun dateStringToMillis(dateString: String): Long {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return try {
        val date = formatter.parse(dateString)!!
        date.time
    } catch (e: Exception) {
        Calendar.getInstance().apply {
            set(Calendar.YEAR, 2006)
            set(Calendar.MONDAY, 0)
            set(Calendar.DAY_OF_MONTH, 1)
        }.timeInMillis
    }
}