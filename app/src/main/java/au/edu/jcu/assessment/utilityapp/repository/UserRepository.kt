package au.edu.jcu.assessment.utilityapp.repository

import au.edu.jcu.assessment.utilityapp.api.RandomUserApi
import au.edu.jcu.assessment.utilityapp.model.RandomUserResponse

// Repository separates API data access from the ViewModel.
class UserRepository(
    private val api: RandomUserApi
) {
    // Requests user data using the selected nationality.
    suspend fun getRandomUsers(
        nationality: String?
    ): RandomUserResponse {
        return api.getRandomUsers(nationality)
    }
}
