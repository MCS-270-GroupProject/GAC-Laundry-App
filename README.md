# Who Let The Clothes Out?

Who Let The Clothes Out is an android app that allows the students to view the timers on the washers and dryers of their dorm buildings so that they won't have to make unnecessary trips to the laundry room just to check if there are any machines available

## How It's Made:

**Tech used:** Android Studio, Kotlin, Firebase Database

The app features a home page that displays a list of dorm buildings for which the student can view the status of the laundry machines for a particular buillding. Upon choosing a building, the student is displayed a list of laundry machines in that building with the status of each machine along with a timer if it's being used. The students can not only view the status of the machine but also set a timer for a new machine they're using. No one else but the person using the particular machine can change the status of the machine (in the case he wants to remove the clothes early or put them in for longer). This is achieved by using a "login" system for each machine: providing each student with a temporary unique ID for their machine which can be used to alter it's status. 

Although the timers are started locally, everyone using the app can view the status of the machine along with it's timer dynamically. This global application was achieved by implementing Firebase Database that stores the value of the timer for each machine. This value is synced with the timer of the app whereby data updates are sent every time a second elapses on the timer, thus allowing every other device to see this change in the timer dynamically and globally.

## Future Changes

- A login page to verify if the user is a student of Gustavus
- A master tablet/kiosk with the app installed in each laundry room to set the timer for the machines
- Notification displaying the machine timers and a message when laundry is done
- Queue system
- A more efficient "login" system for machines
- A more efficient timer

  
## Optimizations

*(optional)*

## Lessons Learned:


## License

This project is licensed under the [NAME HERE] License - see the LICENSE.md file for details
