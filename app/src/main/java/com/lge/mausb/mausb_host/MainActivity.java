package com.lge.mausb.mausb_host;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    Switch sw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );

        final Intent intent_wifi_list = new Intent ( this, wifi_device_List.class );

        sw = (Switch) findViewById ( R.id.switch2 );

        sw.setOnCheckedChangeListener ( new CompoundButton.OnCheckedChangeListener ( ) {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    startActivity ( intent_wifi_list );
                }else{
                    //Disconnect the wifi connection
                }
            }
        } );
    }
}
