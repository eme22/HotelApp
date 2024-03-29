package com.eme22.pc1app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.eme22.pc1app.R
import com.eme22.pc1app.data.model.Sucursal
import com.eme22.pc1app.databinding.FragmentHomeBinding
import com.eme22.pc1app.ui.sucursal.SucursalFragment

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.SucursalesCardView.setOnClickListener {
            val fTransaction = requireActivity().supportFragmentManager.beginTransaction()
            fTransaction.add(
                R.id.container,
                SucursalFragment.newInstance(),
                "SucursalFragment"
            )
            fTransaction.addToBackStack("SucursalFragment")
            fTransaction.commit()
        }

        homeViewModel.text.observe(viewLifecycleOwner) {

        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}