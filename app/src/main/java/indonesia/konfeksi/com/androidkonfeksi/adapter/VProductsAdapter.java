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
        View view = inflater.inflate(R.layout.listproduct, parent, false);

        Locale localeID = new Locale("in", "ID");

        formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        final VProduct vproduct = productList.get(position);

        holder.kodebarang.setText(vproduct.getKode_barang());
        holder.stokjual.setText(vproduct.getStok_jual());
        holder.namabarang.setText(vproduct.getNama_barang());
        double hargabarang = Double.parseDouble(vproduct.getHarga_barang());
        holder.hargabarang.setText(formatRupiah.format((double)hargabarang));
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx.getApplicationContext(), IsiBarang.class);
                intent.putExtra("id_barang", vproduct.getId_barang());
                intent.putExtra("kode_barang", vproduct.getKode_barang());
                intent.putExtra("kode_barcode", vproduct.getKode_barcode());
                intent.putExtra("nama_barang", vproduct.getNama_barang());
                intent.putExtra("diskon_rupiah", vproduct.getDiskon_rupiah());
                intent.putExtra("harga_barang",vproduct.getHarga_barang());
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
