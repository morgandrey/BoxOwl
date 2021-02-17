package com.example.boxowl.presentation.order.active

import com.example.boxowl.models.Order


/**
 * Created by Andrey Morgunov on 10/02/2021.
 */

interface ActiveOrdersContract {
    interface View {
        fun onSuccess(dataset: List<Order>)
        fun onError(error: String)
    }

    interface Presenter {
        fun loadActiveOrders(courierId: Long)
    }
}