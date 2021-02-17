package com.example.boxowl.presentation.order.history

import com.example.boxowl.remote.OrderService
import com.example.boxowl.remote.Service
import com.example.boxowl.utils.showAPIErrors
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


/**
 * Created by Andrey Morgunov on 11/02/2021.
 */

class HistoryOrdersPresenter(private val view: HistoryOrdersContract.View) :
    HistoryOrdersContract.Presenter {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private lateinit var orderService: OrderService

    override fun loadHistoryOrders(courierId: Long) {
        orderService = Service.orderService
        compositeDisposable.add(
            orderService.getHistoryOrders(courierId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { response ->
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