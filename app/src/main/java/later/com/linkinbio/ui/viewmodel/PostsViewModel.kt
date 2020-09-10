package later.com.linkinbio.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import later.com.linkinbio.api.ApiService
import later.com.linkinbio.model.LinkinbioPost
import later.com.linkinbio.model.LinksResponse

class PostsViewModel(private val backgroundScheduler: Scheduler,
                     private  val mainScheduler: Scheduler,
                     private  val apiService: ApiService): ViewModel() {


    private val linksList by lazy { MutableLiveData<List<LinkinbioPost>>() }

    val linksLiveData : LiveData<List<LinkinbioPost>>
        get() = linksList


    private val isError by lazy { MutableLiveData<Boolean>() }

    val isErrorLiveData : LiveData<Boolean>
        get() = isError


    var disposable: CompositeDisposable = CompositeDisposable()

    fun fetchLinks() {
        disposable.add(apiService.getLinks("32192")
                .subscribeOn(backgroundScheduler)
                .observeOn(mainScheduler)
                .subscribe({
                    showResult(it)
                },{
                    showError()
                }))
    }

    private fun showResult(linksListResponse: LinksResponse){
        linksList.value= linksListResponse.linkinbio_posts
        isError.value = false
    }

    private fun showError() {
        isError.value = true
    }

    override fun onCleared() {
        disposable.dispose()
        disposable.clear()
        super.onCleared()
    }

    fun onItemClick(post: LinkinbioPost) {
        val newLinksList = linksLiveData.value?.toMutableList() ?: return
        val viewedPost = newLinksList[newLinksList.indexOf(post)]
        viewedPost.isViewed = true
        linksList.value = newLinksList
    }
}