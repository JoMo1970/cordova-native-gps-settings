var exec = require('cordova/exec');

//init plugin closures
module.exports = {
  //this function will render the gps settings view if the user has gps disabled
  showNativeGpsSettings : function(arg0, success, error) {
    console.log("Rendering Native Settings View via 'gpsettings' plugin");
    //init plugin call
    exec(success, error, "gpssettings", "RenderGpsSettings", [arg0]);
  },
  //this function will kill the application from the native processes
  killApplication : function(arg0, success, error) {
    console.log("Killing Application via 'gpsettings' plugin");
    //init plugin call
    exec(success, error, "gpssettings", "KillApplication", [arg0]);
  }
};
