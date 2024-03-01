package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditProfileActivity extends AppCompatActivity {

    private EditText edtFirstName, edtLastName, edtPhoneNumber, edtAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        edtFirstName = findViewById(R.id.edt_first_name);
        edtLastName = findViewById(R.id.edt_last_name);
        edtPhoneNumber = findViewById(R.id.edt_phoneNumber);
        edtAddress = findViewById(R.id.edt_address);

        Intent intent = getIntent();
        if(intent != null) {
            edtFirstName.setText(intent.getStringExtra("FIRST_NAME"));
            edtLastName.setText(intent.getStringExtra("LAST_NAME"));
            edtPhoneNumber.setText(intent.getStringExtra("MOBILE_NO"));
        }

        Button btSave = findViewById(R.id.bt_save);
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = edtFirstName.getText().toString().trim();
                String lastName = edtLastName.getText().toString().trim();
                String phoneNumber = edtPhoneNumber.getText().toString().trim();
                String address = edtAddress.getText().toString().trim();

                Intent intent1 = new Intent();
                intent1.putExtra("FIRST_NAME", firstName);
                intent1.putExtra("LAST_NAME", lastName);
                intent1.putExtra("MOBILE_NO", phoneNumber);
                setResult(2, intent1);
                finish();

//                if (firstName.length() == 0) {
//                    edtFirstName.setError("Field is required!");
//                }
//                if (lastName.length() == 0) {
//                    edtLastName.setError("Field is required!");
//                }
//                if (phoneNumber.length() == 0) {
//                    edtPhoneNumber.setError("Field is required!");
//                }
//                if (address.length() == 0) {
//                    edtAddress.setError("Field is required!");
//                }
//                if (!(firstName.isEmpty() || lastName.isEmpty() || phoneNumber.isEmpty() || address.isEmpty())) {
//                    Toast.makeText(EditProfileActivity.this, "Saved: " + firstName + ", "
//                            + lastName + ", " + phoneNumber + ", " + address + ".", Toast.LENGTH_SHORT).show();
//
//                }

            }
        });

    }
}