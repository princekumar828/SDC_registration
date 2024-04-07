# SDC Registration Android App

This Android app allows students to register for the Software Development Club (SDC) at NIT Warangal (NITW).
The registration process is conducted in a step-by-step UI format, and all user data is stored in Firebase Firestore.
Authentication is restricted to users with NITW college email IDs, and registration slips can be uploaded to Firebase Storage. 
Users can also update their information using the login feature.

## Features

- **Step-by-Step Registration:** Users are guided through a series of form inputs in a step-by-step manner for a smooth registration experience.
  
- **Firebase Firestore Integration:** User registration data is stored securely in Firebase Firestore, ensuring data persistence and real-time updates.
  
- **NITW College Email Authentication:** Only users with a valid NITW college email address can authenticate and access the registration features.
  
- **Firebase Storage for Document Uploads:** Users can upload their registration slip directly to Firebase Storage for easy management and retrieval.
  
- **Profile Update:** Registered users can log in and update their information as needed, maintaining accurate records.

## Pending Tasks

- **Update Profile**
  - Implement functionality to allow users to update their profile information (e.g., name, email, phone number).

- **PDF Upload**
  - Enable users to upload PDF documents (e.g., registration slip) to Firebase Storage.

- **Date Picker**
  - Integrate a date picker component to facilitate date selection for specific registration or event-related tasks.

- **Connecting with Admin Panel**
  - Establish a connection between the app and an admin panel for administrative purposes (e.g., viewing registered users, managing events).

## Installation and Setup

1. **Clone the Repository**

   ```bash
   git clone https://github.com/princekumar828/SDC_registration.git
