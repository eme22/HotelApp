package com.eme22.pc1app.ui.dashboard

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eme22.pc1app.R
import com.eme22.pc1app.data.model.DateInfo
import com.eme22.pc1app.ui.login.LoginFormState
import java.util.Date
import java.util.concurrent.TimeUnit


class DashboardViewModel : ViewModel() {


    private val _dateForm = MutableLiveData<DateFormState>()
    val dateFormState: LiveData<DateFormState> = _dateForm

    private val _dateResult = MutableLiveData<DateInfo>()
    val dateResult: LiveData<DateInfo> = _dateResult

    private val _lastPressedSave = MutableLiveData<Long>()
    val lastPressedSave: LiveData<Long> = _lastPressedSave

    fun save(date: Long, email: String, phone: String, type: Int, pay: Boolean) {

        val date1 = Date(TimeUnit.SECONDS.toMillis(date))


        val result = DateInfo(
            date1,
            email,
            phone,
            type,
            pay
        )

        _dateResult.value = result;

    }

    fun dataChanged(date: Long, email: String, phone: String, type: Int, pay: Boolean) {

        if (!isEmailValid(email)) {
            _dateForm.value = DateFormState(emailError = true)
        } else if (!validCellPhone(phone)) {
            _dateForm.value = DateFormState(phoneError = true)
        } else {
            _dateForm.value = DateFormState(isDataValid = true)
        }


    }

    fun isEmailValid(email: String) : Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun validCellPhone(number: String): Boolean {
        return Patterns.PHONE.matcher(number).matches()
    }

    fun setLastPressed(last: Long) {
        _lastPressedSave.value = last
    }

}