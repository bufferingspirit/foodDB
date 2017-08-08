package com.example.admin.fooddb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class DisplayActivity extends AppCompatActivity {


    private static final String TAG = "ListingData";
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        ArrayList names = new ArrayList<String>();
        populateNameList(names);
        //List<String> test = new ArrayList<String>();

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_entry, names);
        lv = (ListView) findViewById(R.id.food_list);
        lv.setAdapter(adapter);
    }


    public List<String> populateNameList(ArrayList<String> names) {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        ArrayList<foodEntry> entries = databaseHelper.getEntries();
        for(int i = 0; i < entries.size(); i++){
            names.add(i, entries.get(i).getName());
            Log.d(TAG, "getContact: " + "Name: " + entries.get(i).getName() + ", " +
                    "Number: " + entries.get(i).getCalories());
        }
        return names;
    }
}
