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
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
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
        `when`(apiService.getLinks(anyString())).thenReturn(Single.just(createMockResponse()))
        viewModel.fetchLinks()
        verify(observer).onChanged(createMockResponse().linkinbio_posts)
        verifyNoMoreInteractions(observer)
    }

    @Test
    fun testOnPostClick(){
        val post1 = LinkinbioPost(imageUrl = "1",linkUrl = "1")
        val post2 = LinkinbioPost(imageUrl = "2",linkUrl = "2")
        val post3 = LinkinbioPost(imageUrl = "3",linkUrl = "3")
        val postList =  createMockResponse(listOf(post1, post2, post3))
        `when`(apiService.getLinks(anyString())).thenReturn(Single.just(postList))
        viewModel.fetchLinks()

        Assert.assertFalse(post2.isViewed)
        viewModel.onItemClick(post2)
        Assert.assertTrue(post2.isViewed)

        verify(observer, times(2)).onChanged(postList.linkinbio_posts)
        verifyNoMoreInteractions(observer)
    }

    @Test
    fun testOnItemClickWithInvalidPost(){
        val post1 = LinkinbioPost(imageUrl = "1",linkUrl = "1")
        val post2 = LinkinbioPost(imageUrl = "2",linkUrl = "2")
        val post3 = LinkinbioPost(imageUrl = "3",linkUrl = "3")
        val post4 = LinkinbioPost(imageUrl = "4",linkUrl = "4")
        val postList =  createMockResponse(listOf(post1, post2, post3))
        `when`(apiService.getLinks(anyString())).thenReturn(Single.just(postList))
        viewModel.fetchLinks()
        verify(observer).onChanged(postList.linkinbio_posts)

        Assert.assertFalse(post4.isViewed)
        viewModel.onItemClick(post4)
        Assert.assertFalse(post4.isViewed)
        verify(observer).onChanged(postList.linkinbio_posts)
    }

    @Test
    fun testOnItemClickWithNoData(){
        viewModel.onItemClick(mockLinkInbioPostResponse())
        verifyZeroInteractions(observer)

    }
}

private fun createMockResponse(list : List<LinkinbioPost> = listOf(mockLinkInbioPostResponse())) = LinksResponse(list)

private fun mockLinkInbioPostResponse(imageUrl : String ="https://dnh0aphdpud22.cloudfront.net/linkinbio/9e07d809eabebe-LIB9986419.jpg",
                                      linkUrl : String = "https://later.com/blog/save-instagram-photos/?utm_source=instagram&utm_medium=social&utm_campaign=social_IGPost_sept92020_saveigphotos") =
     LinkinbioPost(imageUrl,linkUrl)

