/********* gpssettings.m Cordova Plugin Implementation *******/

#import <Cordova/CDV.h>
#import <CoreLocation/CoreLocation.h>

@interface gpssettings : CDVPlugin <CLLocationManagerDelegate> {
    // Member variables go here.
}
- (void)CheckGPS:(CDVInvokedUrlCommand*)command;
@end

@implementation gpssettings

//global variables
CLLocationManager *locationManager;
CDVPluginResult* pluginResult = nil;
CDVInvokedUrlCommand* globalCommand = nil;

//this function will render the GPS settings view
- (void)RenderGpsSettings:(CDVInvokedUrlCommand*)command {
  //init to the settings view
  [[UIApplication sharedApplication] openURL:[NSURL URLWithString:@"prefs:root=Settings"]];
}
//this function will terminate the application
- (void)KillApplication:(CDVInvokedUrlCommand*)command {
  //close the app
  exit(0);
}

@end
