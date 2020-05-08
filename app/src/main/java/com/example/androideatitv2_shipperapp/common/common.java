package com.example.androideatitv2_shipperapp.common;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androideatitv2_shipperapp.Model.ShipperUserModel;
import com.example.androideatitv2_shipperapp.Model.TokenModel;
import com.example.androideatitv2_shipperapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

public class common {

    public static final String SHIPPER_REF = "Shippers";
    public static final String CATEGORY_REF = "Category";
    public static final int DEFAULT_COLUMN_COUNT =0 ;
    public static final int FULL_WIDTH_COLUMN = 1;
    public static final String ORDER_REF = "Orders" ;
    public static final String NOTI_TITLE = "title";
    public static final String NOTI_CONTENT = "content";
    public static final String TOKEN_REF ="Tokens" ;
    public static final String SHIPPER_ORDER_REF ="ShippingOrder" ; // Same as Server app
    public static final String SHIPPING_ORDER_DATA = "ShippingData" ;
    public static ShipperUserModel currentShipperUser;




    public static void setSpanString(String welcome, String name, TextView textView) {

        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(welcome);
        SpannableString spannableString = new SpannableString(name);
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        spannableString.setSpan(boldSpan , 0 , name.length() , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.append(spannableString);
        textView.setText(builder , TextView.BufferType.SPANNABLE);

    }

    public static void setSpanStringColor(String welcome, String name, TextView textView, int color) {

        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(welcome);
        SpannableString spannableString = new SpannableString(name);
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        spannableString.setSpan(boldSpan , 0 , name.length() , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(color), 0 , name.length() , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.append(spannableString);
        textView.setText(builder , TextView.BufferType.SPANNABLE);

    }

    public static String convertStatusToString(int orderStatus) {
        switch (orderStatus)
        {
            case 0:
                return "Placed";
            case 1:
                return "Shipping";
            case 2:
                return "Shipped";
            case -1:
                return "Cancelled";
            default:
                return "Error";
        }
    }

    public static void showNotification(Context context, int id, String title, String content, Intent intent) {
        PendingIntent pendingIntent =null;
        if(intent != null)
            pendingIntent = PendingIntent.getActivity(context ,id , intent , PendingIntent.FLAG_UPDATE_CURRENT);
        String NOTIFICATION_CHANNEL_ID = "edmt_dev_eat_it_v2";
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {

            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID ,
                    "Eat It V2" , NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription(" Eat It V2 ");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[] {0,1000,500,1000});
            notificationChannel.enableVibration(true);


            notificationManager.createNotificationChannel(notificationChannel);

        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context , NOTIFICATION_CHANNEL_ID);
        builder.setContentTitle(title)
                .setContentText(content)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_restaurant_menu_black_24dp));
        if(pendingIntent != null)
            builder.setContentIntent(pendingIntent);

        Notification notification = builder.build();
        notificationManager.notify(id , notification);


    }

    public static void UpdateToken(Context context, String newToken , boolean isServer , boolean isShipper) {

        FirebaseDatabase.getInstance()
                .getReference(common.TOKEN_REF)
                .child(common.currentShipperUser.getUid())
                .setValue(new TokenModel(common.currentShipperUser.getPhone(),newToken, isServer,isShipper))
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

    }
    public static String CreateTopicOrder() {
        return new StringBuilder("/topics/new_order").toString();
    }
}

