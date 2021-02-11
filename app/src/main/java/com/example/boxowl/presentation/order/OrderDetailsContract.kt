package com.example.boxowl.presentation.order

import com.example.boxowl.models.Order


/**
 * Created by Andrey Morgunov on 08/02/2021.
 */

interface OrderDetailsContract {
    interface View {
        fun onSuccess()
        fun onError(error: String)
    }

    interface Presenter {
        fun takeOrder(order: Order)
    }
}