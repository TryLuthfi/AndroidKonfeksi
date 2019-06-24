package indonesia.konfeksi.com.androidkonfeksi.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import indonesia.konfeksi.com.androidkonfeksi.R;

public class DashBoard extends AppCompatActivity {
    private LinearLayout penjualan;
    private LinearLayout pembelian;
    private LinearLayout return1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        final String id_karyawan = getId_Karyawan();
        final String kode_karyawan = getKode_Karyawan();
        final String nama = getNama();
        final String alamat = getAlamat();
        final String kota = getKota();
        final String negara = getNegara();
        final String kode_pos = getKode_Pos();
        final String no_telp = getNo_Telp();
        final String email = getEmail();
        final String status = getStatus();

        penjualan = findViewById(R.id.penjualan);
        pembelian = findViewById(R.id.pembelian);
        return1 = findViewById(R.id.return1);
        penjualan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TambahPenjualan.class);
                startActivity(intent);
            }
        });
        pembelian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TambahPembelian.class);
                startActivity(intent);
            }
        });
        return1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DashBoard.this, id_karyawan+kode_karyawan+nama+alamat+kota+negara+kode_pos+email+status, Toast.LENGTH_SHORT).show();
            }
        });


    }

    private String getId_Karyawan(){
        SharedPreferences preferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String id_karyawan = preferences.getString("id_karyawan", "null");
        return id_karyawan;
    }

    private String getKode_Karyawan(){
        SharedPreferences preferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String kode_karyawan = preferences.getString("kode_karyawan", "null");
        return kode_karyawan;
    }

    private String getNama(){
        SharedPreferences preferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String nama = preferences.getString("nama", "null");
        return nama;
    }

    private String getAlamat(){
        SharedPreferences preferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String alamat = preferences.getString("alamat", "null");
        return alamat;
    }

    private String getKota(){
        SharedPreferences preferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String kota = preferences.getString("kota", "null");
        return kota;
    }

    private String getNegara(){
        SharedPreferences preferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String negara = preferences.getString("negara", "null");
        return negara;
    }

    private String getKode_Pos(){
        SharedPreferences preferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String id_karyawan = preferences.getString("id_karyawan", "null");
        return id_karyawan;
    }

    private String getNo_Telp(){
        SharedPreferences preferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String no_telp = preferences.getString("no_telp", "null");
        return no_telp;
    }

    private String getEmail(){
        SharedPreferences preferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String email = preferences.getString("email", "null");
        return email;
    }

    private String getStatus(){
        SharedPreferences preferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String status = preferences.getString("status", "null");
        return status;
    }
}
