package com.yemreak.noluyooyle

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log

class MyDatabase {

    companion object {

        private const val NAME_DATABASE = "Datas"

        private const val NAME_TABLE_SOUNDS = "Sounds"
        private const val NAME_TABLE_SOUNDS_SUM = "summary"
        private const val NAME_TABLE_SOUNDS_ID = "id"
        private const val NAME_TABLE_SOUNDS_NAME = "name"

        private const val NAME_TABLE_FAV = "Favorite"
        private const val NAME_TABLE_FAV_POSITION = "position"

        private var database: SQLiteDatabase? = null

        // Ses oynatıcısı
        var mediaPlayer: MediaPlayer? = null

        // Seslerin özet isimleri +
        val names = arrayListOf(
                "Aç Camı",
                "Adamısınız siz hayır değiliz abi",
                "Ulan gerizekalı adam yaptığına bak lan",
                "Ben bela faruk",
                "Ne oluyo öylehh",
                "Yamuk insanlara tahammül edemiyorum",
                "Ölmekden kurtuldu azıcık daha yaşayacaksın şerefsiz",
                "Azrailden hızlı mısınız lan",
                "oooo araba",
                "Baştan ben size dedim",
                "ben beklemem lan ben motorcuyum",
                "ben bela faruk",
                "bende böyle bir delikanlıyım yanihh",
                "gerekirse ileriden döneriz aslanım",
                "bugün yine karayollarının düzenini ve yürürlülüğünü sağlıyorum",
                "cıkmazsak ne olucak lan",
                "trafikte sonunuzu getirebilir",
                "onun ki bunun ki dalaksız ciğersiz",
                "delikanlıysa cekilmez tabi",
                "sola sinyal verip sola döndü arkadaşlar",
                "eğğ özel güclerimi kulanacam",
                "kumarhanesini havaya uçurdum",
                "ben sansam sağa dönecek sansam",
                "abi boşver ya",
                "ben silindir kapağı faruk",
                "herşeyi bilmene gerek yok hadini bil yeter",
                "abi dilin değişti ya",
                "çıkmazsak ne olacak hani",
                "mardinliye dissmi atınız",
                "mevlanamıyım aslanım ben ha",
                "motora mp1 çalar taktırdım",
                "motorcu önceliği var cünkü",
                "motorcuyum ben ya",
                "ne dayısı ayol haha",
                "ne o öyleh",
                "noluyo öyle",
                "o sürücü kadın mı lan",
                "ben senin için yaşamayı göze almışım",
                "ötürde dövem şunları ötür",
                "salak salak konuşma aslanım",
                "motorumun yarısını ele geçirdi ya",
                "bekle geliyorum selanı okuyacam bekle",
                "kim becerirse becersin",
                "gel lan teke tek çıkışalım",
                "taktiği güzel ama geliştirmesi lazım",
                "tam bir dayak arabası",
                "tövbeler tövbesi diablo",
                "ulan aşufte karı",
                "sosyete çocukları",
                "yakıt pompası faruk",
                "acilen cezalandırılması gerek",
                "bunun anlamı yaşamaktan bıktım",
                "yaşamaktan sıkılan var mı"
        )

        // Seslerin id'leri +
        val soundIds = arrayListOf(
                R.raw.ses1,
                R.raw.ses2,
                R.raw.ses3,
                R.raw.ses4,
                R.raw.ses5,
                R.raw.ses6,
                R.raw.ses7,
                R.raw.ses8,
                R.raw.ses9,
                R.raw.ses10,
                R.raw.ses11,
                R.raw.ses12,
                R.raw.ses13,
                R.raw.ses14,
                R.raw.ses15,
                R.raw.ses16,
                R.raw.ses17,
                R.raw.ses18,
                R.raw.ses19,
                R.raw.ses20,
                R.raw.ses21,
                R.raw.ses22,
                R.raw.ses23,
                R.raw.ses24,
                R.raw.ses25,
                R.raw.ses26,
                R.raw.ses27,
                R.raw.ses28,
                R.raw.ses29,
                R.raw.ses30,
                R.raw.ses31,
                R.raw.ses32,
                R.raw.ses33,
                R.raw.ses34,
                R.raw.ses35,
                R.raw.ses36,
                R.raw.ses37,
                R.raw.ses38,
                R.raw.ses39,
                R.raw.ses40,
                R.raw.ses41,
                R.raw.ses42,
                R.raw.ses43,
                R.raw.ses44,
                R.raw.ses45,
                R.raw.ses46,
                R.raw.ses47,
                R.raw.ses48,
                R.raw.ses49,
                R.raw.ses50,
                R.raw.ses51,
                R.raw.ses52,
                R.raw.ses53
        )

        // Seslerin dosya isimleri
        val soundsName = arrayListOf(
                "ses1.wav",
                "ses2.wav",
                "ses3.wav",
                "ses4.wav",
                "ses5.wav",
                "ses6.wav",
                "ses7.wav",
                "ses8.wav",
                "ses9.wav",
                "ses10.wav",
                "ses11.wav",
                "ses12.wav",
                "ses13.wav",
                "ses14.wav",
                "ses15.wav",
                "ses16.wav",
                "ses17.wav",
                "ses18.wav",
                "ses19.wav",
                "ses20.wav",
                "ses21.wav",
                "ses22.wav",
                "ses23.wav",
                "ses24.wav",
                "ses25.wav",
                "ses26.wav",
                "ses27.wav",
                "ses28.wav",
                "ses29.wav",
                "ses30.wav",
                "ses31.wav",
                "ses32.wav",
                "ses33.wav",
                "ses34.wav",
                "ses35.wav",
                "ses36.wav",
                "ses37.wav",
                "ses38.wav",
                "ses39.wav",
                "ses40.wav",
                "ses41.wav",
                "ses42.wav",
                "ses43.wav",
                "ses44.wav",
                "ses45.wav",
                "ses46.wav",
                "ses47.wav",
                "ses48.wav",
                "ses49.wav",
                "ses50.wav",
                "ses51.wav",
                "ses52.wav",
                "ses53.wav"
        )

        // Favori seslerin no'su
        val favSoundsPosition = arrayListOf<Int>()


        /**
         * MediaPlayer'i ayarlama
         */
        fun initMediaPlayer(context: Context, position: Int) {
            if (mediaPlayer != null) {
                mediaPlayer!!.reset()
                mediaPlayer!!.setDataSource(context, getSoundUri(context, position))
                mediaPlayer!!.prepare()
            } else {
                mediaPlayer = MediaPlayer.create(context, soundIds[position])
            }
        }

        fun initDatabase(context: Context) {
            createDataBase(context)
            createTable()
        }

        private fun createDataBase(context: Context) {
            // Database yoksa oluşturma
            database = context.openOrCreateDatabase(NAME_DATABASE, Context.MODE_PRIVATE, null)
        }

        private fun createTable() {
            if (database != null) {
                /*
                database!!.execSQL(
                        "CREATE TABLE IF NOT EXISTS $NAME_TABLE_SOUNDS (" +
                                "$NAME_TABLE_SOUNDS_SUM VARCHAR," +
                                "$NAME_TABLE_SOUNDS_ID INT(10)," +
                                "$NAME_TABLE_SOUNDS_NAME VARCHAR," +
                                ")"
                )
                */
                database!!.execSQL(
                        "CREATE TABLE IF NOT EXISTS $NAME_TABLE_FAV (" +
                                "$NAME_TABLE_FAV_POSITION VARCHAR" +
                                ")"
                )
            } else {
                Log.e("createTable", "Database oluşturulmamış :(")
            }
        }

        fun saveTheFavs(context: Context) {
            createDataBase(context)
            createTable()

            // Tabloyu silme
            delTheFavs()

            // Tabloyu yeniden oluşturma
            for (i in favSoundsPosition) {
                insertIntoFav(i)
            }
        }

        private fun delTheFavs() {
            if (database != null) {
                database!!.execSQL("DELETE FROM $NAME_TABLE_FAV")
            } else {
                Log.e("insertIntoFav", "Database oluşturulmamış :(")
            }
        }

        fun getTheFavs() {
            if (database != null) {
                // İmleç tanımlama
                val cursor = database!!.rawQuery("SELECT * FROM $NAME_TABLE_FAV", null)

                val positionIndex = cursor.getColumnIndex(NAME_TABLE_FAV_POSITION)

                // Verileri array'e ekliyoruz
                while (cursor.moveToNext()) {
                    favSoundsPosition.add(cursor.getInt(positionIndex))
                }

                cursor?.close()

            } else {
                Log.e("insertIntoFav", "Database oluşturulmamış :(")
            }
        }

        private fun insertIntoSounds(sum: String, resId: Int, name: String) {
            if (database != null) {
                database!!.execSQL(
                        "INSERT INTO $NAME_TABLE_SOUNDS (" +
                                "$NAME_TABLE_SOUNDS_SUM, " +
                                "$NAME_TABLE_SOUNDS_ID, " +
                                NAME_TABLE_SOUNDS_NAME +
                                ") VALUES (" +
                                "'$sum', $resId, '$name'" +
                                ") "
                )
            } else {
                Log.e("insertIntoSounds", "Database oluşturulmamış :(")
            }
        }

        private fun insertIntoFav(position: Int) {
            if (database != null) {
                database!!.execSQL(
                        "INSERT INTO $NAME_TABLE_FAV (" +
                                NAME_TABLE_FAV_POSITION +
                                ") VALUES (" +
                                position +
                                ") "
                )
            } else {
                Log.e("insertIntoFav", "Database oluşturulmamış :(")
            }
        }

        /**
         * Raw klasöründeki seslerin uri olarak alma
         * @param context Aktivite kaynağı
         * @param position SesID indexi
         */
        fun getSoundUri(context: Context, position: Int): Uri {
            return Uri.parse("android.resource://${context.packageName}/${soundIds[position]}")
        }
    }
}