package indonesia.konfeksi.com.androidkonfeksi.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import indonesia.konfeksi.com.androidkonfeksi.R;
import indonesia.konfeksi.com.androidkonfeksi.konfigurasi.konfigurasi;

public class TambahPembelian extends AppCompatActivity {
    private static final String TAG = "TambahPembelian";
    private String date;
    private TextView tgl;
    private ProgressDialog loading;
    private ProgressDialog loading2;
    private Button tambah;

    private TextView nonota;
    private TextView teksbukti;
    private Spinner namaSupplier;
    private Spinner metodeBayar;
    private Spinner namaBarang;
    private EditText jumlahColi;
    private EditText keterangan;
    private Spinner status;
    private EditText nofaktur;
    private EditText namaGudang;
    private EditText biaya;
    private EditText totalHarga;

    private File f;
    private ImageView buktiImage;
    private Bitmap imageUri;
    private Uri contentUri;
    private static final int PICK_IMAGE = 100;
    private ByteArrayOutputStream byteArrayOutputStream;
    private byte[] byteArray;
    private String ConvertImage;
    private static final int CAMERA_REQUEST = 1;

    String idSupplierPilih;
    String idBarangPilih;
    int idStatusPilih;
    String idPembayaranPilih;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_pembelian);

        nonota = findViewById(R.id.id_nonota);
        namaSupplier = findViewById(R.id.id_namasupplier);
        metodeBayar = findViewById(R.id.id_metodepembayaran);
        namaBarang = findViewById(R.id.id_namabarang);
        jumlahColi = findViewById(R.id.id_jumlah);
        keterangan = findViewById(R.id.id_keterangan);
        status = findViewById(R.id.id_status);
        nofaktur = findViewById(R.id.id_nofaktur);
        namaGudang = findViewById(R.id.id_namagudang);
        biaya = findViewById(R.id.id_biaya);
        totalHarga = findViewById(R.id.id_totalharga);
        tambah = findViewById(R.id.tambah);
        buktiImage = findViewById(R.id.buktiImage);
        teksbukti = findViewById(R.id.teksbukti);
        byteArrayOutputStream = new ByteArrayOutputStream();

        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahBarangPembelian();
            }
        });

        buktiImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog alertDialog = new Dialog(TambahPembelian.this);
                alertDialog.setCancelable(true);
                alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                alertDialog.setCanceledOnTouchOutside(true);
                alertDialog.setContentView(R.layout.gallery_dialog);
                LinearLayout galery= alertDialog.findViewById(R.id.galery);
                LinearLayout camera= alertDialog.findViewById(R.id.camera);
                galery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openGallery();
                    }
                });
                camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openCamera();
                    }
                });

                alertDialog.show();
            }
        });


        List<String> metodepembayaran = new ArrayList<String>();
        metodepembayaran.add("Cash");
        metodepembayaran.add("Piutang");
        ArrayAdapter<String> adapterBayarSupplier = new ArrayAdapter<String>(
                TambahPembelian.this,
                android.R.layout.simple_spinner_dropdown_item, metodepembayaran);
        metodeBayar.setAdapter(adapterBayarSupplier);

        List<String> statusbarang = new ArrayList<String>();
        statusbarang.add("Tampilkan");
        statusbarang.add("Sembunyikan");
        ArrayAdapter<String> adapterStatusBarang = new ArrayAdapter<String>(
                TambahPembelian.this,
                android.R.layout.simple_spinner_dropdown_item, statusbarang);
        status.setAdapter(adapterStatusBarang);


        ambilNoNota();

        namaSupplier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                StringWithTag supplier = (StringWithTag) parent.getItemAtPosition(position);
                Log.d(TAG, "onItemSelected: " + supplier.id + ", " + supplier.string);
                idSupplierPilih = (String) supplier.id;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        namaBarang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                StringWithTag barang = (StringWithTag) parent.getItemAtPosition(position);
                Log.d(TAG, "onItemSelected: " + barang.id + ", " + barang.string);
                idBarangPilih = (String) barang.id;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    idStatusPilih = 1;
                }else{
                    idStatusPilih = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        metodeBayar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    idPembayaranPilih = "cash";
                }else{
                    idPembayaranPilih = "piutang";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    List<StringWithTag> supplierName = new ArrayList<StringWithTag>();
    List<StringWithTag> barangName = new ArrayList<StringWithTag>();
    private void ambilNoNota() {
        loading2 = ProgressDialog.show(TambahPembelian.this, "Updating...", "Mohon Tunggu...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, konfigurasi.URL_GET_NONOTA,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject obj = new JSONObject(response);
                        nonota.setText("NOTA" + String.format("%03d", Integer.parseInt(obj.getString("nota"))));
                        nofaktur.setText("FAK" + String.format("%03d", Integer.parseInt(obj.getString("nota"))));

                        JSONArray arrSupplier = obj.getJSONArray("supplier");
                        for(int i = 0; i < arrSupplier.length(); i++){
                            JSONObject supplierJson = arrSupplier.getJSONObject(i);
                            String namasupplier = supplierJson.getString("nama_supplier");
                            String idsupplier = supplierJson.getString("id_supplier");
                            supplierName.add(new StringWithTag(namasupplier, idsupplier));
                        }

                        ArrayAdapter<StringWithTag> adapterSpinnerSupplier = new ArrayAdapter<StringWithTag>(
                                TambahPembelian.this,
                                android.R.layout.simple_spinner_dropdown_item, supplierName);
                        namaSupplier.setAdapter(adapterSpinnerSupplier);


                        JSONArray arrBarang = obj.getJSONArray("barang");
                        for(int j = 0; j < arrBarang.length(); j++){
                            JSONObject barangJson = arrBarang.getJSONObject(j);
                            String namabarang = barangJson.getString("nama_barang");
                            String idbarang = barangJson.getString("id_barang");
                            //barangName.add(namabarang);
                            barangName.add(new StringWithTag(namabarang, idbarang));

                        }
                        ArrayAdapter<StringWithTag> adapterBarangSupplier = new ArrayAdapter<StringWithTag>(
                                TambahPembelian.this,
                                android.R.layout.simple_spinner_dropdown_item, barangName);
                        namaBarang.setAdapter(adapterBarangSupplier);
                        loading2.dismiss();



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

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }

    public void tambahBarangPembelian(){
        loading = ProgressDialog.show(TambahPembelian.this, "Updating...", "Mohon Tunggu...", false, false);

//

        if(getId_Karyawan() == "null"){
            Log.d(TAG, "tambahBarangPembelian: " + getId_Karyawan());
            Toast.makeText(TambahPembelian.this, "Id Karyawan tidak tersedia", Toast.LENGTH_LONG).show();
            return;
        }


        StringRequest stringRequest = new StringRequest(Request.Method.POST, konfigurasi.URL_TAMBAH_PEMBELIAN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject resObj = new JSONObject(response);
                            if(resObj.getString("success").equals("true")){
                                loading.dismiss();
                                Log.d(TAG, "onResponse: " + resObj.toString());
                                Toast.makeText(TambahPembelian.this, "Berhasil Menambah Data", Toast.LENGTH_SHORT).show();
                                final Dialog alertDialog = new Dialog(TambahPembelian.this);
                                alertDialog.setCancelable(true);
                                alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                alertDialog.setCanceledOnTouchOutside(true);
                                alertDialog.setContentView(R.layout.dialog_pembelian);
                                Button tambah =alertDialog.findViewById(R.id.tambah);
                                Button tidak =alertDialog.findViewById(R.id.tidak);
                                tidak.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent (TambahPembelian.this, DashBoard.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                                tambah.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        recreate();
                                        jumlahColi.setText(null);
                                        keterangan.setText(null);
                                        namaGudang.setText(null);
                                        biaya.setText(null);
                                        totalHarga.setText(null);
                                    }
                                });
                                alertDialog.show();
                            }else{
                                loading.dismiss();
                                Log.d(TAG, "onResponse false: " + resObj.toString());
                                Toast.makeText(TambahPembelian.this, resObj.getString("message"), Toast.LENGTH_LONG).show();
                            }
                        } catch (Throwable t) {
                            loading.dismiss();
                            Log.d("My App", "Could not parse malformed JSON: \"" + response + "\"");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onErrorResponse: " + error);
                        loading.dismiss();
                        Toast.makeText(TambahPembelian.this, "Server Tidak Terjangkau", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("no_faktur", nofaktur.getText().toString());
                params.put("no_nota", nonota.getText().toString());
                params.put("metode_pembayaran", idPembayaranPilih);
                params.put("total_harga", totalHarga.getText().toString());
                params.put("biaya", biaya.getText().toString());
                params.put("id_karyawan", getId_Karyawan());
                params.put("id_supplier", idSupplierPilih);
                params.put("coly", jumlahColi.getText().toString());
                params.put("status", String.valueOf(idStatusPilih));
                params.put("id_barang", idBarangPilih);
                params.put("ket", keterangan.getText().toString());
                params.put("nama_gudang", namaGudang.getText().toString());
                params.put("image",ConvertImage);
                Log.d(TAG, "getParams: " + params.toString());
                return params;
            }
        };

        Volley.newRequestQueue(this).add(stringRequest);


    }

    private String getId_Karyawan(){
        SharedPreferences preferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String id_karyawan = preferences.getString("id_karyawan", "null");
        return id_karyawan;
    }



    private static class StringWithTag {
        public String string;
        public Object id;

        public StringWithTag(String string, Object id) {
            this.string = string;
            this.id = id;
        }

        @Override
        public String toString() {
            return string;
        }
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    private void openCamera(){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent,CAMERA_REQUEST);
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = TambahPembelian.this.managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {

            if (data != null) {
                contentUri = data.getData();

                if (imageUri != null){
                    imageUri.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream);
                    byteArray = byteArrayOutputStream.toByteArray();
                    ConvertImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
                }else {

                }

                try {
                    imageUri = MediaStore.Images.Media.getBitmap(Objects.requireNonNull(getApplicationContext()).getContentResolver(), contentUri);
                    String selectedPath = getPath(contentUri);
                    buktiImage.setImageBitmap(imageUri);
                    f = new File(selectedPath);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Failed!", Toast.LENGTH_SHORT).show();
                }

            }
        }
        if (requestCode == CAMERA_REQUEST) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            buktiImage.setImageBitmap(photo);
        }
    }


}

