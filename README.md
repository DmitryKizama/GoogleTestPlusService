GoogleTestPlusService

App contains main activity with 2 fragment, button "LogIn" and toolbar. In toolbare there are name of the App and button "LogOut" which shows only when you Logged in 
application. In first fragment there are a picture of your Google account, name, Gmail. In second fragment there are a button "start service" and TextView with - "Next number = "
if you push button start service, you will see that every 2 (or you can write your own number of sec) the number in text view will be changed. 
If you exit the application,you will get notification with new number every 2 sec. You can stop service, push "Stop service" button, that will 
appear instead "Start service".

Using:
Glide (for download img)
Google plus api
Service (worked, even if the app is closed)
