package com.example.sidnotebook.summary;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private WebView myWebView;
    private TextView story;
    private Context context;
    private String selectedText;
    boolean loadingFinished=true;
    private static final String ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = findViewById(R.id.readAloudBtn);
        story = findViewById(R.id.storyBox);

        //Internet permission setup
        final Context context = getApplicationContext();
        int res = context.checkCallingOrSelfPermission("android.permission.INTERNET");

        if (res!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{"android.permission.INTERNET"},1);
        }

        res = context.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE");
        if (res!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"},1);
        }

        res = context.checkCallingOrSelfPermission("android.permission.READ_EXTERNAL_STORAGE");
        if (res!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{"android.permission.READ_EXTERNAL_STORAGE"},1);
        }

        //setup webView
        myWebView = findViewById(R.id.webView);

        myWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String s, String s1, String s2, String s3, long l) {

                DownloadManager.Request req= new DownloadManager.Request(Uri.parse(s));
                req.setMimeType(s3);
                String cookies = CookieManager.getInstance().getCookie(s);
                req.addRequestHeader("cookie",cookies);
                req.allowScanningByMediaScanner();
                req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                req.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"greetings.mp3");
                DownloadManager dm =(DownloadManager)getSystemService(DOWNLOAD_SERVICE);
                dm.enqueue(req);
                }
        });

        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                loadingFinished=false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                loadingFinished = true;
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        //setup storyBox for reading
        story = findViewById(R.id.storyBox);

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int selStart, selEnd;
                int min = 0;
                int max = story.getText().length();
                if (story.isFocused()) {
                    selStart = story.getSelectionStart();
                    selEnd = story.getSelectionEnd();
                    Log.e("hello",""+selStart+","+selEnd);
                    min = Math.max(0, Math.min(selStart, selEnd));
                    max = Math.max(0, Math.max(selStart, selEnd));
                    selectedText = story.getText().subSequence(min,max).toString();
                }

                String data = selectedText;
                //data = story.getText().toString();
                String[] a = data.split(" ");

                data = "";
                for(int i =0; i <a.length; i++){
                    data +=   a[i]+"%20" ;
                }

                myWebView.getSettings().setJavaScriptEnabled(true);
                myWebView.getSettings().setLoadsImagesAutomatically(true);

                //delay - allow server to process the text
                myWebView.loadUrl("http://192.168.43.136:5000/audgen/?text="+data);

                File file = new File("/storage/emulated/0/Download","greetings.mp3");
                if (file.exists()){
                    file.delete();
                }
                else{
                    ;
                }

                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                myWebView.loadUrl("http://192.168.43.136:5000/download");
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        File file = new File("/storage/emulated/0/Download","greetings.mp3");
                        MediaPlayer greet = MediaPlayer.create(MainActivity.this, Uri.parse("/storage/emulated/0/Download/greetings.mp3"));
                        if(file.exists()) {
                            greet.start();
                        }
                    }
                },2000);

            }
        });





    }





}
