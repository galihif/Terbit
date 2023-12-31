package com.giftech.terbit.presentation.receiver.pushnotification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.giftech.terbit.domain.enums.NotificationType
import com.giftech.terbit.domain.usecase.UpdateShownStatusUserNotificationUseCase
import com.giftech.terbit.presentation.util.Constants
import com.giftech.terbit.presentation.util.RemindersManager
import com.giftech.terbit.presentation.util.buildNotification
import com.giftech.terbit.presentation.util.goAsync
import com.giftech.terbit.presentation.util.show
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class PushNotificationReceiver : BroadcastReceiver() {
    
    @Inject
    lateinit var updateShownStatusUserNotificationUseCase: UpdateShownStatusUserNotificationUseCase
    
    override fun onReceive(
        context: Context,
        intent: Intent,
    ) = goAsync {
        val reminderId = intent.getIntExtra(
            Constants.NotificationExtras.REMINDER_ID,
            -1,
        )
        val notificationId = intent.getIntExtra(
            Constants.NotificationExtras.NOTIFICATION_ID,
            -1,
        )
        val notificationTitle = intent.getStringExtra(
            Constants.NotificationExtras.NOTIFICATION_TITLE
        ).orEmpty()
        val notificationMessage = intent.getStringExtra(
            Constants.NotificationExtras.NOTIFICATION_MESSAGE
        ).orEmpty()
        val notificationIdLink = intent.getIntExtra(
            Constants.NotificationExtras.NOTIFICATION_ID_LINK,
            -1
        )
        val notificationDeepLink = intent.getStringExtra(
            Constants.NotificationExtras.NOTIFICATION_DEEP_LINK,
        )
        val notificationDateTimeMillis = intent.getLongExtra(
            Constants.NotificationExtras.NOTIFICATION_DATE_TIME_MILLIS,
            -1,
        )
        val notificationType = intent.getStringExtra(
            Constants.NotificationExtras.NOTIFICATION_TYPE
        ).orEmpty().let {
            NotificationType.fromTypeId(it)
        }
        
        if (notificationType == NotificationType.DAILY_TIPS) {
            // Reschedule the reminder (daily-type notification) for tomorrow
            RemindersManager.startReminder(
                applicationContext = context.applicationContext,
                reminderId = reminderId,
                notificationId = notificationId,
                notificationTitle = notificationTitle,
                notificationMessage = notificationMessage,
                notificationDeepLink = notificationDeepLink,
                notificationIdLink = notificationIdLink,
                notificationType = notificationType.typeId,
                dateTimeMillis = Calendar.getInstance().apply {
                    timeInMillis = notificationDateTimeMillis
                    add(
                        Calendar.DAY_OF_MONTH,
                        1
                    )
                }.timeInMillis,
            )
        } else {
            // Update shown status for user notification
            updateShownStatusUserNotificationUseCase(
                notificationId = notificationId,
                idLink = notificationIdLink,
            )
        }
        
        // Show notification
        val notification = buildNotification(
            context = context,
            channelId = notificationType.typeId,
            id = reminderId,
            title = notificationTitle,
            message = notificationMessage,
        )
        notification.show(
            context = context,
            id = reminderId,
        )
    }
    
}