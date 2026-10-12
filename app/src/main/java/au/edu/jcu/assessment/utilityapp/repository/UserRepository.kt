package au.edu.jcu.assessment.utilityapp.repository

import au.edu.jcu.assessment.utilityapp.api.RandomUserApi
import au.edu.jcu.assessment.utilityapp.model.RandomUserResponse

class UserRepository(
    private val api: RandomUserApi
) {
    suspend fun getRandomUsers(
        nationality: String?
    ): RandomUserResponse {
        return api.getRandomUsers(nationality)
    }
}
