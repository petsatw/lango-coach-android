package com.example.langocoach.core.data

import java.io.InputStream

interface JsonSource {
    fun openStream(): InputStream
}