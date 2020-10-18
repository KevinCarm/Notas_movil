package com.example.notas

class Nota(
    var titulo: String, var descripcion: String
) {
    constructor( idNota: Int, titulo: String, descripcion: String):this(titulo, descripcion)
}