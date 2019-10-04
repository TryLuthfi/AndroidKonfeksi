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
import android.view.KeyEvent;
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
    // nota layout
    private TextView input_no_nota;
    private TextView input_tgl_nota;
    private TextView input_waktu;
    private TextView input_kasir;
    private Spinner input_pengambil_barang;
    List<Penjualan.ListKaryawan> listKaryawans = new ArrayList<>();

    // pelanggan layout
    private Spinner input_nama_pelanggan;
    private TextView input_no_hp;
    private TextView input_alamat;
    private TextView input_info_lain;
    // pelanggan logic
    List<Penjualan.ListPelanggan> listPelanggan = new ArrayList<>();
    private String idPelanggan;
    private String TelpPelanggan;
    private String AlamatPelanggan;
    private String CatatanPelanggan;


    // prepare layout
    private LinearLayout isi;
    private ProgressBar loading;
    private TextView error;

    // Barang
    List<ProductPenjualanBarang> listBarang  = new ArrayList<>();
    private RecyclerView container_barang;
    private AlertDialog dialogPencarianBarang;
    List<ProductPenjualanBarang> barangDitemukan = new ArrayList<>();
    private AlertDialog dialogBarangDitemukan;
    private TextView namaBarangDialog;
    private TextView hargaaBarangDialog;

    private RecyclerView recyclerViewDialog;
    private Button tambah_pembelian;
    private Button final_tambah_pembelian;
    private PenjualanAdapter adapter;
    private String idBarang;
    private EditText kodeBarangDialog;
    private String kode;
    private String nama;
    private String harga;
    private EditText qtyBarang;
    private TextView subTootal;
    private TextView txErr;
    private Button cobaLagi;
    private String QTYINTs;
    private String hargaINTs;

    private int QTYINT;
    private int hargaINT;
    private int subTotalINT;
    private ProgressDialog loadingg;


    List<ProductPenjualanBarang> barangPilih3;
    ProductPenjualanBarang barangPilih2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjualan);

        // prepare set layout
        isi = findViewById(R.id.isi);
        loading = findViewById(R.id.loading);
        error = findViewById(R.id.error);
        txErr = findViewById(R.id.err_ap);
        cobaLagi = findViewById(R.id.reload_ap);
        // prepare logic
        isi.setVisibility(View.INVISIBLE);
        loading.setVisibility(View.VISIBLE);
        txErr.setVisibility(View.INVISIBLE);
        cobaLagi.setVisibility(View.INVISIBLE);

        // nota set layout
        input_no_nota = findViewById(R.id.input_no_nota);
        input_tgl_nota = findViewById(R.id.input_tgl_nota);
        input_waktu = findViewById(R.id.input_waktu);
        input_kasir = findViewById(R.id.input_kasir);
        input_pengambil_barang = findViewById(R.id.input_pengambil_barang);
        // nota logic
        input_tgl_nota.setText(setDate());
        input_waktu.setText(settime());
        getNota();
        input_kasir.setText(Objects.requireNonNull(getIntent().getExtras()).getString("NamaKaryawan"));
        getKaryawan();

        // pelanggan set layout
        input_nama_pelanggan = findViewById(R.id.input_nama_pelanngan);
        input_no_hp = findViewById(R.id.input_no_hp);
        input_alamat = findViewById(R.id.input_alamat);
        input_info_lain = findViewById(R.id.input_info_lain);
        // pelanggan logic
        getPelanggan();
        pelangganSpiner();



        // barang set layout
        container_barang = (RecyclerView) findViewById(R.id.recyclerView);
        tambah_pembelian = findViewById(R.id.tambah_pembelian);
        //dialog cari barang
        setUpDialogTambahBarang();
        // barang logic
        getDataBarang();
        tambah_pembelian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogPencarianBarang.show();
                kodeBarangDialog.setText("");
                namaBarangDialog.setText("");
                hargaaBarangDialog.setText("");
                subTootal.setText("");
            }
        });
        dialogPencarianBarang.setButton(Dialog.BUTTON_POSITIVE,"TAMBAH", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                for(int i = 0; i < productBarang.size(); i++){
//                    if (barangPilih2 == null){
//                        Toast.makeText(getApplicationContext(), "Barang Tidak Ada", Toast.LENGTH_SHORT).show();
//                    }else {
//                        if(productBarang.get(i).getIdBarang().equalsIgnoreCase(barangPilih2.getIdBarang())
//                                && productBarang.get(i).getIdVarianHarga().equalsIgnoreCase(barangPilih2.getIdVarianHarga()))
//                        {
//                            error.setVisibility(View.GONE);
//                            recyclerView.setVisibility(View.VISIBLE);
//                            barangPilih3.add(barangPilih2);
//                            PenjualanTambahAdapter penjualanadapter = new PenjualanTambahAdapter(Penjualan.this, barangPilih3);
//                            recyclerView.setAdapter(penjualanadapter);
//                            final_tambah_pembelian.setVisibility(View.VISIBLE);
//                        }
//                    }
//                }
            }
        });
        dialogPencarianBarang.setButton(Dialog.BUTTON_NEGATIVE,"CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        kodeBarangDialog.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    cariBarang();
                    return true;
                }
                return false;
            }
        });




