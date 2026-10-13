# QA Test Data Generator

## Project Overview

QA Test Data Generator is an Android utility application developed for CP5307 Mobile Computing Assessment 1 at James Cook University by Marion Andriolo.

The application generates fictional user profiles using the Random User API, helping software testers obtain sample data for testing registration forms, user accounts and other applications.

## Features

- Generate fictional user profiles containing gender, names, emails, phone numbers, addresses, dates of birth, usernames, passwords, nationalities, and profile pictures.
- Select a specific nationality or generate a random nationality.
- Choose which profile fields are visible.
- Switch between light and dark mode.
- Navigate between Utility and Settings screens.
- Scroll through profile information on different screen sizes.

## Technologies

- Kotlin
- Android Studio
- Jetpack Compose
- Material Design 3
- ViewModel and StateFlow
- Retrofit and Gson
- Kotlin Coroutines
- Coil for image loading
- Git and GitHub

## Application Architecture

The application follows a layered structure:

`Compose UI → ViewModel → Repository → Retrofit → Random User API`

- Compose UI: Displays profiles and settings.
- ViewModel: Manages user profiles, nationality selection, visible fields and theme preferences.
- Repository: Separates API data access from UI state management.
- Retrofit: Fetches JSON data from the Random User API.
- Data models: Represent API responses and user profiles.

## How to Run

1. Clone or download this GitHub repository.
2. Open the project in Android Studio.
3. Allow Gradle to synchronise.
4. Run the application on an Android emulator or compatible device.
5. Open Settings to customise the generated profiles.
6. Return to Utility and press Generate User.

An internet connection is required to fetch new profiles.

## API

This project uses the Random User API:

https://randomuser.me/

The nationality setting is passed through the optional `nat` query parameter.

## Testing

The application successfully passed nine manual functional tests covering API profile generation, nationality filtering, field visibility, light and dark mode, navigation, scrolling and screen rotation. Both screens were checked for usability and layout issues.

## Limitations

- The application generates one profile at a time.
- Settings are not permanently saved after the application process ends.
- Network failures are logged rather than displayed through a dedicated error message.

## Development

The GitHub commit history documents the project's development, including UI implementation, API integration, settings and architecture improvements.

This project was developed for educational purposes.