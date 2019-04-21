package com.yemreak.noluyooyle

import android.content.Context
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileOutputStream

class MyFile {

    companion object {

        var folderPath: String? = null
            private set

        var filePath: String? = null
            private set

        /**
         * Belleğe ses dosyası yazma
         */
        fun writeEmulatorStorage(context: Context, position: Int) {
            // İçerideki dosyayı geçici olarak kayıt etmek için input stream oluşturuyoruz.
            val inputStream = context.resources.openRawResource(MyDatabase.soundIds[position])

            try {
                // Ses'in konulacağı yer için gerekli dosyaları oluşturuyoruz
                if (createSoundFolders()) {
                    // Çıktıyı oluşturmak için output stream tanımlıyoruz
                    val outputStream = FileOutputStream(File(
                            folderPath,
                            MyDatabase.soundsName[position]
                    ))

                    // İçerdeki dosyası 1Kb'sini dışarıdaki dosyaya yazıyoruz (Bitene kadar)
                    val buf = ByteArray(1024)
                    var len = inputStream.read(buf, 0, buf.size)
                    while (len != -1) {
                        outputStream.write(buf, 0, len)
                        len = inputStream.read(buf, 0, buf.size)
                    }

                    inputStream.close()
                    outputStream.close()

                    // Dosya konumunu güncelliyoruz
                    filePath = folderPath + "/${MyDatabase.soundsName[position]}"
                }

            } catch (e: Exception) {
                Log.e("writeEmulatorStorage", "$e")
            }
        }

        private fun createSoundFolders(): Boolean {
            try {
                val folderMain = "media"

                var f = File(Environment.getExternalStorageDirectory(), folderMain)
                if (!f.exists()) {
                    f.mkdirs()
                }

                val folderParent = "audio"

                f = File(Environment.getExternalStorageDirectory().toString() + "/" + folderMain, folderParent)
                if (!f.exists()) {
                    f.mkdirs()
                }

                val folderChild = "furkanemirce"
                f = File(Environment.getExternalStorageDirectory().toString() + "/" + folderMain + "/" + folderParent, folderChild)
                if (!f.exists()) {
                    f.mkdirs()
                }

                folderPath = "${Environment.getExternalStorageDirectory()}/$folderMain/$folderParent/$folderChild"

                return true

            } catch (e: Exception) {
                Log.e("createSoundFolders", "$e")
                return false
            }
        }

    }

}