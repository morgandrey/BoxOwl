package com.example.boxowl.ui.order.active

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.boxowl.AuthActivity
import com.example.boxowl.R
import com.example.boxowl.bases.FragmentInteractionListener
import com.example.boxowl.databinding.FragmentActiveOrderDetailsBinding
import com.example.boxowl.models.Order
import com.example.boxowl.presentation.order.ProductAdapter
import com.example.boxowl.presentation.order.active.ActiveOrderDetailsContract
import com.example.boxowl.presentation.order.active.ActiveOrderDetailsPresenter
import com.example.boxowl.ui.extension.onClick
import com.example.boxowl.utils.showToast
import com.wada811.viewbinding.viewBinding


class ActiveOrderDetailsFragment : Fragment(R.layout.fragment_active_order_details),
    ActiveOrderDetailsContract.View {

    interface OnActiveOrderDetailsFragmentInteractionListener : FragmentInteractionListener

    private val binding: FragmentActiveOrderDetailsBinding by viewBinding()
    private lateinit var listener: OnActiveOrderDetailsFragmentInteractionListener
    private lateinit var activeOrderDetailsPresenter: ActiveOrderDetailsPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activeOrderDetailsPresenter = ActiveOrderDetailsPresenter(this)
        loadView()
    }

    private fun loadView() {
        val bundle = arguments
        val order = bundle!!.getSerializable("order") as Order
        listener.setToolbarTitle(getString(R.string.order_details, order.OrderId.toString()))
        with(binding) {
            clientTextView.text = order.ClientName + " " + order.ClientSurname
            telTextView.text = order.ClientPhone
            addressTextView.text = order.DeliveryAddress
            orderDescriptionTextView.text = order.OrderDescription
            ratingTv.text = getString(R.string.rating_text, order.OrderRating.toString())
            courierRewardTextView.text = getString(
                R.string.courier_reward_text,
                order.CourierReward.toString()
            )
            completeOrderButton.onClick {
                AlertDialog.Builder(activity)
                    .setMessage(R.string.dialog_complete_order_message)
                    .setTitle(R.string.dialog_confirmation_title)
                    .setPositiveButton(R.string.dialog_ok) { _, _ ->
                        activeOrderDetailsPresenter.completeOrder(order)
                    }
                    .setNegativeButton(R.string.dialog_cancel, null)
                    .create()
                    .show()
            }
            productsRecyclerView.layoutManager = LinearLayoutManager(activity)
            productsRecyclerView.adapter = ProductAdapter(order.Products!!)
            cancelOrderButton.onClick {
                AlertDialog.Builder(activity)
                    .setMessage(R.string.dialog_cancel_order_message)
                    .setTitle(R.string.dialog_confirmation_title)
                    .setPositiveButton(R.string.dialog_ok) { _, _ ->
                        activeOrderDetailsPresenter.completeOrder(order)
                    }
                    .setNegativeButton(R.string.dialog_cancel, null)
                    .create()
                    .show()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnActiveOrderDetailsFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(requireContext().toString())
        }
    }

    override fun onStart() {
        super.onStart()
        listener.setBottomNavigationVisibility(false)
    }

    override fun onSuccess() {
        requireView().findNavController()
            .navigate(R.id.orderHistoryFragment)
    }

    override fun onDestroy() {
        activeOrderDetailsPresenter.onDestroy()
        super.onDestroy()
    }

    override fun onError(error: String) {
        showToast(requireContext(), error)
    }
}