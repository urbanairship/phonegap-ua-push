/* Copyright 2018 Urban Airship and Contributors */

package com.urbanairship.cordova;

import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.urbanairship.push.PushMessage;
import com.urbanairship.util.UAStringUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility methods.
 */
public class Utils {

    /**
     * Parses a {@link PushMessage} from a status bar notification.
     *
     * @param statusBarNotification The status bar notification.
     * @return The push message from the status bar notification.
     */
    @NonNull
    public static PushMessage messageFromNotification(@NonNull StatusBarNotification statusBarNotification) {
        Bundle extras = statusBarNotification.getNotification().extras;
        if (extras == null) {
            return new PushMessage(new Bundle());
        }

        Bundle pushBundle = extras.getBundle(CordovaNotificationFactory.PUSH_MESSAGE_BUNDLE_EXTRA);
        if (pushBundle == null) {
            return new PushMessage(new Bundle());
        } else {
            return new PushMessage(pushBundle);
        }
    }

    /**
     * Helper method to create a notification JSONObject.
     *
     * @param message The push message.
     * @param notificationTag The optional notification tag.
     * @param notificationId The optional notification ID.
     * @return A JSONObject containing the notification data.
     */
    @NonNull
    public static JSONObject notificationObject(@NonNull PushMessage message, @Nullable String notificationTag, @Nullable Integer notificationId) throws JSONException {
        JSONObject data = new JSONObject();
        Map<String, String> extras = new HashMap<>();
        for (String key : message.getPushBundle().keySet()) {
            if ("android.support.content.wakelockid".equals(key)) {
                continue;
            }
            if ("google.sent_time".equals(key)) {
                extras.put(key, Long.toString(message.getPushBundle().getLong(key)));
                continue;
            }
            if ("google.ttl".equals(key)) {
                extras.put(key, Integer.toString(message.getPushBundle().getInt(key)));
                continue;
            }
            String value = message.getPushBundle().getString(key);
            if (value != null) {
                extras.put(key, value);
            }
        }

        data.putOpt("message", message.getAlert());
        data.putOpt("title", message.getTitle());
        data.putOpt("subtitle", message.getSummary());
        data.putOpt("extras", new JSONObject(extras));

        if (notificationId != null) {
            data.putOpt("notification_id", notificationId);
            data.putOpt("notificationId", getNotificationId(notificationId, notificationTag));
        }
        return data;
    }

    @NonNull
    private static String getNotificationId(int notificationId, @Nullable String notificationTag) {
        String id = String.valueOf(notificationId);
        if (!UAStringUtil.isEmpty(notificationTag)) {
            id += ":" + notificationTag;
        }
        return id;
    }
}
