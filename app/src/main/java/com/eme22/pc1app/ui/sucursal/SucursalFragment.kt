package com.eme22.pc1app.ui.sucursal

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.eme22.pc1app.ReservarCitaActivity
import com.eme22.pc1app.data.adapter.SucursalAdapter
import com.eme22.pc1app.databinding.FragmentSucursalBinding
import com.google.gson.GsonBuilder


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


        viewModel = ViewModelProvider(this)[SucursalViewModel::class.java]


        viewModel.sucursales.observe(viewLifecycleOwner) { sucursales ->

            print(
                "SUCUS"+GsonBuilder().setPrettyPrinting().create().toJson(sucursales))

            binding.catergoryNoItems.visibility =
                if (sucursales.isEmpty()) View.VISIBLE else View.GONE

            binding.categoryItemProgressBar.visibility = View.GONE

            val sucursalAdapter = SucursalAdapter(sucursales.toMutableList()) { sucursal ->
                // Manejar el clic en el elemento de la lista aquí
                val intent = Intent(root.context, ReservarCitaActivity::class.java)
                val bundle = Bundle()

                if (requireActivity().intent.getSerializableExtra("USUARIO") != null) {
                    val usuario = requireActivity().intent.getSerializableExtra("USUARIO")
                    bundle.putSerializable("USUARIO", usuario)
                }
                //bundle.putString("persona", person.toJson())
                //bundle.putInt("idpersona", person.id.toInt())
                bundle.putParcelable("sucursal", sucursal);
                intent.putExtras(bundle)
                root.context.startActivity(intent)
            }

            binding.categoryFragmentRecyler.apply {
                layoutManager = GridLayoutManager(requireContext(), 2)
                adapter = sucursalAdapter
            }
        }

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