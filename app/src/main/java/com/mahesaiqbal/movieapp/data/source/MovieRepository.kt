package com.mahesaiqbal.movieapp.data.source

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mahesaiqbal.movieapp.data.source.local.LocalRepository
import com.mahesaiqbal.movieapp.data.source.local.entity.popularmovieentity.PopularMovieEntity
import com.mahesaiqbal.movieapp.data.source.local.entity.topratedmovieentity.TopRatedMovieEntity
import com.mahesaiqbal.movieapp.data.source.remote.ApiResponse
import com.mahesaiqbal.movieapp.data.source.remote.RemoteRepository
import com.mahesaiqbal.movieapp.data.source.remote.response.detail.DetailResponse
import com.mahesaiqbal.movieapp.data.source.remote.response.popular.ResultPopularMovie
import com.mahesaiqbal.movieapp.data.source.remote.response.toprated.ResultTopRatedMovie
import com.mahesaiqbal.movieapp.utils.AppExecutors
import com.mahesaiqbal.movieapp.vo.Resource

class MovieRepository(
    var localRepository: LocalRepository,
    var remoteRepository: RemoteRepository,
    var appExecutors: AppExecutors
) : MovieDataSource {

    companion object {
        @Volatile
        private var INSTANCE: MovieRepository? = null

        fun getInstance(
            localRepository: LocalRepository,
            remoteData: RemoteRepository,
            appExecutor: AppExecutors
        ): MovieRepository? {
            if (INSTANCE == null) {
                synchronized(MovieRepository::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = MovieRepository(localRepository, remoteData, appExecutor)
                    }
                }
            }
            return INSTANCE
        }
    }

    override fun getAllPopularMovies(): LiveData<Resource<PagedList<PopularMovieEntity>>> {
        return object : NetworkBoundResource<PagedList<PopularMovieEntity>, MutableList<ResultPopularMovie>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<PopularMovieEntity>> {
                return LivePagedListBuilder(localRepository.getAllPopularMovie(), 10).build()
            }

            override fun shouldFetch(data: PagedList<PopularMovieEntity>): Boolean {
                return data == null || data.size == 0
            }

            override fun createCall(): LiveData<ApiResponse<MutableList<ResultPopularMovie>>>? {
                return remoteRepository.getAllPopularMovie()
            }

            override fun saveCallResult(data: MutableList<ResultPopularMovie>?) {
                val popularMovies = mutableListOf<PopularMovieEntity>()

                for (i in data!!.indices) {
                    val response: ResultPopularMovie = data[i]
                    val (id, backdropPath, originalLanguage,
                        overview, popularity, posterPath,
                        releaseDate, title, voteAverage,
                        voteCount) = response
                    val popularMovie = PopularMovieEntity(id, backdropPath, originalLanguage,
                        overview, popularity, posterPath, releaseDate, title, voteAverage,
                        voteCount, false)

                    popularMovies.add(popularMovie)
                }

                localRepository.insertPopularMovie(popularMovies)
            }
        }.asLiveData()
    }

    override fun getAllFavoritedPopularMovies(): LiveData<Resource<PagedList<PopularMovieEntity>>> {
        return object : NetworkBoundResource<PagedList<PopularMovieEntity>, MutableList<ResultPopularMovie>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<PopularMovieEntity>> {
                return LivePagedListBuilder(localRepository.getFavoritedPopularMovie(), 10).build()
            }

            override fun shouldFetch(data: PagedList<PopularMovieEntity>): Boolean {
                return false
            }

            override fun createCall(): LiveData<ApiResponse<MutableList<ResultPopularMovie>>>? {
                return null
            }

            override fun saveCallResult(data: MutableList<ResultPopularMovie>?) {

            }

        }.asLiveData()
    }

    override fun getAllTopRatedMovies(): LiveData<Resource<PagedList<TopRatedMovieEntity>>> {
        return object : NetworkBoundResource<PagedList<TopRatedMovieEntity>, MutableList<ResultTopRatedMovie>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<TopRatedMovieEntity>> {
                return LivePagedListBuilder(localRepository.getAllTopRatedMovie(), 10).build()
            }

            override fun shouldFetch(data: PagedList<TopRatedMovieEntity>): Boolean {
                return data == null || data.size == 0
            }

            override fun createCall(): LiveData<ApiResponse<MutableList<ResultTopRatedMovie>>>? {
                return remoteRepository.getAllTopRatedMovie()
            }

            override fun saveCallResult(data: MutableList<ResultTopRatedMovie>?) {
                val topRatedMovies = mutableListOf<TopRatedMovieEntity>()

                for (i in data!!.indices) {
                    val response: ResultTopRatedMovie = data[i]
                    val (id, backdropPath, originalLanguage,
                        overview, popularity, posterPath,
                        releaseDate, title, voteAverage,
                        voteCount) = response
                    val topRatedMovie = TopRatedMovieEntity(id, backdropPath, originalLanguage,
                        overview, popularity, posterPath, releaseDate, title, voteAverage,
                        voteCount, false)

                    topRatedMovies.add(topRatedMovie)
                }

                localRepository.insertTopRatedMovie(topRatedMovies)
            }
        }.asLiveData()
    }

    override fun getAllFavoritedTopRatedMovies(): LiveData<Resource<PagedList<TopRatedMovieEntity>>> {
        return object : NetworkBoundResource<PagedList<TopRatedMovieEntity>, MutableList<ResultTopRatedMovie>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<TopRatedMovieEntity>> {
                return LivePagedListBuilder(localRepository.getFavoritedTopRatedMovie(), 10).build()
            }

            override fun shouldFetch(data: PagedList<TopRatedMovieEntity>): Boolean {
                return false
            }

            override fun createCall(): LiveData<ApiResponse<MutableList<ResultTopRatedMovie>>>? {
                return null
            }

            override fun saveCallResult(data: MutableList<ResultTopRatedMovie>?) {

            }

        }.asLiveData()
    }

    override fun getDetailPopularMovie(movieId: Int): LiveData<Resource<PopularMovieEntity>> {
        return object : NetworkBoundResource<PopularMovieEntity, DetailResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<PopularMovieEntity> {
                return localRepository.getDetailPopularMovie(movieId)
            }

            override fun shouldFetch(data: PopularMovieEntity): Boolean {
                return data == null
            }

            override fun createCall(): LiveData<ApiResponse<DetailResponse>>? {
                return remoteRepository.getDetailMovie(movieId)
            }

            override fun saveCallResult(data: DetailResponse?) {
                val response: DetailResponse = data!!
                val (id, backdropPath, originalLanguage,
                    overview, popularity, posterPath,
                    releaseDate, title, voteAverage,
                    voteCount) = response
                val detailMovie = PopularMovieEntity(id, backdropPath, originalLanguage,
                    overview, popularity, posterPath, releaseDate, title, voteAverage,
                    voteCount, false)
            }

        }.asLiveData()
    }

    override fun getDetailTopRatedMovie(movieId: Int): LiveData<Resource<TopRatedMovieEntity>> {
        return object : NetworkBoundResource<TopRatedMovieEntity, DetailResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<TopRatedMovieEntity> {
                return localRepository.getDetailTopRatedMovie(movieId)
            }

            override fun shouldFetch(data: TopRatedMovieEntity): Boolean {
                return data == null
            }

            override fun createCall(): LiveData<ApiResponse<DetailResponse>>? {
                return remoteRepository.getDetailMovie(movieId)
            }

            override fun saveCallResult(data: DetailResponse?) {
                val response: DetailResponse = data!!
                val (id, backdropPath, originalLanguage,
                    overview, popularity, posterPath,
                    releaseDate, title, voteAverage,
                    voteCount) = response
                val detailMovie = TopRatedMovieEntity(id, backdropPath, originalLanguage,
                    overview, popularity, posterPath, releaseDate, title, voteAverage,
                    voteCount, false)
            }

        }.asLiveData()
    }

    override fun setPopularMovieFavorited(popularMovie: PopularMovieEntity, state: Boolean) {
        val runnable = { localRepository.setPopularMovieFavorite(popularMovie, state) }

        appExecutors.diskIO().execute(runnable)
    }

    override fun setTopRatedMovieFavorited(topRatedMovie: TopRatedMovieEntity, state: Boolean) {
        val runnable = { localRepository.setTopRatedMovieFavorite(topRatedMovie, state) }

        appExecutors.diskIO().execute(runnable)
    }
}
