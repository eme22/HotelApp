package com.eme22.pc1app.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eme22.pc1app.R
import com.eme22.pc1app.data.model.Habitacion

class PisoRecyclerViewAdapter (
    private val pisos: HashMap<Int, List<Habitacion>>,
    private val clickListener: (Habitacion)->Unit
    ) :
    RecyclerView.Adapter<PisoRecyclerViewAdapter.PisoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PisoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_piso, parent, false)
        return PisoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PisoViewHolder, position: Int) {
        val piso = pisos[position+1]
        if (piso != null) {
            holder.bind(position+1, piso)
        }
    }

    override fun getItemCount(): Int {
        return pisos.size
    }

    inner class PisoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val rvHabitacion: RecyclerView = itemView.findViewById(R.id.rvHabitacion)
        private val txtPiso: TextView = itemView.findViewById(R.id.txtPiso)

        fun bind(piso: Int, habitaciones: List<Habitacion>) {

            println("Piso: $piso")
            println("Habitaciones: $habitaciones")

            txtPiso.text = "Piso ${piso}"
            rvHabitacion.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)

            val habitacionAdapter = HabitacionRecyclerViewAdapter(habitaciones){ habitacion ->
                clickListener(habitacion)
            }
            rvHabitacion.adapter = habitacionAdapter
        }
    }
}