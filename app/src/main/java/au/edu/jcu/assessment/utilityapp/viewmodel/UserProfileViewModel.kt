
package au.edu.jcu.assessment.utilityapp.viewmodel

import androidx.lifecycle.ViewModel
import au.edu.jcu.assessment.utilityapp.model.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UserProfileViewModel : ViewModel() {

    private val _user = MutableStateFlow(
        UserProfile(
            gender = "female",
            name = "Sophie Taylor",
            email = "sophie.taylor@example.com",
            phone = "0400 000 000",
            nationality = "AU",
            pictureUrl = "https://randomuser.me/api/portraits/women/75.jpg",
            address = "25 King Street, Brisbane, Australia",
            birthday = "1995-04-12",
            username = "sophietaylor",
            password = "Example123!"
        )
    )

    val user: StateFlow<UserProfile> = _user //read only version of MutableStateFlow

    fun generateUser() {
        _user.value = UserProfile(
            gender = "male",
            name = "Lucas Bernard",
            email = "lucas.bernard@example.com",
            phone = "0411 222 333",
            nationality = "FR",
            pictureUrl = "https://randomuser.me/api/portraits/men/32.jpg",
            address = "12 Rue Victor Hugo, Paris, France",
            birthday = "1992-08-20",
            username = "lucasbernard",
            password = "TestPassword456!"
        )
    }
}
