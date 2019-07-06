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
import indonesia.konfeksi.com.androidkonfeksi.adapter.HistoryPembelianAdapter;
import indonesia.konfeksi.com.androidkonfeksi.adapter.HistoryPenjualanAdapter;
import indonesia.konfeksi.com.androidkonfeksi.json.ProductHistoryPembelian;
import indonesia.konfeksi.com.androidkonfeksi.json.ProductHistoryPenjualan;
import indonesia.konfeksi.com.androidkonfeksi.konfigurasi.konfigurasi;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryPembelian extends Fragment {
    private RecyclerView recyclerView;
    List<ProductHistoryPembelian> productList;
    private String JSON_STRING, kode_barang, kode_barcode, nama_barang, harga_barang;
    private ProgressBar progressBar;
    private TextView txErr;
    private Button cobaLagi;
    private View view;


    public HistoryPembelian() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_history_pembelian, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        productList = new ArrayList<>();
        loadProducts();

        return view;
    }

    private void loadProducts() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, konfigurasi.URL_GET_HISTORYPEMBELIAN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject product = array.getJSONObject(i);
                                productList.add(new ProductHistoryPembelian(
                                        product.getString("id_pembelian"),
                                        product.getString("date"),
                                        product.getString("time"),
                                        product.getString("total_harga"),
                                        product.getString("no_faktur"),
                                        product.getString("no_nota"),
                                        product.getString("biaya"),
                                        product.getString("id_karyawan"),
                                        product.getString("kode_karyawan"),
                                        product.getString("nama_karyawan"),
                                        product.getString("email_karyawan"),
                                        product.getString("id_supplier"),
                                        product.getString("nama_supplier"),
                                        product.getString("kode_supplier")
                                ));
                            }

                            HistoryPembelianAdapter adapter = new HistoryPembelianAdapter(getActivity(), productList);
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
