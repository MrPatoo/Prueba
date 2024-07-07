package RecyclerViewHelpers

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import rodrigo.cordova.prueba.R

class ViewHolder(view: View):RecyclerView.ViewHolder(view) {

    //Todo el contenido de viewholder
    val txtNombreCard = view.findViewById<TextView>(R.id.txtTituloCard)
    val imgTutoria = view.findViewById<ImageView>(R.id.imgTutoriaCard)
    val imgEditar = view.findViewById<ImageView>(R.id.imgEditar)
    val imgEliminar = view.findViewById<ImageView>(R.id.imgEliminar)

}