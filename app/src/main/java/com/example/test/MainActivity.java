package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.test.database.DatabaseHandler;
import com.example.test.database.UserInfo;
import com.example.test.resolver.DemoContentResolver;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner spinnerCampus;

    private RadioButton radioButtonStaff;

    private RadioButton radioButtonManager;
    private String role;

    private CheckBox checkBoxRemember;

    private EditText edtUsername, edtPassword;

    private DemoContentResolver contentResolver;

    private DatabaseHandler databaseHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinnerCampus = findViewById(R.id.spinner_campus);
        checkBoxRemember = findViewById(R.id.cancer);
        edtUsername = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.campus,
                android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCampus.setAdapter(arrayAdapter);
        spinnerCampus.setOnItemSelectedListener(this);
        radioButtonStaff = findViewById(R.id.radio_staff);
        radioButtonStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                role = radioButtonStaff.getText().toString();
            }
        });
        radioButtonManager = findViewById(R.id.radio_mod);
        radioButtonManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                role = radioButtonManager.getText().toString();
            }
        });

        Button btLogin = findViewById(R.id.bt_login);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
//                Toast.makeText(MainActivity.this, "Login button is clicked, role = " +
//                                role + " | cancer: " + checkBoxRemember.isChecked()
//                                + "username: " + username + "password: " + password
//                        , Toast.LENGTH_SHORT).show();
                SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("USER_NAME", username);
                editor.putString("PASSWORD", password);
                editor.commit();
                UserInfo userInfo = contentResolver.getUser(username);
                Toast.makeText(MainActivity.this, "TOAST RESOLVER" + userInfo.getUsername(), Toast.LENGTH_SHORT).show();
                if (userInfo != null && userInfo.getPassword().equals(password)) {
                    Intent intent = new Intent(MainActivity.this, UserProfileActivity.class);
                    startActivity(intent);
                }

            }
        });

        edtPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedItem = parent.getItemAtPosition(position).toString();
        Toast.makeText(this, "Seleted Campus: " + selectedItem, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
