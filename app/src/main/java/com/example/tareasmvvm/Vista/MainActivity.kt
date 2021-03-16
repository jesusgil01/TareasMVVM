package com.example.tareasmvvm.Vista

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tareasmvvm.Adaptador.TareasAdaptador
import com.example.tareasmvvm.Modelo.Tareas
import com.example.tareasmvvm.R
import com.example.tareasmvvm.Viewmodel.TareaViewmodel
import java.util.*

class MainActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    private var viewManager = LinearLayoutManager(this)
    private lateinit var tareaViewmodel: TareaViewmodel
    private lateinit var rcvTareas: RecyclerView
    private lateinit var btnAgregar: Button

    private var dia = 0
    private var mes = 0
    private var anio = 0

    private var savedDia = 0
    private var savedMes = 0
    private var savedAnio = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rcvTareas = findViewById(R.id.rcvTarea)
        tareaViewmodel =
                ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
                .get(TareaViewmodel::class.java)
        btnAgregar = findViewById(R.id.btnAgregar)

        btnAgregar.setOnClickListener {
            agregar()
        }

        inicializarAdaptador()
    }

    private fun inicializarAdaptador() {
        rcvTareas.layoutManager = viewManager
        observarLista()
    }

    private fun observarLista() {
        tareaViewmodel.lista.observe(this, Observer {
            rcvTareas.adapter = TareasAdaptador(tareaViewmodel, it, this )
        })

        pickDate()
    }

    private fun getDateCalendar() {
        val cal = Calendar.getInstance()
        dia = cal.get(Calendar.DAY_OF_MONTH)
        mes = cal.get(Calendar.MONTH)
        anio = cal.get(Calendar.YEAR)
    }

    private fun pickDate() {
        var btnfecha = findViewById<ImageButton>(R.id.btnSeleccionarFecha)
        btnfecha.setOnClickListener {
            getDateCalendar()

            DatePickerDialog(this, this,anio,mes,dia,).show()
        }
    }

    private fun agregar() {
        var titulo = findViewById<EditText>(R.id.txtTitulo).text.toString()
        var maestro = findViewById<EditText>(R.id.txtMaestro).text.toString()
        var fecha = findViewById<TextView>(R.id.txtFecha).text.toString()

        if (titulo.isNullOrBlank()) {
            Toast.makeText(this,
                    "Agregue un título para la tarea",
                          Toast.LENGTH_SHORT)
                    .show()
        } else if (maestro.isNullOrBlank()) {
            Toast.makeText(this,
                    "Agregue un maestro para la tarea",
                    Toast.LENGTH_SHORT)
                    .show()
        } else if (fecha.isNullOrBlank()) {
            Toast.makeText(this,
                    "Agregue una fecha de entrega",
                    Toast.LENGTH_SHORT)
                    .show()
        } else {
            var  nuevaTarea = Tareas(titulo, maestro, fecha)
            tareaViewmodel.agregar(nuevaTarea)
            findViewById<EditText>(R.id.txtTitulo).text.clear()
            findViewById<EditText>(R.id.txtMaestro).text.clear()
            findViewById<TextView>(R.id.txtFecha).text = ""
            rcvTareas.adapter?.notifyDataSetChanged()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDia = dia
        savedMes = mes +1
        savedAnio = anio

        getDateCalendar()

        var txtFecha = findViewById<TextView>(R.id.txtFecha)
        txtFecha.text = "$savedDia-$savedMes-$savedAnio"
    }

}