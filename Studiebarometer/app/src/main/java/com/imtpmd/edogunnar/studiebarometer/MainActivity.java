package com.imtpmd.edogunnar.studiebarometer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

    ArrayList<HashMap<String, String>> arrayListVakken;
    String vakNaam;
    String vakStudiePunten;
    String vakCijfer;
    String vakPeriode;

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

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("onResume", "CALLED");
        datumVaststellen();
        studiepuntenVaststellen();
    }

    public void naamVaststellen() {
        try {

            TextView naamStudent = (TextView) findViewById(R.id.naamStudent);
            naamStudent.setText(mPrefs.readStringFromSharedPreferences(getBaseContext(), "naamStudent"));
        } catch (NullPointerException e) {
            Log.d("Naam mPrefs", "Kon naam niet vinden");
            startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            finish();
        }
    }


    public void datumVaststellen() {
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

            datumVandaagParsed = simpleDate.parse("28/04/2016");

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


        try {
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

            Log.d("huidigePeriode MA", huidigePeriode.getText().toString());

        } catch (NullPointerException e) {
            Log.d("periode uitlezen", "niet gelukt");
            e.printStackTrace();
        }


        try {
            myPreferences = mPrefs.readStringFromSharedPreferences(getBaseContext(), "vakken");
        } catch (NullPointerException e) {
            Log.d("sharedPreferences", "geen preferences gevonden");
            e.printStackTrace();
            JSONParser(loadJSONFromAsset());
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Fabulous!", Snackbar.LENGTH_LONG).setAction("Action", null).show();

//                Log.d("sharedprefs-vakken", mPrefs.readStringFromSharedPreferences(getBaseContext(), "vakken"));
                // open nieuwe activity om cijfers toe te voegen
                startActivity(new Intent(MainActivity.this, InputActivity.class));
//                finish();
            }
        });
    }

    public void studiepuntenVaststellen() {
        try {
            TextView studiePunten = (TextView) findViewById(R.id.aantalStudiepunten);
            ListView ingevoerdeCijfers = (ListView) findViewById(R.id.cijfersListView);
            int aantalStudiePunten = 0;

            try {
                JSONObject obj = new JSONObject(mPrefs.readStringFromSharedPreferences(getBaseContext(), "vakken"));

                JSONArray jsonArray = obj.getJSONArray("vakken");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject vak = jsonArray.getJSONObject(i);
                    vakNaam = vak.getString("name");
                    vakPeriode = vak.getString("period");
                    double vakCijfer = Double.parseDouble(vak.getString("grade"));
                    int vakStudiePunten = Integer.parseInt(vak.getString("ects"));

                    // tellen studiepunten
                    if (vakCijfer >= 5.5) {
                        aantalStudiePunten = aantalStudiePunten + vakStudiePunten;
                    }

//                    // ingevoerde cijfers noteren in listview
//                    if (vakCijfer >= 1)
//                    {
//                        // populate listview
//                        // per listitem: cijferVak & cijferCijfer
//
//                        ListView cijferLijstListView = (ListView) findViewById(R.id.cijfersListView);
//
//                        List<String> cijferLijst = new ArrayList<String>();
//
//                        cijferLijst.clear();
//                        try {
//                            for (int c = 0; c < jsonArray.length(); c++) {
//                                vak = jsonArray.getJSONObject(i);
//                                String vakNaamCijferLijst = vak.getString("name");
//                                String vakPeriodeCijferLijst = vak.getString("period");
//                                String vakCijferCijferLijst = vak.getString("grade");
//                                String vakStudiepuntenCijferLijst = vak.getString("ects");
//
//                                if (Double.parseDouble(vakCijferCijferLijst) >= 1) {
//                                    cijferLijst.add(vakNaam);
//                                    Log.d("cijferLijst", cijferLijst.toString());
//                                }
//                            }
//                            if (cijferLijst.isEmpty())
//                            {
//                                cijferLijst.add("Je hebt nog geen cijfers ingevoerd");
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                        ArrayAdapter<String> cijferLijstArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cijferLijst); //selected item will look like a spinner set from XML
////                        cijferLijstArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                        cijferLijstListView.setAdapter(cijferLijstArrayAdapter);
//
//
//                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            studiePunten.setText(String.valueOf(aantalStudiePunten));


//            Log.d("EC's mPrefs", mPrefs.readStringFromSharedPreferences(getBaseContext(), "aantalStudiepunten"));
        } catch (NullPointerException e) {
            Log.d("EC's", "Kon studiepunten-aantal niet berekenen");
            e.printStackTrace();
        }
    }

    public String JSONParser(String jsonString) {
        Log.d("JSONParser()", "Started!");

        Log.d("myPreferences", "GEEN PREFS GEVONDEN : " + myPreferences);
        try {
            JSONObject obj = new JSONObject(jsonString);

            JSONArray jsonArray = obj.getJSONArray("vakken");
            arrayListVakken = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> hashMap;

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject vak = jsonArray.getJSONObject(i);
                vakNaam = vak.getString("name");
                vakStudiePunten = vak.getString("ects");
                vakCijfer = vak.getString("grade");
                vakPeriode = vak.getString("period");

                // waarden in arraylist zetten
                hashMap = new HashMap<String, String>();
                hashMap.put("name", vakNaam);
                hashMap.put("ects", vakStudiePunten);
                hashMap.put("grade", vakCijfer);
                hashMap.put("period", vakPeriode);

                arrayListVakken.add(hashMap);
            }
            Log.d("jsonstring.json", "succesvol ingelezen");


        } catch (JSONException e) {
            Log.d("JSONParser()", "catch");
            e.printStackTrace();
        }
        Log.d("arrayListVakken", arrayListVakken.toString());
        return jsonString;
    }

    public ArrayList<HashMap<String, String>> getArrayList() {
        return arrayListVakken;
    }

    public String loadJSONFromAsset() {
        // JSON string ophalen
        Log.d("loadJSONFromAsset()", "Started!");
        Toast.makeText(getBaseContext(), "jsonstring.json inlezen!; ", Toast.LENGTH_SHORT).show();
        String json; // = null;
        try {
            InputStream is = getAssets().open("jsonstring.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            mPrefs.saveStringToSharedPreferences(getBaseContext(), "vakken", json); // opslaan in preferences

        } catch (IOException ex) {
            Log.d("loadJSONFromAsset()", "catch");
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}
