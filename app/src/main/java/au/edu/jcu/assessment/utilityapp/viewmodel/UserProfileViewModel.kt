package au.edu.jcu.assessment.utilityapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import au.edu.jcu.assessment.utilityapp.api.RandomUserRetrofitInstance
import au.edu.jcu.assessment.utilityapp.model.UserProfile
import au.edu.jcu.assessment.utilityapp.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


// ViewModel manages the application's data and UI state.
// It connects the user interface to the Repository.
class UserProfileViewModel : ViewModel() {

    // Create the Repository and pass it the Retrofit API service.
    // The Repository is responsible for requesting data from the API.
    private val userRepository = UserRepository(
        RandomUserRetrofitInstance.api
    )
    // Store the current user profile in a MutableStateFlow.
    // MutableStateFlow allows the ViewModel to update the value.
    // A sample user is displayed before the first API request.
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
    //read only version of MutableStateFlow
    val user: StateFlow<UserProfile> = _user

    // Request a new random user from the API.
    // Called when the Generate User button is pressed.
    fun fetchRandomUser() {
        // Launch a coroutine with the ViewModel lifecycle.
        viewModelScope.launch {
            // Handle possible errors during the API request.
            try {
                val response = userRepository.getRandomUsers(
                    nationality = _selectedNationality.value
                )
                val apiUser = response.results.first()

                //convert API response
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
                // Write debugging information to Android Studio Logcat.
                Log.d("RandomUserAPI", "Name: ${apiUser.name.first}")
                Log.d("RandomUserAPI", "Email: ${apiUser.email}")

            } catch (e: Exception) {
                // Log any error, such as a network failure.
                // Currently, the error is not displayed to the user.
                Log.e("RandomUserAPI", "Error fetching user", e)
            }
        }
    }

    //to choose nationality
    private val _selectedNationality = MutableStateFlow<String?>(null)

    val selectedNationality: StateFlow<String?> = _selectedNationality

    fun updateNationality(nationality: String?) {
        _selectedNationality.value = nationality
    }
    // Store the visible fields for the Generator screen.
    private val _visibleFields = MutableStateFlow(
        setOf(
            "picture",
            "name",
            "gender",
            "email",
            "phone",
            "nationality",
            "address",
            "birthday",
            "username",
            "password"
        )
    )

    // Expose the selected visible fields as read-only state
    val visibleFields: StateFlow<Set<String>> = _visibleFields

    fun updateFieldVisibility(
        field: String,
        isVisible: Boolean
    ) {
        _visibleFields.value = if (isVisible) {
            _visibleFields.value + field
        } else {
            _visibleFields.value - field
        }
    }

    private val _darkModeEnabled = MutableStateFlow(false)

    val darkModeEnabled: StateFlow<Boolean> = _darkModeEnabled

    //dark mode toggle
    fun updateDarkMode(enabled: Boolean) {
        _darkModeEnabled.value = enabled
    }

}
