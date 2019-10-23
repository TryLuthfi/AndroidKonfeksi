package indonesia.konfeksi.com.androidkonfeksi.adapter;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
        holder.kode_barang.setText(product.getKodeBarcodeVarian());
        holder.nama_barang.setText(product.getNamaBarang());
        holder.satuan.setText(product.getSatuan());
        holder.meter.setText(("(")+product.getMeter()+("Pcs)"));
        holder.qty.setVisibility(View.GONE);
        holder.txX.setVisibility(View.GONE);
        holder.txHarga.setVisibility(View.VISIBLE);
        String textHarga = "Rp. " + product.getHarga();
        if(Integer.parseInt(product.getHarga()) < Integer.parseInt(product.getHargaAwal())){
            textHarga = "Rp. " + product.getHarga() + ", Disc : <b>Rp. " + (Integer.parseInt(product.getHargaAwal()) - Integer.parseInt(product.getHarga())) + "</b>";
        }
        holder.harga.setText(Html.fromHtml(textHarga));
        holder.harga.setTypeface(null, Typeface.BOLD);
        holder.subTotal.setVisibility(View.GONE);
        holder.txSubTotal.setVisibility(View.GONE);
        holder.cStok.setVisibility(View.VISIBLE);
        holder.stok.setText(product.getStokGudang());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {

        View view;
        TextView kode_barang;
        TextView nama_barang;
        TextView satuan;
        TextView meter;
        TextView qty;
        TextView txX;
        TextView txHarga;
        TextView harga;
        TextView subTotal;
        TextView txSubTotal;
        LinearLayout cStok;
        TextView stok;

        public ProductViewHolder(View itemView) {
            super(itemView);
            view = itemView;

            kode_barang = itemView.findViewById(R.id.kode_barang);
            nama_barang = itemView.findViewById(R.id.nama_barang);
            satuan = itemView.findViewById(R.id.satuan);
            meter = itemView.findViewById(R.id.meter);
            qty = itemView.findViewById(R.id.qty);
            txX = itemView.findViewById(R.id.tx_x);
            txHarga = itemView.findViewById(R.id.tx_harga);
            harga = itemView.findViewById(R.id.harga);
            subTotal = itemView.findViewById(R.id.sub_total);
            txSubTotal = itemView.findViewById(R.id.tx_sub_total);
            cStok = itemView.findViewById(R.id.c_stok);
            stok = itemView.findViewById(R.id.stok);
        }
    }
}
