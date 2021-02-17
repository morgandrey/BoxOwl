package com.example.boxowl.presentation.order.active

import com.example.boxowl.remote.OrderService
import com.example.boxowl.remote.Service
import com.example.boxowl.utils.showAPIErrors
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


/**
 * Created by Andrey Morgunov on 10/02/2021.
 */

class ActiveOrdersPresenter(private val view: ActiveOrdersContract.View) :
    ActiveOrdersContract.Presenter {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private lateinit var orderService: OrderService

    override fun loadActiveOrders(courierId: Long) {
        orderService = Service.orderService
        compositeDisposable.add(
            orderService.getActiveOrders(courierId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {response ->
                        if (response.code() == 200) {
                            view.onSuccess(response.body()!!)
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