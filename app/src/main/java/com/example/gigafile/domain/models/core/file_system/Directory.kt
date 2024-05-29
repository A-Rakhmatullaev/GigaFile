package com.example.gigafile.domain.models.core.file_system

class Directory(
    override val id: String,
    override val name: String,
    override val size: String,
    val itemSize: String,
    val isSystemElement: Boolean,
    val containsSystemFile: Boolean
) : FileSystemElement()