package com.example.gigafile.presentation.utils.adapters.core

interface BaseViewHolder <T> {
    fun bind(item: T, position: Int, vararg values: Any)
}