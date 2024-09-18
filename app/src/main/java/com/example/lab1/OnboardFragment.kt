package com.example.lab1

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.View


class OnboardFragment : Fragment(R.layout.fragment_onboard) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed({
            (activity as MainActivity).showFragment(AuthorizationFragment::class.java)
        }, 2000)
    }
}
