package indonesia.konfeksi.com.androidkonfeksi.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.util.ArrayList;
import java.util.List;

import indonesia.konfeksi.com.androidkonfeksi.R;
import indonesia.konfeksi.com.androidkonfeksi.adapter.HistoryPenjualanAdapter;
import indonesia.konfeksi.com.androidkonfeksi.json.ProductHistoryPenjualan;
import indonesia.konfeksi.com.androidkonfeksi.konfigurasi.konfigurasi;

public class HistoryPenjualan extends Fragment {
    private RecyclerView recyclerView;
    List<ProductHistoryPenjualan> productList;
    private String JSON_STRING, kode_barang, kode_barcode, nama_barang, harga_barang;
    private ProgressBar progressBar;
    private TextView txErr;
    private Button cobaLagi;
    private View view;


    public HistoryPenjualan() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_history_penjualan, container, false);

        progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        productList = new ArrayList<>();
        loadProducts();
        return view;
    }

    private void loadProducts() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, konfigurasi.URL_GET_HISTORYPENJUALAN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject product = array.getJSONObject(i);
                                    productList.add(new ProductHistoryPenjualan(
                                            product.getString("id_penjualan"),
                                            product.getString("id_karyawan"),
                                            product.getString("id_pelanggan"),
                                            product.getString("date"),
                                            product.getString("time"),
                                            product.getString("no_faktur"),
                                            product.getString("no_nota"),
                                            product.getString("metode_pembayaran"),
                                            product.getString("diskon_persen"),
                                            product.getString("diskon_rupiah"),
                                            product.getString("total_harga"),
                                            product.getString("biaya"),
                                            product.getString("selisih"),
                                            product.getString("status"),
                                            product.getString("kode_karyawan"),
                                            product.getString("username"),
                                            product.getString("password"),
                                            product.getString("nama"),
                                            product.getString("alamat"),
                                            product.getString("kota"),
                                            product.getString("negara"),
                                            product.getString("kode_pos"),
                                            product.getString("no_telp"),
                                            product.getString("email"),
                                            product.getString("date_input"),
                                            product.getString("date_edit"),
                                            product.getString("token"),
                                            product.getString("id_posisi"),
                                            product.getString("kode_pelanggan"),
                                            product.getString("nama_toko"),
                                            product.getString("no_telp2"),
                                            product.getString("no_telp3"),
                                            product.getString("catatan"),
                                            product.getString("nama_karyawan")
                                    ));
                            }

                            HistoryPenjualanAdapter adapter = new HistoryPenjualanAdapter(getActivity(), productList);
                            recyclerView.setAdapter(adapter);

                            progressBar.setVisibility(View.INVISIBLE);
                            recyclerView.setVisibility(View.VISIBLE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }

}
