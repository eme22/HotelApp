package com.eme22.pc1app.data.api

import com.eme22.pc1app.data.model.Habitacion
import com.eme22.pc1app.data.model.ListResponse
import com.eme22.pc1app.data.model.Reserva
import com.eme22.pc1app.data.model.Sucursal
import com.eme22.pc1app.data.model.User
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.OffsetDateTime

interface HotelApiService {

    @GET("/usuarios/buscar-por-email/{email}")
    suspend fun getUserByEmail(@Path("email") email: String ): User?

    @GET("/usuarios")
    suspend fun getUsers(@Query("page") page: Int?, @Query("size") size: Int?): ListResponse<User>

    @GET("/sucursales")
    suspend fun getSucursales(@Query("page") page: Int?, @Query("size") size: Int?): Response<ListResponse<Sucursal>>

    @GET("/sucursales/{id}")
    suspend fun getSucursal(@Path("id") id: String): Response<Sucursal>

    @GET("habitaciones/buscar-por-sucursal")
    suspend fun getHabitacionesBySucursal(@Query("sucursalId") sucursal: String, @Query("page") page: Int?, @Query("size") size: Int?): Response<ListResponse<Habitacion>>

    @GET("habitaciones/buscar-por-piso")
    suspend fun getHabitacionesByPiso(@Query("sucursalId") sucursal: String, @Query("piso") piso: Int, @Query("page") page: Int?, @Query("size") size: Int?): Response<ListResponse<Habitacion>>

    @GET("habitaciones/buscar-por-fecha-disponible")
    suspend fun getHabitacionesDisponibles(@Query("sucursalId") sucursal: String, @Query("fechaInicio") fechaInicio: OffsetDateTime, @Query("fechaFin") fechaFin: OffsetDateTime, @Query("page") page: Int?, @Query("size") size: Int?): Response<ListResponse<Habitacion>>

    @POST("reservas")
    suspend fun crearReserva(@Body reserva: Reserva): Response<Reserva>

    @GET("habitaciones/buscar-por-numero")
    suspend fun getHabitacionPorNumero(@Query("sucursalId") sucursal: String, @Query("numero") numero: String): Response<Habitacion>

    companion object {
        const val BASE_URL = "https://hotel-ws-production.up.railway.app/"

        fun create(): HotelApiService {
            val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
            val client = OkHttpClient.Builder().addInterceptor(logger).build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(HotelApiService::class.java)
        }
    }
}