# QA Test Data Generator

## Project Overview

QA Test Data Generator is an Android utility application developed for CP5307 Mobile Computing Assessment 1 at James Cook University by Marion Andriolo.
The application generates fictional user profiles using the Random User API, to help software testers obtain sample data for testing registration forms, user accounts and other applications.

## Features

- Generate fictional profiles containing names, emails, phone numbers, addresses, dates of birth, usernames, passwords, nationalities and profile pictures.
- Select a specific nationality or generate a random nationality.
- Choose which profile fields are visible.
- Switch between light and dark mode.
- Navigate between Generator and Settings screens.
- Adapt the interface to portrait and landscape orientations.

## Getting Started

1. Clone or download this GitHub repository.
2. Open the project in Android Studio.
3. Allow Gradle to synchronise.
4. Run the application on an Android emulator or compatible device.
5. Open Settings to customise the generated profiles.
6. Return to Generator and press Generate User.

An internet connection is required to fetch new profiles.

## Project Structure

The application follows a layered architecture:

`Compose UI → ViewModel → Repository → Retrofit → Random User API`

- Compose UI: Displays profiles and settings.
- ViewModel: Manages generated profiles, nationality selection, visible fields and theme preferences.
- Repository: Separates API data access from UI state management.
- Retrofit: Fetches JSON data from the Random User API.

## Main Composables

- `UtilityScreen`: Displays generated user profiles and the Generate User button.
- `LandscapeProfileLayout`: Adapts profile information to landscape orientation.
- `ProfileField`: Displays profile information using reusable cards and icons.
- `SettingsScreen`: Manages nationality, visible fields and dark mode.
- `NationalityDropdown`: Provides a reusable nationality selector.

## Key Concepts Covered

- Kotlin and Jetpack Compose
- Material Design 3 components and layouts
- ViewModel, StateFlow and Coroutines
- Retrofit and Gson for API integration
- Coil for profile image loading
- Responsive layouts and navigation
- State management and reusable composables
- Git and GitHub version control

## Testing and Limitations

The application was manually tested for profile generation, nationality filtering, field visibility, theme switching, navigation and screen rotation.
Current limitations include generating one profile at a time, settings not being permanently saved, and limited user-facing network error handling.

## Suggested Extensions

- Generate multiple profiles at once.
- Export test data as CSV or JSON.
- Copy generated information to the clipboard.
- Save user preferences using DataStore.
- Improve network error handling.

## License

This project was developed for educational purposes as part of CP5307 Mobile Computing at James Cook University. 