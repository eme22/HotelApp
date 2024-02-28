package com.eme22.pc1app.ui.reservadetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eme22.pc1app.data.api.HotelApiService
import com.eme22.pc1app.data.model.Reserva
import com.eme22.pc1app.data.model.Sucursal
import com.eme22.pc1app.data.model.User
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.OffsetDateTime

class ReservaDetailViewModel : ViewModel() {

    private val hotelApiService = HotelApiService.create()

    private val _reserva : MutableLiveData<Reserva> = MutableLiveData()
    val reserva: LiveData<Reserva> = _reserva
    fun crearReserva(
        numeroHabitacion: Int?,
        fecha: OffsetDateTime,
        fechaFin: OffsetDateTime,
        sucursal: Sucursal,
        usuario: User,
        precio2: Long,
        onReservaCreated: ReservaDetail.OnReservaCreated
    ) {
        viewModelScope.launch {
            val reserva2 = hotelApiService.crearReserva(
                Reserva(
                    fechaFin.toString(),
                    usuario,
                    null,
                    hotelApiService.getHabitacionPorNumero(sucursal.id!!, numeroHabitacion.toString()).body()!!,
                    fecha.toString()
                )

            )

            reserva2.let {
                it.body()?.let { it1 -> onReservaCreated.onReservaCreated(it1) }
            }

        }

    }

    fun getDefaultUser(): User {
        return runBlocking { hotelApiService.getUserByEmail("msb1999711@gmail.com")!! }
    }
}