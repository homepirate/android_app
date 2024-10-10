package com.example.lab1

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.lab1.databinding.FragmentOnboardBinding


class OnboardFragment : Fragment(R.layout.fragment_onboard) {

    private var _binding: FragmentOnboardBinding? = null
    private val binding get() = _binding ?: throw RuntimeException()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentOnboardBinding.bind(view)

        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(R.id.action_onboardToAuthorization)
        }, 2000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}