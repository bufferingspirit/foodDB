package com.example.admin.fooddb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainSavingToData";
    private EditText name, calories, fat, protein;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = (EditText) findViewById(R.id.name);
        calories = (EditText) findViewById(R.id.calories);
        fat = (EditText) findViewById(R.id.fat);
        protein = (EditText) findViewById(R.id.protein);

    }

    public void saveEntry(View view) {
        String nameString = name.getText().toString();
        String calorieString = calories.getText().toString();
        String fatString = fat.getText().toString();
        String proteinString = protein.getText().toString();
        foodEntry entry = new foodEntry(nameString, calorieString, fatString, proteinString);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        if(databaseHelper.checkIfEntryExits(nameString)){
            databaseHelper.updateEntry(entry);
        }
        else {
            long saved = databaseHelper.saveNewFoodEntry(entry);
            if (saved == -1) {
                Toast.makeText(this, "FAILED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Successfully saved!!!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void getEntry(View view) {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        if(databaseHelper.checkIfEntryExits(name.getText().toString())){
            foodEntry entry = databaseHelper.getEntry(name.getText().toString());
            calories.setText(entry.getCalories());
            fat.setText(entry.getFat());
            protein.setText(entry.getProtein());
        }
        else{Toast.makeText(this, "ENTRY DOES NOT EXIST", Toast.LENGTH_SHORT).show();}
    }

    public void deleteEntry(View view){
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        if(databaseHelper.checkIfEntryExits(name.getText().toString()))
            databaseHelper.deleteEntry(name.getText().toString());
    }

    public void switchView(View view){
        Intent intent = new Intent(this, DisplayActivity.class);
        startActivity(intent);
    }
}
