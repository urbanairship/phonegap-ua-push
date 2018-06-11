# Urban Airship Cordova Plugin

This plugin supports Cordova apps running on both iOS and Android.

### Resources:
 - [Getting started guide](http://docs.urbanairship.com/platform/cordova/)
 - [JSDocs](http://docs.urbanairship.com/reference/libraries/urbanairship-cordova/latest/)
 - [Migration docs](MIGRATION.md)

### Contributing Code

We accept pull requests! If you would like to submit a pull request, please fill out and submit our
[Contributor License Agreement](https://docs.google.com/forms/d/e/1FAIpQLScErfiz-fXSPpVZ9r8Di2Tr2xDFxt5MgzUel0__9vqUgvko7Q/viewform).

One of our engineers will verify receipt of the agreement before approving your pull request.

### Issues

Please visit http://support.urbanairship.com/ for any issues integrating or using this plugin.

### Requirements:
 - Cordova-CLI >= 7.0.0
 - Android [GCM Setup](http://docs.urbanairship.com/reference/push-providers/gcm.html#android-gcm-setup)
 - iOS [APNS Setup](http://docs.urbanairship.com/reference/push-providers/apns.html)

### Quickstart

Due to an [issue in the android resource processing (AAPT2)](https://issuetracker.google.com/issues/69347762),
the GCM/FCM sender ID either needs to be prefixed with `sender:` or you can disable AAPT2 with
[cordova-disable-aapt2](https://github.com/runtrizapps/cordova-android-disable-aapt2).

1. Install this plugin using Cordova CLI:

        cordova plugin add urbanairship-cordova


2. Modify the config.xml file to contain (replacing with your configuration settings):

        <!-- Urban Airship app credentials -->
        <preference name="com.urbanairship.production_app_key" value="Your Production App Key" />
        <preference name="com.urbanairship.production_app_secret" value="Your Production App Secret" />
        <preference name="com.urbanairship.development_app_key" value="Your Development App Key" />
        <preference name="com.urbanairship.development_app_secret" value="Your Development App Secret" />

        <!-- If the app is in production or not -->
        <preference name="com.urbanairship.in_production" value="true | false" />

        <!-- Optional config values -->

        <!-- Enable push when the application launches -->
        <preference name="com.urbanairship.enable_push_onlaunch" value="true | false" />

        <!-- Enable Analytics when the application launches -->
        <!-- Warning: Features that depend on analytics being enabled may not work properly if analytics is disabled (reports, location segmentation, region triggers, push to local time). -->
        <preference name="com.urbanairship.enable_analytics" value="true | false" />

        <!-- Urban Airship development log level defaults to debug -->
        <preference name="com.urbanairship.development_log_level" value="none | error | warn | info | debug | verbose" />

        <!-- Urban Airship production log level defaults to error -->
        <preference name="com.urbanairship.production_log_level" value="none | error | warn | info | debug | verbose" />

        <!-- Override the Android notification icon -->
        <preference name="com.urbanairship.notification_icon" value="ic_notification" />

        <!-- Override the Android notification large icon -->
        <preference name="com.urbanairship.notification_large_icon" value="ic_notification_large" />

        <!-- Override the Android notification sound (sound file should be in res/raw)-->
        <preference name="com.urbanairship.notification_sound" value="push" />

        <!-- Specify the notification accent color for Android API 21+ (Lollipop) -->
        <preference name="com.urbanairship.notification_accent_color" value="#0000ff" />

        <!-- Clear the iOS badge on launch -->
        <preference name="com.urbanairship.clear_badge_onlaunch" value="true | false" />

        <!-- Enables/disables auto launching the message center when the corresponding push is opened. -->
        <preference name="com.urbanairship.auto_launch_message_center" value="true | false" />

        <!-- iOS 10 alert foreground notification presentation option -->
        <preference name="com.urbanairship.ios_foreground_notification_presentation_alert" value="true | false"/>

        <!-- iOS 10 badge foreground notification presentation option -->
        <preference name="com.urbanairship.ios_foreground_notification_presentation_badge" value="true | false"/>

        <!-- iOS 10 sound foreground notification presentation option -->
        <preference name="com.urbanairship.ios_foreground_notification_presentation_sound" value="true | false"/>

3. Add any desired platform-specific resource files to config.xml:

       <!-- Optional: include custom notification button groups in XML format -->
       <platform name="android">
            ...
            <resource-file src="ua_custom_notification_buttons.xml" target="app/src/main/res/xml/ua_custom_notification_buttons.xml" />
       </platform>
       
       ...

       <!-- Optional: include custom notification categories in plist format -->
       <platform name="iOS">
            ...
            <resource-file src="UACustomNotificationCategories.plist" />
       </platform>

4. *(Android Only)* Add a reference to your google-servies.json file, for configuring FCM (assuming it is parallel to config.xml in your project directory):

       <platform name="android">
            ...
            <resource-file src="google-services.json" target="app/google-services.json" />
       </platform>

5. *(iOS Only)* Add your Apple Developer Account Team ID to the [build.json](https://cordova.apache.org/docs/en/latest/guide/platforms/ios/#using-buildjson):

        {
            "ios": {
                "debug": {
                    "developmentTeam": "XXXXXXXXXX"
                },
                "release": {
                    "developmentTeam": "XXXXXXXXXX"
                }
            }
        }
    Your iOS builds will need to reference the build.json using Cordova's "--buildConfig" flag.

6. Enable user notifications:

        // Enable user notifications (will prompt the user to accept push notifications)
        UAirship.setUserNotificationsEnabled(true, function (enabled) {
            console.log("User notifications are enabled! Fire away!")
        })

### Sample

A sample can be found in the Example directory.

1. Add your UA credentials to the `config_sample.xml` file in the root directory and save.
2. Add your development team id to the `build_sample.json` file in the root directory and save.
3. Run the script with the command `./create_sample.sh PROJECT_PATH PROJECT_NAME`
4. cd to the newly-created project directory, e.g. sample/test
5. Build the platform you want to test.
   * iOS
      1. Build with command `cordova build ios --emulator`
      2. After successful build, connect an iOS device to test
      3. Run on device with command `cordova run ios --device --developmentTeam=XXXXXXXXXX`
         * Please refer to "[Signing an App](https://cordova.apache.org/docs/en/latest/guide/platforms/ios/#signing-an-app)" for more information about code signing.
   * Android
      1. Build with command `cordova build android` in test directory
      2. After successful build, connect an android device to test
      3. Test with command `cordova run android`
