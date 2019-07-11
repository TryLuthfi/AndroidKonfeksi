package indonesia.konfeksi.com.androidkonfeksi.activity;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import indonesia.konfeksi.com.androidkonfeksi.R;
import indonesia.konfeksi.com.androidkonfeksi.fragment.HistoryPembelian;
import indonesia.konfeksi.com.androidkonfeksi.fragment.HistoryPenjualan;
import indonesia.konfeksi.com.androidkonfeksi.fragment.KonfirmasiKasir;

public class Kasir extends AppCompatActivity {

    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.tab)
    TabLayout tab;
//    private int[] tabIcons = {
//            R.drawable.home,
//            R.drawable.search,
//            R.drawable.plus,
//            R.drawable.profil
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kasir);
        ButterKnife.bind (this);

        pager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(pager);

        tab = (TabLayout) findViewById(R.id.tab);
        tab.setupWithViewPager(pager);

    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new KonfirmasiKasir(), "Konfirmasi Kasir");//isitulisan jika ingin
        adapter.addFrag(new HistoryPembelian(), "History Pembelian");
        adapter.addFrag(new HistoryPenjualan(), "History Penjualan");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
