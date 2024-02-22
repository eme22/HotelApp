package com.eme22.pc1app.ui.sucursal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eme22.pc1app.data.api.HotelApiService
import com.eme22.pc1app.data.model.Sucursal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SucursalViewModel : ViewModel() {

    private val hotelApiService = HotelApiService.create()

    private val _sucursales : MutableLiveData<List<Sucursal>> = MutableLiveData()
    val sucursales: LiveData<List<Sucursal>> = _sucursales

    fun obtenerSucursales() {
        viewModelScope.launch {
            try {
                val sucursalesObtenidas = withContext(Dispatchers.IO) {
                    hotelApiService.getSucursales(null, null)
                }

                if (!sucursalesObtenidas.isSuccessful) throw Exception(sucursalesObtenidas.message())

                if (sucursalesObtenidas.body() == null) throw Exception("No se encontraron sucursales")

                if (sucursalesObtenidas.body()!!.content == null) throw Exception("No se encontraron sucursales")

                if (sucursalesObtenidas.body()!!.content!!.isEmpty()) throw Exception("No se encontraron sucursales")

                _sucursales.value = sucursalesObtenidas.body()!!.content!!

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}