package com.example.tareasmvvm.Viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tareasmvvm.Modelo.Tareas

class TareaViewmodel: ViewModel() {
    var lista = MutableLiveData<ArrayList<Tareas>>()
    var nuevaLista = arrayListOf<Tareas>()

    fun agregar(tarea: Tareas){
        nuevaLista.add(tarea)
        lista.value = nuevaLista
    }

    fun eliminar(tarea: Tareas){
        nuevaLista.remove(tarea)
        lista.value = nuevaLista
    }
}