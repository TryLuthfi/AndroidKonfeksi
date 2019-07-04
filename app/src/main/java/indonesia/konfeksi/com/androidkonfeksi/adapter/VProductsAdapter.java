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
import indonesia.konfeksi.com.androidkonfeksi.json.VProduct;

public class VProductsAdapter extends RecyclerView.Adapter<VProductsAdapter.ProductViewHolder> {

    private Activity mCtx;
    private List<VProduct> productList;
    private NumberFormat formatRupiah;



    public VProductsAdapter(Activity mCtx, List<VProduct> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.listvproduct, parent, false);

        Locale localeID = new Locale("in", "ID");

        formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        final VProduct vproduct = productList.get(position);

        holder.satuan.setText(vproduct.getUkuran());
        holder.date_input.setText(vproduct.getDate_input());
        holder.warna.setText(vproduct.getWarna());
        holder.jumlah.setText(vproduct.getMeter());
        holder.stok_jual.setText(vproduct.getStok_jual());
        double hargabarang = Double.parseDouble(vproduct.getHarga_barang());
        holder.harga.setText(formatRupiah.format((double)hargabarang));

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView satuan;
        TextView date_input;
        TextView warna;
        TextView jumlah;
        TextView stok_jual;
        TextView harga;
        View view;

        public ProductViewHolder(View itemView) {
            super(itemView);
            view = itemView;

            satuan = itemView.findViewById(R.id.satuan);
            date_input = itemView.findViewById(R.id.date_input);
            warna = itemView.findViewById(R.id.warna);
            jumlah = itemView.findViewById(R.id.jumlah);
            stok_jual = itemView.findViewById(R.id.stok_jual);
            harga = itemView.findViewById(R.id.harga);
        }
    }
}
