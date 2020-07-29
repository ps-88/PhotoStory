package com.psele.photostory.Fragments;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.psele.photostory.Adapter.NotificationAdapter;

import com.psele.photostory.Model.Notification;
import com.psele.photostory.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationFragment extends Fragment {

    private RecyclerView recyclerView;
    private NotificationAdapter notificationAdapter;
    private List<Notification> notificationList;


    ImageView   notif;

    final int NOTIF_ID = 1;
    NotificationManager manager ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);



        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        notificationList = new ArrayList<>();
        notificationAdapter = new NotificationAdapter(getContext(), notificationList);
        recyclerView.setAdapter(notificationAdapter);

        readNotifications();

     //   manager  =(NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);

//        notif = view.findViewById(R.id.notif_ring);
//        notif.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String channelId = null;
//                if(Build.VERSION.SDK_INT >= 26) {
//
//                    channelId =  "some_channel_id" ;
//                    CharSequence channelName =  "Some Channel" ;
//                    int  importance = NotificationManager. IMPORTANCE_HIGH ;
//                    NotificationChannel notificationChannel =  new  NotificationChannel(channelId, channelName, importance);
//                    notificationChannel.enableLights( true );
//                    notificationChannel.setLightColor(Color. RED );
//                    notificationChannel.enableVibration( true );
//                    notificationChannel.setVibrationPattern( new long []{ 100 ,  200 ,  300 ,  400 ,  500 ,  400 ,  300 ,  200 ,  400 });
//
//                    manager.createNotificationChannel(notificationChannel);
//                }
//
//                NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(),channelId);
//                builder.setSmallIcon(android.R.drawable.star_on)
//                        .setContentTitle("Notification title")
//                        .setContentText("Notification extra text body...");
//
//                builder.setPriority(android.app.Notification.PRIORITY_MAX);
//
//                Intent intent = new Intent(getContext(),NotificationFragment.class  );
//              //intent.putExtra("notif_txt",notifText.getText().toString());
//                PendingIntent pendingIntent = PendingIntent.getActivity(getContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
//                builder.setContentIntent(pendingIntent);
//
//                //Adding action  - note must work with NotificatioComapt
//                Intent actionIntent = new Intent(getContext(),NotificationFragment.class);
//                actionIntent.putExtra("notif_txt","PLAY");
//                PendingIntent playPendingIntent = PendingIntent.getActivity(getContext(),1,actionIntent,PendingIntent.FLAG_UPDATE_CURRENT);
//                builder.addAction(new NotificationCompat.Action(android.R.drawable.ic_media_play,"Play",playPendingIntent));
//
//               android.app.Notification notification1 = builder.build();
//
//                notification1.defaults = android.app.Notification.DEFAULT_VIBRATE;
//                notification1.flags |= android.app.Notification.FLAG_AUTO_CANCEL;
//
//                manager.notify(NOTIF_ID,builder.build());
//



           // }
      //  });


        return view;
    }

    private void readNotifications(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notifications").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                notificationList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Notification notification = snapshot.getValue(Notification.class);
                    notificationList.add(notification);
                }

                Collections.reverse(notificationList);
                notificationAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
