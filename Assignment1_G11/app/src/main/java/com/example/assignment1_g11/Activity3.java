package com.example.assignment1_g11;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Activity3 extends AppCompatActivity implements View.OnClickListener{

    public static Map<String, Integer> counterMap = new HashMap<>();
    public static final String TAG = "Server_Connection";
    private static int VIDEO_REQUEST=201;
    String ipv4AddressServer="0.0.0.0";
    String portNumber="5000";
    Button recordUserGesture;
    Button postFileToServer;
    String gestureSelected;
    String realPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        gestureSelected=getIntent().getStringExtra("Selected_Gesture");

        recordUserGesture=findViewById(R.id.recordButton);
        postFileToServer=findViewById(R.id.uploadButton);

        recordUserGesture.setOnClickListener(this);
        postFileToServer.setOnClickListener(this);
    }

    public void postRequest(String postUrl, RequestBody httpRequestBody){

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(postUrl)
                .post(httpRequestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, e.toString());
                call.cancel();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Activity3.this, "Video Uploaded to server", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Activity3.this, "Failed to connect to server", Toast.LENGTH_SHORT).show();
                        Intent backToSquareOne = new Intent(Activity3.this, MainActivity.class);
                        finish();
                        startActivity(backToSquareOne);
                    }
                });
            }
        });

    }



    public String getPathFromURI(Context context, Uri contentUri) {

        if ( contentUri.toString().indexOf("file:///") > -1 ){
            return contentUri.getPath();
        }

        Cursor thisCursor = null;
        try {
            String[] temp = { MediaStore.Images.Media.DATA };
            thisCursor = context.getContentResolver().query(contentUri,  temp, null, null, null);
            int column_index = thisCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            thisCursor.moveToFirst();
            return thisCursor.getString(column_index);
        }finally {
            if (thisCursor != null) {
                thisCursor.close();
            }
        }
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.recordButton:
                recordVideo(view);
                break;

            case R.id.uploadButton:
                httpMultiFromRequestBody(realPath);
                break;

        }



    }

    public void recordVideo(View view) {

        Intent recordIntent= new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        recordIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT,5);
        recordIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        recordIntent.putExtra("android.intent.extras.CAMERA_FACING", 1);
        startActivityForResult(recordIntent, VIDEO_REQUEST);

    }

    public void httpMultiFromRequestBody(String videoUri){

        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        requestPermissions(permissions,1);

        if (counterMap.get(gestureSelected) == null) {
            counterMap.put(gestureSelected, 1);
        } else {
            Integer num=counterMap.get(gestureSelected);
            counterMap.put(gestureSelected, num+1);
        }

        String postUrl = "http://" + ipv4AddressServer + ":" + portNumber + "/";

        File stream=null;
        RequestBody postBodyImage=null;
        try
        {
            stream=new File(videoUri);

            postBodyImage = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("image",
                            gestureSelected+"_PRACTICE_"+counterMap.get(gestureSelected)+
                                    "_Anne"+".mp4", RequestBody.create(MediaType.parse("video/*"), stream))
                    .build();

        }
        catch (Exception ioexp)
        {
            ioexp.printStackTrace();
        }

        postRequest(postUrl, postBodyImage);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VIDEO_REQUEST && resultCode == RESULT_OK && data!=null ) {
            if (data.getData() != null) {
                Uri uri = data.getData();
                realPath=getPathFromURI(getApplicationContext(),uri);
                Toast.makeText(this, "Video has been saved to:\n" +
                        data.getData(), Toast.LENGTH_LONG).show();
            }
        }
        else if (requestCode == VIDEO_REQUEST && resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "Video recording cancelled.",
                    Toast.LENGTH_LONG).show();
        } else if(requestCode == VIDEO_REQUEST) {
            Toast.makeText(this, "Failed to record video",
                    Toast.LENGTH_LONG).show();
        }
    }

}