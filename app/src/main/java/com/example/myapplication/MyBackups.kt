package com.example.myapplication

import android.app.backup.BackupAgentHelper
import android.app.backup.BackupDataInput
import android.app.backup.BackupDataOutput
import android.app.backup.SharedPreferencesBackupHelper
import android.os.ParcelFileDescriptor
import android.util.Log
import java.io.IOException


class MyBackups : BackupAgentHelper() {

    override fun onCreate() {
        Log.i("---------->", "creating backup class")
        val defaultPrefsHelper = SharedPreferencesBackupHelper(this, PREFS)
        this.addHelper(PREFS_BACKUP_KEY, defaultPrefsHelper)
    }

    @Throws(IOException::class)
    override fun onBackup(
        oldState: ParcelFileDescriptor,
        data: BackupDataOutput,
        newState: ParcelFileDescriptor
    ) {
        Log.i("---------->", "backing up $data")
        super.onBackup(oldState, data, newState)
    }

    @Throws(IOException::class)
    override fun onRestore(
        data: BackupDataInput,
        appVersionCode: Int,
        newState: ParcelFileDescriptor
    ) {
        Log.i("---------->", "restoring")
        super.onRestore(data, appVersionCode, newState)
        // post-processing code goes here
    }

    companion object {
        fun newInstance() = MyBackups()
        const val PREFS = "data_prefs"
        const val PREFS_BACKUP_KEY = "myprefs"
    }
}