
# <p align="center">Vacation Reservation App</p>

This is an Airbnb style reservation app for a fictional company called Don't Wreck My House LLC that runs on the console. It gives the user the ability create profiles for both guests and hosts as well as to look up, modify, and delete those profiles. Each profile contains all past and upcoming reservation information as well as personal/contact information. The application also supports the creation of reservations, including providing info on conflicts, price, and the option to cancel or modify dates. All data is maintained via file writing with Java's File I/O library.

## 🧐 Features  / Demo
- When the app is started, a main menu with selection options will be presented. Type in your desired action's number to get started. You will be returned to the main menu upon completing each action.
- To add a guest profile, you will need to provide a first name, last name, valid email, and phone in ########## format. Emails are validated via Java's Regex API pattern matching. To view a guest profile and associated reservations you will need to select that option and provide a registered email address.

  ‎

https://user-images.githubusercontent.com/67916002/203409608-86df393e-897b-4e32-8130-da14119bf0fc.mov

- To add a host profile, you will also need to validated contact information with the addition of the address, city, postal code, and state of your rental property. You will also need to provide a standard and weekend rate. To view a host and associated reservations select that option and provide the host email.
- To create a reservation provide the desired host's email and you will then be asked to confirm the host. Repeat this process for the guest seeking the reservation. You will then be shown the host's current bookings/conflicts. Enter a start and end date for the reservation. Your dates will be checked for validity and availabilty. You will then be presented a summary of your reservation along with the total for confirmation. After confirming, your reservation is booked.
- To edit a reservation, again enter the host's email as well as the guest's and confirm both. You will then be presented a list of active reservations for that combination. Enter the cooresponding ID of the desired reservation and hit enter. You will then be provided the option to change the start or end date, all changes will be evaluated for conflicts. You will then confirm or reject the changed reservation.
- To delete a reservation, enter a host and guest email and then simply confirm the reservation you want to remove. To delete a host or guest profile you will need to look them up by their email and then confirm the deletion. When a guest or host is deleted all associated upcoming reservations will also be deleted.

## 🛠️ Tech Stack
- [Java](https://www.java.com/)
- [Spring](https://spring.io)
- [JUnit](https://junit.org/junit5/)
- [Maven](https://maven.apache.org/)      