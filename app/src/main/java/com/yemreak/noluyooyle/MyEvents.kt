package com.yemreak.noluyooyle

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast



class MyEvents {

    companion object {

        /**
         * Ses paylaşma olayını başlatır
         * @param context İznin isteneceği Activity context'i
         * @param position Sesin index numarası
         */
        fun shareSoundEvent(context: Context, position: Int) {
            MyPermissions.requestPermission(context, MyPermissions.PermissionFor.SOUNDS)

            if (MyPermissions.checkPermission(context, MyPermissions.PermissionFor.SOUNDS)) {
                // Belleğe yazmadan paylaşamayız
                MyFile.writeEmulatorStorage(context, position)
                shareSound(context)
            }
        }

        /**
         * Bildirim sesi yapma olayını başlatır
         * @param context İznin isteneceği Activity context'i
         * @param position Sesin index numarası
         */
        fun setRingToneEvent(context: Context, position: Int) {
            MyPermissions.requestPermission(context, MyPermissions.PermissionFor.RINGTONE)

            if (MyPermissions.checkPermission(context, MyPermissions.PermissionFor.RINGTONE)) {
                showAlert(
                        context,
                        "Bildirim sesi ayarlama",
                        "Bildirim sesinizi değiştirmek istediğinize emin misiniz?",
                        { setRingTone(context, position) },
                        { dontSetRingTone(context) }
                )
            }
        }

        fun playSound(context: Context, position: Int) {
            Log.e("Media Positon", "$position")
            // MediaPLayer'i oluşturma
            MyDatabase.initMediaPlayer(context, position)
            // MediaPlayer'i başlatma
            MyDatabase.mediaPlayer!!.start()

            // MyDatabase.soundPool.play(MyDatabase.sounds[position], 1f, 1f, 0, 0, 1f)
        }

        /**
         * Image Button'ları basılmaz ve soluk hale getirir
         * @param imageButton İşlemin uygulanacağı image button
         */
        fun setIBDisabled(imageButton: ImageButton) {
            imageButton.apply {
                alpha = .3f
                isClickable = false
                isEnabled = false
            }
        }

        fun setIBRed(imageButton: ImageButton) {
            imageButton.apply {
                setColorFilter(Color.RED)
            }
        }

        private fun shareSound(context: Context) {
            val sharedIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_STREAM, Uri.parse(MyFile.filePath))
                type = "audio/wav"
            }

            context.startActivity(Intent.createChooser(sharedIntent, "Ses paylaşımı :)"))
        }

        private fun setRingTone(context: Context, position: Int) {
            RingtoneManager.setActualDefaultRingtoneUri(
                    context,
                    RingtoneManager.TYPE_NOTIFICATION,
                    MyDatabase.getSoundUri(context, position)
            )
            showToast(context, "Başarılı :)")
            playNotificationSound(context)
        }

        private fun dontSetRingTone(context: Context) {
            showToast(context, "İsteğiniz üzerine iptal edildi")
        }

        fun addToFavEvent(context: Context, position: Int) {
            with(MyDatabase.favSoundsPosition) {
                if (!contains(position)) {
                    add(position)

                    showToast(context, "Favorilere eklendi :)")
                    // playNotificationSound(context) Çok gürültülü
                } else {
                    showToast(context, "Zaten favorilerine eklemişsin")
                }
            }
        }

        fun removeFromFavEvent(context: Context, position: Int) {
            with(MyDatabase.favSoundsPosition) {
                if (contains(position)) {
                    remove(position)

                    showToast(context, "Favorilerden silindi :)")
                    // playNotificationSound(context) Çok gürültülü
                } else {
                    showToast(context, "Zaten favorilerden silindi, ")
                }
            }
        }

        private fun showToast(context: Context, text: String){
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        }

        private fun showAlert(context: Context, title: String, message: String, funcPositive: () -> Unit, funcNegative: () -> Unit) {
            val builder = AlertDialog.Builder(context, R.style.LightAlertDialog)

            builder.apply {
                setTitle(title)
                setMessage(message)
                setPositiveButton("Evet") { _, _ ->
                    funcPositive()
                }
                setNegativeButton("Hayır") { _, _ ->
                    funcNegative()
                }
            }

            val dialog = builder.create()
            dialog.show()
        }

        private fun playNotificationSound(context: Context) {
            try {
                val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                val r = RingtoneManager.getRingtone(context, notification)
                r.play()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

    }

}