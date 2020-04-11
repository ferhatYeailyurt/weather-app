package com.ismailhakkiaydin.weather.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ismailhakkiaydin.weather.api.WeatherAPIClient
import com.ismailhakkiaydin.weather.model.WeatherResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LocationViewModel(application: Application) : AndroidViewModel(application) {

   // val currentLocationData by lazy { MutableLiveData<WeatherResponse>() }

    private val apiClient = WeatherAPIClient()
    private val disposable = CompositeDisposable()

    val locationData = MutableLiveData<WeatherResponse>()



    fun getWeatherDataWithGPS(latitude: String, longitude: String) {
        disposable.add(
            apiClient.getDataFromGps(latitude, longitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<WeatherResponse>(){
                    override fun onSuccess(t: WeatherResponse) {
                        locationData.value=t
                        Log.i("BİLGİ : ", "CALIŞTI")
                    }

                    override fun onError(e: Throwable) {
                        Log.i("BİLGİ : ", "HATA"+e.message +" "+e.printStackTrace())

                    }
                })
        )

    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}