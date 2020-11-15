package com.example.boxowl.remote

import com.example.boxowl.models.Order
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET


/**
 * Created by Andrey Morgunov on 13/11/2020.
 */

interface OrderService {
    @GET("api/orders")
    fun getOrders(): Observable<Response<List<Order>>>
}