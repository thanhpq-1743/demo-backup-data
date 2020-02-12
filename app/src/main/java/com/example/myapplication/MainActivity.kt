package com.example.myapplication

import android.annotation.SuppressLint
import android.app.backup.BackupManager
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.app.backup.RestoreObserver
import android.content.SharedPreferences
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log


class MainActivity : AppCompatActivity() {

    lateinit var edit: SharedPreferences.Editor

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val backupManager = BackupManager(this)
        val prefs = getSharedPreferences(MyBackups.PREFS, Context.MODE_PRIVATE);
        edit = prefs.edit();

        val value = prefs.getString(MyBackups.PREFS_BACKUP_KEY, "");
        editText.setText(value);

        button.setOnClickListener {
            Log.i("---------->", "MainActivity  - Click: ")
            edit.putString(MyBackups.PREFS_BACKUP_KEY, editText.text.toString());
            edit.commit();
            backupManager.dataChanged();
        }


        backupManager.requestRestore(object : RestoreObserver() {
            override fun restoreFinished(error: Int) {
                super.restoreFinished(error)
                val value = prefs.getString(MyBackups.PREFS_BACKUP_KEY, "")
                editText.setText(value)
            }

            override fun restoreStarting(numPackages: Int) {
                super.restoreStarting(numPackages)
            }

            override fun onUpdate(nowBeingRestored: Int, currentPackage: String) {
                super.onUpdate(nowBeingRestored, currentPackage)
            }
        })
    }
}
