package com.yan.highprivacytest

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.pm.ProviderInfo
import android.database.Cursor
import android.net.Uri
import android.util.Log

/**
 * @author Bevan (Contact me: https://github.com/genius158)
 * @since  2020/9/24
 */
class TestProvider : ContentProvider() {
    override fun insert(uri: Uri, values: ContentValues?): Uri? {
       return null
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        return null
    }

    override fun onCreate(): Boolean {
        Log.e("TestProvider", "onCreate onCreate onCreate onCreate onCreate")
        return true
    }

    override fun attachInfo(context: Context?, info: ProviderInfo?) {
        super.attachInfo(context, info)
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
       return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
      return 0
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }
}