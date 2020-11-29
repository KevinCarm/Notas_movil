package com.example.notas

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.*
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.notas.data.RecursosNota
import com.example.notas.data.daoNota
import com.example.notas.data.daoRecursosNota
import java.io.File
import java.io.IOException
import kotlin.jvm.Throws

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fragment_agregar_nota.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragment_agregar_nota : Fragment(),
    PopupMenu.OnMenuItemClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var vista: View
    private lateinit var title: EditText
    private lateinit var description: EditText
    private lateinit var btnSave: Button
    private lateinit var fragmentManag: FragmentManager
    private lateinit var fragmentTransaction: FragmentTransaction
    private lateinit var floating: com.google.android.material.floatingactionbutton.FloatingActionButton
    private lateinit var activity: MainActivity
    private lateinit var listaRecursos: ArrayList<RecursosNota>
    private lateinit var imagen: ImageView
    private var images: ArrayList<Foto> = ArrayList()

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

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_agregar_nota, container, false)

        initialize(vista)
        return vista
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun initialize(root: View) {
        listaRecursos = ArrayList<RecursosNota>()
        title = root.findViewById(R.id.txtNoteTitle)
        description = root.findViewById(R.id.txtDescriptionNote)
        btnSave = root.findViewById(R.id.btnAddNote)
        floating = root.findViewById(R.id.floatingNote)
        floating.setOnClickListener {
            val popup: PopupMenu = PopupMenu(getActivity(), floating)
            popup.menuInflater.inflate(R.menu.popup_menu, popup.menu)
            popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.item_add_from_gallery -> {
                        takeImageFromGallery()
                        return@OnMenuItemClickListener true
                    }
                    R.id.item_add_from_camera -> {
                        takePicture()
                        return@OnMenuItemClickListener true
                    }
                    R.id.item_save_photo -> {
                        custom_dialog()
                        return@OnMenuItemClickListener true
                    }
                }
                true
            })
            popup.show()
        }
        btnSave.setOnClickListener {
            addNote()
            addResourcesToDB()
        }
    }


    private fun takePicture() {
        val intent: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // if (intent.resolveActivity(getActivity()!!.packageManager) != null) {
        var imageFile: File? = null
        try {
            imageFile = createImage()
        } catch (e: IOException) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
        if (imageFile != null) {
            val uriImage: Uri? = getActivity()?.let {
                FileProvider.getUriForFile(
                    it,
                    "com.example.notas.fileprovider",
                    imageFile
                )
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uriImage)
        }
        startActivityForResult(intent, CAMERA_REQUEST)
        //}
    }

    var rute: String = ""

    @Throws(IOException::class)
    private fun createImage(): File {
        val imageName = "foto_"
        val directory: File? = getActivity()!!.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(imageName, ".jpg", directory)
        rute = image.absolutePath
        Toast.makeText(context, rute, Toast.LENGTH_SHORT).show()
        return image
    }


    private fun addNote() {
        try {
            context?.let {
                daoNota(it).insert(
                    Nota(
                        title.text.toString(),
                        description.text.toString()
                    )
                )
            }
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun addResourcesToDB() {
        try {
            listaRecursos.forEach {
                context?.let { context ->
                    daoRecursosNota(context)
                        .insert(it)
                }
            }
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun takeImageFromGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent, "Seleccione una imagen"),
            GALLERY_REQUEST
        )
    }

    private fun custom_dialog() {
        val dialog: Dialog? = context?.let { Dialog(it) }
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setContentView(R.layout.dialog_custom)
        val btnStop: Button? = dialog?.findViewById(R.id.btnStopRecorder)
        val btnStart: Button? = dialog?.findViewById(R.id.btnStartRecorder)
        btnStart?.setOnClickListener {
            try {

            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
        btnStop?.setOnClickListener {

        }
        dialog?.show()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == GALLERY_REQUEST && resultCode == Activity.RESULT_OK) {
            val uri: Uri? = data?.data
            listaRecursos.add(RecursosNota(uri.toString(), "image"))
            Toast.makeText(context, uri.toString(), Toast.LENGTH_SHORT).show()
        }
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            val bit: Bitmap = BitmapFactory.decodeFile(rute)
            listaRecursos.add(RecursosNota(rute, "image"))
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == CAMERA_REQUEST) {
            if (permissions.size >= 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePicture()
            }
        }
        if (requestCode == PERMISSION_WRITTE_STORAGE) {
            if (permissions.size >= 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    companion object {
        private val CAMERA_REQUEST = 123
        private val GALLERY_REQUEST = 124
        private val PERMISSION_WRITTE_STORAGE = 125

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
        fun newInstance() =
            fragment_agregar_nota().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return true
    }
}