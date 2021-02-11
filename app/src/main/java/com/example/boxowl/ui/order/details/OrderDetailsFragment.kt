package com.example.boxowl.ui.order.details

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.boxowl.R
import com.example.boxowl.bases.FragmentInteractionListener
import com.example.boxowl.databinding.FragmentOrderDetailsBinding
import com.example.boxowl.models.CurrentCourier
import com.example.boxowl.models.Order
import com.example.boxowl.presentation.order.OrderDetailsContract
import com.example.boxowl.presentation.order.OrderDetailsPresenter
import com.example.boxowl.ui.extension.onClick
import com.example.boxowl.utils.showToast
import com.wada811.viewbinding.viewBinding


class OrderDetailsFragment : Fragment(R.layout.fragment_order_details), OrderDetailsContract.View {

    private val binding: FragmentOrderDetailsBinding by viewBinding()

    interface OnOrderDetailsFragmentInteractionListener : FragmentInteractionListener

    private lateinit var listener: OnOrderDetailsFragmentInteractionListener
    private lateinit var orderDetailsPresenter: OrderDetailsPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        orderDetailsPresenter = OrderDetailsPresenter(this)
        loadCurrentOrder()
    }

    private fun loadCurrentOrder() {
        val bundle = arguments
        val order = bundle!!.getSerializable("order") as Order
        listener.setToolbarTitle("Order details №${order.OrderId}")
        with(binding) {
            clientTextView.text = order.ClientName + " " + order.ClientSurname
            telTextView.text = order.ClientPhone
            addressTextView.text = order.DeliveryAddress
            orderDescriptionTextView.text = order.OrderDescription
            ratingTv.text = getString(R.string.rating_text, order.OrderRating.toString())
            costTextView.text = getString(
                R.string.cost_text,
                (0.05 * order.Products!!.sumByDouble { it.ProductCost }).toString()
            )
            takeOrderBtn.onClick {
                order.CourierId = CurrentCourier.courier.CourierId
                orderDetailsPresenter.takeOrder(order)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnOrderDetailsFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(requireContext().toString() + " must implement OnOrderDetailsFragmentInteractionListener")
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
        orderDetailsPresenter.onDestroy()
        super.onDestroy()
    }

    override fun onError(error: String) {
        showToast(requireContext(), error)
    }
}