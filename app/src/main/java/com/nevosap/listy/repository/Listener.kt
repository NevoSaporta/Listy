package com.nevosap.listy.repository

interface Listener<T> {
    fun onSuccess(element:T)
    fun onFailure(error:Throwable)
}