package com.eme22.pc1app.data.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import java.io.Serializable

data class ListResponse<T>(
	val number: Int? = null,
	val last: Boolean? = null,
	val size: Int? = null,
	val numberOfElements: Int? = null,
	val totalPages: Int? = null,
	val pageable: Pageable? = null,
	val sort: Sort? = null,
	val content: List<T>? = null,
	val first: Boolean? = null,
	val totalElements: Int? = null,
	val empty: Boolean? = null
) : Serializable

@Parcelize
data class User(
	val password: String? = null,
	val reservas: List<Reserva?>? = null,
	val apellido: String? = null,
	val tipoUsuario: Int? = null,
	val id: String? = null,
	val nombre: String? = null,
	val correoElectronico: String? = null,
	val dni: Int? = null,
	val numeroTelefono: String? = null
) : Parcelable

@Parcelize
data class Habitacion(
	val estado: String? = null,
	val sucursal: Sucursal? = null,
	val numeroHabitacion: Int? = null,
	val id: String? = null,
	val tipoHabitacion: TipoHabitacion? = null
) : Parcelable

@Parcelize
data class TipoHabitacion(
	val descripcion: String? = null,
	val cantidadPersonas: Int? = null,
	val imagen: String? = null,
	val precioPorHora: Int? = null,
	val id: String? = null,
	val precioPorDia: Int? = null,
	val nombre: String? = null
) : Parcelable

@Parcelize
data class Sucursal(
	val direccion: String? = null,
	val imagen: String? = null,
	val id: String? = null,
	val nombre: String? = null,
	val numeroTelefono: String? = null
) : Parcelable

@Parcelize
data class Reserva(
	val fechaCheckOut: String? = null,
	val usuario: String? = null,
	val id: String? = null,
	val habitacion: Habitacion? = null,
	val fechaCheckIn: String? = null
) : Parcelable


@Parcelize
data class Pageable(
	val paged: Boolean? = null,
	val pageNumber: Int? = null,
	val offset: Int? = null,
	val pageSize: Int? = null,
	val unpaged: Boolean? = null,
	val sort: Sort? = null
) : Parcelable

@Parcelize
data class Sort(
	val unsorted: Boolean? = null,
	val sorted: Boolean? = null,
	val empty: Boolean? = null
) : Parcelable

