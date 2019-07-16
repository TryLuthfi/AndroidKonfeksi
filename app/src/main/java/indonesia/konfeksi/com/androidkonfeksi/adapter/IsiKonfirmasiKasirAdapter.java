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
import indonesia.konfeksi.com.androidkonfeksi.json.ProductIsiKonfirmasiKasir;
import indonesia.konfeksi.com.androidkonfeksi.json.ProductKonfirmasiKasir;

public class IsiKonfirmasiKasirAdapter extends RecyclerView.Adapter<IsiKonfirmasiKasirAdapter.ProductViewHolder> {

    private Activity mCtx;
    private List<ProductIsiKonfirmasiKasir> productList;
    private NumberFormat formatRupiah;



    public IsiKonfirmasiKasirAdapter(Activity mCtx, List<ProductIsiKonfirmasiKasir> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.listproduct, parent, false);

        Locale localeID = new Locale("in", "ID");

        formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        final ProductIsiKonfirmasiKasir product = productList.get(position);

        holder.nama_barang.setText(product.getNama_barang());
        holder.kode_barang.setText(product.getKode_barang());
        holder.stok_jual.setText(product.getStok_jual());
        double hargabarang1 = Double.parseDouble(product.getHarga());
        holder.harga_barang.setText(formatRupiah.format((double)hargabarang1));

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView nama_barang;
        TextView kode_barang;
        TextView stok_jual;
        TextView harga_barang;
        TextView grandTotal;
        View view;

        public ProductViewHolder(View itemView) {
            super(itemView);
            view = itemView;

            nama_barang = view.findViewById(R.id.nama_barang);
            kode_barang = view.findViewById(R.id.kode_barang);
            stok_jual = view.findViewById(R.id.stok_jual);
            harga_barang = view.findViewById(R.id.harga_barang);
        }
    }
}


