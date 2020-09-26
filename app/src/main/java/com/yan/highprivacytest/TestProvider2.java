package com.yan.highprivacytest;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


/**
 * @author Bevan (Contact me: https://github.com/genius158)
 * @since 2020/9/24
 */
public class TestProvider2 extends TestProvider3 {
    @Override
    public void attachInfo(Context context, ProviderInfo info) {
        super.attachInfo(context, info);
        Log.e("attachInfo3", "TestProvider2 attachInfo attachInfo attachInfo ");
        Log.e("attachInfo2", "TestProvider2 attachInfo attachInfo attachInfo ");
        Log.e("attachInfo1", "TestProvider2 attachInfo attachInfo attachInfo ");
    }
    @Override
    public boolean onCreate() {
        Log.e("TestProvider3", "TestProvider2 TestProvider2 TestProvider2");

        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
