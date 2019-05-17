package indonesia.konfeksi.com.androidkonfeksi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class DashBoard extends AppCompatActivity {
    private LinearLayout penjualan;
    private LinearLayout pembelian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        penjualan = findViewById(R.id.penjualan);
        pembelian = findViewById(R.id.pembelian);
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
    }
}
