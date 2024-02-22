package com.eme22.pc1app.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.eme22.pc1app.R
import com.eme22.pc1app.databinding.FragmentDashboardBinding
import com.eme22.pc1app.ui.login.afterTextChanged
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView


class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    private var sendToResults: Boolean = false

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(requireActivity())[DashboardViewModel::class.java]

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        val calendar = binding.calendario
        val email = binding.emailEdit
        val phone = binding.telefonoEdit
        val servicio = binding.typeRadioGroup
        val payHere = binding.pagoCheck
        val normal = binding.normalRadio
        val guardar = binding.guardarButton
        val limpiar = binding.limpiarButton

        calendar.minDate = System.currentTimeMillis() - 1000

        guardar.setOnClickListener {

            dashboardViewModel.setLastPressed(System.currentTimeMillis() + 1000)

            dashboardViewModel.save(
                calendar.date,
                email.text.toString(),
                phone.text.toString(),
                servicio.checkedRadioButtonId,
                payHere.isSelected
            )



            println(dashboardViewModel.lastPressedSave.value)
        }

        dashboardViewModel.dateFormState.observe(viewLifecycleOwner, Observer {
            val dateState = it ?: return@Observer

            guardar.isEnabled = dateState.isDataValid

            if (dateState.emailError) {
                email.error = getString(R.string.invalid_email)
            }

            if (dateState.phoneError) {
                phone.error = getString(R.string.invalid_phone)
            }

        })

        dashboardViewModel.lastPressedSave.observe(viewLifecycleOwner, Observer {
            val lastPressed = it ?: return@Observer
            sendToResults = System.currentTimeMillis() <= lastPressed
        })

        dashboardViewModel.dateResult.observe(viewLifecycleOwner, Observer {

            val dateResult = it ?: return@Observer

            if (dateResult.email.isEmpty()) return@Observer
            if (dateResult.phone.isEmpty()) return@Observer

            println(sendToResults)

            if (!sendToResults) return@Observer

            val bottomNavigationView: BottomNavigationView = requireActivity().findViewById(R.id.nav_view) as BottomNavigationView
            bottomNavigationView.selectedItemId = R.id.navigation_results

        })

        email.afterTextChanged {
            dashboardViewModel.dataChanged(
                calendar.date,
                email.text.toString(),
                phone.text.toString(),
                servicio.checkedRadioButtonId,
                payHere.isSelected
            )
        }

        phone.afterTextChanged {
            dashboardViewModel.dataChanged(
                calendar.date,
                email.text.toString(),
                phone.text.toString(),
                servicio.checkedRadioButtonId,
                payHere.isSelected
            )
        }

        limpiar.setOnClickListener {
            calendar.date = System.currentTimeMillis()
            email.text.clear()
            phone.text.clear()
            normal.isChecked = true
            payHere.isSelected = false
        }

        /*
        val textView: TextView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
         */



        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}