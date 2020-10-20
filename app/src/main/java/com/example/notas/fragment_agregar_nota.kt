package com.example.notas

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.notas.data.daoNota

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fragment_agregar_nota.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragment_agregar_nota : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var vista: View
    private lateinit var txtNombre: EditText
    private lateinit var txtDescripcion: EditText
    private lateinit var botonGuardar: Button
    private lateinit var agregar_archivo: Button
    private lateinit var agregar_desde_camara: Button
    private lateinit var fragmentManag: FragmentManager
    private lateinit var fragmentTransaction: FragmentTransaction

    private  var nombre_imagen: ArrayList<String> = ArrayList()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_agregar_nota, container, false)
        botonGuardar = vista.findViewById(R.id.btAgregarNota)
        txtNombre = vista.findViewById(R.id.txtAgregarTitulo)
        txtDescripcion = vista.findViewById(R.id.txtAgregarDescripcion)
        agregar_archivo = vista.findViewById(R.id.agregar_archivo)
        agregar_desde_camara = vista.findViewById(R.id.agregar_camara)

        ObjectsSetOnClick()

        return vista
    }


    private fun ObjectsSetOnClick(){
        botonGuardar.setOnClickListener {
            try{
                context?.let { it1 -> daoNota(it1).insert(
                    Nota(
                        txtNombre.text.toString(),
                        txtDescripcion.text.toString()
                    )
                ) }
                Toast.makeText(context, "Agregados ", Toast.LENGTH_SHORT).show()
            } catch (e: Exception){
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
        agregar_desde_camara.setOnClickListener {
            val capturarFoto: capturar_foto = capturar_foto()

            val fragmentTransaction = fragmentManager!!.beginTransaction()

            fragmentManager!!.beginTransaction().replace(
                R.id.contenedor_pequeño, capturarFoto
            ).addToBackStack(null).commit()
        }
    }

    private fun obtener_desde_camara(){
        val intent: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK){
            val cPhoto = data!!.extras?.get("data") as Bitmap
        }
    }


    companion object {
        private val CAMERA_REQUEST = 123
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment fragment_agregar_nota.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            fragment_agregar_nota().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}