package net.tobysullivan.whereami;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.tobysullivan.whereami.locationsvc.Location;
import net.tobysullivan.whereami.locationsvc.MyLocationService;

public class MyLocation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_location);

        Button b1 = (Button)findViewById(R.id.whereAmIButton);
        b1.setOnClickListener(whereAmIClickHandler);
    }

    private void startLocationService() {
        Intent intent = new Intent(this, MyLocationService.class);
        bindService(intent, svcConnection, Context.BIND_AUTO_CREATE);
    }

    private void updateWithLocation(Location loc) {
        if (loc == null) return;

        String content = String.format("%d, %d", loc.getLatitude(), loc.getLongitude());

        TextView locationTxt = (TextView)findViewById(R.id.locationText);
        locationTxt.setText(content);
    }

    View.OnClickListener whereAmIClickHandler = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            TextView locationTxt = (TextView)findViewById(R.id.locationText);
            locationTxt.setText("Searching...");

            startLocationService();
        }
    };

    private ServiceConnection svcConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyLocationService.MyLocationBinder binder = (MyLocationService.MyLocationBinder)service;
            while (!binder.hasLocation()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {}
            }

            updateWithLocation(binder.getLocation());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}
