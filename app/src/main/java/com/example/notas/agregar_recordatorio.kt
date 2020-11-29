package com.example.notas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [agregar_recordatorio.newInstance] factory method to
 * create an instance of this fragment.
 */
class agregar_recordatorio : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var vista: View
    private lateinit var title: EditText
    private lateinit var description: EditText
    private lateinit var btn_save: Button
    private lateinit var addFile: Button
    private lateinit var addFileFromCamera: Button
    private lateinit var fragmentManag: FragmentManager
    private lateinit var fragmentTransaction: FragmentTransaction
    private lateinit var showImages: Button
    private lateinit var activity: MainActivity

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
        vista = inflater.inflate(R.layout.fragment_agregar_recordatorio, container, false)
        title = vista.findViewById(R.id.txtAgregarTituloRecordatorio)
        description = vista.findViewById(R.id.txtAgregarDescripcion_recordatorio)
        btn_save = vista.findViewById(R.id.btAgregarRecordatorio)
        addFile = vista.findViewById(R.id.agregar_archivoRecordatorio)
        addFileFromCamera = vista.findViewById(R.id.agregar_camara_recordatorio)
        showImages = vista.findViewById(R.id.btn_verImagenRecordatorio)
        return vista
    }

    private fun onClickObjects(){
        addFile.setOnClickListener {
            Toast.makeText(context,"Holi",Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment agregar_recordatorio.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            agregar_recordatorio().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}