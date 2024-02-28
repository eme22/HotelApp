package com.eme22.pc1app.data.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.eme22.pc1app.R
import com.eme22.pc1app.data.model.ESTADO_HABITACION
import com.eme22.pc1app.data.model.Habitacion
import com.eme22.pc1app.databinding.ItemHabitacionBinding
import com.eme22.pc1app.ui.login.LoginActivity

interface HabitacionClickListener{
    fun onHabitacionClick(habitacion: Habitacion)
}

class HabitacionRecyclerViewAdapter(
    private val habitaciones: List<Habitacion>,
    private val clickListener:(Habitacion)->Unit) :
    RecyclerView.Adapter<HabitacionRecyclerViewAdapter.HabitacionViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitacionViewHolder {

        val binding = ItemHabitacionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HabitacionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HabitacionViewHolder, position: Int) {
        val habitacion = habitaciones[position]
        holder.bind(habitacion)
    }

    override fun getItemCount(): Int {
        return habitaciones.size
    }

    inner class HabitacionViewHolder(val binding: ItemHabitacionBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bind(habitacion: Habitacion) {
                binding.numeroHabitacion.text = habitacion.numeroHabitacion.toString()

                when (habitacion.estado) {
                    ESTADO_HABITACION.DISPONIBLE -> {

                        binding.root.setOnClickListener {
                            val position = adapterPosition
                            if (position != RecyclerView.NO_POSITION) {
                                val habitacion = habitaciones[position]
                                clickListener(habitacion)
                            }
                        }

                        binding.ivBackground.setImageDrawable(
                            AppCompatResources.getDrawable(
                                binding.root.context,
                                R.drawable.puerta_fondo_disponible
                            )
                        )
                    }

                    ESTADO_HABITACION.NO_DISPONIBLE -> {
                        binding.ivBackground.setImageDrawable(
                            AppCompatResources.getDrawable(
                                binding.root.context,
                                R.drawable.puerta_fondo_nodisponible
                            )
                        )
                    }

                    ESTADO_HABITACION.RESERVADA_SINPAGO -> {
                        binding.ivBackground.setImageDrawable(
                            AppCompatResources.getDrawable(
                                binding.root.context,
                                R.drawable.puerta_fondo_reservadasinpago
                            )
                        )
                    }

                    else -> {}
                }
        }
    }
}