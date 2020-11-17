package com.example.boxowl.ui.order.history

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.boxowl.R
import com.example.boxowl.bases.FragmentInteractionListener
import com.example.boxowl.databinding.FragmentOrderHistoryBinding
import com.example.boxowl.utils.loadingSpotsDialog
import com.wada811.viewbinding.viewBinding


class OrderHistoryFragment : Fragment(R.layout.fragment_home) {

    interface OnHistoryFragmentInteractionListener : FragmentInteractionListener

    private val binding: FragmentOrderHistoryBinding by viewBinding()
    private lateinit var loadingDialog: AlertDialog
    private lateinit var listener: OnHistoryFragmentInteractionListener

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadView()
    }

    private fun loadView() {
        loadingDialog = loadingSpotsDialog(requireContext())
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(
                true
            ) {
                override fun handleOnBackPressed() {
                    val intent = Intent(Intent.ACTION_MAIN)
                    intent.addCategory(Intent.CATEGORY_HOME)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }
            })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnHistoryFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(requireContext().toString() + " must implement OnOrderHistoryFragmentInteractionListener")
        }
    }

    override fun onStart() {
        super.onStart()
        listener.setBottomNavigation(true, R.id.navigation_order_history)
        listener.setToolbarTitle(resources.getString(R.string.title_order_history))
    }
}