package com.example.gigafile.presentation.utils.adapters.core

// TODO: Change to include fields: items, diffUtil
interface BaseAdapter <T> {
    /**
     * Sets value in adapter
     *
     * @param newItems - collection of new items, that will be fed to adapter
     */
    fun data(newItems: Collection<T>)

    /**
     * Returns value from adapter
     *
     * @return Collection<T> return values present in adapter
     */
    fun data(): Collection<T>

    /**
     * Sets callback in adapter
     *
     * @param callback - object that contains functions, that can be called within ViewHolder
     */
    fun callback (callback: BaseAdapterCallback<T>)
}