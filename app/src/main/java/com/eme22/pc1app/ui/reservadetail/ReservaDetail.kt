package com.eme22.pc1app.ui.reservadetail

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eme22.pc1app.data.model.Reserva
import com.eme22.pc1app.data.model.User
import com.eme22.pc1app.databinding.FragmentReservaDetailBinding
import java.time.OffsetDateTime
import java.util.Collections

class ReservaDetail : Fragment() {

    companion object {
        fun newInstance() = ReservaDetail()
    }

    private lateinit var viewModel: ReservaDetailViewModel

    private var _binding: FragmentReservaDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentReservaDetailBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this)[ReservaDetailViewModel::class.java]

        return binding.root
    }


    fun interface OnReservaCreated {
        fun onReservaCreated(reserva : Reserva)
    }


}