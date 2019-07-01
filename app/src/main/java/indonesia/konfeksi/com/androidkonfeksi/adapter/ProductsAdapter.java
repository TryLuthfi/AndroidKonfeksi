package indonesia.konfeksi.com.androidkonfeksi.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import indonesia.konfeksi.com.androidkonfeksi.R;
import indonesia.konfeksi.com.androidkonfeksi.activity.IsiBarang;
import indonesia.konfeksi.com.androidkonfeksi.json.Product;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {

    private Activity mCtx;
    private List<Product> productList;
    private NumberFormat formatRupiah;



    public ProductsAdapter(Activity mCtx, List<Product> productList) {
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
        final Product product = productList.get(position);

        holder.kodebarang.setText(product.getKode_barang());
        holder.stokjual.setText(product.getStok_jual());
        holder.namabarang.setText(product.getNama_barang());
        double hargabarang = Double.parseDouble(product.getHarga_barang());
        holder.hargabarang.setText(formatRupiah.format((double)hargabarang));
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx.getApplicationContext(), IsiBarang.class);
                intent.putExtra("id_barang", product.getId_barang());
                intent.putExtra("kode_barang", product.getKode_barang());
                intent.putExtra("kode_barcode", product.getKode_barcode());
                intent.putExtra("nama_barang", product.getNama_barang());
                intent.putExtra("diskon_rupiah", product.getDiskon_rupiah());
                intent.putExtra("harga_barang", product.getHarga_barang());
                mCtx.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView kodebarang;
        TextView stokjual;
        TextView namabarang;
        TextView hargabarang;
        View view;

        public ProductViewHolder(View itemView) {
            super(itemView);
            view = itemView;

            kodebarang = itemView.findViewById(R.id.kode_barang);
            stokjual = itemView.findViewById(R.id.stok_jual);
            namabarang = itemView.findViewById(R.id.nama_barang);
            hargabarang = itemView.findViewById(R.id.harga_barang);
        }
    }
}