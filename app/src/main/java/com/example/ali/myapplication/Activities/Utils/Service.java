package com.example.ali.myapplication.Activities.Utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.ali.myapplication.Activities.Activity.UcHome;
import com.example.ali.myapplication.Activities.ModelClasses.UserModel;
import com.example.ali.myapplication.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;

import static com.google.android.gms.internal.zzagy.runOnUiThread;

/**
 * Created by AdnanAhmed on 10/28/2017.
 */

public class Service extends android.app.Service {
    String id;
    DatabaseReference firebase;
    int childChangeCount = 0;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        firebase = FirebaseDatabase.getInstance().getReference();
//        if (FirebaseAuth.getInstance().getCurrentUser().getUid() != null) {
//            id = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        }
//        if (FirebaseAuth.getInstance().getCurrentUser().getUid() != null) {
        getTokenNotifications();
//        }
        Timerr();
        return START_STICKY;
    }

    public void Timerr() {
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                SmartLocation.with(getApplicationContext()).location()
                        .oneFix()
                        .start(new OnLocationUpdatedListener() {
                            @Override
                            public void onLocationUpdated(Location location) {
                                if (location != null) {
                                    Map<String, String> map = new HashMap<>();
                                    map.put("lat", location.getLatitude() + "");
                                    map.put("lng", location.getLongitude() + "");
                                    map.put("teamId", SharedPref_Team.getCurrentUser(getApplicationContext()).getTeam_uid() + "");
                                    map.put("uc_id", SharedPref_Team.getCurrentUser(getApplicationContext()).getUc_id() + "");
//
//                     map.put("uc_id",SharedPref_Team.getCurrentUser(getApplicationContext()).get+"");

                                    if (!SharedPref_Team.getCurrentUser(getApplicationContext()).getMember_email().equals("")) {
                                        firebase.child("TeamTracking").child(SharedPref_Team.getCurrentUser(getApplicationContext()).getMember_uid()).setValue(map, new DatabaseReference.CompletionListener() {
                                            @Override
                                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                                            }
                                        });
                                    }
                                }

                            }
                        });
            }

        }, 0, 15000);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    private void getTokenNotifications() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            id = FirebaseAuth.getInstance().getCurrentUser().getUid();

            firebase.child("form_tokens").child(id).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    if (dataSnapshot.getValue() != null) {
                        Log.d("TAG", dataSnapshot.getValue().toString());
                        long tokenDate = 0;
                        String message = "Message";

                        for (DataSnapshot d : dataSnapshot.getChildren()) {
//                            for (DataSnapshot d1 : d.getChildren()) {

                            if(d.getValue()!=null) {
                                tokenDate = Long.parseLong(d.child("token_date").getValue().toString());
                                message = d.child("form_status").getValue().toString();

                            }
//
// }
                        }

                        final long finalTokenDate = tokenDate;
                        final String finalMessage = message;
                        firebase.child("ActivitySeen").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.getValue() != null) {
                                    if (childChangeCount == 0) {
                                        if (Long.parseLong(dataSnapshot.getValue().toString()) < finalTokenDate) {
                                            Log.d("TAG", dataSnapshot.getValue().toString());
                                            Intent intent = new Intent(getApplicationContext(), UcHome.class);
                                            NotificationManager notificationManager =
                                                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                                            Notification notification = null;
                                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                                                notification = new Notification.Builder(getApplicationContext())
                                                        .setTicker("E-Polio")
                                                        .setContentTitle("E-Polio")
                                                        .setStyle(new Notification.BigTextStyle().bigText("Your form request is " + finalMessage))
                                                        .setContentText("Your form request is " + finalMessage)
                                                        .setTicker("E-Polio")
                                                        .setPriority(Notification.PRIORITY_HIGH)
                                                        .setSmallIcon(R.mipmap.nadra)
                                                        .setAutoCancel(true)
                                                        .setContentIntent(pendingIntent)
                                                        .setVibrate(new long[]{500, 500})
                                                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                                                        .build();
                                            }
                                            Random r = new Random();
                                            int i = r.nextInt(80 - 65) + 65;
//                                    int i = 1221;
                                            notificationManager.notify(++i, notification);
                                            if (id != null) {
                                                firebase.child("ActivitySeen").child(id).setValue(ServerValue.TIMESTAMP, new DatabaseReference.CompletionListener() {
                                                    @Override
                                                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//                                Utils.log("completed");
                                                    }
                                                });

                                            }


                                        }
                                        ++childChangeCount;
                                    } else {
                                        childChangeCount = 0;
                                    }


                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    if (dataSnapshot.getValue() != null) {
                        Log.d("TAG", dataSnapshot.getValue().toString());
                        long tokenDate = 0;
                        String message = "";
                        for (DataSnapshot d : dataSnapshot.getChildren()) {
                            tokenDate = Long.parseLong(d.child("token_date").getValue().toString());
                            message = d.child("form_status").getValue().toString();

                        }
                        final long finalTokenDate = tokenDate;
                        final String finalMessage = message;
                        firebase.child("ActivitySeen").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.getValue() != null) {

                                    if (Long.parseLong(dataSnapshot.getValue().toString()) < finalTokenDate) {
                                        Log.d("TAG", dataSnapshot.getValue().toString());
                                        Intent intent = new Intent(getApplicationContext(), UcHome.class);
                                        NotificationManager notificationManager =
                                                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                                        Notification notification = null;
                                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                                            notification = new Notification.Builder(getApplicationContext())
                                                    .setTicker("E-Polio")
                                                    .setContentTitle("E-Polio")
                                                    .setStyle(new Notification.BigTextStyle().bigText("Your form request is " + finalMessage))
                                                    .setContentText("Your form request is " + finalMessage)
                                                    .setTicker("E-Polio")
                                                    .setPriority(Notification.PRIORITY_HIGH)
                                                    .setSmallIcon(R.mipmap.nadra)
                                                    .setAutoCancel(true)
                                                    .setContentIntent(pendingIntent)
                                                    .setVibrate(new long[]{500, 500})
                                                    .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                                                    .build();
                                        }
                                        Random r = new Random();
                                        int i = r.nextInt(80 - 65) + 65;
//                                    int i = 1221;
                                        notificationManager.notify(++i, notification);
                                        if (id != null) {
                                            firebase.child("ActivitySeen").child(id).setValue(ServerValue.TIMESTAMP, new DatabaseReference.CompletionListener() {
                                                @Override
                                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//                                Utils.log("completed");
                                                }
                                            });

                                        }


                                    }

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
}
