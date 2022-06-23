package com.kostas.redone.ui.dollar

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.kostas.redone.databinding.FragmentHomeBinding
import com.kostas.redone.di.DollarApp
import com.kostas.redone.di.ViewModelFactory
import com.kostas.redone.ui.adapter.DollarInfoAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val component by lazy {
        (requireActivity().application as DollarApp).component
    }

    private lateinit var viewModel: HomeViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val adapter = DollarInfoAdapter()

        binding.rvDollarPriceList.adapter = adapter

        viewModel = ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.dollarFlow.collect {
                adapter.submitList(it)
            }
        }

        binding.srlHomeSwipeRefreshLayout.setOnRefreshListener {
            viewModel.getMonthDollarPrice()
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.dollarFlow.collect {
                    adapter.submitList(it)
                    binding.srlHomeSwipeRefreshLayout.isRefreshing = false
                }
            }
        }

        binding.buttonAddNotification.setOnClickListener {
            val dialog = DollarNotificationFragment()

            dialog.show(requireActivity().supportFragmentManager, "notification")
        }

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}