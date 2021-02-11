package com.example.boxowl.models

/**
 * Created by Andrey Morgunov on 25/10/2020.
 */

data class Courier(
    var CourierId: Long = 0,
    var CourierName: String = "",
    var CourierSurname: String = "",
    var CourierImage: String? = null,
    var CourierPhone: String = "",
    var CourierPassword: String? = null,
    var CourierSalt: String? = null,
    var CourierRating: Long = 0
)