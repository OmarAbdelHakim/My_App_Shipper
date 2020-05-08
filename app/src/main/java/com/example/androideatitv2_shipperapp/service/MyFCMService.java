package com.example.androideatitv2_shipperapp.service;

import com.example.androideatitv2_shipperapp.common.common;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Random;

import androidx.annotation.NonNull;

    public class MyFCMService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {

        Map<String,String> dataRecv = remoteMessage.getData();
        if(dataRecv != null)
        {
            common.showNotification(this , new Random().nextInt(),
                    dataRecv.get(common.NOTI_TITLE),
                    dataRecv.get(common.NOTI_CONTENT),
                    null);
        }



    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);

        common.UpdateToken(this ,s ,false,true); // because we are in shipper app so shipper =true
    }
}
