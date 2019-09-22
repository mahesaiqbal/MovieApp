package com.mahesaiqbal.movieapp.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.mahesaiqbal.movieapp.data.source.remote.ApiResponse
import com.mahesaiqbal.movieapp.data.source.remote.StatusResponse.*
import com.mahesaiqbal.movieapp.utils.AppExecutors
import com.mahesaiqbal.movieapp.vo.Resource

abstract class NetworkBoundResource<ResultType, RequestType>(val mExecutors: AppExecutors) {

    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.loading(null)

        val dbSource = loadFromDB()

        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData -> result.setValue(Resource.success(newData)) }
            }
        }
    }

    protected fun onFetchFailed() {}

    protected abstract fun loadFromDB(): LiveData<ResultType>

    protected abstract fun shouldFetch(data: ResultType): Boolean

    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>?

    protected abstract fun saveCallResult(data: RequestType?)

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {

        val apiResponse = createCall()

        result.addSource(
            dbSource
        ) { newData -> result.setValue(Resource.loading(newData)) }
        result.addSource(apiResponse!!) { response ->

            result.removeSource(apiResponse)
            result.removeSource(dbSource)

            when (response.status) {
                SUCCESS -> mExecutors.diskIO().execute {

                    saveCallResult(response.body)

                    mExecutors.mainThread().execute {
                        result.addSource(
                            loadFromDB()
                        ) { newData -> result.setValue(Resource.success(newData)) }
                    }

                }

                EMPTY -> mExecutors.mainThread().execute {
                    result.addSource(
                        loadFromDB()
                    ) { newData -> result.setValue(Resource.success(newData)) }
                }

                ERROR -> {
                    onFetchFailed()
                    result.addSource(dbSource) { newData -> result.setValue(Resource.error(response.message!!, newData)) }
                }
            }
        }
    }

    fun asLiveData(): LiveData<Resource<ResultType>> = result
}
