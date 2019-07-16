package indonesia.konfeksi.com.androidkonfeksi.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import indonesia.konfeksi.com.androidkonfeksi.R;
import indonesia.konfeksi.com.androidkonfeksi.activity.isiHistoryPembelian;
import indonesia.konfeksi.com.androidkonfeksi.activity.isiKonfirmasiKasir;
import indonesia.konfeksi.com.androidkonfeksi.json.ProductHistoryPembelian;
import indonesia.konfeksi.com.androidkonfeksi.json.ProductHistoryPenjualan;

public class HistoryPembelianAdapter extends RecyclerView.Adapter<HistoryPembelianAdapter.ProductViewHolder> {

    private Activity mCtx;
    private List<ProductHistoryPembelian> productList;
    private NumberFormat formatRupiah;



    public HistoryPembelianAdapter(Activity mCtx, List<ProductHistoryPembelian> productList) {
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
        final ProductHistoryPembelian product = productList.get(position);

        holder.dateInput.setText(product.getDate());
        holder.nama.setText(product.getNama_supplier());
        holder.no_nota.setText(product.getNo_nota());
        holder.kasir.setText(product.getNama_karyawan());
        double hargabarang = Double.parseDouble(product.getBiaya());
        holder.grandTotal.setText(formatRupiah.format((double)hargabarang));

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (mCtx.getApplicationContext(), isiHistoryPembelian.class);
                intent.putExtra("no_faktur", product.getNo_faktur());
                intent.putExtra("no_nota", product.getNo_nota());
                intent.putExtra("nama_karyawan", product.getNama_karyawan());
                intent.putExtra("nama_supplier", product.getNama_supplier());
                intent.putExtra("date", product.getDate());
                intent.putExtra("time", product.getTime());
                intent.putExtra("total_harga", product.getTotal_harga());
                intent.putExtra("biaya", product.getBiaya());
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
            kasir = itemView.findViewById(R.id.kasir);
            no_nota = itemView.findViewById(R.id.no_nota);
            grandTotal = itemView.findViewById(R.id.grand_total);
        }
    }
}