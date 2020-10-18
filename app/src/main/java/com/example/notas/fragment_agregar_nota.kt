package com.example.notas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.notas.data.daoNota
import kotlinx.android.synthetic.main.fragment_agregar_nota.*

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
        botonGuardar.setOnClickListener {
            try{
                context?.let { it1 -> daoNota(it1).insert(Nota(txtNombre.text.toString(),txtDescripcion.text.toString())) }
                Toast.makeText(context,"Agregados ",Toast.LENGTH_SHORT).show()
            } catch (e: Exception){
                Toast.makeText(context,e.message,Toast.LENGTH_SHORT).show()
            }
        }
        return vista
    }


    companion object {
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