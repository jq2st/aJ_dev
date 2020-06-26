package com.alabs.labfourcontacts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import Data.DBHandler;
import Models.Person;

public class EditContactActivity extends AppCompatActivity {

    DBHandler db;
    int personId;
    Person person;

    EditText nameEdit;
    EditText surnameEdit;
    EditText phoneEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        personId = extras.getInt("personToEdit");
        db = new DBHandler(this);
        person = db.getPerson(personId);
        nameEdit = findViewById(R.id.editTextPersonName);
        surnameEdit = findViewById(R.id.editTextPersonSurname);
        phoneEdit = findViewById(R.id.editTextPersonPhone);
        nameEdit.setText(person.getName());
        surnameEdit.setText(person.getSurname());
        phoneEdit.setText(person.getPhone());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void editPerson(View view) {
        Person editedPerson = new Person(personId, nameEdit.getText().toString(), surnameEdit.getText().toString(), phoneEdit.getText().toString());
        if (!editedPerson.getName().equals("") && !editedPerson.getPhone().equals("")) {
            db.editPerson(editedPerson);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else {
            Toast toast = Toast.makeText(getApplicationContext(),"Поля 'Имя' и 'Телефон' не могут быть пустыми", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void deletePerson(View view) {
        db.deletePersons(personId);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}