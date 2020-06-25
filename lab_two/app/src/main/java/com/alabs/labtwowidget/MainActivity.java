package com.alabs.labtwowidget;

import androidx.appcompat.app.AppCompatActivity;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinnerColor;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedEditor;
    int widgetID = AppWidgetManager.INVALID_APPWIDGET_ID;
    Intent resultValue;
    EditText editTextBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            widgetID = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        if (widgetID == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }

        resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetID);
        setResult(RESULT_CANCELED, resultValue);

        spinnerColor = findViewById(R.id.spinnerColor);
        spinnerColor.setOnItemSelectedListener(this);
        sharedPreferences = this.getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);
        sharedEditor = sharedPreferences.edit();


        spinnerColor.setSelection(Integer.parseInt(sharedPreferences.getString("widgetColor" + widgetID, "0")));

        editTextBody = findViewById(R.id.editTextBody);
        editTextBody.setText(sharedPreferences.getString("text" + widgetID, "Текст Заметки"));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String chosenColor = "" + spinnerColor.getSelectedItemId();
        sharedEditor.putString("widgetColor" + widgetID, chosenColor);
        sharedEditor.apply();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void saveSettings(View v){
        sharedEditor.putString("text" + widgetID, String.valueOf(editTextBody.getText()));
        sharedEditor.apply();
        AppWidget.updateWidget(this, AppWidgetManager.getInstance(this), widgetID);
        setResult(RESULT_OK, resultValue);
        finish();
    }
}