package com.giftech.terbit.domain.usecase

import com.giftech.terbit.domain.model.UserNotification
import com.giftech.terbit.domain.repository.IUserNotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetEligibleUserNotificationListUseCase @Inject constructor(
    private val userNotificationRepository: IUserNotificationRepository,
) {
    
    operator fun invoke(): Flow<List<UserNotification>> {
        return userNotificationRepository.getAll()
            .map { userNotificationList ->
                userNotificationList
                    .filter {
                        it.activeStatus && it.shownStatus
                    }
                    .sortedByDescending {
                        it.triggerDateTimeInMillis
                    }
            }
    }
    
}