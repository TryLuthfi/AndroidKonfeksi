package indonesia.konfeksi.com.androidkonfeksi.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import indonesia.konfeksi.com.androidkonfeksi.R;
import android.content.Intent;
import indonesia.konfeksi.com.androidkonfeksi.activity.isiKonfirmasiKasir;
import indonesia.konfeksi.com.androidkonfeksi.json.ProductKonfirmasiKasir;

public class KonfirmasiKasirAdapter extends RecyclerView.Adapter<KonfirmasiKasirAdapter.ProductViewHolder> {

    private Activity mCtx;
    private List<ProductKonfirmasiKasir> productList;
    private NumberFormat formatRupiah;



    public KonfirmasiKasirAdapter(Activity mCtx, List<ProductKonfirmasiKasir> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.listhistorypenjualan, parent, false);

        Locale localeID = new Locale("in", "ID");
        formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        final ProductKonfirmasiKasir product = productList.get(position);

        holder.dateInput.setText(product.getDate_input());
        holder.nama.setText(product.getNama());
        holder.no_nota.setText(product.getNo_nota());
        holder.kasir.setText(product.getNama_karyawan());
        double hargabarang = Double.parseDouble(product.getTotal_harga());
        holder.grandTotal.setText(formatRupiah.format((double)hargabarang));
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (mCtx.getApplicationContext(), isiKonfirmasiKasir.class);
                intent.putExtra("no_nota", product.getNo_nota());
                intent.putExtra("id_penjualan", product.getId_penjualan());
                intent.putExtra("nama_karyawan", product.getNama_karyawan());
                intent.putExtra("total_harga", product.getTotal_harga());
                intent.putExtra("tanggal", product.getDate());
                intent.putExtra("nama_pelanggan", product.getNama());
                intent.putExtra("no_telp", product.getNo_telp());
                intent.putExtra("alamat", product.getAlamat());
                mCtx.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView dateInput;
        TextView nama;
        TextView kasir;
        TextView no_nota;
        TextView grandTotal;
        View view;

        public ProductViewHolder(View itemView) {
            super(itemView);
            view = itemView;

            dateInput = itemView.findViewById(R.id.date_input);
            nama = itemView.findViewById(R.id.nama);
            no_nota = itemView.findViewById(R.id.no_nota);
            kasir = itemView.findViewById(R.id.kasir);
            grandTotal = itemView.findViewById(R.id.grand_total);
        }
    }
}

