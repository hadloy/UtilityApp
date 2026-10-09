package au.edu.jcu.assessment.utilityapp.viewmodel

import androidx.lifecycle.ViewModel
import au.edu.jcu.assessment.utilityapp.model.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import android.util.Log
import androidx.lifecycle.viewModelScope
import au.edu.jcu.assessment.utilityapp.api.RandomUserRetrofitInstance
import kotlinx.coroutines.launch

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
    fun fetchRandomUser() {
        viewModelScope.launch {
            try {
                val response = RandomUserRetrofitInstance.api.getRandomUsers()
                val apiUser = response.results.first()

                _user.value = UserProfile(
                    name = "${apiUser.name.first} ${apiUser.name.last}",
                    gender = apiUser.gender,
                    email = apiUser.email,
                    phone = apiUser.phone,
                    nationality = apiUser.nat,
                    address = "${apiUser.location.street.number} ${apiUser.location.street.name}, ${apiUser.location.city}, ${apiUser.location.country}",
                    birthday = apiUser.dob.date.substringBefore("T"),
                    username = apiUser.login.username,
                    password = apiUser.login.password,
                    pictureUrl = apiUser.picture.large
                )

                Log.d("RandomUserAPI", "Name: ${apiUser.name.first}")
                Log.d("RandomUserAPI", "Email: ${apiUser.email}")

            } catch (e: Exception) {
                Log.e("RandomUserAPI", "Error fetching user", e)
            }
        }
    }
}
