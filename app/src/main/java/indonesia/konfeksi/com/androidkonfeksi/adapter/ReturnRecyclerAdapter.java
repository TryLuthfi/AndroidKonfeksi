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
import indonesia.konfeksi.com.androidkonfeksi.activity.Return;
import indonesia.konfeksi.com.androidkonfeksi.json.ProductHistoryPenjualan;

public class ReturnRecyclerAdapter extends RecyclerView.Adapter<ReturnRecyclerAdapter.ProductViewHolder> {

    private Activity mCtx;
    private List<ProductHistoryPenjualan> productList;

    public ReturnRecyclerAdapter(Activity mCtx, List<ProductHistoryPenjualan> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_recycler_dialog, parent, false);;

        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, final int position) {
        final ProductHistoryPenjualan product = productList.get(position);
        holder.kode_barang.setText(product.getNo_faktur());

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView kode_barang;
        View view;

        public ProductViewHolder(View itemView) {
            super(itemView);
            view = itemView;

            kode_barang = itemView.findViewById(R.id.kode_barang);
        }
    }
}