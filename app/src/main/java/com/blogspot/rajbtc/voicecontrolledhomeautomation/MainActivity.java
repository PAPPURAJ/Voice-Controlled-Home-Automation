package com.blogspot.rajbtc.voicecontrolledhomeautomation;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.rajbtc.voicecontrolledhomeautomation.R;
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
        // Write a message to the database


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
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    String text = result.get(0).toLowerCase();

                    if (text.contains("light") && text.contains("on")) {

                        database.getReference("Light").setValue("1");

                    } else if ((text.contains("light") && text.contains("off"))) {
                        database.getReference("Light").setValue("0");
                    }
                    else if (text.contains("fan") && text.contains("on")) {
                        database.getReference("Fan").setValue("1");

                    } else if ((text.contains("fan") && text.contains("off"))) {

                        database.getReference("Fan").setValue("0");

                    } else {
                        ((TextView) findViewById(R.id.tv_speech_to_text)).setText("Please try again");
                    }


                }
                break;
            }
        }
    }
}