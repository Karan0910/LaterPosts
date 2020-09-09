package later.com.linkinbio.api

import io.reactivex.Observable
import later.com.linkinbio.model.LinksResponse
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    
    
    @GET("linkinbio_posts")
    fun getLinks(@Query("instagram_profile_id") instaProfileId : String) : Observable<LinksResponse>

    companion object {
        fun create() : ApiService {

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://api-prod.linkin.bio/api/pub/")
                    .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}
