package indonesia.konfeksi.com.androidkonfeksi.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import indonesia.konfeksi.com.androidkonfeksi.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryPembelian extends Fragment {
    private View view;


    public HistoryPembelian() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_history_pembelian, container, false);

        return view;
    }

}
