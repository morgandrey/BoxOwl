package com.example.boxowl.remote

import com.example.boxowl.models.Order
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*


/**
 * Created by Andrey Morgunov on 13/11/2020.
 */

interface OrderService {
    @GET("api/available-orders")
    fun getOrders(@Query("courierId") courierId: Long): Observable<Response<List<Order>>>

    @POST("api/order/take")
    fun takeOrder(@Body order: Order): Observable<Response<Boolean>>

    @POST("api/order/complete")
    fun completeOrder(@Body order: Order): Observable<Response<Boolean>>

    @GET("api/courier/{courierId}/active-orders")
    fun getActiveOrders(@Path("courierId") courierId: Long): Observable<Response<List<Order>>>

    @GET("api/courier/{courierId}/history-orders")
    fun getHistoryOrders(@Path("courierId") courierId: Long): Observable<Response<List<Order>>>
}