package com.example.notas

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.notas.data.daoFoto
import com.example.notas.data.daoNota
import java.io.InputStream

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
    private lateinit var btn_verImagen: Button
    private lateinit var activity: MainActivity

    private var imagenes: ArrayList<Foto> = ArrayList()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as MainActivity
    }


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
        btn_verImagen = vista.findViewById(R.id.btn_verImagen)

        ObjectsSetOnClick()

        return vista
    }

    private fun agregarNota_BD() {
        try {
            context?.let { it1 ->
                daoNota(it1).insert(
                    Nota(
                        txtNombre.text.toString(),
                        txtDescripcion.text.toString()
                    )
                )
            }

        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun agregarFotos_BD() {
        Toast.makeText(context, "Cantidad ${imagenes.size} ", Toast.LENGTH_SHORT).show()
        imagenes.forEach {
            context?.let { it1 -> daoFoto(it1).insert(it) }
        }
    }

    private fun ObjectsSetOnClick() {
        botonGuardar.setOnClickListener {
            try {
                agregarNota_BD()
                agregarFotos_BD()
            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            }
        }
        agregar_desde_camara.setOnClickListener {
            val intent: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, CAMERA_REQUEST)
        }
        agregar_archivo.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Image"), GALLERY_REQUEST)
        }
        btn_verImagen.setOnClickListener {
            val mostrar: mostrar_imagenes = mostrar_imagenes()
            val bundle: Bundle = Bundle()
            bundle.putParcelableArrayList("list", imagenes)
            mostrar.arguments = bundle
            activity.changeFragment(mostrar)
        }
    }


    fun custom_dialog(ima: Bitmap) {
        val dialog: Dialog? = context?.let { Dialog(it) }
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setContentView(R.layout.dialog_custom)

        val imagen: ImageView? = dialog?.findViewById(R.id.custom_image)
        val descripcion: EditText? = dialog?.findViewById(R.id.custom_description)
        val boton: Button? = dialog?.findViewById(R.id.custom_button)
        boton?.setOnClickListener {

            imagenes.add(Foto(descripcion?.text.toString(), ima))
            Toast.makeText(context, "Guardada correctamente", Toast.LENGTH_SHORT).show()
            dialog.hide()
        }
        imagen!!.setImageBitmap(ima)
        boton?.isEnabled = true
        dialog.show()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            val cPhoto = data!!.extras?.get("data") as Bitmap
            custom_dialog(cPhoto)
        }
        if (requestCode == GALLERY_REQUEST && resultCode == Activity.RESULT_OK) {
            val selectedPhotoUri = data?.data
            try {
                selectedPhotoUri?.let {
                    if (Build.VERSION.SDK_INT < 28) {
                        val bitmap = MediaStore.Images.Media.getBitmap(
                            getActivity()?.contentResolver,
                            it
                        )
                        custom_dialog(bitmap)
                    } else {
                        val source = getActivity()?.contentResolver?.let { it1 ->
                            ImageDecoder.createSource(
                                it1, it
                            )
                        }
                        val bitmap = source?.let { it1 -> ImageDecoder.decodeBitmap(it1) }
                        if (bitmap != null) {
                            custom_dialog(bitmap)
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    companion object {
        private val CAMERA_REQUEST = 123
        private val GALLERY_REQUEST = 124

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