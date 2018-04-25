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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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

    public DBHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

         /*
        Setting up Database handler
         */
        dbHandler = new DBHandler(this);

        /*
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

        listViewSet(username);
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
        TextView message, username;
        //String info = getInfo(wifiManager);
        message = findViewById(R.id.text2);
        username = findViewById(R.id.text);
        message.setText("You have not yet attempted to sign in");
        username.setText("Hello " + user);
    }

    public void onButtonClick(){
        /*
        Code for what happens when the sign in button is
        clicked
         */
        final Intent finished = new Intent(this, LoginActivity.class);
        Button signIn = findViewById(R.id.sigin_button);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(finished);
                finish();
            }
        });
    }

    public void resetText(){
        /*
        resets the text view to notify the user of changes
         */
        TextView value = findViewById(R.id.text2);
        value.setText("You have been signed in");
    }

    public void resetText2(){
        /*
        notifies user if information is incorrect
         */
        TextView value = findViewById(R.id.text2);
        value.setText("You have not been signed in");
    }

    public void getTime(String code, String username){
        /*
        Gets the time and compares it to the times from the database
        does the same with the date and bssid
         */
        WifiManager wifiManager = (WifiManager)getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        SimpleDateFormat simpleDate = new SimpleDateFormat("HH:mm aa", Locale.getDefault());
        SimpleDateFormat stringFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        ArrayList times, times2, times3, times4;
        times = dbHandler.getTimes1(code);
        times2 = dbHandler.getTimes2(code);
        times3 = dbHandler.getTimes3(code);
        //times4 = dbHandler.getTimes4(code);
        Date time = calendar.getTime();
        String stringTime = stringFormat.format(time);
        try{
            Date comparison1 = simpleDate.parse(String.valueOf(times.get(0)));
            Date comparison2 = simpleDate.parse(String.valueOf(times2.get(0)));
            String currentDay = String.valueOf(times3.get(0));
            //String bssid = String.valueOf(times4.get(0));
            //String info = getInfo(wifiManager);
            if((time.after(comparison1)) && (time.after(comparison2)) && dayOfTheWeek.equals(currentDay)){
                resetText();
                dbHandler.insertSigned(username, stringTime, code);
            }else{
                resetText2();
            }
        }catch (ParseException e){
            //Todo Auto Generated catch block
            e.printStackTrace();
        }


    }

    public void listViewSet(final String user){
        final ListView classes;
        classes = findViewById(R.id.listClasses2);
        ArrayList id;
        id = dbHandler.getStudentClasses1(user);
        String idNumber = String.valueOf(id.get(0));
        ArrayList lectures;
        lectures = dbHandler.getStudentClasses2(idNumber);
        //String[] lectures = new String[] {"Class 1", "Class 2", "Class 3"};
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.listview, lectures);
        classes.setAdapter(adapter);
        classes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = (String)classes.getItemAtPosition(position);
                getTime(item, user);
            }
        });
    }



}
