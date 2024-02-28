package com.eme22.pc1app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eme22.pc1app.data.api.HotelApiService
import com.eme22.pc1app.data.model.Habitacion
import com.eme22.pc1app.data.model.Sucursal
import com.google.gson.GsonBuilder
import kotlinx.coroutines.launch
import java.time.OffsetDateTime
import java.util.stream.Collectors


class ReservaCitaViewModel : ViewModel() {

    private val hotelApiService = HotelApiService.create()

    private val _sucursal : MutableLiveData<Sucursal> = MutableLiveData()
    val sucursal: LiveData<Sucursal> = _sucursal

    private val _fechaInicio : MutableLiveData<OffsetDateTime> = MutableLiveData()
    val fechaInicio: LiveData<OffsetDateTime> = _fechaInicio

    private val _fechaFin : MutableLiveData<OffsetDateTime> = MutableLiveData()
    val fechaFin: LiveData<OffsetDateTime> = _fechaFin

    private val _pisos: MutableLiveData<HashMap<Int, List<Habitacion>>> = MutableLiveData()
    val pisos: LiveData<HashMap<Int, List<Habitacion>>> = _pisos

    private val _validacion: MutableLiveData<Boolean> = MutableLiveData()
    val validacion: LiveData<Boolean> = _validacion

    fun setSucursal(sucursal: Sucursal){
        _sucursal.value = sucursal
    }

    fun generateSeatLists() {
        viewModelScope.launch {

            println("sucursal: ${sucursal.value?.id}")
            println("fechaInicio: ${fechaInicio.value}")
            println("fechaFin: ${fechaFin.value}")

            val habitacionesOcupadas = getHabitaciones(sucursal.value?.id!!, fechaInicio.value!!, fechaFin.value!!)

            println("habitaciones no ocupadas:"+ GsonBuilder().setPrettyPrinting().create().toJson(habitacionesOcupadas))

            if (habitacionesOcupadas.isNullOrEmpty()) return@launch

            val habitacionesPorPiso: Map<Int, MutableList<Habitacion>>? =
                habitacionesOcupadas.stream().collect(Collectors.groupingBy(Habitacion::piso))

            println("habitaciones por piso:"+ GsonBuilder().setPrettyPrinting().create().toJson(habitacionesPorPiso))

            _pisos.value = habitacionesPorPiso as HashMap<Int, List<Habitacion>>?

        }
    }

    private suspend fun getHabitaciones(sucursalId: String, fechaInicio: OffsetDateTime, fechaFin: OffsetDateTime) : List<Habitacion>? {

        val habitacionesDisponibles = hotelApiService.getHabitacionesDisponibles(sucursalId, fechaInicio, fechaFin, null, null).body()?.content

        val todasLasHabitaciones = hotelApiService.getHabitacionesBySucursal(sucursalId, null, null).body()?.content

        return todasLasHabitaciones!!.filter { habitacion ->
            habitacionesDisponibles!!.contains(habitacion)
        }

    }

    fun setFechaInicio(fechaInicio: OffsetDateTime) {
        _fechaInicio.value = fechaInicio
    }

    fun setFechaFin(fechaFin: OffsetDateTime) {
        _fechaFin.value = fechaFin
    }

}