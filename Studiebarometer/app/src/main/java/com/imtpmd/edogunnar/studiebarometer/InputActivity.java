package com.imtpmd.edogunnar.studiebarometer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Gunnar on 25-3-2016.
 */
public class InputActivity extends AppCompatActivity {
    com.imtpmd.edogunnar.studiebarometer.SharedPreferences mprefs;
//    com.imtpmd.edogunnar.studiebarometer.MainActivity mainActivity;

    private MainActivity mainActivity;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        try {
            ArrayList<HashMap<String, String>> arrayList = mainActivity.getArrayList();
            ArrayAdapter<HashMap<String, String>> arrayAdapter = new ArrayAdapter<HashMap<String, String>>( this, android.R.layout.simple_spinner_dropdown_item, arrayList);
            Spinner spPeriode = (Spinner) findViewById(R.id.periodeKeuze);
            spPeriode.setAdapter(arrayAdapter);
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }

        Log.d("vakNamen", mprefs.readStringFromSharedPreferences(getBaseContext(), "vakken"));

//        try {
//            JSONObject jsonObject = new JSONObject(mprefs.readStringFromSharedPreferences(getBaseContext(), "vakken"));
//            JSONArray jsonArray = jsonObject.getJSONArray("vakken");
//            mainActivity.arrayList = new ArrayList<HashMap<String, String>>();
//            HashMap<String, String> hashMap;
//
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject vak = jsonArray.getJSONObject(i);
//                String vakNaam = vak.getString("name");
//                String vakStudiePunten = vak.getString("ects");
//                String vakCijfer = vak.getString("grade");
//                String vakPeriode = vak.getString("period");
//
//                // waarden in arraylist zetten
//                hashMap = new HashMap<String, String>();
//                hashMap.put("name", vakNaam);
//                hashMap.put("ects", vakStudiePunten);
//                hashMap.put("grade", vakCijfer);
//                hashMap.put("period", vakPeriode);
//
//                mainActivity.arrayList.add(hashMap);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//            Toast.makeText(getBaseContext(), "Error in JSON file", Toast.LENGTH_SHORT).show();
//        }




//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arrayList);



        // Spinner click listener
//        spPeriode.setOnItemSelectedListener(InputActivity);
    }
}
