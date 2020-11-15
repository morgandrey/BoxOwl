package com.example.boxowl.models


/**
 * Created by Andrey Morgunov on 13/11/2020.
 */

data class Order (
    var ClientId: Long = 0,
    var ClientName: String = "",
    var ClientSurname: String = "",
    var ClientPhone: String = "",
    var DeliveryAddress: String = "",
    var OrderDate: String = "",
    var OrderDescription: String = "",
    var OrderStatusId: Long = 0,
    var Products: List<Product>? = null
)