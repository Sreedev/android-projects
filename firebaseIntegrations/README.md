# FirebasIntegrations is a project which has all the Firebase services implemented.

Please not before using the project you have to set up all the firebase services with your personal credentials and enable it
in yout firebase console. After that only you can use the below code.

## [Firebase Email auth](https://medium.com/@sreedev.r5/firebase-e-mail-authentication-taking-one-step-closer-to-know-users-f67ac21b1661)
> User already registered or not check.<br />
> User registration.<br />
> User email verification.<br />
> Use email verified or not check.<br />
> User login.


## [Firebase Database](https://medium.com/@sreedev.r5/firebase-phone-number-authentication-taking-one-step-closer-to-know-users-54aa6a2bc489)
> Initiate Database and Database register object.<br />
> Give table name when inititaing Database register object.<br />
> Use the register object to insert, delete and update data.


## Firebase Phone number auth.
> Add your phone number in the PhoneAuthFragment.<br />
> If in emulator, add mock phone number and OTP in Firebase console. GIve same phone number on PhoneAuthFragment.<br />
> Authentication will be done using that phone number.

## Firebase MLKit - Face detection
> Make sure you have enabled the permision for Camera and Storage before checking this functionality.<br />
> As of now only one face is getting detected, face object can be used to get all detected faces.

## Firebase Cloud store
> Select a photo from gallery.<br />
> Upload the photo.<br />
> Download the photo.<br />
> Storage of that file happens in Firebase cloud storage.

## Text recognition
> Make sure you have enabled the permision for Camera and Storage before checking this functionality.<br />
> Take the pic and click on the scan text button, ML Kit will return the detected text.

## Object detection - On device
> Make sure you have enabled the permision for Camera and Storage before checking this functionality.<br />
> Take the pic and click on the ODD button, ML Kit will classify the the object and print it in the console.<br />
> On device classifications are very limited, there are only few categories.

## Object detection - On cloud{Dev in progress}
