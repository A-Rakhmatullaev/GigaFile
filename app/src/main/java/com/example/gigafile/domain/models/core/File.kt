package com.example.gigafile.domain.models.core

class File (
    override val id: String,
    override val name: String,
    override val size: String
) : FileSystemElement()