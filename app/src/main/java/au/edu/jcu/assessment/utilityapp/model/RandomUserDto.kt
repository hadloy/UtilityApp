package au.edu.jcu.assessment.utilityapp.model

data class RandomUserDto(
    val gender: String,
    val name: NameDto,
    val email: String,
    val phone: String,
    val nat: String,
    val location: LocationDto,
    val dob: DobDto,
    val login: LoginDto,
    val picture: PictureDto
)
