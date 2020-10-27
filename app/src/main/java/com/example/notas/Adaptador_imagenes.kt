package com.example.notas

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class adaptador_imagenes(
    var contexto: Context,
    private var lista_Imagenes: ArrayList<Foto>,
    var inflador: LayoutInflater =
        contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
):  RecyclerView.Adapter<adaptador_imagenes.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var vista_previa: ImageView
        var descripcion: TextView

        init {
            vista_previa = itemView.findViewById<View>(R.id.vista_previa) as ImageView
            //vista_previa.scaleType = ImageView.ScaleType.CENTER_INSIDE
            descripcion = itemView.findViewById<View>(R.id.descripcion_previa) as TextView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = inflador.inflate(R.layout.elemento_selector, null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val foto: Foto = lista_Imagenes[position]
        holder.descripcion.text = foto.description
        holder.vista_previa.setImageBitmap(foto.Photo)
    }

    override fun getItemCount(): Int {
     return lista_Imagenes.size
    }
}