package com.example.boxowl.presentation.home

import com.example.boxowl.models.Order


/**
 * Created by Andrey Morgunov on 13/11/2020.
 */

interface HomeContract {
    interface View {
        fun onSuccess(dataset: List<Order>)
        fun onError(error: String)
    }

    interface Presenter {
        fun loadOrders()
    }
}