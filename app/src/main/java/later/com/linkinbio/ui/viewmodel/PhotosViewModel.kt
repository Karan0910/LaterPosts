package later.com.linkinbio.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import later.com.linkinbio.api.ApiService
import later.com.linkinbio.model.LinksResponse

class PhotosViewModel : ViewModel() {

    private val linksList by lazy { MutableLiveData<List<LinksResponse>>() }

    val linksLiveData : LiveData<List<LinksResponse>>
        get() = linksList


    private val isError by lazy { MutableLiveData<Boolean>() }

    val isErrorLiveData : LiveData<Boolean>
        get() = isError

    val apiService by lazy {
        ApiService.create()
    }

    var disposable: CompositeDisposable? = null

    fun fetchLinks() {
        disposable?.add(apiService.getLinks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({showResult(it)

                },{
                    showError()
                }))
    }

    private fun showResult(linksListResponse: List<LinksResponse>){
        linksList.value= linksListResponse
        isError.value = false
    }

    private fun showError() {
        isError.value = true
    }

    override fun onCleared() {
        disposable?.dispose()
        disposable?.clear()
        super.onCleared()
    }

}