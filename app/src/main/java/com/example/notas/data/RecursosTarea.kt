package com.example.notas.data

class RecursosTarea(val id: Int, val uri: String, val tipo: String) {
    constructor(uri: String, tipo: String): this(0,uri,tipo)
}