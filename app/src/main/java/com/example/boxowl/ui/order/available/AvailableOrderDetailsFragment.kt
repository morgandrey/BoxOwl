package com.example.boxowl.ui.order.available

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.boxowl.R
import com.example.boxowl.bases.FragmentInteractionListener
import com.example.boxowl.databinding.FragmentAvailableOrderDetailsBinding
import com.example.boxowl.models.CurrentCourier
import com.example.boxowl.models.Order
import com.example.boxowl.presentation.order.available.AvailableOrderDetailsContract
import com.example.boxowl.presentation.order.available.AvailableOrderDetailsPresenter
import com.example.boxowl.ui.extension.onClick
import com.example.boxowl.utils.showToast
import com.wada811.viewbinding.viewBinding


class AvailableOrderDetailsFragment : Fragment(R.layout.fragment_available_order_details),
    AvailableOrderDetailsContract.View {

    private val binding: FragmentAvailableOrderDetailsBinding by viewBinding()

    interface OnAvailableOrderDetailsFragmentInteractionListener : FragmentInteractionListener

    private lateinit var listener: OnAvailableOrderDetailsFragmentInteractionListener
    private lateinit var availableOrderDetailsPresenter: AvailableOrderDetailsPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        availableOrderDetailsPresenter = AvailableOrderDetailsPresenter(this)
        loadCurrentOrder()
    }

    private fun loadCurrentOrder() {
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
            takeOrderBtn.onClick {
                order.CourierId = CurrentCourier.courier.CourierId
                availableOrderDetailsPresenter.takeOrder(order)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnAvailableOrderDetailsFragmentInteractionListener) {
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
            .navigate(R.id.activeOrdersFragment)
    }

    override fun onDestroy() {
        availableOrderDetailsPresenter.onDestroy()
        super.onDestroy()
    }

    override fun onError(error: String) {
        showToast(requireContext(), error)
    }
}