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

import indonesia.konfeksi.com.androidkonfeksi.Interface.RecyclerViewClickListener;
import indonesia.konfeksi.com.androidkonfeksi.R;
import indonesia.konfeksi.com.androidkonfeksi.json.ProductPenjualanBarang;

public class DialogRecyclerAdapter extends RecyclerView.Adapter<DialogRecyclerAdapter.ProductViewHolder> {

    private Activity mCtx;
    private List<ProductPenjualanBarang> productList;
    private NumberFormat formatRupiah;


    private static RecyclerViewClickListener itemListener;

    public DialogRecyclerAdapter(Activity mCtx, List<ProductPenjualanBarang> productList, RecyclerViewClickListener itemListener) {
        this.mCtx = mCtx;
        this.productList = productList;
        this.itemListener = itemListener;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_recycler_dialog, parent, false);

        Locale localeID = new Locale("in", "ID");

        formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, final int position) {
        final ProductPenjualanBarang product = productList.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemListener.recyclerViewListClicked(v, position);
            }
        });
//        holder.kode_barang.setText(product.getKodeBarang());
        holder.nama_barang.setText(product.getNamaBarang());
//        holder.ukuran.setText(product.getUkuran());
        holder.meter.setText(("(")+product.getMeter()+("Pcs)"));

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView kode_barang;
        TextView nama_barang;
        TextView ukuran;
        TextView meter;
        View view;

        public ProductViewHolder(View itemView) {
            super(itemView);
            view = itemView;

            kode_barang = itemView.findViewById(R.id.kode_barang);
            nama_barang = itemView.findViewById(R.id.nama_barang);
            ukuran = itemView.findViewById(R.id.ukuran);
            meter = itemView.findViewById(R.id.meter);
        }
    }
}
