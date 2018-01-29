package com.lge.mausb.mausb_host;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class wifi_device_List extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION = 1;
    IntentFilter intentFilter = null;

    public static final String TAG = "SINJO";


    WifiManager wifiManager;


    ArrayAdapter<String> adapter;

    ArrayList<String> connections = new ArrayList<String> (  );

    List<ScanResult> refreshScanList = new ArrayList<ScanResult> (  );

    BroadcastReceiver wifiScanReceiver = new BroadcastReceiver ( ) {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "In broadcast receiver" );
            refreshScanList = wifiManager.getScanResults ();
            Log.d(TAG, "Scan result " + refreshScanList );
            for(int i = 0; i < refreshScanList.size (); i++){
                if(!refreshScanList.get ( i ).SSID.isEmpty()){
                    connections.add ( refreshScanList.get ( i ).SSID );
                }

            }

            Log.d(TAG, "CONNECTIONS: " + connections);

            adapter.notifyDataSetChanged ();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );

        setContentView ( R.layout.activity_wifi_device__list );


        ListView listView =  findViewById ( R.id.wifi_list );
        //TextView textView = (TextView)findViewById ( R.id.textView2 );

        IntentFilter intentFilter = new IntentFilter ( wifiManager.SCAN_RESULTS_AVAILABLE_ACTION  );



        wifiManager = (WifiManager) getApplicationContext().getSystemService ( Context.WIFI_SERVICE );



        adapter = new ArrayAdapter<> ( this, R.layout.activity_wifi_device__list, R.id.wifi_text, connections);
        //adapter = new ArrayAdapter<String> ( this, R.layout.activity_wifi_device__list, R.id.textView2, connections);

        listView.setAdapter(adapter);

        registerReceiver ( wifiScanReceiver, new IntentFilter ( wifiManager.SCAN_RESULTS_AVAILABLE_ACTION ) );



        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission( Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method

        }else{
            wifiManager.startScan ();
            //do something, permission was previously granted; or legacy device
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Do something with granted permission
            wifiManager.startScan ();
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy ();
        unregisterReceiver ( wifiScanReceiver );
    }

}
