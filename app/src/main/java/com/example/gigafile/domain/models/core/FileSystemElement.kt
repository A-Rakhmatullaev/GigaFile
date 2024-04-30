package com.example.gigafile.domain.models.core

sealed class FileSystemElement {
    abstract val id: String

    abstract val name: String

    abstract val size: String

    override fun hashCode(): Int {
        // TODO:
        return name.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        // TODO:
        return super.equals(other)
    }
}

