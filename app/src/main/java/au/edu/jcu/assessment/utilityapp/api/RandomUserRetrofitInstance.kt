package au.edu.jcu.assessment.utilityapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RandomUserRetrofitInstance {

    private const val BASE_URL = "https://randomuser.me/"

    //Creates the Retrofit service the first time it's accessed
    val api: RandomUserApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL) //set server address
            .addConverterFactory(GsonConverterFactory.create()) // Tells Retrofit to use Gson to convert JSON into Kotlin data objects
            .build()
            .create(RandomUserApi::class.java)
    }
}
