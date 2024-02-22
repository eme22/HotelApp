package com.eme22.pc1app.data.adapter

 import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.eme22.pc1app.data.api.HotelApiService.Companion.BASE_URL
import com.eme22.pc1app.data.model.Sucursal
import com.eme22.pc1app.data.model.TipoHabitacion
import com.eme22.pc1app.databinding.ItemImageBinding
import com.squareup.picasso.Picasso


class SucursalAdapter(private val sucursales: MutableList<Sucursal>, private val listener: (Sucursal) -> Unit) :
    RecyclerView.Adapter<SucursalAdapter.SucursalViewHolder>() {

    fun addSucursal(sucursal: Sucursal) {
        sucursales.add(sucursal)
        notifyItemInserted(sucursales.size - 1)
    }

    fun removeSucursal(sucursal: Sucursal) {
        val index = sucursales.indexOf(sucursal)
        sucursales.removeAt(index)
        notifyItemRemoved(index)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateSucursales(sucursales: List<Sucursal>) {
        this.sucursales.clear()
        this.sucursales.addAll(sucursales)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SucursalViewHolder {

        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SucursalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SucursalViewHolder, position: Int) {
        holder.bind(sucursales[position])
    }

    override fun getItemCount(): Int {
        return sucursales.size
    }

    inner class SucursalViewHolder(val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(sucursal: Sucursal) {

            val circularProgressDrawable = CircularProgressDrawable(binding.root.context)
            circularProgressDrawable.apply {
                strokeWidth = 5f
                centerRadius = 30f
                start()
            }

            binding.textViewNombre.text = sucursal.nombre

            Picasso.get().load(BASE_URL + sucursal.imagen).fit().centerCrop().placeholder(circularProgressDrawable).into(binding.imageViewImagen)

            itemView.setOnClickListener { listener(sucursal) }

        }
    }
}

class TipoHabitacionAdapter(private val tipoHabitaciones: MutableList<TipoHabitacion>, private val listener: (TipoHabitacion) -> Unit) :
    RecyclerView.Adapter<TipoHabitacionAdapter.TipoHabitacionViewHolder>() {

    fun addTipoHabitacion(tipoHabitacion: TipoHabitacion) {
        tipoHabitaciones.add(tipoHabitacion)
        notifyItemInserted(tipoHabitaciones.size - 1)
    }

    fun removeTipoHabitacion(tipoHabitacion: TipoHabitacion) {
        val index = tipoHabitaciones.indexOf(tipoHabitacion)
        tipoHabitaciones.removeAt(index)
        notifyItemRemoved(index)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateTipoHabitaciones(tipoHabitaciones: List<TipoHabitacion>) {
        this.tipoHabitaciones.clear()
        this.tipoHabitaciones.addAll(tipoHabitaciones)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TipoHabitacionViewHolder {

        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TipoHabitacionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TipoHabitacionViewHolder, position: Int) {
        holder.bind(tipoHabitaciones[position])

    }

    override fun getItemCount(): Int {
        return tipoHabitaciones.size
    }

    inner class TipoHabitacionViewHolder(val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tipoHabitacion: TipoHabitacion) {

            val circularProgressDrawable = CircularProgressDrawable(binding.root.context)
            circularProgressDrawable.apply {
                strokeWidth = 5f
                centerRadius = 30f
                start()
            }

            println(BASE_URL + tipoHabitacion.imagen)
            println(tipoHabitacion.nombre)


            binding.textViewNombre.text = tipoHabitacion.nombre

            Picasso.get().load(BASE_URL + tipoHabitacion.imagen).fit().centerCrop().placeholder(circularProgressDrawable).into(binding.imageViewImagen)

            itemView.setOnClickListener { listener(tipoHabitacion) }

        }
    }
}

/*
class HabitacionAdapter(private val habitaciones: MutableList<Habitacion>, private val listener: (Habitacion) -> Unit) :
    RecyclerView.Adapter<HabitacionAdapter.HabitacionViewHolder>() {

    fun addHabitacion(habitacion: Habitacion) {
        habitaciones.add(habitacion)
        notifyItemInserted(habitaciones.size - 1)
    }

    fun removeHabitacion(habitacion: Habitacion) {
        val index = habitaciones.indexOf(habitacion)
        habitaciones.removeAt(index)
        notifyItemRemoved(index)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateHabitaciones(habitaciones: List<Habitacion>) {
        this.habitaciones.clear()
        this.habitaciones.addAll(habitaciones)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitacionViewHolder {

        val binding = ItemSucursalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HabitacionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HabitacionViewHolder, position: Int) {
        holder.bind(habitaciones[position])
    }

    override fun getItemCount(): Int {
        return habitaciones.size
    }

    inner class HabitacionViewHolder(val binding: ItemSucursalBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(habitacion: Habitacion) {

            val circularProgressDrawable = CircularProgressDrawable(binding.root.context)
            circularProgressDrawable.apply {
                strokeWidth = 5f
                centerRadius = 30f
                start()
            }

            Picasso.get().load(BASE_URL + habitacion.imagen).placeholder(circularProgressDrawable).into(binding.imageViewImagen)

            itemView.setOnClickListener { listener(habitacion) }

        }
    }
}
*/
