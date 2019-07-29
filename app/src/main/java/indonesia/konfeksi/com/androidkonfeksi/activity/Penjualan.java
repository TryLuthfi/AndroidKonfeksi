package indonesia.konfeksi.com.androidkonfeksi.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import indonesia.konfeksi.com.androidkonfeksi.Interface.RecyclerViewClickListener;
import indonesia.konfeksi.com.androidkonfeksi.R;
import indonesia.konfeksi.com.androidkonfeksi.adapter.DialogRecyclerAdapter;
import indonesia.konfeksi.com.androidkonfeksi.adapter.PenjualanAdapter;
import indonesia.konfeksi.com.androidkonfeksi.adapter.PenjualanTambahAdapter;
import indonesia.konfeksi.com.androidkonfeksi.json.ProductPenjualanBarang;
import indonesia.konfeksi.com.androidkonfeksi.konfigurasi.konfigurasi;

public class Penjualan extends AppCompatActivity implements RecyclerViewClickListener {
    private static final String TAG = "Penjualan";
    private String date;
    private String time;
    private String idPelanggan;
    private String TelpPelanggan;
    private String AlamatPelanggan;
    private String CatatanPelanggan;
    private String mPostKeyNama = null;
    private Spinner input_nama_pelanggan;
    private TextView input_tgl_nota;
    private TextView input_waktu;
    private TextView input_no_nota;
    private TextView input_kasir;
    private TextView input_no_hp;
    private TextView input_alamat;
    private TextView input_info_lain;
    private TextView namaBarangDialog;
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewDialog;
    private Button tambah_pembelian;
    private Button final_tambah_pembelian;
    private AlertDialog dialog;
    private LayoutInflater inflater;
    private View dialogView;
    private AlertDialog dialog2;
    private LayoutInflater inflater2;
    private View dialogView2;
    private PenjualanAdapter adapter;
    private String idBarang;
    private EditText kodeBarangDialog;
    private String kode;
    private String nama;
    private String harga;
    private LinearLayout isi;
    private ProgressBar loading;
    private TextView namaaBarang;
    private TextView hargaaBarang;
    private EditText qtyBarang;
    private TextView subTootal;
    private TextView txErr;
    private Button cobaLagi;
    private TextView error;
    private String QTYINTs;
    private String hargaINTs;

    private int QTYINT;
    private int hargaINT;
    private int subTotalINT;
    private ProgressDialog loadingg;

    List<ProductPenjualanBarang> productBarang;

