package com.example.boxowl.ui.order.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.boxowl.R
import com.example.boxowl.databinding.FragmentOrderDetailsBinding
import com.wada811.viewbinding.viewBinding


class OrderDetailsFragment : Fragment(R.layout.fragment_order_details) {

    private val binding: FragmentOrderDetailsBinding by viewBinding()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_details, container, false)
    }
}