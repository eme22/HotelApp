package com.eme22.pc1app.data.model

import java.io.Serializable
import java.util.Date

data class DateInfo(
    val date: Date,
    val email: String,
    val phone: String,
    val type: Int,
    val payHere: Boolean
) : Serializable
