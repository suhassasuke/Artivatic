package com.wnet.artivatic.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wnet.artivatic.data.ApiInterface
import com.wnet.artivatic.data.api_model.PlaceModel
import com.wnet.artivatic.utils.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HomeViewModel : ViewModel() {
    var updatesResult: MutableLiveData<PlaceModel> = MutableLiveData()

    var ApiService = Utils.getRetrofit().create(ApiInterface::class.java)

    fun upgradeResult(): LiveData<PlaceModel> {
        return updatesResult
    }

    fun loadData() {
        CompositeDisposable().add(
            ApiService.GetDetails()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.e("GetAllData", it.toString())
                    updatesResult.postValue(it)
                }, {
                    it.printStackTrace()
                })
        )
    }
}