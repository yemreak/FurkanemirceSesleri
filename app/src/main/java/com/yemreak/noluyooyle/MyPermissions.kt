package com.yemreak.noluyooyle

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity


class MyPermissions {

    enum class PermissionFor {
        SOUNDS,
        RINGTONE
    }

    companion object {

        const val REQUEST_EXTERNAL_STORAGE = 0
        const val REQUEST_MANAGE_WRITE_SETTINGS = 1

        /**
         * İzin kontrolü
         * @param context İznin isteneceği Activity context'i
         * @return İzin verildiyse true
         */
        fun checkPermission(context: Context, permissionFor: PermissionFor): Boolean {
            return when (permissionFor) {
                PermissionFor.SOUNDS -> {
                    ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_GRANTED
                }

                PermissionFor.RINGTONE -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                        Settings.System.canWrite(context)
                    else
                        false
                }
            }
        }

        /**
         * İzin isteme
         * @param context İznin isteneceği Actvity context'i
         * @param permissionFor İznin ne için isteneceği
         */
        fun requestPermission(context: Context, permissionFor: PermissionFor) {
            if (!checkPermission(context, permissionFor)) {
                when (permissionFor) {
                    PermissionFor.SOUNDS -> {
                        ActivityCompat.requestPermissions(
                                context as Activity,
                                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                                REQUEST_EXTERNAL_STORAGE
                        )
                    }
                    PermissionFor.RINGTONE -> {
                        // "Sistem ayarlarını değiştirme" izni içeren Aktivite başlatma
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS, Uri.parse("package:" + context.packageName))
                            (context as AppCompatActivity).startActivityForResult(intent, REQUEST_MANAGE_WRITE_SETTINGS)
                        }
                    }
                }
            }
        }

    }

}
