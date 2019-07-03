package indonesia.konfeksi.com.androidkonfeksi.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import indonesia.konfeksi.com.androidkonfeksi.R;

public class DashBoard extends AppCompatActivity {
    private LinearLayout produkButton;
    private LinearLayout pembelianButton;
    private LinearLayout kasirButton;
    private LinearLayout penjualanButton;
    private LinearLayout returnButton;
    private TextView text;
    private TextView profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        final String id_karyawan = getId_Karyawan();
        final String nama = getNama();

        produkButton = findViewById(R.id.produk);
        pembelianButton = findViewById(R.id.pembelian);
        penjualanButton = findViewById(R.id.penjualan);
        kasirButton = findViewById(R.id.kasir);
        returnButton = findViewById(R.id.returnBarang);
        text = findViewById(R.id.textt);
        if (id_karyawan != null) {
            text.setText("" + nama);
        }

        produkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Produk.class);
                startActivity(intent);
            }
        });
        pembelianButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TambahPembelian.class);
                startActivity(intent);
            }
        });

        kasirButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(DashBoard.this, id_karyawan, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Kasir.class);
                startActivity(intent);
            }
        });

        penjualanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(DashBoard.this, id_karyawan, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Penjualan.class);
                startActivity(intent);
            }
        });

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(DashBoard.this, id_karyawan, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Return.class);
                startActivity(intent);
            }
        });


    }

    private String getId_Karyawan(){
        SharedPreferences preferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        return preferences.getString("id_karyawan", "null");
    }

    private String getNama(){
        SharedPreferences preferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        return preferences.getString("nama", "Nama Karyawan");
    }
}
