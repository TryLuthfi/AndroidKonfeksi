package indonesia.konfeksi.com.androidkonfeksi.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import indonesia.konfeksi.com.androidkonfeksi.R;

public class DashBoard extends AppCompatActivity {
    private LinearLayout penjualan;
    private LinearLayout pembelian;
    private LinearLayout return1;
    private TextView text;
    private TextView profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        final String id_karyawan = getId_Karyawan();
        final String nama = getNama();

        penjualan = findViewById(R.id.penjualan);
        pembelian = findViewById(R.id.pembelian);
        return1 = findViewById(R.id.return1);
        text = findViewById(R.id.textt);
        if (id_karyawan != "null") {
            text.setText("Selamat Datang "+nama);
        }

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
                Toast.makeText(DashBoard.this, id_karyawan, Toast.LENGTH_SHORT).show();
            }
        });


    }

    private String getId_Karyawan(){
        SharedPreferences preferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String id_karyawan = preferences.getString("id_karyawan", "null");
        return id_karyawan;
    }

    private String getNama(){
        SharedPreferences preferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String nama = preferences.getString("nama", "null");
        return nama;
    }
}
