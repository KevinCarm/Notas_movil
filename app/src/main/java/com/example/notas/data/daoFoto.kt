package com.example.notas.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.Toast
import com.example.notas.Foto
import com.example.notas.Nota
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream


class daoFoto(
    val contexto: Context, private val database: AdminSQL = AdminSQL(contexto),
    var base: SQLiteDatabase = database.writableDatabase
) {
    fun insert(foto: Foto): Boolean {
        val baos = ByteArrayOutputStream(20480)
        foto.Photo?.compress(Bitmap.CompressFormat.PNG, 0, baos)
        val blob = baos.toByteArray()

        return try{
            val cv: ContentValues = ContentValues()
            val nota = getAllNote().get(getAllNote().size - 1)
            cv.put(Tabla_foto().campo_idNota,nota.idNota)
            cv.put(Tabla_foto().campo_descripcion,foto.description)
            cv.put(Tabla_foto().campo_foto,blob)
            base.insert(Tabla_foto().nombre_tabla, null,cv)
            Toast.makeText(contexto,"Imagenes guardadas correctamente",Toast.LENGTH_LONG).show()
            true
        }catch (e: Exception){
            Toast.makeText(contexto,e.message,Toast.LENGTH_LONG).show()
            false
        }

    }

    fun getAll(): ArrayList<Foto>? {
        base = database.readableDatabase
        val listaFotos: ArrayList<Foto> = ArrayList()
        try {
            val readQuery = "SELECT * FROM ${Tabla_foto().nombre_tabla}"
            val cursor: Cursor = base.rawQuery(readQuery, null)
            while (cursor.moveToNext()) {
                val blob = cursor.getBlob(3)
                val bais = ByteArrayInputStream(blob)
                val bitmap = BitmapFactory.decodeStream(bais)
                listaFotos.add(
                    Foto(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getString(2),
                        bitmap
                    )
                )
            }
            cursor.close()
        } catch (e: Exception) {

        }
        return listaFotos
    }

    fun getAllNote(): ArrayList<Nota> {
        base = database.readableDatabase
        val listaNotas: ArrayList<Nota> = ArrayList()
        try {
            val readQuery: String = "SELECT * FROM ${Tabla_nota().nombre_tabla}"
            val cursor: Cursor = base.rawQuery(readQuery, null)
            while (cursor.moveToNext()) {
                listaNotas.add(Nota(cursor.getInt(0), cursor.getString(1), cursor.getString(2)))
            }
            cursor.close()
        } catch (e: java.lang.Exception) {
            Toast.makeText(contexto, e.message, Toast.LENGTH_SHORT).show()
        }

        return listaNotas;
    }
}