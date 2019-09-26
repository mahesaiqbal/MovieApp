package com.mahesaiqbal.movieapp.ui.detail.topratedmovie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.mahesaiqbal.movieapp.data.source.MovieRepository
import com.mahesaiqbal.movieapp.data.source.local.entity.topratedmovieentity.TopRatedMovieEntity
import com.mahesaiqbal.movieapp.data.source.remote.response.trailer.ResultTrailerMovie
import com.mahesaiqbal.movieapp.vo.Resource
import org.junit.*

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class DetailTopRatedMovieViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var movieRepository: MovieRepository

    @Mock
    lateinit var observerDetail: Observer<Resource<TopRatedMovieEntity>>

    @Mock
    lateinit var observerTrailer: Observer<MutableList<ResultTrailerMovie>>

    private var viewModel: DetailTopRatedMovieViewModel? = null

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = DetailTopRatedMovieViewModel(movieRepository)
    }

    @Test
    fun getDetailMovie() {
        val dummyDetailMovie = MutableLiveData<Resource<TopRatedMovieEntity>>()
        val resultMovie = mock(TopRatedMovieEntity::class.java)

        dummyDetailMovie.setValue(Resource.success(resultMovie))

        `when`<LiveData<Resource<TopRatedMovieEntity>>>(movieRepository.getDetailTopRatedMovie(ArgumentMatchers.anyInt())).thenReturn(
            dummyDetailMovie
        )

        viewModel?.getDetailTopRatedMovieTest()?.observeForever(observerDetail)

        verify(observerDetail).onChanged(Resource.success(resultMovie))
    }

    @Test
    fun getTrailers() {
        val dummy = mutableListOf<ResultTrailerMovie>()

        val expected = MutableLiveData<MutableList<ResultTrailerMovie>>()
        expected.postValue(dummy)

        `when`(movieRepository.getTrailerMovie(ArgumentMatchers.anyInt())).thenReturn(expected)

        viewModel?.getTrailerMovies()
        viewModel?.trailerMovie?.observeForever(observerTrailer)

        verify(observerTrailer).onChanged(dummy)

        assertNotNull(viewModel?.trailerMovie?.value)
        assertEquals(expected.value, viewModel?.trailerMovie?.value)
    }
}