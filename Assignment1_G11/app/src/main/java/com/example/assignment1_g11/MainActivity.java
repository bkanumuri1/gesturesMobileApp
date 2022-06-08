package com.example.assignment1_g11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Spinner gestureSpinner;
    private Button nextToAct2;

    public void nextToTutorial() {
        gestureSpinner = findViewById(R.id.gestureList);
        Intent intent = new Intent(this, Activity2.class);
        intent.putExtra("Selected_Gesture: ", gestureSpinner.getSelectedItem().toString());
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gestureSpinner = findViewById(R.id.gestureList);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.gestureList, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        gestureSpinner.setAdapter(adapter);

        nextToAct2 = (Button) findViewById(R.id.button1);
        nextToAct2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button1:
                nextToTutorial();
                break;
        }

    }
}