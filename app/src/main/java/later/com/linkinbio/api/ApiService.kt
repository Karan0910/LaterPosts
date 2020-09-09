package later.com.linkinbio.api

import io.reactivex.Observable
import later.com.linkinbio.model.LinksResponse
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface ApiService {
    
    
    @GET()
    fun getLinks() : Observable<LinksResponse>

    companion object {
        fun create() : ApiService {

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://api-prod.linkin.bio/api/pub/linkinbio_posts?instagram_profile_id=32192")
                    .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}
