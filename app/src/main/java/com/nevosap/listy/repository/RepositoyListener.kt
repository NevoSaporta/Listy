package com.nevosap.listy.repository

interface RepositoyListener<T> {
    fun onSuccess(element:T)
    fun onFailure(error:Throwable)
}