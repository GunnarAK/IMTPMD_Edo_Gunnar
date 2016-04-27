package com.imtpmd.edogunnar.studiebarometer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Gunnar on 29-3-2016.
 */
public class RegisterActivity extends AppCompatActivity {

    SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button submit = (Button) findViewById(R.id.submitButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView naamStudent = (TextView) findViewById(R.id.editTextNaamStudent);
                String stringNaamStudent = naamStudent.getText().toString(); // naam extracten uit textview
//                String aantalStudiepunten = "0";

                mPrefs.saveStringToSharedPreferences(getBaseContext(), "naamStudent", stringNaamStudent); // naam opslaan in preferences
//                mPrefs.saveStringToSharedPreferences(getBaseContext(), "aantalStudiepunten", aantalStudiepunten); // studiepunten opslaan in preferences
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}
