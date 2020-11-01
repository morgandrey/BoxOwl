package com.example.boxowl.presentation.profile

import com.example.boxowl.models.User
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

    override fun updateUserData(user: User) {
        profileService = Service.profileService
        compositeDisposable.add(
                profileService.updateUserProfile(user.UserId, user)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                {
                                    view.loadUserData(it.body()!!)
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