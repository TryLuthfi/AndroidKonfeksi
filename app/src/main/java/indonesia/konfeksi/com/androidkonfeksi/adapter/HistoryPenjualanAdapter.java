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
import indonesia.konfeksi.com.androidkonfeksi.activity.IsiBarang;
import indonesia.konfeksi.com.androidkonfeksi.json.Product;
import indonesia.konfeksi.com.androidkonfeksi.json.ProductHistoryPenjualan;

public class HistoryPenjualanAdapter extends RecyclerView.Adapter<HistoryPenjualanAdapter.ProductViewHolder> {

    private Activity mCtx;
    private List<ProductHistoryPenjualan> productList;
    private NumberFormat formatRupiah;



    public HistoryPenjualanAdapter(Activity mCtx, List<ProductHistoryPenjualan> productList) {
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
        final ProductHistoryPenjualan product = productList.get(position);

        holder.dateInput.setText(product.getDate_input());
        holder.nama.setText(product.getNama());
        holder.no_nota.setText(product.getNo_nota());
        holder.kasir.setText(product.getNama_karyawan());
        double hargabarang = Double.parseDouble(product.getBiaya());
        holder.grandTotal.setText(formatRupiah.format((double)hargabarang));
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
