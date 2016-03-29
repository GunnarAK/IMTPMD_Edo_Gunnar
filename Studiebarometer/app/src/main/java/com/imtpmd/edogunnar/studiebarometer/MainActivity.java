package com.imtpmd.edogunnar.studiebarometer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ArrayList<HashMap<String, String>> arrayList;
    SharedPreferences  mPrefs;
    public String myPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // functies aanroepen bij opstart app
        //loadJSONFromAsset();
        //sharedPreferences = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);

        JSONParser();

        try {
            mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor prefsEditor = mPrefs.edit();
            Gson gson = new Gson();
            String json = gson.toJson(arrayList);
            Log.d("json", json);
            prefsEditor.putString("MyJSONObject", json);
            prefsEditor.commit();

            myPreferences = mPrefs.getString("MyJSONObject", ""); // leest waarden uit
            Toast.makeText(MainActivity.this, myPreferences, Toast.LENGTH_SHORT).show();
            //Log.d("myPreferences", myPreferences);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Log.d("Jammer joh", "Jammer joh");
        }
        Log.d("myPreferences - end act", myPreferences);



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

    @Override
    protected void onStart()
    {
        super.onStart();

    }

    public void JSONParser() {
        // JSON inladen in listview

        //Log.d("myPreferences", myPreferences);

        if (myPreferences == null ) {
            //Log.d("JSONParser()", "if");
            Log.d("Preferences", "Nog geen preferences gevonden. Aanmaken...");
            try {
                JSONObject obj = new JSONObject(loadJSONFromAsset());
                //Log.d("JSONParser()", "Started!");
                JSONArray jsonArray = obj.getJSONArray("vakken");
                arrayList = new ArrayList<HashMap<String, String>>();
                HashMap<String, String> hashMap;

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject vak = jsonArray.getJSONObject(i);
                    String vakNaam = vak.getString("name");
                    String vakStudiePunten = vak.getString("ects");
                    String vakCijfer = vak.getString("grade");
                    String vakPeriode = vak.getString("period");

                    //Add your values in your `ArrayList` as below:
                    hashMap = new HashMap<String, String>();
                    hashMap.put("name", vakNaam);
                    hashMap.put("ects", vakStudiePunten);
                    hashMap.put("grade", vakCijfer);
                    hashMap.put("period", vakPeriode);

                    arrayList.add(hashMap);
                }
                Log.d("jsonstring.json", "succesvol ingelezen");


            } catch (JSONException e) {
                Log.d("JSONParser()", "catch");
                e.printStackTrace();
            }
        } else {
            Log.d("myPreferences", "bevat iets anders dan 'null'");
        }

    }

    public String loadJSONFromAsset() {
        // JSON string ophalen
        //Log.d("loadJSONFromAsset()", "Started!");
        String json = null;
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

    @Override
    protected void onPause()
    {
        super.onPause();
        myPreferences = "uw moeder is een flapdrol";
        Toast.makeText(MainActivity.this, myPreferences, Toast.LENGTH_SHORT).show();
        Log.d("myPreferences-onPause()", myPreferences);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Log.d("myPreferences-onDestroy", myPreferences);
    }
}
