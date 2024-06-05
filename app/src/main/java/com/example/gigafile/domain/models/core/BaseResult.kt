package com.example.gigafile.domain.models.core

sealed class BaseResult <out T, out K>: Result {
    class Success <T>(val data: T): BaseResult<T, Nothing>()
    class Error <K>(val err: K): BaseResult<Nothing, K>()
}