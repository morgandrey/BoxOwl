package com.example.boxowl.models

import java.io.Serializable


/**
 * Created by Andrey Morgunov on 13/11/2020.
 */

data class Order (
    var OrderId: Long = 0,
    var CourierId: Long = 0,
    var ClientName: String = "",
    var ClientSurname: String = "",
    var ClientPhone: String = "",
    var DeliveryAddress: String = "",
    var OrderDate: String = "",
    var OrderDescription: String = "",
    var OrderStatusId: Long = 0,
    var OrderRating: Long = 0,
    var CourierReward: Double = 0.0,
    var Products: List<Product>? = null
) : Serializable