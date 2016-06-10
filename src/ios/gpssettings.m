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
/*//this function will validate if GPS is enabled/disabled
- (void)CheckGPS:(CDVInvokedUrlCommand*)command
{
    //check if gps is enabled
    if([CLLocationManager locationServicesEnabled]){
        NSLog(@"Location Services Enabled. Getting Location.");

        //init the passed command as global command
        globalCommand = command;

        //init the location manager
        locationManager = [[CLLocationManager alloc] init];
        locationManager.delegate = self;
        locationManager.desiredAccuracy = kCLLocationAccuracyBest;

        //init update event
        [locationManager startUpdatingLocation];
    }
    else {
        //init alert view controller
        UIAlertController *alert = [UIAlertController alertControllerWithTitle:@"GPS Disabled" message:@"Would you like to enable GPS?" preferredStyle:UIAlertControllerStyleActionSheet];

        //init controller actions
        UIAlertAction *confirmAction = [UIAlertAction actionWithTitle:@"Yes" style:UIAlertActionStyleDefault handler:^(UIAlertAction * action) {
            NSLog(@"Confirm Button pressed. Opening Settings view");

            //init the shared settings view
            //[[UIApplication sharedApplication] openURL:[NSURL URLWithString: UIApplicationOpenSettingsURLString]];
            [[UIApplication sharedApplication] openURL:[NSURL URLWithString:@"prefs:root=Settings"]];

            //close the app
            exit(0);
        }];
        UIAlertAction *cancelAction = [UIAlertAction actionWithTitle:@"No" style:UIAlertActionStyleDefault handler:^(UIAlertAction * action) {
            NSLog(@"Cancel Button Pressed. Shutting down app");

            //close the app
            exit(0);
        }];

        //add the actions to the alert view controller
        [alert addAction:confirmAction];
        [alert addAction:cancelAction];

        //render the alert
        [self.viewController presentViewController:alert animated:YES completion:nil];

    }

}

//this function will trigger when the location has updated with an error
-(void)locationManager:(CLLocationManager *)manager didFailWithError:(NSError *) error {
    NSLog(@"User location lookup failed");
}
//this function will trigger when the location has updated with correct data
-(void) locationManager:(CLLocationManager *)manager didUpdateLocations:(NSArray *) locations {

    //get the location object
    NSLog(@"Location Acquired");
    CLLocation * location = [locations lastObject];

    //get the latitude and longitude values
    NSString *latitude = [NSString stringWithFormat:@"%.20lf", location.coordinate.latitude];
    NSString *longitude = [NSString stringWithFormat:@"%.20lf", location.coordinate.longitude];
    //NSLog([NSString stringWithFormat: @"%@/%@", @"User Latitude: ", latitude ]);
    //NSLog([NSString stringWithFormat: @"%@/%@", @"User Longitude: ", longitude ]);

    //init response dictionary
    NSMutableDictionary * jsonLocationDictionary = [NSMutableDictionary dictionary];
    [jsonLocationDictionary setValue:latitude forKey:@"latitude"];
    [jsonLocationDictionary setValue:longitude forKey:@"longitude"];


    //init the pluginResult
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary: jsonLocationDictionary];


    //send back the result
    [self.commandDelegate sendPluginResult:pluginResult callbackId:globalCommand.callbackId];

}*/

@end
