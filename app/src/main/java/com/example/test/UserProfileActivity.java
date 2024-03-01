package com.example.test;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.service.MyDemoBoundService;
import com.example.test.service.MyDemoService;
import com.squareup.picasso.Picasso;

public class UserProfileActivity extends AppCompatActivity {
    private TextView tvPhoneNo;
    private TextView tvFirstName;
    private TextView tvLastName;
    private ImageView imgAvatar;
    private final int GPS_REQUEST_CODE = 2;
    private ActivityResultLauncher activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == 2) {
                        Intent intent = result.getData();
                        if (intent != null) {
                            String firstName = intent.getStringExtra("FIRST_NAME");
                            String lastName = intent.getStringExtra("LAST_NAME");
                            String phoneNumber = intent.getStringExtra("MOBILE_NO");
                            tvFirstName.setText(firstName);
                            tvLastName.setText(lastName);
                            tvPhoneNo.setText(phoneNumber);
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        tvPhoneNo = findViewById(R.id.tv_phoneNumber);
        tvFirstName = findViewById(R.id.first_name);
        tvLastName = findViewById(R.id.last_name);
        imgAvatar = findViewById(R.id.img_avatar);
        String url = "https://i1.sndcdn.com/artworks-XDRz5sfnFVEkTdIh-J8OhyQ-t500x500.jpg";
        Picasso.get().load(Uri.parse(url)).error(R.drawable.facebook)
                .placeholder(R.drawable.facebook)
                .into(imgAvatar);

        Button btEditProfile = findViewById(R.id.bt_edit_profile);
        tvPhoneNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("tel:" + tvPhoneNo.getText().toString());
                Intent it = new Intent(Intent.ACTION_DIAL, uri);
                startActivity(it);
            }
        });
        btEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileActivity.this, EditProfileActivity.class);
                intent.putExtra("FIRST_NAME", tvFirstName.getText().toString());
                intent.putExtra("LAST_NAME", tvLastName.getText().toString());
                intent.putExtra("MOBILE_NO", tvPhoneNo.getText().toString());
//                startActivityForResult(intent, 2);
                activityResultLauncher.launch(intent);
                Log.d(getClass().getSimpleName(), "End of OnClickListener()");
            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        tvFirstName.setText(tvFirstName.getText().toString() + ", username=" + sharedPreferences.getString("USER_NAME", ""));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            String firstName = data.getStringExtra("FIRST_NAME");
            String lastName = data.getStringExtra("LAST_NAME");
            String phoneNumber = data.getStringExtra("MOBILE_NO");
            tvFirstName.setText(firstName);
            tvLastName.setText(lastName);
            tvPhoneNo.setText(phoneNumber);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
//        if (menu instanceof MenuBuilder) {
//
//        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_edit_profile) {
            showEditProfile();
            return true;
        } else if (item.getItemId() == R.id.menu_logout) {
            logout();
            return true;
//        } else if (item.getItemId() == R.id.menu_gps) {
//            Toast.makeText(this, "Perm", Toast.LENGTH_SHORT).show();
//            requestGPS();
//            return true;
        } else if (item.getItemId() == R.id.menu_setting) {
            Toast.makeText(this, "Perm", Toast.LENGTH_SHORT).show();
//            sendNotification();
            startService();
            return true;
        } else if (item.getItemId() == R.id.menu_showCount) {
            showCount();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(serviceConnection);
    }

    private void startService() {
        Intent intent = new Intent(this, MyDemoService.class);
        intent.putExtra("MSG", "Demo Service");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {

                if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this)
                            .setCancelable(false)
                            .setPositiveButton("OK", null)
                            .setTitle("Request for GPS Permission")
                            .setMessage("Please grant the permission for this service to use app");
                    builder.show();
                }
                requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, NOTIFY_CODE);
            } else {
                Toast.makeText(this, "GPS permission granted", Toast.LENGTH_SHORT).show();
            }
        }
        startService(intent);
    }

    private void showCount() {
        Toast.makeText(this, "Count got from service is " + myDemoBoundService.getCount(), Toast.LENGTH_SHORT).show();
    }

    private MyDemoBoundService myDemoBoundService = null;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            myDemoBoundService = ((MyDemoBoundService.MyBinder) iBinder).getMyDemoBoundService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, MyDemoBoundService.class);
        intent.putExtra("COUNT", 1);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private void showEditProfile() {
        Intent intent = new Intent(this, EditProfileActivity.class);
        intent.putExtra("First Name", tvFirstName.getText().toString());
        intent.putExtra("Last Name", tvLastName.getText().toString());
        intent.putExtra("Phone No", tvPhoneNo.getText().toString());
        startActivity(intent);
    }

    private void logout() {
        finish();
    }

    private final int NOTIFY_CODE = 1;

    private void sendNotification() {
        // if version => 33 request permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {

                if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this)
                            .setCancelable(false)
                            .setPositiveButton("OK", null)
                            .setTitle("Request for GPS Permission")
                            .setMessage("Please grant the permission for this service to use app");
                    builder.show();
                }
                requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, NOTIFY_CODE);
            } else {
                Toast.makeText(this, "GPS permission granted", Toast.LENGTH_SHORT).show();
            }
        }
        final String CHANNEL_ID = "001";
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setSmallIcon(R.drawable.ic_action_name)
                        .setContentTitle("Demo Notification")
                        .setContentText("Hello! This is a demo message")
                        .setAutoCancel(false);
        NotificationManager notificationManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, NotificationDetailActivity.class);
        intent.putExtra("MSG", "Hello! This is a demo message");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, NOTIFY_CODE, intent, PendingIntent.FLAG_IMMUTABLE);
        builder.setContentIntent(pendingIntent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "Demo Channel", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        notificationManager.notify(1, builder.build());
    }


    private void requestGPS() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setCancelable(false)
                        .setPositiveButton("OK", null)
                        .setTitle("Request for GPS Permission")
                        .setMessage("Please grant the permission for this service to use app");
                builder.show();
            }
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, GPS_REQUEST_CODE);
        } else {
            Toast.makeText(this, "GPS permission granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == GPS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "GPS permission granted by users", Toast.LENGTH_SHORT).show();
            }
        }
    }

}

