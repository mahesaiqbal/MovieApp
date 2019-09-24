package com.mahesaiqbal.movieapp.ui.favorite.popularmovie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.mahesaiqbal.movieapp.data.source.MovieRepository
import com.mahesaiqbal.movieapp.data.source.local.entity.popularmovieentity.PopularMovieEntity
import com.mahesaiqbal.movieapp.vo.Resource
import org.junit.Before

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class FavoritePopularMovieViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var movieRepository: MovieRepository

    @Mock
    lateinit var observer: Observer<Resource<PagedList<PopularMovieEntity>>>

    private var viewModel: FavoritePopularMovieViewModel? = null

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = FavoritePopularMovieViewModel(movieRepository)
    }

    @Test
    fun getFavPopularMovies() {
        val dummy = MutableLiveData<Resource<PagedList<PopularMovieEntity>>>()
        val pagedList = mock(PagedList::class.java) as PagedList<PopularMovieEntity>

        dummy.setValue(Resource.success(pagedList))

        `when`<LiveData<Resource<PagedList<PopularMovieEntity>>>>(movieRepository.getAllFavoritedPopularMovies()).thenReturn(
            dummy
        )

        viewModel?.getAllFavoritePopularMovies()?.observeForever(observer)

        verify(observer).onChanged(Resource.success(pagedList))
    }
}