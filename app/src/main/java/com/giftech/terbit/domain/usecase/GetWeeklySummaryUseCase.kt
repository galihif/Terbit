package com.giftech.terbit.domain.usecase

import com.giftech.terbit.domain.enums.ProgramTag
import com.giftech.terbit.domain.enums.SedenterType
import com.giftech.terbit.domain.model.FillOutAsaq
import com.giftech.terbit.domain.model.WeeklySummary
import com.giftech.terbit.domain.repository.IAsaqResponseRepository
import com.giftech.terbit.domain.repository.IProgramRepository
import com.giftech.terbit.domain.repository.IWeeklySummaryRepository
import com.giftech.terbit.domain.util.Constants
import com.giftech.terbit.domain.util.toLocalDateTime
import com.giftech.terbit.domain.util.toSinglePrecision
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class GetWeeklySummaryUseCase @Inject constructor(
    private val weeklySummaryRepository: IWeeklySummaryRepository,
    private val programRepository: IProgramRepository,
    private val asaqResponseRepository: IAsaqResponseRepository,
) {
    
    @OptIn(ExperimentalCoroutinesApi::class)
    suspend operator fun invoke(): Flow<WeeklySummary> {
        return programRepository.getAll()
            .map { programList ->
                programList
                    .filter { it.tag == ProgramTag.PRE_TEST }
                    .all { it.isCompleted }
            }
            .filter { isPreTestDone ->
                isPreTestDone
            }
            .flatMapConcat {
                programRepository.getAll()
                    .map { programList ->
                        val preTest = programList.filter {
                            it.tag == ProgramTag.PRE_TEST
                        }
                        val currentDate = LocalDate.now()
                        val preTestDate =
                            preTest.first().completionDateInMillis.toLocalDateTime().toLocalDate()
                        val day1Date = preTestDate.plusDays(Constants.BreakDays.AFTER_PRE_TEST)
                        
                        val currentDay = currentDate.toEpochDay() - day1Date.toEpochDay() + 1
                        val currentWeek = if (currentDay % 7 == 0L) {
                            (currentDay / 7).toInt()
                        } else {
                            (currentDay / 7).toInt() + 1
                        }
                        
                        currentWeek
                    }.flatMapConcat { ongoingWeek ->
                        val week = ongoingWeek - 1
                        weeklySummaryRepository.getAll()
                            .map { weeklySummaryList ->
                                // Get all weekly summaries that have not been presented
                                weeklySummaryList
                                    .filter {
                                        it.week <= week &&
                                                !it.hasPresented
                                    }
                                    .sortedBy {
                                        it.week
                                    }
                            }
                            .filter { it.isNotEmpty() }
                            .map { it.first() }
                            .flatMapConcat { weeklySummary ->
                                programRepository.getAll()
                                    .map { programList ->
                                        val weeklyProgramList = programList
                                            .filter { it.tag == ProgramTag.WEEKLY_PROGRAM }
                                            .filter { it.week == weeklySummary.week }
                                        weeklyProgramList
                                    }
                                    .filter { weeklyProgramList ->
                                        weeklyProgramList.all { it.isCompleted }
                                    }
                                    .map { weeklyProgramList ->
                                        val asaqWeeklyProgramList = weeklyProgramList
                                            .filterIsInstance(FillOutAsaq::class.java)
                                        val asaqProgramIdList = asaqWeeklyProgramList
                                            .map { it.programId }
                                        asaqProgramIdList
                                    }
                                    .flatMapConcat { asaqProgramIdList ->
                                        asaqResponseRepository.getAll()
                                            .map { asaqResponseList ->
                                                asaqResponseList
                                                    .filter { it.programId in asaqProgramIdList }
                                            }
                                            .map { asaqResponseList ->
                                                val sedentaryAverageHours = asaqResponseList
                                                    .groupBy { it.programId }
                                                    .mapValues { asaqResponseListByProgramId ->
                                                        asaqResponseListByProgramId.value.map { it.freq }
                                                            .average().toSinglePrecision()
                                                    }
                                                    .values
                                                    .average()
                                                    .div(60.0)
                                                    .toSinglePrecision()
                                                    .toFloat()
                                                
                                                val sedentaryLevel = when {
                                                    sedentaryAverageHours < 2.0 -> SedenterType.RINGAN
                                                    sedentaryAverageHours > 5.0 -> SedenterType.BERAT
                                                    else -> SedenterType.SEDANG
                                                }
                                                
                                                WeeklySummary(
                                                    week = weeklySummary.week,
                                                    hasPresented = weeklySummary.hasPresented,
                                                    sedentaryAverageHours = sedentaryAverageHours,
                                                    sedentaryLevel = sedentaryLevel,
                                                )
                                            }
                                    }
                            }
                    }
            }
    }
    
}