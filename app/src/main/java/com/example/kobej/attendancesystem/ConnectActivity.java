package com.example.kobej.attendancesystem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Kobe Davis - 6905105
 * 05/04/18
 */

public class ConnectActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        /*
        creating a wifi manager
        Essential to accessing all of the wifi
        */
        WifiManager wifiManager = (WifiManager)getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);

        /*
        Getting the information passed from the previous intent
         */
        Intent intent = getIntent();
        ArrayList<String> carried;
        carried = intent.getStringArrayListExtra("carryList");
        String username = String.valueOf(carried.get(0));


        strongestWifi(wifiManager);
        setText(wifiManager, username);
        onButtonClick();
    }


    public void strongestWifi(WifiManager wifiManager){
        /*
        finds the strongest wifi
         */
        TextView textView;
        //scans all available wifi points and outputs list of results
        List<ScanResult> results = wifiManager.getScanResults();
        String message;
        //as long as results aren't empty runs
        if(results != null){
            final int size = results.size();
            if(size == 0){
                message = "No access points in range";
            }else{
                //gets first result as a variable
                ScanResult signal = results.get(0);
                int counter = 1;
                for(ScanResult result : results){
                    textView = findViewById(R.id.text);
                    textView.setText(counter++ + "." + result.SSID +
                    " : " + result.level + "\n" + result.BSSID +
                    "\n" + result.capabilities + "\n" +
                    "\n =============================\n");
                    //if any of the other results are stronger they become signal
                    if(wifiManager.compareSignalLevel(signal.level, result.level
                    )<0){
                        signal = result;
                    }
                }
                message = String.format("%s networks found. %s id the strongest.",
                        size, signal.SSID + ":" + signal.level);
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
            //Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }

    public String getInfo(WifiManager wifiManager){
        /*
        finds the BSSID using wifiInfo
         */
        String info;
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        info = wifiInfo.getBSSID();
        return info;
    }

    public void setText(WifiManager wifiManager, String user){
        /*
        sets text views
         */
        TextView bssid, username;
        String info = getInfo(wifiManager);
        bssid = findViewById(R.id.text2);
        username = findViewById(R.id.text);
        bssid.setText(info.toString());
        Integer time = time();
        if(time.equals(1)){
            username.setText("Good Morning" + user);
        }
        if(time.equals(2)){
            username.setText("Good Afternoon " + user);
        }
        if(time.equals(3)){
            username.setText("Good Evening " + user);
        }

    }

    public Integer time(){
        /*
        works out the time to greet person based on time
        will also be useful for determining class times
         */
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        SimpleDateFormat simpleDate = new SimpleDateFormat("HH:mm aa", Locale.getDefault());
        Date time = calendar.getTime();
        try{
            Date comparison = simpleDate.parse("12:00 PM");
            Date comparison2 = simpleDate.parse("5:00 PM");
            if(time.after(comparison2)){
                return 3;
            }
            if((time.after(comparison)) && (time.before(comparison2))){
                return 2;
            }
            if(time.before(comparison)){
                return 1;
            }
            if(time.equals(comparison)){
                return 2;
            }

        }catch (ParseException e){
            //Todo Auto Generated catch block
            e.printStackTrace();
        }
        return 4;
    }

    public void onButtonClick(){
        /*
        Code for what happens when the sign in button is
        clicked
         */
        Button signIn = findViewById(R.id.sigin_button);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View v) {
                resetText();
            }
        });
    }

    public void resetText(){
        TextView value = findViewById(R.id.text2);
        value.setText("You have been signed in");
    }

}
