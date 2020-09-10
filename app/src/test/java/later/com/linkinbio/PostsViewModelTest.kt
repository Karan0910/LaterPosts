package later.com.linkinbio


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import later.com.linkinbio.api.ApiService
import later.com.linkinbio.ui.viewmodel.PostsViewModel
import androidx.lifecycle.Observer
import io.reactivex.Single
import later.com.linkinbio.model.LinkinbioPost
import later.com.linkinbio.model.LinksResponse
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PostsViewModelTest {

    @Mock
    lateinit var apiService : ApiService

    @Mock
    lateinit var observer : Observer<List<LinkinbioPost>>

    val scheduler : Scheduler = Schedulers.trampoline()

    lateinit var viewModel: PostsViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun initViewModel(){
        viewModel = PostsViewModel(scheduler,scheduler,apiService)
        viewModel.linksLiveData.observeForever(observer)
    }

    @Test
    fun testFetchLinks(){
        Mockito.`when`(apiService.getLinks(anyString())).thenReturn(Single.just(createMockResponse()))
        viewModel.fetchLinks()
        verify(observer).onChanged(createMockResponse().linkinbio_posts)
        verifyNoMoreInteractions(observer)
    }

}

private fun createMockResponse() = LinksResponse(listOf(mockLinkInbioPostResponse()))

private fun mockLinkInbioPostResponse() =
     LinkinbioPost( "https://dnh0aphdpud22.cloudfront.net/linkinbio/9e07d809eabebe-LIB9986419.jpg",
              "https://later.com/blog/save-instagram-photos/?utm_source=instagram&utm_medium=social&utm_campaign=social_IGPost_sept92020_saveigphotos")
