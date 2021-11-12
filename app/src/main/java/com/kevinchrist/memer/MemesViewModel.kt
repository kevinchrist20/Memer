package com.kevinchrist.memer

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevinchrist.memer.model.MemesResponse
import com.kevinchrist.memer.utils.State
import kotlinx.coroutines.launch

class MemesViewModel : ViewModel() {
    private val repository = Repository()

    private val _memeResponse = MutableLiveData<State>()
    val memeResponse: LiveData<State> get() = _memeResponse

    init {
        viewModelScope.launch {
            _memeResponse.postValue(State.Loading)
            kotlin.runCatching { repository.getMemes() }
                .onSuccess { response ->
                    if (response.isSuccessful && response.body() != null) {
                        val results = response.body() as MemesResponse
                        _memeResponse.postValue(State.Success(results))
                    } else {
                        _memeResponse.postValue(State.Error(response.message()))
                    }
                }
                .onFailure { _memeResponse.postValue(State.Error(it.message)) }
        }
    }
}