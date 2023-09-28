package com.giftech.terbit.notif

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.giftech.terbit.domain.enums.NotificationType
import com.giftech.terbit.domain.model.UserNotification
import java.util.Calendar

object RemindersManager {
    
    fun startReminder(
        applicationContext: Context,
        reminderId: Int,
        notificationId: Int,
        notificationTitle: String,
        notificationMessage: String,
        notificationDeepLink: String?,
        notificationIdLink: Int,
        notificationType: String,
        dateTimeMillis: Long,
    ) {
        val dateTime = Calendar.getInstance().apply {
            timeInMillis = dateTimeMillis
        }
        
        if (notificationType == NotificationType.DAILY_TIPS.typeId) {
            // If the time has passed, schedule the reminder (daily-type notification) for tomorrow
            val currentDateTimeMillis = Calendar.getInstance().apply {
                set(Calendar.SECOND, 0)
            }.timeInMillis
            if (currentDateTimeMillis > dateTimeMillis) {
                dateTime.add(Calendar.DAY_OF_MONTH, 1)
            }
        }
        
        val intent =
            Intent(
                applicationContext.applicationContext,
                NotificationAlarmReceiver::class.java
            ).let { intent ->
                intent.putExtra(Constants.NotificationParams.REMINDER_ID, reminderId)
                intent.putExtra(Constants.NotificationParams.NOTIFICATION_ID, notificationId)
                intent.putExtra(Constants.NotificationParams.NOTIFICATION_TITLE, notificationTitle)
                intent.putExtra(
                    Constants.NotificationParams.NOTIFICATION_MESSAGE,
                    notificationMessage,
                )
                intent.putExtra(
                    Constants.NotificationParams.NOTIFICATION_ID_LINK,
                    notificationIdLink,
                )
                intent.putExtra(
                    Constants.NotificationParams.NOTIFICATION_DEEP_LINK,
                    notificationDeepLink,
                )
                intent.putExtra(
                    Constants.NotificationParams.NOTIFICATION_DATE_TIME_MILLIS,
                    dateTime.timeInMillis,
                )
                intent.putExtra(Constants.NotificationParams.NOTIFICATION_TYPE, notificationType)
                
                PendingIntent.getBroadcast(
                    applicationContext.applicationContext,
                    reminderId,
                    intent,
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
                )
            }
        
        val alarmManager = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setAlarmClock(
            AlarmManager.AlarmClockInfo(
                dateTime.timeInMillis,
                intent,
            ),
            intent,
        )
    }
    
    fun stopReminder(
        context: Context,
        reminderId: Int,
    ) {
        val alarmManager =
            context.applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(
            context.applicationContext,
            NotificationAlarmReceiver::class.java
        ).let { intent ->
            PendingIntent.getBroadcast(
                context.applicationContext,
                reminderId,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
            )
        }
        alarmManager.cancel(intent)
    }
    
    fun startReminder(
        context: Context,
        dailyTipsNotification: com.giftech.terbit.domain.model.Notification,
    ) {
        startReminder(
            applicationContext = context.applicationContext,
            reminderId = dailyTipsNotification.id,
            notificationId = dailyTipsNotification.id,
            notificationTitle = dailyTipsNotification.title,
            notificationMessage = dailyTipsNotification.message,
            notificationDeepLink = null,
            notificationIdLink = 0,
            notificationType = dailyTipsNotification.type.typeId,
            dateTimeMillis = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, dailyTipsNotification.triggerHours)
                set(Calendar.MINUTE, dailyTipsNotification.triggerMinutes)
                set(Calendar.SECOND, 0)
            }.timeInMillis
        )
    }
    
    fun startReminder(
        context: Context,
        userNotification: UserNotification,
    ) {
        startReminder(
            applicationContext = context.applicationContext,
            reminderId = userNotification.reminderId,
            notificationId = userNotification.notificationId,
            notificationTitle = userNotification.title,
            notificationMessage = userNotification.message,
            notificationDeepLink = userNotification.deepLink,
            notificationIdLink = userNotification.idLink,
            notificationType = userNotification.notificationType.typeId,
            dateTimeMillis = userNotification.triggerDateTimeInMillis,
        )
    }
    
}