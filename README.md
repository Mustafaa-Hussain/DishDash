# DishDash
![DishDash](https://github.com/user-attachments/assets/841e48b5-8d9e-4913-926d-ffbae0008511)

## Description
**DishDash Application** is an Android mobile application that helps users plan their weekly meals. It provides meal suggestions, category browsing, meal searches, and the ability to save favorite meals for offline access. The app integrates with Firebase for authentication and data backup, while local storage (Room Database) is used for storing favorites.


## Features
- **Meal of the Day:** Get a random meal suggestion for inspiration.
- **Meal Search:** Search for meals based on:
  - Country.
  - Ingredient.
  - Category.
  - Search by meal name.
- **Categories & Countries:** View available meal categories and explore popular meals in different countries.
- **Favorites:**
  - Add/remove meals from favorites.
  - View favorite meals offline and sync data on firebase.
- **Meal Planning:**
  - Add meals to the current week's meal plan.
  - Add meals to the mobile calendar as events.
  - View weekly meal plans offline and sync data on firebase.
- **Authentication:**
  - User registration and login.
  - Google Authentication via Firebase.
  - Guest mode (restricted access to viewing only).
- **Meal Details Page:**
  - Display meal name, image, origin country, ingredients (with images), measurements, preparation steps, and an embedded video.

## Technologies Used
- **Android (Java)**
- **XML-based UI, Material Design Components**
- **MVP Architecture**
- **RxJava**
- **Retrofit (API Requests)**
- **Room Database (Local Storage)**
- **Glide Media Downloading**
- **Firebase Authentication & Cloud Storage**

## API Integration
- The application fetches meal data from [TheMealDB API](https://themealdb.com/api.php).

## Installation & Setup
1. Clone the repository:
   ```sh
   git clone https://github.com/Mustafaa-Hussain/DishDash.git
   ```
2. Open the project in **Android Studio**.
3. Configure **Firebase** in the project.
4. Build and run the application on an emulator or physical device.
