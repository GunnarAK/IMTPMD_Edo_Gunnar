package com.imtpmd.edogunnar.studiebarometer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<HashMap<String, String>> arrayList;
    public String myPreferences;
//    public String naamStudent;
    com.imtpmd.edogunnar.studiebarometer.SharedPreferences mPrefs;
    SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy");
    String stringDatumBeginPeriode1;
    String stringDatumEindPeriode1;
    String stringDatumBeginPeriode2;
    String stringDatumEindPeriode2;
    String stringDatumBeginPeriode3;
    String stringDatumEindPeriode3;
    String stringDatumBeginPeriode4;
    String stringDatumEindPeriode4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        naamVaststellen();
        datumVaststellen();
        studiepuntenVaststellen();
    }

    public void naamVaststellen()
    {
        try {

            TextView naamStudent = (TextView) findViewById(R.id.naamStudent);
            naamStudent.setText(mPrefs.readStringFromSharedPreferences(getBaseContext(), "naamStudent"));
        } catch (NullPointerException e) {
            Log.d("Naam mPrefs", "Kon naam niet vinden");
            startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            finish();
        }
    }


    public void datumVaststellen()
    {
        Date datumVandaagParsed = new Date();

        String datumVandaag = simpleDate.format(new Date());
        Log.d("datumVandaag", datumVandaag);

        TextView huidigePeriode = (TextView) findViewById(R.id.huidigePeriode);


        Date datumBeginPeriode1 = new Date();
        Date datumEindPeriode1 = new Date();


        Date datumBeginPeriode2 = new Date();
        Date datumEindPeriode2 = new Date();


        Date datumBeginPeriode3 = new Date();
        Date datumEindPeriode3 = new Date();


        Date datumBeginPeriode4 = new Date();
        Date datumEindPeriode4 = new Date();

        try {

            datumVandaagParsed = simpleDate.parse(datumVandaag);

            stringDatumBeginPeriode1 = "01/09/2015";
            datumBeginPeriode1 = simpleDate.parse(stringDatumBeginPeriode1);
            stringDatumEindPeriode1 = "10/11/2015";
            datumEindPeriode1 = simpleDate.parse(stringDatumEindPeriode1);

            Log.d("Periode 1", stringDatumBeginPeriode1 + " - " + stringDatumEindPeriode1);

            stringDatumBeginPeriode2 = "17/11/2015";
            datumBeginPeriode2 = simpleDate.parse(stringDatumBeginPeriode2);
            stringDatumEindPeriode2 = "02/02/2016";
            datumEindPeriode2 = simpleDate.parse(stringDatumEindPeriode2);

            Log.d("Periode 2", stringDatumBeginPeriode2 + " - " + stringDatumEindPeriode2);

            stringDatumBeginPeriode3 = "09/02/2016";
            datumBeginPeriode3 = simpleDate.parse(stringDatumBeginPeriode3);
            stringDatumEindPeriode3 = "20/04/2016";
            datumEindPeriode3 = simpleDate.parse(stringDatumEindPeriode3);

            Log.d("Periode 3", stringDatumBeginPeriode3 + " - " + stringDatumEindPeriode3);

            stringDatumBeginPeriode4 = "27/04/2016";
            datumBeginPeriode4 = simpleDate.parse(stringDatumBeginPeriode4);
            stringDatumEindPeriode4 = "13/07/2016";
            datumEindPeriode4 = simpleDate.parse(stringDatumEindPeriode4);

            Log.d("Periode 4", stringDatumBeginPeriode4 + " - " + stringDatumEindPeriode4);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }


        if (datumVandaagParsed.after(datumBeginPeriode1) && datumVandaagParsed.before(datumEindPeriode1)) {

            huidigePeriode.setText("Periode 1");
        }

        if (datumVandaagParsed.after(datumBeginPeriode2) && datumVandaagParsed.before(datumEindPeriode2)) {

            huidigePeriode.setText("Periode 2");
        }

        if (datumVandaagParsed.after(datumBeginPeriode3) && datumVandaagParsed.before(datumEindPeriode3)) {

            huidigePeriode.setText("Periode 3");
        }

        if (datumVandaagParsed.after(datumBeginPeriode4) && datumVandaagParsed.before(datumEindPeriode4)) {

            huidigePeriode.setText("Periode 4");
        }


        try {
            myPreferences = mPrefs.readStringFromSharedPreferences(getBaseContext(), "vakken");
        } catch (NullPointerException e) {
            Log.d("sharedPreferences", "geen preferences gevonden");
            e.printStackTrace();
            JSONParser();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Fabulous!", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                // open nieuwe activity om cijfers toe te voegen
                startActivity(new Intent(MainActivity.this, InputActivity.class));
            }
        });
    }

    public void studiepuntenVaststellen() {
        try {
            TextView studiePunten = (TextView) findViewById(R.id.aantalStudiepunten);
            studiePunten.setText(mPrefs.readStringFromSharedPreferences(getBaseContext(), "aantalStudiepunten"));

            Log.d("EC's mPrefs", mPrefs.readStringFromSharedPreferences(getBaseContext(), "aantalStudiepunten"));
        } catch (NullPointerException e) {
            Log.d("EC's mPrefs", "Kon studiepunten-aantal niet vinden");
            e.printStackTrace();
        }
    }

    public void JSONParser() {
        // JSON inladen in listview
        Log.d("JSONParser()", "Started!");

        Log.d("myPreferences", "GEEN PREFS GEVONDEN : " + myPreferences);
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());

            JSONArray jsonArray = obj.getJSONArray("vakken");
            arrayList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> hashMap;

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject vak = jsonArray.getJSONObject(i);
                String vakNaam = vak.getString("name");
                String vakStudiePunten = vak.getString("ects");
                String vakCijfer = vak.getString("grade");
                String vakPeriode = vak.getString("period");

                // waarden in arraylist zetten
                hashMap = new HashMap<String, String>();
                hashMap.put("name", vakNaam);
                hashMap.put("ects", vakStudiePunten);
                hashMap.put("grade", vakCijfer);
                hashMap.put("period", vakPeriode);

                arrayList.add(hashMap);
            }
            Log.d("jsonstring.json", "succesvol ingelezen");
            Log.d("vakNamen", mPrefs.readStringFromSharedPreferences(getBaseContext(), "vakNaam"));

            mPrefs.saveStringToSharedPreferences(getBaseContext(), "vakken", arrayList.toString()); // opslaan in preferences


        } catch (JSONException e) {
            Log.d("JSONParser()", "catch");
            e.printStackTrace();
        }
    }

    public ArrayList<HashMap<String, String>> getArrayList()
    {
        return arrayList;
    }

    public String loadJSONFromAsset() {
        // JSON string ophalen
        //Log.d("loadJSONFromAsset()", "Started!");
        Toast.makeText(getBaseContext(), "jsonstring.json inlezen!; ", Toast.LENGTH_SHORT).show();
        String json; // = null;
        try {
            InputStream is = getAssets().open("jsonstring.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            Log.d("loadJSONFromAsset()", "catch");
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    @Override
//    protected void onPause()
//    {
//        super.onPause();
//    }
}
