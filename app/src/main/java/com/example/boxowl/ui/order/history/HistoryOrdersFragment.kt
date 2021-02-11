package com.example.boxowl.ui.order.history

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.boxowl.R
import com.example.boxowl.bases.FragmentInteractionListener
import com.example.boxowl.databinding.FragmentOrderHistoryBinding
import com.example.boxowl.models.CurrentCourier
import com.example.boxowl.models.Order
import com.example.boxowl.presentation.order.AvailableOrdersAdapter
import com.example.boxowl.presentation.order.HistoryOrdersAdapter
import com.example.boxowl.presentation.order.HistoryOrdersContract
import com.example.boxowl.presentation.order.HistoryOrdersPresenter
import com.example.boxowl.utils.showToast
import com.wada811.viewbinding.viewBinding


class HistoryOrdersFragment : Fragment(R.layout.fragment_available_orders),
    HistoryOrdersContract.View {

    interface OnHistoryFragmentInteractionListener : FragmentInteractionListener

    private val binding: FragmentOrderHistoryBinding by viewBinding()
    private lateinit var listener: OnHistoryFragmentInteractionListener
    private lateinit var historyOrdersPresenter: HistoryOrdersPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadView()
    }

    private fun loadView() {
        historyOrdersPresenter = HistoryOrdersPresenter(this)
        binding.orderRecyclerView.visibility = View.GONE
        binding.orderProgressBar.visibility = View.VISIBLE
        historyOrdersPresenter.loadHistoryOrders(CurrentCourier.courier.CourierId)
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

    override fun onDestroy() {
        historyOrdersPresenter.onDestroy()
        super.onDestroy()
    }

    override fun onSuccess(dataset: List<Order>) {
        binding.orderRecyclerView.visibility = View.VISIBLE
        binding.orderProgressBar.visibility = View.GONE
        binding.orderRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.orderRecyclerView.adapter = HistoryOrdersAdapter(dataset)
    }

    override fun onError(error: String) {
        showToast(requireContext(), error)
    }
}