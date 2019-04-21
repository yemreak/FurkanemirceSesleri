package com.yemreak.noluyooyle

import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*

class MainActivity : AppCompatActivity() {

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Eğer ilk kez oluşturuluyorsa
        if (savedInstanceState == null) {
            // Database'yi oluşturma
            MyDatabase.initDatabase(this)
            // Favorileri alıyoruz
            MyDatabase.getTheFavs()
        }


        setSupportActionBar(toolbar)
        // supportActionBar?.setDisplayHomeAsUpEnabled(true) // Sol üst geri veya home butonunu açar

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter
        // 1 adım sağı ve solundaki tabları korur, onları yeniden işlemez
        container.offscreenPageLimit = 1

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))

        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Geliştiriciye mesaj gönderme işlemi yakında eklenecektir", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()

        // MediaPlayer'i kapatıyoruz.
        MyDatabase.mediaPlayer?.release()

        // Favorileri kaydediyoruz
        MyDatabase.saveTheFavs(this)
    }


    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1)
        }

        override fun getCount(): Int {
            // Her bölge 10 tane ses gösterdiği için, toplam ses sayının 10'da birinin 2 (1 tane de favoriler) fazlası kadar tab olacak
            return MyDatabase.names.size / 10 + 2
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    class PlaceholderFragment : Fragment() {

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val rootView = inflater.inflate(R.layout.fragment_main, container, false)

            // Her bir fragment içindeki recylerview için adapter oluşturuyoruz (Fragment içinde de verileri değiştirmemiz lazım)
            // CustomAdapter 0 tabanlı çalışır, bu yüzden 1. sayfa verisi 0 olmalı. 1 çıkarıyoruz
            rootView.rv_main.adapter = CustomAdapter(context, arguments!!.getInt(ARG_SECTION_NUMBER) - 1)

            // Verilerin dikey pozisyonda sıralanmasını sağlıyoruz
            val layoutManager = LinearLayoutManager(activity)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            rootView.rv_main.layoutManager = layoutManager

            return rootView
        }

        override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
            when (requestCode) {
                MyPermissions.REQUEST_EXTERNAL_STORAGE -> {
                    // İzin verildi mi kontrolü
                    if (grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                            grantResults[1] == PackageManager.PERMISSION_GRANTED
                    ) {
                        // İzin verildikten sonra yapılacak işlemler
                    } else {
                        Toast.makeText(context, "İzne ihtiyacım var :(", Toast.LENGTH_SHORT).show()
                    }
                }
                MyPermissions.REQUEST_MANAGE_WRITE_SETTINGS ->  {
                    // İzin verildi mi kontrolü
                    if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        // İzin verildikten sonra yapılacak işlemler
                    } else {
                        Toast.makeText(context, "İzne ihtiyacım var :(", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        companion object {
            /**
             * The fragment argument representing the section number for this
             * fragment.
             */
            private const val ARG_SECTION_NUMBER = "section_number"

            /**
             * Returns a new instance of this fragment for the given section
             * number.
             */
            fun newInstance(sectionNumber: Int): PlaceholderFragment {
                val fragment = PlaceholderFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                fragment.arguments = args

                return fragment
            }
        }
    }
}
