package com.example.gigafile.domain.models.core.file_system

class File (
    override val id: String,
    override val name: String,
    override val size: String,
    val isSystemElement: Boolean
) : FileSystemElement()