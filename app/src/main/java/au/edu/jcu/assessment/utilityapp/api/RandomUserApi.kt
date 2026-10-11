package au.edu.jcu.assessment.utilityapp.api

import au.edu.jcu.assessment.utilityapp.model.RandomUserResponse
import retrofit2.http.GET
import retrofit2.http.Query

//Suspend Allows the function to perform a network operation without blocking the main thread
interface RandomUserApi {
    @GET("api/")
    suspend fun getRandomUsers(
        @Query("nat") nationality: String? = null,
    ): RandomUserResponse

}
