package RecyclerViewHelpers

import Modelo.tbTutoria
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import rodrigo.cordova.prueba.R

class Adaptador(var Datos: List<tbTutoria>):RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //unir rcv con card--------------
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_card, parent, false)
        return ViewHolder(vista)
    }

    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //controlar card===========
       // val item = Datos[position]
       // holder.txtNombreCard.text = item.
    }
}