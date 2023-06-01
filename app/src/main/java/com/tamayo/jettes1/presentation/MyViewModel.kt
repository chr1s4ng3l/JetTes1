package com.tamayo.jettes1.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tamayo.jettes1.domain.DomainData
import com.tamayo.jettes1.domain.GetDataUseCase
import com.tamayo.jettes1.utils.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MyViewModel @Inject constructor(private val getDataUseCase: GetDataUseCase) : ViewModel() {

    private val _data: MutableStateFlow<UIState<List<DomainData>>> =
        MutableStateFlow(UIState.LOADING)
    val data: StateFlow<UIState<List<DomainData>>> get() = _data

    private val _tag = MutableStateFlow("")
    val tag get() = _tag.asStateFlow()


    fun searchWithTag(query: String) {
            _tag.value = query
    }


     fun getData(tag: String) = viewModelScope.launch {
        getDataUseCase.invoke(tag).collect {
            _data.value = it
        }


    }


}