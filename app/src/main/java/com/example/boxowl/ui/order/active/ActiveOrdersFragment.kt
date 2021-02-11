package com.example.boxowl.ui.order.active

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.boxowl.R
import com.example.boxowl.bases.FragmentInteractionListener
import com.example.boxowl.databinding.FragmentActiveOrdersBinding
import com.example.boxowl.models.CurrentCourier
import com.example.boxowl.models.Order
import com.example.boxowl.presentation.order.*
import com.example.boxowl.utils.showToast
import com.wada811.viewbinding.viewBinding


class ActiveOrdersFragment : Fragment(R.layout.fragment_active_orders), ActiveOrdersContract.View {

    interface OnActiveOrdersFragmentInteractionListener : FragmentInteractionListener

    private val binding: FragmentActiveOrdersBinding by viewBinding()
    private lateinit var listener: OnActiveOrdersFragmentInteractionListener
    private lateinit var activeOrdersPresenter: ActiveOrdersPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadView()
    }

    private fun loadView() {
        activeOrdersPresenter = ActiveOrdersPresenter(this)
        binding.orderRecyclerView.visibility = View.GONE
        binding.orderProgressBar.visibility = View.VISIBLE
        activeOrdersPresenter.loadActiveOrders(CurrentCourier.courier.CourierId)
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
        if (context is OnActiveOrdersFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(requireContext().toString() + " must implement OnActiveOrdersFragmentInteractionListener")
        }
    }

    override fun onStart() {
        super.onStart()
        listener.setBottomNavigation(true, R.id.navigation_active_orders)
        listener.setToolbarTitle(resources.getString(R.string.title_active_orders))
    }

    override fun onDestroy() {
        activeOrdersPresenter.onDestroy()
        super.onDestroy()
    }

    override fun onSuccess(dataset: List<Order>) {
        binding.orderRecyclerView.visibility = View.VISIBLE
        binding.orderProgressBar.visibility = View.GONE
        binding.orderRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.orderRecyclerView.adapter = ActiveOrdersAdapter(dataset)
    }

    override fun onError(error: String) {
        showToast(requireContext(), error)
    }
}