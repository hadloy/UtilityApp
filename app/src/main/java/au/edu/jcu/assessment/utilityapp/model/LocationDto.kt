package au.edu.jcu.assessment.utilityapp.model

data class LocationDto(
    val street: StreetDto,
    val city: String,
    val state: String,
    val country: String,
    val postcode: String
)
