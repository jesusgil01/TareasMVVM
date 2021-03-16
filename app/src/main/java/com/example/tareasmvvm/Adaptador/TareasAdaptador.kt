package com.example.tareasmvvm.Adaptador

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.tareasmvvm.Modelo.Tareas
import com.example.tareasmvvm.R
import com.example.tareasmvvm.Viewmodel.TareaViewmodel

class TareasAdaptador(val viewModel: TareaViewmodel,
                      var listaTareas: ArrayList<Tareas>,
                      val context: Context
                      ): RecyclerView.Adapter<TareasAdaptador.ViewHolder>() {

    var tareas: MutableList<Tareas> = ArrayList()

    inner class ViewHolder(private var view: View): RecyclerView.ViewHolder(view) {
        val itemTitulo: TextView
        val itemMaestro: TextView
        val itemFecha: TextView
        val btnItemBorrar: ImageButton
        init {
            itemTitulo = view.findViewById(R.id.itemTitulo)
            itemMaestro = view.findViewById(R.id.itemMaestro)
            itemFecha = view.findViewById(R.id.itemFecha)
            btnItemBorrar = view.findViewById(R.id.btnItemBorrar)
        }
        fun bind(tarea: Tareas){
            itemTitulo.text = tarea.titulo
            itemMaestro.text = tarea.maestro
            itemFecha.text = tarea.fecha.toString()
            btnItemBorrar.setOnClickListener{
                viewModel.eliminar(tarea)
                notifyItemRemoved(listaTareas.indexOf(tarea))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TareasAdaptador.ViewHolder {
        /*Se crea una nueva vista definida en la interfaz de usuario item_row
        * Este método es incovado por el manejador de layout*/
        var root = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_row, parent, false)
        return  ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        /*Reemplaza el contenido de las vista
        * También es invocado por el manejado de layout
        * Obtenemos el elemento de la lista con el contenido indicado en la lista*/
        holder.bind(listaTareas.get(position))
    }

    override fun getItemCount(): Int {
        return listaTareas.size
    }
}