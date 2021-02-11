package com.example.boxowl.ui.order.available

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.boxowl.R
import com.example.boxowl.bases.FragmentInteractionListener
import com.example.boxowl.databinding.FragmentAvailableOrdersBinding
import com.example.boxowl.models.Order
import com.example.boxowl.presentation.order.AvailableOrdersAdapter
import com.example.boxowl.presentation.order.AvailableOrdersContract
import com.example.boxowl.presentation.order.AvailableOrdersPresenter
import com.example.boxowl.utils.showToast
import com.wada811.viewbinding.viewBinding


/**
 * Created by Andrey Morgunov on 27/10/2020.
 */

class AvailableOrdersFragment : Fragment(R.layout.fragment_available_orders), AvailableOrdersContract.View {

    interface OnAvailableOrdersFragmentInteractionListener : FragmentInteractionListener

    private val binding: FragmentAvailableOrdersBinding by viewBinding()
    private lateinit var availableOrdersPresenter: AvailableOrdersPresenter
    private lateinit var listener: OnAvailableOrdersFragmentInteractionListener

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadView()
    }

    private fun loadView() {
        availableOrdersPresenter = AvailableOrdersPresenter(this)
        binding.orderRecyclerView.visibility = View.GONE
        binding.orderProgressBar.visibility = View.VISIBLE
        availableOrdersPresenter.loadOrders()
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
        if (context is OnAvailableOrdersFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(requireContext().toString() + " must implement OnAvailableOrdersFragmentInteractionListener")
        }
    }

    override fun onStart() {
        super.onStart()
        listener.setBottomNavigation(true, R.id.navigation_available_orders)
        listener.setToolbarTitle(resources.getString(R.string.title_available_orders))
    }

    override fun onDestroy() {
        availableOrdersPresenter.onDestroy()
        super.onDestroy()
    }

    override fun onSuccess(dataset: List<Order>) {
        binding.orderRecyclerView.visibility = View.VISIBLE
        binding.orderProgressBar.visibility = View.GONE
        binding.orderRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.orderRecyclerView.adapter = AvailableOrdersAdapter(dataset)
    }

    override fun onError(error: String) {
        showToast(requireContext(), error)
    }
}