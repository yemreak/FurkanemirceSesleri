package com.yemreak.noluyooyle

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView


/**
 * RecyclerView içindeki verileri ayarlar
 *
 * @param context Aktiviye kaynağı
 * @param tabIndex Sayfanın index numarası (sırası) (ilk sayfa 0'dır = Favoriler)
 */
class CustomAdapter(private val context: Context?, private val tabIndex: Int) : RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {

    /**
     * Veriler sıfırdan oluşturulduğunda çalışır.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        /**
         * Layout dosyasını xml'den javaya çevir. View içine at
         * Bu layout'u parent içine ekleme, main activity'deki parent'i (constrait layout) kullan diyoruz.
         */
        val view = LayoutInflater.from(context).inflate(R.layout.layout_custom_lv_item, parent, false)
        // Inflate edilen view'i MyViewHolder'da işleyip, geri döndüreceğiz
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return when {
            tabIndex == 0 -> MyDatabase.favSoundsPosition.size
            MyDatabase.names.size > (tabIndex) * 10 -> 10
            else -> MyDatabase.names.size % 10
        }
    }

    /**
     * Veriler oluşturulduktan sonra, gösterim haline olduğunda çalışır.
     */
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       // Tıklanan verideki bilgileri yenileme
        holder.refreshData(position)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        // Text Views
        private val tvSoundName: TextView = itemView.findViewById(R.id.tv_soundName)

        // Image Buttons
        private val ibAdd: ImageButton = itemView.findViewById(R.id.ib_add)
        private val ibStar: ImageButton = itemView.findViewById(R.id.ib_star)
        private val ibRingtone: ImageButton = itemView.findViewById(R.id.ib_ringtone)
        private val ibShare: ImageButton = itemView.findViewById(R.id.ib_share)
        private val ibPlay: ImageButton = itemView.findViewById(R.id.ib_play)


        /**
         * View holder verilerini yeniler
         * @param position View'in bulunduğu sıra
         */
        fun refreshData(position: Int) {
            // Pozisyon verisini güncelleme (O. tab favoriler tab'ıdır.)
            val realPosition = when (tabIndex) {
                0 -> MyDatabase.favSoundsPosition[position]
                else -> position + (tabIndex - 1) * 10
            }

            if (realPosition < MyDatabase.names.size) {
                tvSoundName.text = MyDatabase.names[realPosition]

                ibPlay.setOnClickListener {
                    if (context != null)
                        MyEvents.playSound(context, realPosition)
                }

                ibShare.setOnClickListener {
                    if (context != null)
                        MyEvents.shareSoundEvent(context, realPosition)
                }

                ibRingtone.setOnClickListener {
                    if (context != null)
                        MyEvents.setRingToneEvent(context, realPosition)
                }

                when (tabIndex) {
                    0 -> {
                        MyEvents.setIBRed(ibStar)
                        ibStar.setOnClickListener {
                            if (context != null) {
                                MyEvents.removeFromFavEvent(context, realPosition)

                                // Verileri güncelleme
                                notifyDataSetChanged()
                            }

                        }
                    }
                    else -> {
                        ibStar.setOnClickListener {
                            if (context != null) {
                                MyEvents.addToFavEvent(context, realPosition)
                            }
                        }
                    }
                }

                with(MyEvents) {
                    setIBDisabled(ibAdd)
                }

            }
        }
    }
}