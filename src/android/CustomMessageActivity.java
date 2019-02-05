/* Copyright 2010-2019 Urban Airship and Contributors */

package com.urbanairship.cordova;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.urbanairship.messagecenter.MessageActivity;

public class CustomMessageActivity extends MessageActivity {
    public static final String CLOSE_INTENT_ACTION = "CANCEL";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent() != null && CLOSE_INTENT_ACTION.equals(getIntent().getAction())) {
            finish();
        }
    }

    @Override
    protected void onNewIntent(@Nullable Intent intent) {
        super.onNewIntent(intent);
        if (intent != null && CLOSE_INTENT_ACTION.equals(intent.getAction())) {
            finish();
        }
    }
}
