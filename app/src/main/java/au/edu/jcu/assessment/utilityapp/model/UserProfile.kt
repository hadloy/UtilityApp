package au.edu.jcu.assessment.utilityapp.model

//hold random user data
data class UserProfile(
    val name: String,
    val email: String,
    val phone: String,
    val nationality: String,
    val pictureUrl: String,
    val address: String,
    val birthday: String,
    val password: String
)
