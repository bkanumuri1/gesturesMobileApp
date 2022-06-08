package com.example.assignment1_g11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import java.util.AbstractMap;
import java.util.Map;

public class Activity2 extends AppCompatActivity implements View.OnClickListener{

    static String selectedGesture;
    VideoView videoViewObject;
    Button practiceButton;
    Button playButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        selectedGesture = getIntent().getStringExtra("Selected_Gesture");

        practiceButton = (Button) findViewById(R.id.practiceButton);
        practiceButton.setOnClickListener(this);

        playButton = findViewById(R.id.playButton);
        playButton.setOnClickListener(this);
    }

    Map<String,Integer> gestureMapping = Map.ofEntries(
            new AbstractMap.SimpleEntry<String, Integer>("AC Power", R.raw.acpower),
            new AbstractMap.SimpleEntry<String,Integer>("Algorithm", R.raw.algorithm),
            new AbstractMap.SimpleEntry<String,Integer>("Antenna", R.raw.antenna),
            new AbstractMap.SimpleEntry<String,Integer>("Authentication", R.raw.authentication),
            new AbstractMap.SimpleEntry<String,Integer>("Authorization", R.raw.authorization),
            new AbstractMap.SimpleEntry<String,Integer>("Bandwidth", R.raw.bandwidth),
            new AbstractMap.SimpleEntry<String,Integer>("Bluetooth", R.raw.bluetooth),
            new AbstractMap.SimpleEntry<String,Integer>("Browser", R.raw.browser),
            new AbstractMap.SimpleEntry<String,Integer>("Cloud Computing", R.raw.cloudcomputing),
            new AbstractMap.SimpleEntry<String,Integer>("Data Compression", R.raw.datacompression),
            new AbstractMap.SimpleEntry<String,Integer>("Data Link Layer", R.raw.datalinklayer),
            new AbstractMap.SimpleEntry<String,Integer>("Data Mining", R.raw.datamining),
            new AbstractMap.SimpleEntry<String,Integer>("Decryption", R.raw.decryption),
            new AbstractMap.SimpleEntry<String,Integer>("Domain", R.raw.domain),
            new AbstractMap.SimpleEntry<String,Integer>("Email", R.raw.email),
            new AbstractMap.SimpleEntry<String,Integer>("Exposure", R.raw.exposure),
            new AbstractMap.SimpleEntry<String,Integer>("Filter", R.raw.filter),
            new AbstractMap.SimpleEntry<String,Integer>("Firewall", R.raw.firewall),
            new AbstractMap.SimpleEntry<String,Integer>("Flooding", R.raw.flooding),
            new AbstractMap.SimpleEntry<String,Integer>("Gateway", R.raw.gateway),
            new AbstractMap.SimpleEntry<String,Integer>("Hacker", R.raw.hacker),
            new AbstractMap.SimpleEntry<String,Integer>("Header", R.raw.header),
            new AbstractMap.SimpleEntry<String,Integer>("Hot Swap", R.raw.hotswap),
            new AbstractMap.SimpleEntry<String,Integer>("Hyperlink", R.raw.hyperlink),
            new AbstractMap.SimpleEntry<String,Integer>("Infrastructure", R.raw.infrastructure),
            new AbstractMap.SimpleEntry<String,Integer>("Integrity", R.raw.integrity),
            new AbstractMap.SimpleEntry<String,Integer>("Internet", R.raw.internet),
            new AbstractMap.SimpleEntry<String,Integer>("Intranet", R.raw.intranet),
            new AbstractMap.SimpleEntry<String,Integer>("Latency", R.raw.latency),
            new AbstractMap.SimpleEntry<String,Integer>("Loopback", R.raw.loopback),
            new AbstractMap.SimpleEntry<String,Integer>("Motherboard", R.raw.motherboard),
            new AbstractMap.SimpleEntry<String,Integer>("Network", R.raw.network),
            new AbstractMap.SimpleEntry<String,Integer>("Networking", R.raw.networking),
            new AbstractMap.SimpleEntry<String,Integer>("Network Layer", R.raw.networklayer),
            new AbstractMap.SimpleEntry<String,Integer>("Node", R.raw.node),
            new AbstractMap.SimpleEntry<String,Integer>("Packet", R.raw.packet),
            new AbstractMap.SimpleEntry<String,Integer>("Partition", R.raw.partition),
            new AbstractMap.SimpleEntry<String,Integer>("Password Sniffing", R.raw.passwordsniffing),
            new AbstractMap.SimpleEntry<String,Integer>("Patch", R.raw.patch),
            new AbstractMap.SimpleEntry<String,Integer>("Phishing", R.raw.phishing),
            new AbstractMap.SimpleEntry<String,Integer>("Physical Layer", R.raw.physicallayer),
            new AbstractMap.SimpleEntry<String,Integer>("Ping", R.raw.ping),
            new AbstractMap.SimpleEntry<String,Integer>("Port scan", R.raw.portscan),
            new AbstractMap.SimpleEntry<String,Integer>("Presentation Layer", R.raw.presentationlayer),
            new AbstractMap.SimpleEntry<String,Integer>("Protocol", R.raw.protocol));

    public void nextToRecord() {
        Intent intent = new Intent(this, Activity3.class);
        intent.putExtra("Selected_Gesture", selectedGesture);
        startActivity(intent);
    }

    public void playGestureVideos() {
        videoViewObject = findViewById(R.id.videoView);
        int gestureInt = gestureMapping.get(selectedGesture);

        String videoPath = "android.resource://" + "com.example.assignment1_g11/" + gestureInt;

        Uri uri = Uri.parse(videoPath);
        MediaController mediaController= new MediaController(this);
        mediaController.setAnchorView(videoViewObject);

        videoViewObject.setMediaController(mediaController);
        videoViewObject.setVideoURI(uri);
        videoViewObject.requestFocus();
        videoViewObject.start();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.playButton:
                playGestureVideos();
                break;
            case R.id.practiceButton:
                nextToRecord();
                break;
        }
    }
}
