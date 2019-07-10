package indonesia.konfeksi.com.androidkonfeksi.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import indonesia.konfeksi.com.androidkonfeksi.R;
import indonesia.konfeksi.com.androidkonfeksi.json.ProductPenjualan;

public class PenjualanAdapter extends RecyclerView.Adapter<PenjualanAdapter.PenjualanViewHolder> {


    private ArrayList<ProductPenjualan> dataList;

    public PenjualanAdapter(ArrayList<ProductPenjualan> dataList) {
        this.dataList = dataList;
    }

    @Override
    public PenjualanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.listpenjualan, parent, false);
        return new PenjualanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PenjualanViewHolder holder, int position) {
        holder.kodeBarang.setText(dataList.get(position).getKode_Barang());
        holder.namaBarang.setText(dataList.get(position).getNama_Barang());
        holder.Harga.setText(dataList.get(position).getHarga_Barang());
        holder.Qty.setText(dataList.get(position).getQty_Barang());
        holder.subTotal.setText(dataList.get(position).getSubTotal_Barang());
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class PenjualanViewHolder extends RecyclerView.ViewHolder{
        private TextView kodeBarang, namaBarang, Harga, Qty, subTotal;

        public PenjualanViewHolder(View itemView) {
            super(itemView);
            kodeBarang = (TextView) itemView.findViewById(R.id.kode_barang);
            namaBarang = (TextView) itemView.findViewById(R.id.nama_barang);
            Harga = (TextView) itemView.findViewById(R.id.harga);
            Qty = (TextView) itemView.findViewById(R.id.qty);
            subTotal = (TextView) itemView.findViewById(R.id.sub_total);
        }
    }
}