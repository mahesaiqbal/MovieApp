package com.mahesaiqbal.movieapp.ui.detail.popularmovie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.mahesaiqbal.movieapp.data.source.MovieRepository
import com.mahesaiqbal.movieapp.data.source.local.entity.popularmovieentity.PopularMovieEntity
import com.mahesaiqbal.movieapp.data.source.remote.response.trailer.ResultTrailerMovie
import com.mahesaiqbal.movieapp.vo.Resource
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class DetailPopularMovieViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var movieRepository: MovieRepository

    @Mock
    lateinit var observerDetail: Observer<Resource<PopularMovieEntity>>

    @Mock
    lateinit var observerTrailer: Observer<MutableList<ResultTrailerMovie>>

    private var viewModel: DetailPopularMovieViewModel? = null

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = DetailPopularMovieViewModel(movieRepository)
    }

    @Test
    fun getDetailMovie() {
        val dummyDetailMovie = MutableLiveData<Resource<PopularMovieEntity>>()
        val resultMovie = mock(PopularMovieEntity::class.java)

        dummyDetailMovie.setValue(Resource.success(resultMovie))

        `when`<LiveData<Resource<PopularMovieEntity>>>(movieRepository.getDetailPopularMovie(ArgumentMatchers.anyInt())).thenReturn(
            dummyDetailMovie
        )

        viewModel?.getDetailPopularMovieTest()?.observeForever(observerDetail)

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

        Assert.assertNotNull(viewModel?.trailerMovie?.value)
        Assert.assertEquals(expected.value, viewModel?.trailerMovie?.value)
    }
}