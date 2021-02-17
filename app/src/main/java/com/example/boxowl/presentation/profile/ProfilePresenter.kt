package com.example.boxowl.presentation.profile

import com.example.boxowl.models.Courier
import com.example.boxowl.remote.Service
import com.example.boxowl.remote.ProfileService
import com.example.boxowl.utils.showAPIErrors
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Andrey Morgunov on 01/11/2020.
 */

class ProfilePresenter(private val view: ProfileContract.View) : ProfileContract.Presenter {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private lateinit var profileService: ProfileService

    override fun updateCourierData(courier: Courier) {
        profileService = Service.profileService
        compositeDisposable.add(
                profileService.updateUserProfile(courier.CourierId, courier)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                {response ->
                                    if (response.code() == 204) {
                                        view.onUpdateSuccess()
                                    } else {
                                        view.onError(response.message())
                                    }
                                },
                                {
                                    view.onError(showAPIErrors(it))
                                })
        )
    }

    override fun getCourier(courierId: Long) {
        profileService = Service.profileService
        compositeDisposable.add(
            profileService.getCourier(courierId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {response ->
                        if (response.code() == 200) {
                            view.onLoadSuccess(response.body()!!)
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