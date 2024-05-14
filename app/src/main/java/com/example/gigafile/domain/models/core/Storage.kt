package com.example.gigafile.domain.models.core

class Storage(
    override val id: String,
    override val name: String,
    override val size: String,
    val rootDirectoryPath: String
): FileSystemElement()