package com.kevinchrist.memer.utils

import com.kevinchrist.memer.model.MemesResponse

sealed class State {
    object Loading : State()
    class Success(val result: MemesResponse) : State()
    class Error(val msg: String?) : State()
}