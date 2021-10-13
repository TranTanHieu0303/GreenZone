package com.example.greenzone.Object;

import static com.google.firebase.messaging.Constants.MessageNotificationKeys.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.greenzone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

public class PushThongBao extends FirebaseMessagingService {
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        sendRegistrationToServer(token);
    }
    public  void  sendRegistrationToServer(String token){
    }

}
