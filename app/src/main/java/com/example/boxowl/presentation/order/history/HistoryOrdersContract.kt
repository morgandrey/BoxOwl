package com.example.boxowl.presentation.order.history

import com.example.boxowl.models.Order


/**
 * Created by Andrey Morgunov on 11/02/2021.
 */

interface HistoryOrdersContract {
    interface View {
        fun onSuccess(dataset: List<Order>)
        fun onError(error: String)
    }

    interface Presenter {
        fun loadHistoryOrders(courierId: Long)
    }
}