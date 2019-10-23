package indonesia.konfeksi.com.androidkonfeksi.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import indonesia.konfeksi.com.androidkonfeksi.R;
import indonesia.konfeksi.com.androidkonfeksi.json.ProductPenjualanBarang;

public class PenjualanTambahAdapter extends RecyclerView.Adapter<PenjualanTambahAdapter.ProductViewHolder> {

    private Activity mCtx;
    private List<ProductPenjualanBarang> productList;
    private NumberFormat formatRupiah;

    public PenjualanTambahAdapter(Activity mCtx, List<ProductPenjualanBarang> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
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

        holder.kode_barang.setText(product.getKodeBarcodeVarian());
        holder.nama_barang.setText(product.getNamaBarang());
        holder.satuan.setText(product.getSatuan());
        holder.meter.setText(("(")+product.getMeter()+("Pcs)"));
        holder.qty.setText(Integer.toString(product.getQty()));
        String textHarga = "Rp. " + product.getHarga();
        if(Integer.parseInt(product.getHarga()) < Integer.parseInt(product.getHargaAwal())){
            textHarga = "Rp. " + product.getHarga() + ", Disc : <b>Rp. " + (Integer.parseInt(product.getHargaAwal()) - Integer.parseInt(product.getHarga())) + "</b>";
        }
        holder.harga.setText(Html.fromHtml(textHarga));
        holder.subTotal.setText("Rp. " + product.getSubTotal());
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
        TextView harga;
        TextView subTotal;

        public ProductViewHolder(View itemView) {
            super(itemView);
            view = itemView;

            kode_barang = itemView.findViewById(R.id.kode_barang);
            nama_barang = itemView.findViewById(R.id.nama_barang);
            satuan = itemView.findViewById(R.id.satuan);
            meter = itemView.findViewById(R.id.meter);
            qty = itemView.findViewById(R.id.qty);
            harga = itemView.findViewById(R.id.harga);
            subTotal = itemView.findViewById(R.id.sub_total);
        }
    }
}

