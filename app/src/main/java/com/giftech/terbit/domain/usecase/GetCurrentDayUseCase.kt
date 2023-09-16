package com.giftech.terbit.domain.usecase

import com.giftech.terbit.domain.enums.ProgramTag
import com.giftech.terbit.domain.repository.IProgramRepository
import com.giftech.terbit.domain.util.toLocalDateTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class GetCurrentDayUseCase @Inject constructor(
    private val programRepository: IProgramRepository,
) {
    
    operator fun invoke(): Flow<Pair<Int, Int>> {
        return programRepository.getAll()
            .map { programList ->
                // The weekly program opens after 7 days of pre-test
                val programFirstDayDate = programList
                    .last { it.tag == ProgramTag.PRE_TEST }
                    .completionDateInMillis.toLocalDateTime().toLocalDate()
                    .plusDays(7)
                val currentDate = LocalDate.now()
                
                val currentDay = currentDate.toEpochDay() - programFirstDayDate.toEpochDay() + 1
                val currentWeek = if (currentDay % 7 == 0L) {
                    (currentDay / 7).toInt()
                } else {
                    (currentDay / 7).toInt() + 1
                }
                val currentDayOfWeek = if (currentDay % 7 == 0L) {
                    7
                } else {
                    (currentDay % 7).toInt()
                }

                currentWeek to currentDayOfWeek
            }
    }
    
}