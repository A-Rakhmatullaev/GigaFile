package com.example.gigafile.domain.models

import com.example.gigafile.domain.models.core.Data

class Directory(
    override val name: String,
    override val size: String,
    val itemSize: String
    ) : Data()