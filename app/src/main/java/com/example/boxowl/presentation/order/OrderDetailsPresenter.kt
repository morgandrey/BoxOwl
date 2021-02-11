package com.example.boxowl.presentation.order

import com.example.boxowl.models.Order
import com.example.boxowl.remote.OrderService
import com.example.boxowl.remote.Service
import com.example.boxowl.utils.showAPIErrors
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


/**
 * Created by Andrey Morgunov on 08/02/2021.
 */

class OrderDetailsPresenter(private val view: OrderDetailsContract.View) :
    OrderDetailsContract.Presenter {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private lateinit var orderService: OrderService

    override fun takeOrder(order: Order) {
        orderService = Service.orderService
        compositeDisposable.add(
            orderService.takeOrder(order)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { response ->
                        if (response.code() == 200) {
                            view.onSuccess()
                        } else {
                            view.onError(response.message())
                        }
                    },
                    {
                        view.onError(showAPIErrors(it))
                    })
        )
    }

    fun onDestroy() {
        compositeDisposable.clear()
    }
}