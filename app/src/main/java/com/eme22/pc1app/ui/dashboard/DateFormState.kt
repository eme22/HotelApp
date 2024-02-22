package com.eme22.pc1app.ui.dashboard

/**
 * Data validation state of the login form.
 */
data class DateFormState(
    val dateError: Boolean = false,
    val emailError: Boolean = false,
    val phoneError: Boolean = false,
    val isDataValid: Boolean = false
)