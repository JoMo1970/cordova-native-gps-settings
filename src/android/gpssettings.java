package com.albaresapps.plugins;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Intent;
import android.provider.Settings;

/**
 * This class echoes a string called from JavaScript.
 */
public class gpssettings extends CordovaPlugin {

  //init private class variables
  private static final String ACTION_RENDER_GPS_SETTINGS = "RenderGpsSettings";
  private static final String ACTION_KILL_APP = "KillApplication";

    //plugin main interface function
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

      try {
          //check for the render gps settings view
          if (ACTION_RENDER_GPS_SETTINGS.equals(action)) {
              //render the gps settings
              final Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
              this.cordova.getActivity().startActivity(intent);

              //kill the app
              this.cordova.getActivity().finish();
              System.exit(0);
          }
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
          response.put("Error", e.getMessage());

          //send back the response with json object
          callbackContext.error(response);
          return false;
      }
    }
}
