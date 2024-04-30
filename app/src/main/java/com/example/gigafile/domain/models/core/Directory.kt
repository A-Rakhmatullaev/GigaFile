package com.example.gigafile.domain.models.core

class Directory(
    override val id: String,
    override val name: String,
    override val size: String,
    val itemSize: String
) : FileSystemElement()