package com.kostas.redone.ui.dollar

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.kostas.redone.databinding.FragmentDollarNotificationBinding
import com.kostas.redone.di.DollarApp
import com.kostas.redone.di.ViewModelFactory
import javax.inject.Inject


class DollarNotificationFragment : DialogFragment() {

    private var _binding: FragmentDollarNotificationBinding? = null
    private val binding get() = _binding!!

    private val component by lazy {
        (requireActivity().application as DollarApp).component
    }

    private lateinit var homeViewModel: HomeViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(1100, 1100)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDollarNotificationBinding.inflate(inflater, container, false)

        homeViewModel = ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]

        binding.butCancel.setOnClickListener {
            dismiss()
        }

        binding.butNotification.setOnClickListener {
            val textCheckDollar = binding.etCheckDollar.text.toString()

            try {
                val checkPointDouble = textCheckDollar.toDouble()
                homeViewModel.startDollarWorker(checkPointDouble)
                dismiss()
            } catch (e: Exception) {
                binding.etCheckDollar.error = TEXT_FOR_EDIT_ERROR
            }
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = DollarNotificationFragment()

        private const val TEXT_FOR_EDIT_ERROR = "Некорректный ввод значения!"
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}