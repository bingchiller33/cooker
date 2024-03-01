package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.test.database.DatabaseHandler;

public class RegisterUserActivity extends AppCompatActivity {

    private DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        databaseHandler = new DatabaseHandler(this);
        Button btSaveUser = findViewById(R.id.bt_save_user_info);
        btSaveUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = "ui1001";
                String password = "123456";
                String role = "staff";
                String campus = "Ha Noi";
                String result = databaseHandler.insertUser(username, password, role, campus);
                if (result != null) {
                    Toast.makeText(RegisterUserActivity.this, "Registered user successfully",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}