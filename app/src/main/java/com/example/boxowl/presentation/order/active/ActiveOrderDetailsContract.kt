package com.example.boxowl.presentation.order.active

import com.example.boxowl.models.Order


/**
 * Created by Andrey Morgunov on 15/02/2021.
 */

class ActiveOrderDetailsContract {
    interface View {
        fun onSuccess()
        fun onError(error: String)
    }

    interface Presenter {
        fun completeOrder(order: Order)
        fun cancelOrder(order: Order)
    }
}