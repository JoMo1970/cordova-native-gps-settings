package com.albaresapps.plugins;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.provider.Settings;
import android.app.AlertDialog;
import android.content.DialogInterface;

/**
 * This class echoes a string called from JavaScript.
 */
public class gpssettings extends CordovaPlugin {

  //init private class variables
  //private static final String ACTION_CHECK_GPS = "CheckGPS";
  private static final String ACTION_RENDER_GPS_SETTINGS = "RenderGpsSettings";
  //private static final String ACTION_GET_GPS_LOCATION = "GetLocation";
  private static final String ACTION_KILL_APP = "KillApplication";
  //private final String USER_AGENT = "Mozilla/5.0";

    //plugin main interface function
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

      try {
          /*//check for enabled gps
          if (ACTION_CHECK_GPS.equals(action)) {

              //get the current activity
              final Activity currentActivity = this.cordova.getActivity();

              //check if gps is enabled
              if(this.isGPSEnabled()) {
                //get the location details
                Log.d("GPS", "GPS is enabled. Getting location details");

                //init a gps tracker object
                GPSTracker gpsTracker = new GPSTracker(this.cordova.getActivity());

                //init response json object
                JSONObject response = new JSONObject();
                response.put("latitude", gpsTracker.getLatitude());
                response.put("longitude", gpsTracker.getLongitude());

                //set the response object and return it
                callbackContext.success(response);

                //kill the gpsTracker object
                gpsTracker = null;

              }
              else {
                //init alertdialog builder
                AlertDialog.Builder builder = new AlertDialog.Builder(currentActivity);

                //set dialog text
                builder.setMessage("Would you like to enable GPS?").setTitle("GPS Disabled");

                //init dialog buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                  public void onClick(DialogInterface dialog, int id) {
                    Log.d("GPS", "Dialog OK button clicked");
                    //render the gps settings
                    final Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    currentActivity.startActivity(intent);

                    //kill the app
                    currentActivity.finish();
                    System.exit(0);
                  }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                  public void onClick(DialogInterface dialog, int id) {
                    Log.d("GPS", "Dialog Cancel button clicked");
                    //kill the app
                    currentActivity.finish();
                    System.exit(0);
                  }
                });

                //init the dialog onto the view
                AlertDialog dialog = builder.create();
                dialog.show();
              }
              return true;
          }*/
          //check for the render gps settings view
          if (ACTION_RENDER_GPS_SETTINGS.equals(action)) {
              //render the gps settings
              final Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
              this.cordova.getActivity().startActivity(intent);

              //kill the app
              this.cordova.getActivity().finish();
              System.exit(0);
          }
          /*//check for the gps location call
          else if (ACTION_GET_GPS_LOCATION.equals(action)) {
              //init a gps tracker object
              GPSTracker gpsTracker = new GPSTracker(this.cordova.getActivity());

              //init response json object
              JSONObject response = new JSONObject();
              response.put("latitude", gpsTracker.getLatitude());
              response.put("longitude", gpsTracker.getLongitude());

              //set the response object and return it
              callbackContext.success(response);

              //kill the gpsTracker object
              gpsTracker = null;

              //return default
              return true;
          }*/
          //check for the kill application call
          else if(ACTION_KILL_APP.equals(action)) {
              //kill the app
              this.cordova.getActivity().finish();
              System.exit(0);
          }

          //default action
          callbackContext.error("Invalid action");
          return false;

      } catch(Exception e) {
          System.err.println("Exception: " + e.getMessage());

          //init response json object
          JSONObject response = new JSONObject();
          response.put("hasGps", false);

          //send back the response with json object
          callbackContext.error(response);
          return false;
      }
    }

    //this function will return a bool indicating if gps is online or not
    private boolean isGPSEnabled() {
        LocationManager locationManager = (LocationManager)
                this.cordova.getActivity().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private void coolMethod(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }

    //GPSTracker class
    private class GPSTracker extends Service implements LocationListener {

        private final Context mContext;

        // Flag for GPS status
        boolean isGPSEnabled = false;

        // Flag for network status
        boolean isNetworkEnabled = false;

        // Flag for GPS status
        boolean canGetLocation = false;

        Location location; // Location
        double latitude; // Latitude
        double longitude; // Longitude

        // The minimum distance to change Updates in meters
        private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

        // The minimum time between updates in milliseconds
        private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

        // Declaring a Location Manager
        protected LocationManager locationManager;

        public GPSTracker(Context context) {
            this.mContext = context;
            getLocation();
        }

        public Location getLocation() {
            try {
                locationManager = (LocationManager) mContext
                        .getSystemService(LOCATION_SERVICE);

                // Getting GPS status
                isGPSEnabled = locationManager
                        .isProviderEnabled(LocationManager.GPS_PROVIDER);

                // Getting network status
                isNetworkEnabled = locationManager
                        .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                if (!isGPSEnabled && !isNetworkEnabled) {
                    // No network provider is enabled
                } else {
                    this.canGetLocation = true;
                    if (isNetworkEnabled) {
                        locationManager.requestLocationUpdates(
                                LocationManager.NETWORK_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("Network", "Network");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                    // If GPS enabled, get latitude/longitude using GPS Services
                    if (isGPSEnabled) {
                        if (location == null) {
                            locationManager.requestLocationUpdates(
                                    LocationManager.GPS_PROVIDER,
                                    MIN_TIME_BW_UPDATES,
                                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                            Log.d("GPS Enabled", "GPS Enabled");
                            if (locationManager != null) {
                                location = locationManager
                                        .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                if (location != null) {
                                    latitude = location.getLatitude();
                                    longitude = location.getLongitude();
                                }
                            }
                        }
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            return location;
        }


        /**
         * Stop using GPS listener
         * Calling this function will stop using GPS in your app.
         * */
        public void stopUsingGPS(){
            if(locationManager != null){
                locationManager.removeUpdates(GPSTracker.this);
            }
        }


        /**
         * Function to get latitude
         * */
        public double getLatitude(){
            if(location != null){
                latitude = location.getLatitude();
            }

            // return latitude
            return latitude;
        }


        /**
         * Function to get longitude
         * */
        public double getLongitude(){
            if(location != null){
                longitude = location.getLongitude();
            }

            // return longitude
            return longitude;
        }

        /**
         * Function to check GPS/Wi-Fi enabled
         * @return boolean
         * */
        public boolean canGetLocation() {
            return this.canGetLocation;
        }

        @Override
        public void onLocationChanged(Location location) {
        }


        @Override
        public void onProviderDisabled(String provider) {
        }


        @Override
        public void onProviderEnabled(String provider) {
        }


        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }


        @Override
        public IBinder onBind(Intent arg0) {
            return null;
        }
    }
}
