package indonesia.konfeksi.com.androidkonfeksi.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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

import indonesia.konfeksi.com.androidkonfeksi.json.Product;
import indonesia.konfeksi.com.androidkonfeksi.adapter.ProductsAdapter;
import indonesia.konfeksi.com.androidkonfeksi.R;
import indonesia.konfeksi.com.androidkonfeksi.konfigurasi.konfigurasi;

public class Produk extends AppCompatActivity {
    private RecyclerView recyclerView;
    List<Product> productList;
    private String JSON_STRING, kode_barang, kode_barcode, nama_barang, harga_barang;
    private ProgressBar progressBar;
    private TextView txErr;
    private Button cobaLagi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk);

        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        txErr = findViewById(R.id.err_ap);
        cobaLagi = findViewById(R.id.reload_ap);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressBar.setVisibility(View.VISIBLE);
        txErr.setVisibility(View.INVISIBLE);
        cobaLagi.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);

        productList = new ArrayList<>();
        loadProducts();
    }

    public void reload(View v){
        progressBar.setVisibility(View.VISIBLE);
        txErr.setVisibility(View.INVISIBLE);
        cobaLagi.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);

        loadProducts();
    }
    private void loadProducts() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, konfigurasi.URL_GET_BARANG,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject product = array.getJSONObject(i);
                                //adding the product to product list
                                int harga = Integer.parseInt(product.getString("harga_barang"));
                                if(harga > 10000) {
                                    productList.add(new Product(
                                            product.getString("id_barang"),
                                            product.getString("kode_barang"),
                                            product.getString("kode_barcode"),
                                            product.getString("nama_barang"),
                                            product.getString("diskon_persen"),
                                            product.getString("diskon_rupiah"),
                                            product.getString("ukuran"),
                                            product.getString("meter"),
                                            product.getString("warna"),
                                            product.getString("stok_jual"),
                                            product.getString("date_input"),
                                            product.getString("date_edit"),
                                            product.getString("harga_barang")
                                    ));
                                }
                            }

                            //creating adapter object and Xsetting it to recyclerview
                            ProductsAdapter adapter = new ProductsAdapter(Produk.this, productList);
                            recyclerView.setAdapter(adapter);

                            progressBar.setVisibility(View.INVISIBLE);
                            recyclerView.setVisibility(View.VISIBLE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressBar.setVisibility(View.INVISIBLE);
                            txErr.setVisibility(View.VISIBLE);
                            cobaLagi.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.INVISIBLE);
                        txErr.setVisibility(View.VISIBLE);
                        cobaLagi.setVisibility(View.VISIBLE);
                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }


}