//        final_tambah_pembelian = findViewById(R.id.final_tambah_pembelian2);
//
//        productBarang = new ArrayList<>();
//        ambilBarang();
//
//
//
//        barangPilih3 = new ArrayList<>();
//        recyclerView.setLayoutManager(new LinearLayoutManager(Penjualan.this));
//
//
//
//
//
//        final_tambah_pembelian.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tambahBarangPembelian();
//            }
//        });
//
//
//
//        ambilNoNotaPenjualan();
//        ambilListKaryawan();
//
//        kode = kodeBarangDialog.getText().toString().trim();
//
//
//
//
//        qtyBarang.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    int harga = Integer.valueOf(hargaaBarang.getText().toString());
//                    int qty = Integer.valueOf(s.toString());
//                    subTootal.setText(String.valueOf(harga * qty));
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//            }
//        });
//

    }

    // function for prepare
    public void reload(View v) {
        loading.setVisibility(View.VISIBLE);
        txErr.setVisibility(View.INVISIBLE);
        cobaLagi.setVisibility(View.INVISIBLE);
        // recyclerView.setVisibility(View.INVISIBLE);
        // ambilBarang();
    }

    // function for nota
    public void getNota(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, konfigurasi.url_nota_penjualan,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        input_no_nota.setText(response);
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
    public String setDate() {
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
        return formatter.format(today);
    }
    public String settime(){
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");
        return formatter.format(today);
    }
    public void getKaryawan(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, konfigurasi.url_karyawan_penjualan,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray arrKaryawan = new JSONArray(response);
                            for(int i = 0; i < arrKaryawan.length(); i++){
                                JSONObject jsonKaryawan = arrKaryawan.getJSONObject(i);
                                String kodeKaryawan = jsonKaryawan.getString("kode_karyawan");
                                String namaKaryawan = jsonKaryawan.getString("nama");
                                listKaryawans.add(new ListKaryawan(namaKaryawan, kodeKaryawan));
                            }

                            ArrayAdapter<Penjualan.ListKaryawan> listKaryawanArrayAdapter = new ArrayAdapter<Penjualan.ListKaryawan>(
                                    Penjualan.this,
                                    android.R.layout.simple_spinner_dropdown_item, listKaryawans);
                            input_pengambil_barang.setAdapter(listKaryawanArrayAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
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
    private static class ListKaryawan {
        public String nama;
        public Object kode;

        public ListKaryawan(String nama, Object kode) {
            this.nama = nama;
            this.kode = kode;
        }

        @Override
        public String toString() {
            return nama;
        }
    }

    // function for pelanggan
    public void getPelanggan(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, konfigurasi.url_pelanggan_penjualan,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray arrPelanggan = new JSONArray(response);
                        for(int i = 0; i < arrPelanggan.length(); i++){
                            JSONObject jsonPelanggan = arrPelanggan.getJSONObject(i);
                            String namapelanggan = jsonPelanggan.getString("nama");
                            String idpelanggan = jsonPelanggan.getString("id_pelanggan");
                            String notelp = jsonPelanggan.getString("no_telp");
                            String alamat = jsonPelanggan.getString("alamat");
                            String catatan = jsonPelanggan.getString("catatan");
                            listPelanggan.add(new ListPelanggan(namapelanggan, idpelanggan, notelp, alamat, catatan));

                        }
                        ArrayAdapter<Penjualan.ListPelanggan> adapterSpinnerPelanggan = new ArrayAdapter<>(
                                Penjualan.this,
                                android.R.layout.simple_spinner_dropdown_item, listPelanggan);
                        input_nama_pelanggan.setAdapter(adapterSpinnerPelanggan);
                    } catch (JSONException e) {
                        e.printStackTrace();
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
    private static class ListPelanggan {
        public String nama;
        public Object id;
        public String Telp;
        public String Alaamat;
        public String Caatatan;

        public ListPelanggan(String nama, Object id, String Telp, String Alaamat, String Caatatan) {
            this.nama = nama;
            this.id = id;
            this.Telp = Telp;
            this.Alaamat = Alaamat;
            this.Caatatan = Caatatan;
        }

        @Override
        public String toString() {
            return nama;
        }
    }
    private void pelangganSpiner(){
        input_nama_pelanggan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Penjualan.ListPelanggan pelanggan = (Penjualan.ListPelanggan) parent.getItemAtPosition(position);
                // Log.d(TAG, "onItemSelected: " + pelanggan.id + ", " + pelanggan.nama + ", " + pelanggan.Telp + ", " + pelanggan.Alaamat + ", " + pelanggan.Caatatan);
                idPelanggan = (String) pelanggan.id;
                TelpPelanggan = pelanggan.Telp;
                AlamatPelanggan = pelanggan.Alaamat;
                CatatanPelanggan = pelanggan.Caatatan;

                input_no_hp.setText(TelpPelanggan.isEmpty()? "Tidak Ada" : TelpPelanggan);
                input_alamat.setText(AlamatPelanggan.isEmpty()? "Tidak Ada" : AlamatPelanggan);
                input_info_lain.setText(CatatanPelanggan.isEmpty()? "Tidak Ada" : CatatanPelanggan);

                cekTopPelanggan(idPelanggan);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    void cekTopPelanggan(String idPelanggan){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, konfigurasi.url_top_pelanggan_penjualan + "/" + idPelanggan,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

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

    // function for barang
    void getDataBarang(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, konfigurasi.url_barang_penjualan,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject res = new JSONObject(response);
                            JSONArray arrBarang = res.getJSONArray("datanya");
                            for(int i = 0; i < arrBarang.length(); i++){
                                JSONObject jsonBarang = arrBarang.getJSONObject(i);
                                ProductPenjualanBarang barang = new ProductPenjualanBarang(
                                        jsonBarang.getString("kode_barcode_varian"),
                                        jsonBarang.getString("nama_barang"),
                                        jsonBarang.getString("satuan"),
                                        jsonBarang.getString("meter"),
                                        jsonBarang.getString("harga"),
                                        jsonBarang.getString("warna"),
                                        jsonBarang.getString("id_varian_harga"),
                                        jsonBarang.getString("id_barang"),
                                        0,
                                        0
                                );
                                listBarang.add(barang);
                            }

                            isi.setVisibility(View.VISIBLE);
                            loading.setVisibility(View.INVISIBLE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG, "onResponse: " + e );
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
    void setUpDialogTambahBarang(){
        dialogPencarianBarang = new AlertDialog.Builder(Penjualan.this).create();
        LayoutInflater inflaterTambahBarang = getLayoutInflater();
        View dialogViewTambahBarang = inflaterTambahBarang.inflate(R.layout.form_penjualan, null);
        dialogPencarianBarang.setView(dialogViewTambahBarang);
        dialogPencarianBarang.setCancelable(true);

        kodeBarangDialog = dialogViewTambahBarang.findViewById(R.id.kodeBarang);
        namaBarangDialog = dialogViewTambahBarang.findViewById(R.id.namaBarang);
        hargaaBarangDialog = dialogViewTambahBarang.findViewById(R.id.hargaBarang);
        qtyBarang = dialogViewTambahBarang.findViewById(R.id.qtyBarang);
        subTootal = dialogViewTambahBarang.findViewById(R.id.subTotal);
    }
    void cariBarang(){
        kode = kodeBarangDialog.getText().toString().trim();
        kode = kode.replace(" ", "");
        barangDitemukan.clear();

        for(int i = 0; i < listBarang.size(); i++){
            if(listBarang.get(i).getKodeBarcodeVarian().equalsIgnoreCase(kode)){
                barangDitemukan.add(listBarang.get(i));
            }
        }

        if(barangDitemukan.size() > 1){
            dialogBarangDitemukan = new AlertDialog.Builder(Penjualan.this).create();
            LayoutInflater inflaterBarangDitemukan = getLayoutInflater();
            View dialogViewBarangDitemukan = inflaterBarangDitemukan.inflate(R.layout.recycler_dialog, null);
            dialogBarangDitemukan.setView(dialogViewBarangDitemukan);
            dialogBarangDitemukan.setCancelable(true);

            recyclerViewDialog =  dialogViewBarangDitemukan.findViewById(R.id.recyclerViewDialog);
            recyclerViewDialog.setLayoutManager(new LinearLayoutManager(Penjualan.this));

            dialogBarangDitemukan.show();

            DialogRecyclerAdapter adapter = new DialogRecyclerAdapter(Penjualan.this, barangDitemukan, Penjualan.this);
            recyclerViewDialog.setAdapter(adapter);
        }else{
            if(barangDitemukan.size() == 1){
                barangPilih2 = barangDitemukan.get(0);
                namaBarangDialog.setText(barangDitemukan.get(0).getNamaBarang());
                hargaaBarangDialog.setText(barangDitemukan.get(0).getHarga());
            }
        }
    }


    public void recyclerViewListClicked(View v, int position){
        Log.d(TAG, "recyclerViewListClicked: " + position);
        Log.e(TAG, "recyclerViewListClicked: " + barangDitemukan.get(position).getNamaBarang());
        // namaaBarang.setText(barangPilih.get(position).getUkuran());
        // hargaaBarang.setText(barangPilih.get(position).getHarga());
        // dialog2.dismiss();
        // nama = namaaBarang.getText().toString().trim();
        // harga = hargaaBarang.getText().toString().trim();
        // barangPilih2 = barangPilih.get(position);
    }







//    public void tambahBarangPembelian(){
//        loadingg = ProgressDialog.show(Penjualan.this, "Updating...", "Mohon Tunggu...", false, false);
//
////
//
//        if(getId_Karyawan().equals("null")){
//            Log.d(TAG, "tambahBarangPembelian: " + getId_Karyawan());
//            Toast.makeText(Penjualan.this, "Id Karyawan tidak tersedia", Toast.LENGTH_LONG).show();
//            return;
//        }
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, konfigurasi.URL_TAMBAH_PENJUALAN,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject resObj = new JSONObject(response);
//                            if(resObj.getString("success").equals("true")){
//                                loadingg.dismiss();
//                                Log.d(TAG, "onResponse123: " + resObj.toString());
//                                Toast.makeText(Penjualan.this, "Berhasil Menambah Data", Toast.LENGTH_SHORT).show();
//                            }else{
//                                loadingg.dismiss();
//                                Log.d(TAG, "onResponse false: " + resObj.toString());
//                                Toast.makeText(Penjualan.this, resObj.getString("message"), Toast.LENGTH_LONG).show();
//                            }
//                        } catch (Throwable t) {
//                            loadingg.dismiss();
//                            Log.d("My App", "Could not parse malformed JSON: \"" + response + "\"");
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.d(TAG, "onErrorResponse: " + error);
//                        loadingg.dismiss();
//                        Toast.makeText(Penjualan.this, "Server Tidak Terjangkau", Toast.LENGTH_LONG).show();
//                    }
//                }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//
//                params.put("id_karyawan", getId_Karyawan());
//                params.put("id_pelanggan", idPelanggan);
//                params.put("no_nota", kodeBarangDialog.getText().toString());
//                params.put("Total", subTootal.getText().toString());
//
//                for(int i = 0; i < barangPilih3.size(); i++) {
//                    params.put("id_barang["+i+"]", barangPilih3.get(0).getIdBarang());
//                    params.put("id_varian_harga["+i+"]", barangPilih3.get(0).getIdVarianHarga());
//                    params.put("jumlah_beli["+i+"]", String.valueOf(barangPilih3.get(0).getQty()));
//                    params.put("sub_total["+i+"]", String.valueOf(barangPilih3.get(0).getSubTotal()));
//                }
//
//                Log.d(TAG, "hasil: " + barangPilih3.get(0).getIdBarang()+"_"+barangPilih3.get(0).getIdVarianHarga()+"_"+barangPilih3.get(0).getQty()+"_"+barangPilih3.get(0).getSubTotal());
//                Log.d(TAG, "getParams: " + params.toString());
//                return params;
//            }
//        };
//        Volley.newRequestQueue(this).add(stringRequest);
//    }




    private String getId_Karyawan(){
        SharedPreferences preferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String id_karyawan = preferences.getString("id_karyawan", "null");
        return id_karyawan;
    }





//    private void ambilListKaryawan(){
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, konfigurasi.URL_GET_LISTKARYAWAN,
//            new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    try {
//                        JSONArray arrKaryawan = new JSONArray(response);
//                        for(int i = 0; i < arrKaryawan.length(); i++){
//                            JSONObject supplierJson = arrKaryawan.getJSONObject(i);
//                            String namaKaryawan = supplierJson.getString("nama");
//                            String kodeKaryawan= supplierJson.getString("kode_karyawan");
//                            listKaryawans.add(new ListKaryawan(namaKaryawan, kodeKaryawan));
//                        }
//
//                        ArrayAdapter<Penjualan.ListKaryawan> listKaryawanArrayAdapter = new ArrayAdapter<Penjualan.ListKaryawan>(
//                                Penjualan.this,
//                                android.R.layout.simple_spinner_dropdown_item, listKaryawans);
//                        input_pengambil_barang.setAdapter(listKaryawanArrayAdapter);
//                        Log.d(TAG, "onResponse : " + response);
//                    } catch (JSONException e) {
//                        Log.e(TAG, "onError: " + e);
//                        e.printStackTrace();
//                    }
//                }
//            },
//            new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Log.d(TAG, "onErrorResponse: " + error);
//                }
//            });
//        Volley.newRequestQueue(this).add(stringRequest);
//    }
//    private void ambilNoNotaPenjualan() {
//        pelangganName.add(new StringWithTag("-- Umum --", null,null, null, null));
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, konfigurasi.URL_GET_NONOTAPENJUALAN,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject obj = new JSONObject(response);
//                            String noNotaa = obj.getString("nota");
//                            input_no_nota.setText(noNotaa);
//                            Log.d(TAG, "onResponse: " + obj);
//                            JSONArray arrSupplier = obj.getJSONArray("pelanggan");
//                            for(int i = 0; i < arrSupplier.length(); i++){
//                                JSONObject supplierJson = arrSupplier.getJSONObject(i);
//                                String namapelanggan = supplierJson.getString("nama");
//                                String idpelanggan = supplierJson.getString("id_pelanggan");
//                                String notelp = supplierJson.getString("no_telp");
//                                String alamat = supplierJson.getString("alamat");
//                                String catatan = supplierJson.getString("catatan");
//                                pelangganName.add(new StringWithTag(namapelanggan, idpelanggan, notelp, alamat, catatan));
//                            }
//
//                            ArrayAdapter<Penjualan.StringWithTag> adapterSpinnerPelanggan = new ArrayAdapter<Penjualan.StringWithTag>(
//                                    Penjualan.this,
//                                    android.R.layout.simple_spinner_dropdown_item, pelangganName);
//                            input_nama_pelanggan.setAdapter(adapterSpinnerPelanggan);
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            Log.d(TAG, "onResponse: " + e);
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.d(TAG, "onErrorResponse: " + error);
//                    }
//                });
//
//        Volley.newRequestQueue(this).add(stringRequest);
//    }
//    private void ambilBarang(){
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, konfigurasi.URL_GET_AMBIL_BARANG,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject obj = new JSONObject(response);
//
//                            JSONArray arrSupplier = obj.getJSONArray("datanya");
//
//                            for(int i = 0; i < arrSupplier.length(); i++){
//                                JSONObject supplierJson = arrSupplier.getJSONObject(i);
//                                ProductPenjualanBarang barang = new ProductPenjualanBarang(
//                                        supplierJson.getString("id_barang"),
//                                        supplierJson.getString("kode_barang"),
//                                        supplierJson.getString("nama_barang"),
//                                        supplierJson.getString("diskon_persen"),
//                                        supplierJson.getString("diskon_rupiah"),
//                                        supplierJson.getString("id_varian_harga"),
//                                        supplierJson.getString("meter"),
//                                        supplierJson.getString("warna"),
//                                        supplierJson.getString("stok_jual"),
//                                        supplierJson.getString("harga"), 0, 0
//                                );
//                                productBarang.add(barang);
//
//                                isi.setVisibility(View.VISIBLE);
//                                loading.setVisibility(View.INVISIBLE);
//
//                                Log.d(TAG, "onResponse: " + supplierJson);
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            loading.setVisibility(View.INVISIBLE);
//                            txErr.setVisibility(View.VISIBLE);
//                            cobaLagi.setVisibility(View.VISIBLE);
//                            Log.d(TAG, "onResponse: " + e);
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        loading.setVisibility(View.INVISIBLE);
//                        txErr.setVisibility(View.VISIBLE);
//                        cobaLagi.setVisibility(View.VISIBLE);
//                        Log.d(TAG, "onErrorResponse: " + error);
//                    }
//                });
//
//        Volley.newRequestQueue(this).add(stringRequest);
//
//    }
}