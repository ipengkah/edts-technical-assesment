# How to run the application
1. create ***edts*** databse in you local
2. run EdtsApplication.class (like similiar spring boot to run)
   = the table will generate automaticly
3. you can read ***API Spec*** in ***docs*** directory

# Simple flow application
1. register user
   - If you are not register then you can't make a booking
2. assuming the vendor concert create concert 
3. then the user
   - can search available concert
   - can book a ticket according to selected concert
     - when a ticket succes to book the available ticket will be reduce
   

# Test Result
- ER Diagram

<img width="960" alt="ER Diagram" src="https://github.com/ipengkah/edts-technical-assesment/assets/36947684/06d47b77-8a8b-471d-a000-4f5efec63778">

- Unit Test when register User

<img width="960" alt="unit test register user" src="https://github.com/ipengkah/edts-technical-assesment/assets/36947684/ae2eac18-97f8-4824-af2c-7a560fef26f7">


- Unit Test when create concert

<img width="960" alt="unitest create concert" src="https://github.com/ipengkah/edts-technical-assesment/assets/36947684/8ed7cfce-a844-453d-aaa1-985cdf4c4176">

- Unit Test when search concert
<img width="960" alt="unit test search concert" src="https://github.com/ipengkah/edts-technical-assesment/assets/36947684/8ce68ec9-8d56-4a05-98a3-7128005d2670">
<img width="628" alt="image" src="https://github.com/ipengkah/edts-technical-assesment/assets/36947684/5cad1803-a7ae-4ec4-b26b-f4d261f6c5d4">

- Unit Test when create booking
  - this test include ****race booking and multiple booking solution****
<img width="960" alt="Booking Unit Test" src="https://github.com/ipengkah/edts-technical-assesment/assets/36947684/aa9dc9fd-ab7e-4f8e-94e3-faa2c5afc759">


- for Scenarios race booking and multiple booking solution
  - i purchase each ticket 5 from two thread
  - in this case i use two thread to book a ticket, The screenshot below shows before run the test
    
    <img width="306" alt="db before race condition" src="https://github.com/ipengkah/edts-technical-assesment/assets/36947684/f852309a-ecee-49fc-8233-a8e9a0816f88">
    
  - after run the test
    
    <img width="332" alt="db after race condition" src="https://github.com/ipengkah/edts-technical-assesment/assets/36947684/9fe413d2-481f-46e6-81ae-ac132deeb4d2">
      
    
    <img width="511" alt="image" src="https://github.com/ipengkah/edts-technical-assesment/assets/36947684/6518d7bd-78c8-42e4-ab6e-c5f7eb6f9d49">
    
  
**THANK YOU :)**

