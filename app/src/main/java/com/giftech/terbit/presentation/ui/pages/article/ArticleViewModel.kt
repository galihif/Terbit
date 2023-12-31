package com.giftech.terbit.presentation.ui.pages.article

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giftech.terbit.domain.model.Article
import com.giftech.terbit.domain.usecase.ArticleUsecase
import com.giftech.terbit.domain.usecase.CompleteProgramUseCase
import com.giftech.terbit.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel
@Inject constructor(
    private val articleUseCase: ArticleUsecase,
    private val completeProgramUseCase: CompleteProgramUseCase,
    private val userUseCase: UserUseCase
) : ViewModel() {
    
    private val _article = MutableStateFlow<Article?>(null)
    val article: StateFlow<Article?> = _article

    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName

    fun getUserName() {
        viewModelScope.launch {
            userUseCase.getUser().collect{ user ->
                _userName.value = user.nama
            }
        }
    }
    
    fun getArticleByWeekDay(week: Int, day: Int) {
        viewModelScope.launch {
            articleUseCase.getArticleByWeekDay(week, day).collect { article ->
                _article.value = article
            }
        }
    }
    
    fun complete(programId: Int) {
        viewModelScope.launch {
            completeProgramUseCase(
                programId = programId,
            )
        }
    }
    
}