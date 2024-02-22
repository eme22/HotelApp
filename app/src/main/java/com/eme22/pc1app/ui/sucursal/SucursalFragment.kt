package com.eme22.pc1app.ui.sucursal

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.eme22.pc1app.data.adapter.SucursalAdapter
import com.eme22.pc1app.databinding.FragmentSucursalBinding


class SucursalFragment : Fragment() {

    companion object {
        fun newInstance() = SucursalFragment()
    }

    private lateinit var viewModel: SucursalViewModel

    private var _binding: FragmentSucursalBinding? = null

    private var lastTitle: String? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSucursalBinding.inflate(inflater, container, false)
        val root: View = binding.root

        (activity as AppCompatActivity?)!!.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity?)!!.supportActionBar?.setDisplayShowHomeEnabled(true)

        lastTitle = (activity as AppCompatActivity?)!!.supportActionBar?.title.toString()

        (activity as AppCompatActivity?)!!.supportActionBar?.title = ""


        viewModel = ViewModelProvider(this).get(SucursalViewModel::class.java)
        // TODO: Use the ViewModel

        viewModel.sucursales.observe(viewLifecycleOwner, Observer { sucursales ->

            binding.catergoryNoItems.visibility = if (sucursales.isEmpty()) View.VISIBLE else View.GONE

            binding.categoryItemProgressBar.visibility = View.GONE

            var sucursalAdapter = SucursalAdapter(sucursales.toMutableList()) { sucursal ->
                // Manejar el clic en el elemento de la lista aquí
            }

            binding.categoryFragmentRecyler.apply {
                layoutManager = GridLayoutManager(requireContext(),2)
                adapter = sucursalAdapter
            }
        })

        viewModel.obtenerSucursales()

        return root
    }

    override fun onDestroyView() {

        (activity as AppCompatActivity?)!!.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as AppCompatActivity?)!!.supportActionBar?.setDisplayShowHomeEnabled(false)
        (activity as AppCompatActivity?)!!.supportActionBar?.title = lastTitle

        super.onDestroyView()

    }

}