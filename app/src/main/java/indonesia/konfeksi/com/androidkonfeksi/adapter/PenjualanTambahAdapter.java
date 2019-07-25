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

import indonesia.konfeksi.com.androidkonfeksi.Interface.RecyclerViewClickListener;
import indonesia.konfeksi.com.androidkonfeksi.R;
import indonesia.konfeksi.com.androidkonfeksi.json.ProductPenjualanBarang;

public class PenjualanTambahAdapter extends RecyclerView.Adapter<PenjualanTambahAdapter.ProductViewHolder> {

    private Activity mCtx;
    private List<ProductPenjualanBarang> productList;
    private NumberFormat formatRupiah;


    private static RecyclerViewClickListener itemListener;

    public PenjualanTambahAdapter(Activity mCtx, List<ProductPenjualanBarang> productList, RecyclerViewClickListener itemListener) {
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

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {

        View view;

        public ProductViewHolder(View itemView) {
            super(itemView);
            view = itemView;
        }
    }
}

