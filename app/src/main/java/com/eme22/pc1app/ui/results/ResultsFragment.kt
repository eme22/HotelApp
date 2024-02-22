package com.eme22.pc1app.ui.results

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.eme22.pc1app.R
import com.eme22.pc1app.databinding.FragmentDashboardBinding
import com.eme22.pc1app.databinding.FragmentResultsBinding
import com.eme22.pc1app.ui.dashboard.DashboardViewModel

class ResultsFragment : Fragment() {

    companion object {
        fun newInstance() = ResultsFragment()
    }

    private var _binding: FragmentResultsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: ResultsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentResultsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this)[ResultsViewModel::class.java]

        val dashboardViewModel =
            ViewModelProvider(requireActivity())[DashboardViewModel::class.java]

        dashboardViewModel.dateResult.observe(viewLifecycleOwner, Observer {

            binding.resultsText.text = "Ninguno"

            val dateResult = it ?: return@Observer

            if (dateResult.email.isEmpty()) return@Observer
            if (dateResult.phone.isEmpty()) return@Observer

            binding.resultsText.text = "Fecha: "+dateResult.date+"\nCorreo: "+dateResult.email+"\n Telefono: "+dateResult.phone+"\n Tipo de Servicio:"+dateResult.type+"\n Pago en Hotel: "+ if (dateResult.payHere) "Si" else "No"

        })
        // TODO: Use the ViewModel
    }

}