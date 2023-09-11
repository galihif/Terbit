package com.giftech.terbit.ui.pages.ffq.main

import com.giftech.terbit.domain.model.FfqFoodCategory

data class FfqMainState(
    val programId: Int,
    val foodCategoryList: List<FfqFoodCategory>,
    val isAllAnswered: Boolean,
)