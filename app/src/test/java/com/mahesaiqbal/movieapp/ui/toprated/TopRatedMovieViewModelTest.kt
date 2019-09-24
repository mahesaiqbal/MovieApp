package com.mahesaiqbal.movieapp.ui.toprated

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.mahesaiqbal.movieapp.data.source.MovieRepository
import com.mahesaiqbal.movieapp.data.source.local.entity.topratedmovieentity.TopRatedMovieEntity
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
class TopRatedMovieViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var movieRepository: MovieRepository

    @Mock
    lateinit var observer: Observer<Resource<PagedList<TopRatedMovieEntity>>>

    private var viewModel: TopRatedMovieViewModel? = null

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = TopRatedMovieViewModel(movieRepository)
    }

    @Test
    fun getPopularMovies() {
        val dummy = MutableLiveData<Resource<PagedList<TopRatedMovieEntity>>>()
        val pagedList = mock(PagedList::class.java) as PagedList<TopRatedMovieEntity>

        dummy.setValue(Resource.success(pagedList))

        `when`<LiveData<Resource<PagedList<TopRatedMovieEntity>>>>(movieRepository.getAllTopRatedMovies()).thenReturn(dummy)

        viewModel?.getAllTopRatedMovies()?.observeForever(observer)

        verify(observer).onChanged(Resource.success(pagedList))
    }
}