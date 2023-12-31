package com.giftech.terbit.data.source.local

import com.giftech.terbit.data.source.local.statics.ArticleData
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleLocalDataSource @Inject constructor(
    private val articleData: ArticleData,
) {
    
    fun getAll() = flowOf(articleData.getAll())
    
}