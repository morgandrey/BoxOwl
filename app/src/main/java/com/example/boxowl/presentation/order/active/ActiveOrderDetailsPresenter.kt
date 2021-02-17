package com.example.boxowl.presentation.order.active

import com.example.boxowl.models.Order
import com.example.boxowl.remote.OrderService
import com.example.boxowl.remote.Service
import com.example.boxowl.utils.showAPIErrors
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


/**
 * Created by Andrey Morgunov on 15/02/2021.
 */

class ActiveOrderDetailsPresenter(private val view: ActiveOrderDetailsContract.View) :
    ActiveOrderDetailsContract.Presenter {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private lateinit var orderService: OrderService

    override fun completeOrder(order: Order) {
        orderService = Service.orderService
        compositeDisposable.add(
            orderService.completeOrder(order)
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

    override fun cancelOrder(order: Order) {
        TODO("Not yet implemented")
    }

    fun onDestroy() {
        compositeDisposable.clear()
    }
}