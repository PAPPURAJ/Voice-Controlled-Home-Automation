package com.blogspot.rajbtc.voice_automation;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private final int REQ_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView speak = findViewById(R.id.iv_mic);


        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                        "Need to speak");
                try {
                    startActivityForResult(intent, REQ_CODE);
                } catch (ActivityNotFoundException a) {
                    Toast.makeText(getApplicationContext(),
                            "Sorry your device not supported",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    ((TextView) findViewById(R.id.tv_speech_to_text)).setText(result.get(0) + "");
                    DatabaseReference database = FirebaseDatabase.getInstance().getReference("Load");
                    String text = result.get(0).toLowerCase();

                    if (text.contains("light") && text.contains("on")) {

                        database.child("Light").setValue("1");

                    } else if ((text.contains("light") && text.contains("off"))) {
                        database.child("Light").setValue("0");
                    }
                    else if (text.contains("Pump") && text.contains("on")) {
                        database.child("Pump").setValue("1");

                    } else if ((text.contains("Pump") && text.contains("off"))) {

                        database.child("Pump").setValue("0");

                    } else {
                        ((TextView) findViewById(R.id.tv_speech_to_text)).setText("Please try again");
                    }


                }
                break;
            }
        }
    }
}