package com.example.gigafile.domain.models.core.file_system

sealed class FileSystemElement {
    abstract val id: String
    abstract val name: String
    abstract val size: String

    override fun hashCode(): Int {
        // TODO:
        return id.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        // TODO:
        if(other is FileSystemElement && id == other.id) return true
        if(other is FileSystemElement? && id.isNotBlank() && id == other?.id) return true
        return false
    }

    override fun toString(): String {
        return "Id: $id, Name: $name, Size: $size"
    }
}

