# Development Observation Notes
**Project:** CP5307 — QA Test Data Generator

## 22 September 2026

### Git and GitHub Setup
I learned the difference between Git and GitHub, including how commits, pushes and pulls work.

I created a private GitHub repository, connected it to Android Studio, and made my first commits.

### Project Organisation
I learned how to navigate the project structure using Android Studio's Project view and created a README.md file.

**Reflection:** I understand why regular commits are important for tracking development progress.

---

## 24 September 2026

### Assessment Planning
I reviewed the assessment requirements and explored three possible app ideas: QA Test Session Helper, IT Networking Event Calendar, and QA Test Data Generator.

### Version Control
I made a small change in MainActivity.kt and practised committing and pushing it to GitHub.

**Reflection:** I learned how Git records changes and how GitHub can demonstrate the development history of my application.

---

## 1 October 2026

### Project Structure and UI
I reorganised the project, explored button interactions, and changed the application launcher icon.

I also learned the difference between `remember` and `ViewModel` for managing UI state.

**Reflection:** I started understanding why state management matters, particularly when the app is recreated after screen rotation.

---

## 8 October 2026

### Application Design and API Exploration
I refined the QA Test Data Generator concept, validated the app icon, and imported my colour scheme.

I worked with the ViewModel and explored the Random User API JSON response.

I updated `UserProfile` to include gender and username and learned the difference between DTOs and UI models.

**Reflection:** I decided to display the address as one string while keeping username and password as separate fields. I also learned that the API's data structure does not determine the UI layout.

---

## 9 October 2026

### API Data Models
I created Kotlin DTO classes to represent the nested JSON response from the Random User API, including personal details, address, date of birth, login information and profile pictures.

**Reflection:** I learned how nested JSON objects are represented using Kotlin data classes and how these models will support Retrofit integration.

---
## 10 October 2026

### Retrofit Integration

Today, I connected my app to the Random User API using Retrofit and Gson. I created an API interface and Retrofit instance, then used a coroutine in my ViewModel to retrieve fictional user profiles.
I learned how API responses are converted into Kotlin objects and how updating StateFlow automatically refreshes the Compose UI.
I also resolved a naming conflict with an existing Retrofit instance and successfully tested the API using Logcat.

**Next step:** Implement loading indicators and network error handling.