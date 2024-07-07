package rodrigo.cordova.prueba.ui.home

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.provider.MediaStore.Video.Media
import android.view.LayoutInflater
import android.view.PixelCopy.Request
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.activity.result.ActivityResult
import androidx.appcompat.app.AlertDialog
import androidx.core.content.contentValuesOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import rodrigo.cordova.prueba.R
import rodrigo.cordova.prueba.databinding.FragmentHomeBinding
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.sql.DriverManager
import java.sql.SQLException
import java.util.UUID
import javax.annotation.meta.When

class HomeFragment : Fragment() {
    val codigo_opcion_galeria = 102
    lateinit var imageView: ImageView
    lateinit var miPath:String
    val uuid = UUID.randomUUID().toString()

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //Lo que pasa al abrir la galeria----------------------------------------------------------------------
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK){
            when(requestCode){
                codigo_opcion_galeria ->{
                    val imageUri: Uri? = data?.data
                    imageUri?.let {
                        val imageBitmap = MediaStore.Images.Media.getBitmap(contentResolver, it)
                        subirimagenFirebase(imageBitmap) { url ->
                            miPath = url
                            imageView.setImageURI(it)

                        }
                    }
                }



            }
        }

    }

    private fun subirimagenFirebase(bitmap: Bitmap, onSuccess: (String) -> Unit) {
        val storageRef = Firebase.storage.reference
        val imageRef = storageRef.child("images/${uuid}.jpg")
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        val uploadTask = imageRef.putBytes(data)

        uploadTask.addOnFailureListener {
            Toast.makeText(requireContext(), "Error al subir la imagen", Toast.LENGTH_SHORT).show()

        }.addOnSuccessListener { taskSnapshot ->
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                onSuccess(uri.toString())
            }
        }

    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

            //aqui empieza el codigo///////////////////////////////////////////////////



        //mando a llamar
        val btnAgregar = root.findViewById<Button>(R.id.btnAgregar)
        val rcvTutorias = root.findViewById<RecyclerView>(R.id.rcvTutorias)


        //boton agregar
        btnAgregar.setOnClickListener{
            val builder = AlertDialog.Builder(requireContext())

            //Creo el contenido del alertdialog------------------------------------
            val txtNuevoNombreTutoria = EditText(requireContext())
            val imgNuevaTutoria = ImageView(requireContext())

            val textView = TextView(requireContext()).apply {
                text = "Nombre de la tutoría"
            }

            val btnSeleccionarFoto = Button(requireContext()).apply {
                text = "Seleccionar una fotografia"
                layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            }

            val imgFoto = ImageView(requireContext()).apply {
                layoutParams = ViewGroup.LayoutParams(100, 100).apply{
                    top = 20
                }
                visibility = ImageView.GONE
            }

            //Seleccionar una foto///////////////////////////////////////////////
            btnSeleccionarFoto.setOnClickListener{
               val intent = Intent(Intent.ACTION_PICK)
                intent.type  = "image/*"
                startActivityForResult(intent, codigo_opcion_galeria)
            }

            //junto todo
            val layout = LinearLayout(requireContext()).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(50,200,50,10)
                addView(textView)
                addView(txtNuevoNombreTutoria)
                addView(btnSeleccionarFoto)
                addView(imgFoto)

              addView(imgNuevaTutoria)

            }
            //todo junto lo pongo en el alert
            builder.setView(layout)
            builder.show()


        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null


    }


}