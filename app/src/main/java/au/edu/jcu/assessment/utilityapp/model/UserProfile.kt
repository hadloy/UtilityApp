package au.edu.jcu.assessment.utilityapp.model

data class UserProfile(
    val name: String,
    val gender: String,
    val email: String,
    val phone: String,
    val nationality: String,
    val address: String,
    val birthday: String,
    val username: String,
    val password: String,
    val pictureUrl: String
)
