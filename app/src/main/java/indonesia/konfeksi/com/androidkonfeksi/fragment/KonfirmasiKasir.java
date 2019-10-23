package indonesia.konfeksi.com.androidkonfeksi.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import indonesia.konfeksi.com.androidkonfeksi.R;
import indonesia.konfeksi.com.androidkonfeksi.activity.Produk;
import indonesia.konfeksi.com.androidkonfeksi.adapter.KonfirmasiKasirAdapter;
import indonesia.konfeksi.com.androidkonfeksi.adapter.ProductsAdapter;
import indonesia.konfeksi.com.androidkonfeksi.json.Product;
import indonesia.konfeksi.com.androidkonfeksi.json.ProductKonfirmasiKasir;
import indonesia.konfeksi.com.androidkonfeksi.konfigurasi.konfigurasi;

public class KonfirmasiKasir extends Fragment {
    private static final String TAG = "KonfirmasiKasir";
    private RecyclerView recyclerView;
    List<ProductKonfirmasiKasir> productList;
    private String JSON_STRING, kode_barang, kode_barcode, nama_barang, harga_barang;
    private ProgressBar progressBar;
    private TextView txErr;
    private Button cobaLagi;
    private View view;


    public KonfirmasiKasir() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_konfirmasi_kasir, container, false);

        progressBar = view.findViewById(R.id.progressbar);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        productList = new ArrayList<>();
        loadProducts();
        return view;
    }

    private void loadProducts() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, konfigurasi.url_kasir_list_pos,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            //Log.d(TAG, "onResponse: " + array.toString(4));
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject data = array.getJSONObject(i);
                                //int harga = Integer.parseInt(product.getString("harga_barang"));
                                //if(harga > 10000)
                                productList.add(new ProductKonfirmasiKasir(
                                        data.getString("id_penjualan"),
                                        data.getString("id_karyawan"),
                                        data.getString("id_pelanggan"),
                                        data.getString("date"),
                                        data.getString("time"),
                                        data.getString("no_nota"),
                                        data.getString("total_harga"),
                                        data.getString("biaya"),
                                        data.getString("biaya_debit"),
                                        data.getString("selisih"),
                                        data.getString("id_karyawan_pengambil"),
                                        data.getString("status"),
                                        data.getString("tgl_jatuh_tempo")
                                ));
                            }

                            KonfirmasiKasirAdapter adapter = new KonfirmasiKasirAdapter(getActivity(), productList);
                            recyclerView.setAdapter(adapter);

                            recyclerView.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);

                        } catch (JSONException e) {
                            Log.d(TAG, "onResponse: " + e);
                            e.printStackTrace();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onErrorResponse: " + error);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }

}
