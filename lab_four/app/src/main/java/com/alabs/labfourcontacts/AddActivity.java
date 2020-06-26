package com.alabs.labfourcontacts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import Data.DBHandler;
import Models.Person;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void addPerson(View view) {
        EditText nameE = findViewById(R.id.editTextAddPersonName);
        String name = nameE.getText().toString();
        EditText surnameE = findViewById(R.id.editTextAddPersonSurname);
        String surname = surnameE.getText().toString();
        EditText phoneE = findViewById(R.id.editTextAddPersonPhone);
        String phone = phoneE.getText().toString();
        if (!name.equals("") && !phone.equals("")) {
            Person person = new Person(name, surname, phone);
            DBHandler dbHandler = new DBHandler(this);
            dbHandler.addPerson(person);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else {
            Toast toast = Toast.makeText(getApplicationContext(),"Заполните поля", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}