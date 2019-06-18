package indonesia.konfeksi.com.androidkonfeksi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {

    private Activity mCtx;
    private List<Product> productList;

    public ProductsAdapter(Activity mCtx, List<Product> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.listproduct, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        final Product product = productList.get(position);

        holder.kodebarang.setText(product.getKode_barang());
        holder.namabarang.setText(product.getNama_barang());
        holder.kodebarcode.setText(product.getKode_barcode());
        holder.hargabarang.setText(product.getHarga_barang());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView kodebarang;
        TextView kodebarcode;
        TextView namabarang;
        TextView hargabarang;
        View view;

        public ProductViewHolder(View itemView) {
            super(itemView);
            view = itemView;

            kodebarang = itemView.findViewById(R.id.kode_barang);
            kodebarcode = itemView.findViewById(R.id.kode_barcode);
            namabarang = itemView.findViewById(R.id.nama_barang);
            hargabarang = itemView.findViewById(R.id.harga_barang);
        }
    }
}