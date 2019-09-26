package com.mahesaiqbal.movieapp.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mahesaiqbal.movieapp.data.source.remote.response.detail.DetailResponse
import com.mahesaiqbal.movieapp.data.source.remote.response.popular.PopularMovieResponse
import com.mahesaiqbal.movieapp.data.source.remote.response.popular.ResultPopularMovie
import com.mahesaiqbal.movieapp.data.source.remote.response.toprated.ResultTopRatedMovie
import com.mahesaiqbal.movieapp.data.source.remote.response.toprated.TopRatedMovieResponse
import com.mahesaiqbal.movieapp.data.source.remote.response.trailer.ResultTrailerMovie
import com.mahesaiqbal.movieapp.data.source.remote.response.trailer.TrailerResponse
import com.mahesaiqbal.movieapp.network.Client
import com.mahesaiqbal.movieapp.utils.EspressoIdlingResource
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class RemoteRepository {

    companion object {
        private var INSTANCE: RemoteRepository? = null

        fun getInstance(): RemoteRepository? {
            if (INSTANCE == null) {
                INSTANCE = RemoteRepository()
            }
            return INSTANCE
        }
    }

    private val apiService = Client.create()
    private val compositeDisposable = CompositeDisposable()

    val API_KEY = "49a79f125a171a70aafeaefdc6f406b8"

    fun getAllPopularMovie(): LiveData<ApiResponse<MutableList<ResultPopularMovie>>> {
        EspressoIdlingResource.increment()

        val resultPopularMovie: MutableLiveData<ApiResponse<MutableList<ResultPopularMovie>>> = MutableLiveData()

        apiService.getPopularMovie(API_KEY)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<PopularMovieResponse> {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onNext(t: PopularMovieResponse) {
                    resultPopularMovie.postValue(ApiResponse.success(t.results))
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }

                override fun onComplete() {
                    if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow) {
                        EspressoIdlingResource.decrement()
                    }
                }
            })

        return resultPopularMovie
    }

    fun getAllTopRatedMovie(): LiveData<ApiResponse<MutableList<ResultTopRatedMovie>>> {
        EspressoIdlingResource.increment()

        val resultTopRatedMovie: MutableLiveData<ApiResponse<MutableList<ResultTopRatedMovie>>> = MutableLiveData()

        apiService.getTopRatedMovie(API_KEY)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<TopRatedMovieResponse> {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onNext(t: TopRatedMovieResponse) {
                    resultTopRatedMovie.postValue(ApiResponse.success(t.results))
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }

                override fun onComplete() {
                    if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow) {
                        EspressoIdlingResource.decrement()
                    }
                }
            })

        return resultTopRatedMovie
    }

    fun getDetailMovie(movieId: Int): LiveData<ApiResponse<DetailResponse>> {
        EspressoIdlingResource.increment()

        val result: MutableLiveData<ApiResponse<DetailResponse>> = MutableLiveData()

        apiService.getDetailMovie(movieId, API_KEY)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<DetailResponse> {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onNext(t: DetailResponse) {
                    result.postValue(ApiResponse.success(t))
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }

                override fun onComplete() {
                    if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow) {
                        EspressoIdlingResource.decrement()
                    }
                }
            })

        return result
    }

    fun getTrailerMovie(movieId: Int, callback: LoadTrailerCallback) {
        EspressoIdlingResource.increment()

        apiService.getTrailerMovie(movieId, API_KEY)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<TrailerResponse> {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onNext(t: TrailerResponse) {
                    callback.onTrailerReceived(t.results)
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }

                override fun onComplete() {
                    if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow) {
                        EspressoIdlingResource.decrement()
                    }
                }
            })
    }

    fun onDestroy() {
        compositeDisposable.clear()
    }

    interface LoadTrailerCallback {
        fun onTrailerReceived(trailer: MutableList<ResultTrailerMovie>)
        fun onDataNotAvailable()
    }
}