    List<ProductPenjualanBarang> barangPilih;
    List<ProductPenjualanBarang> barangPilih3;
    ProductPenjualanBarang barangPilih2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjualan);

        input_tgl_nota = findViewById(R.id.input_tgl_nota);
        input_waktu = findViewById(R.id.input_waktu);
        input_no_nota = findViewById(R.id.input_no_nota);
        input_kasir = findViewById(R.id.input_kasir);
        input_nama_pelanggan = findViewById(R.id.input_nama_pelanngan);
        input_no_hp = findViewById(R.id.input_no_hp);
        input_alamat = findViewById(R.id.input_alamat);
        input_info_lain = findViewById(R.id.input_info_lain);
        tambah_pembelian = findViewById(R.id.tambah_pembelian);
        final_tambah_pembelian = findViewById(R.id.final_tambah_pembelian2);
        txErr = findViewById(R.id.err_ap);
        cobaLagi = findViewById(R.id.reload_ap);
        mPostKeyNama = Objects.requireNonNull(getIntent().getExtras()).getString("NamaKaryawan");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        isi = findViewById(R.id.isi);
        loading = findViewById(R.id.loading);
        error = findViewById(R.id.error);

        isi.setVisibility(View.INVISIBLE);
        txErr.setVisibility(View.INVISIBLE);
        cobaLagi.setVisibility(View.INVISIBLE);
        loading.setVisibility(View.VISIBLE);
        productBarang = new ArrayList<>();

        ambilBarang();

        dialog = new AlertDialog.Builder(Penjualan.this).create();
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.form_penjualan, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);

        kodeBarangDialog = dialogView.findViewById(R.id.kodeBarang);
        namaBarangDialog = dialogView.findViewById(R.id.namaBarang);
        namaaBarang = dialogView.findViewById(R.id.namaBarang);
        hargaaBarang = dialogView.findViewById(R.id.hargaBarang);
        qtyBarang = dialogView.findViewById(R.id.qtyBarang);
        subTootal = dialogView.findViewById(R.id.subTotal);

        barangPilih3 = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(Penjualan.this));

        dialog.setButton(Dialog.BUTTON_POSITIVE,"TAMBAH", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                for(int i = 0; i < productBarang.size(); i++){

                    if(productBarang.get(i).getIdBarang().equalsIgnoreCase(barangPilih.get(0).getIdBarang())
                            && productBarang.get(i).getIdVarianHarga().equalsIgnoreCase(barangPilih.get(0).getIdVarianHarga()))
                    {
                        error.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        barangPilih3.add(barangPilih2);
                        PenjualanTambahAdapter penjualanadapter = new PenjualanTambahAdapter(Penjualan.this, barangPilih3);
                        recyclerView.setAdapter(penjualanadapter);

                    }

                    if(productBarang.get(i).getIdBarang().equalsIgnoreCase(barangPilih2.getIdBarang())
                            && productBarang.get(i).getIdVarianHarga().equalsIgnoreCase(barangPilih2.getIdVarianHarga()))
                    {
                        Log.d(TAG, "recyclerView: "+barangPilih2.getUkuran());
                        error.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        barangPilih3.add(barangPilih2);
                        PenjualanTambahAdapter penjualanadapter = new PenjualanTambahAdapter(Penjualan.this, barangPilih3);
                        recyclerView.setAdapter(penjualanadapter);
                    }

                }
            }
        });

        dialog.setButton(Dialog.BUTTON_NEGATIVE,"CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        final_tambah_pembelian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahBarangPembelian();
            }
        });

        setDate();
        settime();

        input_tgl_nota.setText(date);
        input_waktu.setText(time);

        if (mPostKeyNama != null) {
            input_kasir.setText(mPostKeyNama);
        }else {
            input_kasir.setText("");
        }

        ambilNoNotaPenjualan();

        kode = kodeBarangDialog.getText().toString().trim();

        input_nama_pelanggan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Penjualan.StringWithTag pelanggan = (Penjualan.StringWithTag) parent.getItemAtPosition(position);
                Log.d(TAG, "onItemSelected: " + pelanggan.id + ", " + pelanggan.string + ", " + pelanggan.Telp + ", " + pelanggan.Alaamat + ", " + pelanggan.Caatatan);
                idPelanggan = (String) pelanggan.id;
                TelpPelanggan = (String) pelanggan.Telp;
                AlamatPelanggan = (String) pelanggan.Alaamat;
                CatatanPelanggan = (String) pelanggan.Caatatan;

                input_no_hp.setText(TelpPelanggan);
                input_alamat.setText(AlamatPelanggan);
                input_info_lain.setText(CatatanPelanggan);

                String noHp = input_no_hp.getText().toString().trim();
                String almt = input_alamat.getText().toString().trim();
                String infolain = input_info_lain.getText().toString().trim();

                if(noHp.isEmpty()){
                    input_no_hp.setText("Tidak Ada");
                }
                if(almt.isEmpty()){
                    input_alamat.setText("Tidak Ada");
                }
                if(infolain.isEmpty()){
                    input_info_lain.setText("Tidak Ada");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        tambah_pembelian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        qtyBarang.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (qtyBarang != null){
                    int harga = Integer.valueOf(hargaaBarang.getText().toString());
                    int qty = Integer.valueOf(qtyBarang.getText().toString());
                    subTootal.setText(String.valueOf(harga * qty));
                }
            }
        });

        kodeBarangDialog.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                kode = kodeBarangDialog.getText().toString().trim();
                barangPilih = new ArrayList<>();
                for(int i = 0; i < productBarang.size(); i++){
                    if(productBarang.get(i).getKodeBarang().equalsIgnoreCase(kode)){
                        barangPilih.add(productBarang.get(i));
                    }
                }

                if(barangPilih.size() > 1){
                    dialog2 = new AlertDialog.Builder(Penjualan.this).create();
                    inflater2 = getLayoutInflater();
                    dialogView2 = inflater2.inflate(R.layout.recycler_dialog, null);
                    dialog2.setView(dialogView2);
                    dialog2.setCancelable(true);

                    recyclerViewDialog =  dialogView2.findViewById(R.id.recyclerViewDialog);
                    recyclerViewDialog.setLayoutManager(new LinearLayoutManager(Penjualan.this));

                    dialog2.show();

                    DialogRecyclerAdapter adapter = new DialogRecyclerAdapter(Penjualan.this, barangPilih, Penjualan.this);
                    recyclerViewDialog.setAdapter(adapter);
                }else{
                    if(barangPilih.size() == 1){
                        namaBarangDialog.setText(barangPilih.get(0).getNamaBarang());
                        hargaaBarang.setText(barangPilih.get(0).getHarga());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void reload(View v) {
        loading.setVisibility(View.VISIBLE);
        txErr.setVisibility(View.INVISIBLE);
        cobaLagi.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);

        ambilBarang();
    }


    public void recyclerViewListClicked(View v, int position){
        Log.d(TAG, "recyclerViewListClicked: " + position);
        Log.e(TAG, "recyclerViewListClicked: " + barangPilih.get(position).getNamaBarang());
        namaaBarang.setText(barangPilih.get(position).getUkuran());
        hargaaBarang.setText(barangPilih.get(position).getHarga());
        dialog2.dismiss();
        nama = namaaBarang.getText().toString().trim();
        harga = hargaaBarang.getText().toString().trim();
        barangPilih2 = barangPilih.get(position);
    }




    private void ambilBarang(){
        productBarang.add(new ProductPenjualanBarang(
                "500",
                "BP500HIJAU",
                "B. yml 800y",
                "10",
                "0",
                "600",
                "1lusin",
                "11",
                "10",
                "0",
                "10000",
                0,
                0
        ));
        StringRequest stringRequest = new StringRequest(Request.Method.GET, konfigurasi.URL_GET_AMBIL_BARANG,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);

                            JSONArray arrSupplier = obj.getJSONArray("datanya");


                            for(int i = 0; i < arrSupplier.length(); i++){
                                JSONObject supplierJson = arrSupplier.getJSONObject(i);
                                ProductPenjualanBarang barang = new ProductPenjualanBarang(
                                        supplierJson.getString("id_barang"),
                                        supplierJson.getString("kode_barang"),
                                        supplierJson.getString("nama_barang"),
                                        supplierJson.getString("diskon_persen"),
                                        supplierJson.getString("diskon_rupiah"),
                                        supplierJson.getString("id_varian_harga"),
                                        supplierJson.getString("ukuran"),
                                        supplierJson.getString("meter"),
                                        supplierJson.getString("warna"),
                                        supplierJson.getString("stok_jual"),
                                        supplierJson.getString("harga"), 0, 0
                                );
                                productBarang.add(barang);

                                isi.setVisibility(View.VISIBLE);
                                loading.setVisibility(View.INVISIBLE);

                                Log.d(TAG, "onResponse: " + barang);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            loading.setVisibility(View.INVISIBLE);
                            txErr.setVisibility(View.VISIBLE);
                            cobaLagi.setVisibility(View.VISIBLE);
                            Log.d(TAG, "onResponse: " + e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.setVisibility(View.INVISIBLE);
                        txErr.setVisibility(View.VISIBLE);
                        cobaLagi.setVisibility(View.VISIBLE);
                        Log.d(TAG, "onErrorResponse: " + error);
                    }
                });

        Volley.newRequestQueue(this).add(stringRequest);

    }

    public void tambahBarangPembelian(){
        loadingg = ProgressDialog.show(Penjualan.this, "Updating...", "Mohon Tunggu...", false, false);

//

        if(getId_Karyawan().equals("null")){
            Log.d(TAG, "tambahBarangPembelian: " + getId_Karyawan());
            Toast.makeText(Penjualan.this, "Id Karyawan tidak tersedia", Toast.LENGTH_LONG).show();
            return;
        }


        StringRequest stringRequest = new StringRequest(Request.Method.POST, konfigurasi.URL_TAMBAH_PENJUALAN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject resObj = new JSONObject(response);
                            if(resObj.getString("success").equals("true")){
                                loadingg.dismiss();
                                Log.d(TAG, "onResponse123: " + resObj.toString());
                                Toast.makeText(Penjualan.this, "Berhasil Menambah Data", Toast.LENGTH_SHORT).show();
                            }else{
                                loadingg.dismiss();
                                Log.d(TAG, "onResponse false: " + resObj.toString());
                                Toast.makeText(Penjualan.this, resObj.getString("message"), Toast.LENGTH_LONG).show();
                            }
                        } catch (Throwable t) {
                            loadingg.dismiss();
                            Log.d("My App", "Could not parse malformed JSON: \"" + response + "\"");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onErrorResponse: " + error);
                        loadingg.dismiss();
                        Toast.makeText(Penjualan.this, "Server Tidak Terjangkau", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("id_karyawan", getId_Karyawan());
                params.put("id_pelanggan", idPelanggan);
                params.put("no_nota", kodeBarangDialog.getText().toString());
                params.put("Total", subTootal.getText().toString());

                for(int i = 0; i < barangPilih3.size(); i++) {
                    params.put("id_barang["+i+"]", barangPilih3.get(0).getIdBarang());
                    params.put("id_varian_harga["+i+"]", barangPilih3.get(0).getIdVarianHarga());
                    params.put("jumlah_beli["+i+"]", String.valueOf(barangPilih3.get(0).getQty()));
                    params.put("sub_total["+i+"]", String.valueOf(barangPilih3.get(0).getSubTotal()));
                }

                Log.d(TAG, "hasil: " + barangPilih3.get(0).getIdBarang()+"_"+barangPilih3.get(0).getIdVarianHarga()+"_"+barangPilih3.get(0).getQty()+"_"+barangPilih3.get(0).getSubTotal());
                Log.d(TAG, "getParams: " + params.toString());
                return params;
            }
        };

        Volley.newRequestQueue(this).add(stringRequest);


    }

    List<Penjualan.StringWithTag> pelangganName = new ArrayList<Penjualan.StringWithTag>();
    private void ambilNoNotaPenjualan() {
        pelangganName.add(new StringWithTag("-- Umum --", null,null, null, null));
        StringRequest stringRequest = new StringRequest(Request.Method.GET, konfigurasi.URL_GET_NONOTAPENJUALAN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            String noNotaa = obj.getString("nota");
                            input_no_nota.setText(noNotaa);

                            JSONArray arrSupplier = obj.getJSONArray("pelanggan");
                            for(int i = 0; i < arrSupplier.length(); i++){
                                JSONObject supplierJson = arrSupplier.getJSONObject(i);
                                String namapelanggan = supplierJson.getString("nama");
                                String idpelanggan = supplierJson.getString("id_pelanggan");
                                String notelp = supplierJson.getString("no_telp");
                                String alamat = supplierJson.getString("alamat");
                                String catatan = supplierJson.getString("catatan");
                                pelangganName.add(new StringWithTag(namapelanggan, idpelanggan, notelp, alamat, catatan));
                            }

                            ArrayAdapter<Penjualan.StringWithTag> adapterSpinnerPelanggan = new ArrayAdapter<Penjualan.StringWithTag>(
                                    Penjualan.this,
                                    android.R.layout.simple_spinner_dropdown_item, pelangganName);
                            input_nama_pelanggan.setAdapter(adapterSpinnerPelanggan);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d(TAG, "onResponse: " + e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onErrorResponse: " + error);
                    }
                });

        Volley.newRequestQueue(this).add(stringRequest);
    }

    public void setDate() {
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
        date = formatter.format(today);
    }

    public void settime(){
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");
        time = formatter.format(today);
    }

    private static class StringWithTag {
        public String string;
        public Object id;
        public String Telp;
        public String Alaamat;
        public String Caatatan;

        public StringWithTag(String string, Object id, String Telp, String Alaamat, String Caatatan) {
            this.string = string;
            this.id = id;
            this.Telp = Telp;
            this.Alaamat = Alaamat;
            this.Caatatan = Caatatan;
        }

        @Override
        public String toString() {
            return string;
        }
    }

    private String getId_Karyawan(){
        SharedPreferences preferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String id_karyawan = preferences.getString("id_karyawan", "null");
        return id_karyawan;
    }
}