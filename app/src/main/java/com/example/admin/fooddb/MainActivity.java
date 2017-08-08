package com.example.admin.fooddb;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainSavingToData";
    private static final int CAMERA_PIC_REQUEST = 2;
    private static final int ACTION_IMAGE_CAPTURE = 2;
    private EditText name, calories, fat, protein;
    private ImageView picture;
    private byte[] blob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = (EditText) findViewById(R.id.name);
        calories = (EditText) findViewById(R.id.calories);
        fat = (EditText) findViewById(R.id.fat);
        protein = (EditText) findViewById(R.id.protein);
        picture = (ImageView) findViewById(R.id.picture);
    }

    public void takePicture(View view){
        Intent takePictureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent,CAMERA_PIC_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ACTION_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            picture.setImageBitmap(imageBitmap);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            blob = bos.toByteArray();
        }
    }

    public void saveEntry(View view) {
        String nameString = name.getText().toString();
        String calorieString = calories.getText().toString();
        String fatString = fat.getText().toString();
        String proteinString = protein.getText().toString();

        foodEntry entry = new foodEntry(nameString, calorieString, fatString, proteinString, blob);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        if(databaseHelper.checkIfEntryExits(nameString)){
            databaseHelper.updateEntry(entry);
            Toast.makeText(this, "Updated!", Toast.LENGTH_SHORT).show();
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
            picture.setImageBitmap(BitmapFactory.decodeByteArray(entry.getPicture(), 0, entry.getPicture().length));
        }
        else{Toast.makeText(this, "ENTRY DOES NOT EXIST", Toast.LENGTH_SHORT).show();}
    }

    public void deleteEntry(View view){
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        if(databaseHelper.checkIfEntryExits(name.getText().toString())) {
            databaseHelper.deleteEntry(name.getText().toString());
            name.setText("");
            calories.setText("");
            fat.setText("");
            protein.setText("");
            picture.setImageBitmap(null);
        }
    }

    public void switchView(View view){
        Intent intent = new Intent(this, DisplayActivity.class);
        startActivity(intent);
    }
}
