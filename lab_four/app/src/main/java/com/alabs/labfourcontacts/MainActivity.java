package com.alabs.labfourcontacts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import Data.DBHandler;
import Models.Person;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBHandler dbHandler = new DBHandler(this);
        List<Person> persons = dbHandler.getPersons();
        LinearLayout contactListLayout = findViewById(R.id.contactList);
        for (Person person: persons) {
            LinearLayout personLayout = new LinearLayout(this);
            personLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            personLayout.setOrientation(LinearLayout.VERTICAL);
            personLayout.setPadding(0, 10, 0, 10);
            personLayout.setId(person.getId());
            TextView tvName = new TextView(this);
            TextView tvPhone = new TextView(this);
            tvName.setText(person.getName() + " " + person.getSurname());
            tvPhone.setText(person.getPhone());
            contactListLayout.addView(personLayout);
            personLayout.addView(tvName);
            personLayout.addView(tvPhone);
            personLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, EditContactActivity.class);
                    intent.putExtra("personToEdit", v.getId());
                    startActivity(intent);
                }
            });
        }

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
    }

    public void goToAdd(View view) {
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }
}