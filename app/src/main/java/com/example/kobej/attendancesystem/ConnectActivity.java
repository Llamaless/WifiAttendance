package com.example.kobej.attendancesystem;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

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

        strongestWifi(wifiManager);
        getInfo(wifiManager);
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

    public ArrayList<String> getInfo(WifiManager wifiManager){
        /*
        finds the BSSID and MacAddress using wifiInfo
         */
        ArrayList<String> info = new ArrayList<>();
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        info.add(wifiInfo.getBSSID());
        info.add(wifiInfo.getMacAddress());
        setText(info);
        return info;
    }

    public void setText(ArrayList<String> info){
        /*
        sets text views
         */
        TextView bssid, macaddress;
        bssid = findViewById(R.id.text);
        macaddress = findViewById(R.id.text2);
        bssid.setText(info.get(0));
        macaddress.setText(info.get(1));
    }
}
