package com.giftech.terbit.ui.pages.notificationinbox

sealed class NotificationInboxEvent {
    
    data class MarkAsRead(
        val notificationId: Int,
        val idLink: Int,
    ) : NotificationInboxEvent()
    
}