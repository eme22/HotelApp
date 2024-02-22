package com.eme22.pc1app.data

import com.eme22.pc1app.data.api.HotelApiService
import com.eme22.pc1app.data.model.LoggedInUser
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    private val hotelApiService = HotelApiService.create()

    suspend fun login(username: String, password: String): Result<LoggedInUser> {
        try {

            val user = hotelApiService.getUserByEmail(username)

            if (user.correoElectronico == null)
                return Result.Error(IOException("Usuario No Existe"))

            if (user.password != password)
                return Result.Error(IOException("Contraseña Incorrecta"))

            val fakeUser = LoggedInUser(username, user.nombre!!, password)

            return Result.Success(fakeUser)

        } catch (e: Throwable) {
            return Result.Error(IOException("Usuario o Contraseña Incorrectas", e))
        }
    }

    fun logout() {

    }
}