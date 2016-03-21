package net.tobysullivan.whereami.locationsvc;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

public class MyLocationService extends Service {
    private LocationFinder locationFinder;
    private MyLocationBinder binder;

    public MyLocationService() {
        super();
    }

    @Override
    public IBinder onBind(Intent intent) {
        if (binder == null) {
            locationFinder = new LocationFinder(getApplicationContext());
            binder = new MyLocationBinder(locationFinder);
        }
        return binder;
    }


    public class MyLocationBinder extends Binder {
        private LocationFinder finder;

        public MyLocationBinder(LocationFinder finder) {
            this.finder = finder;
        }

        public boolean hasLocation() {
            return finder.isReady();
        }

        public Location getLocation() {
            return finder.getLocation();
        }
    }
}
