package indonesia.konfeksi.com.androidkonfeksi.json;

public class ProductPenjualan {
    private String kode_Barang;
    private String nama_Barang;
    private String harga_Barang;
    private String qty_Barang;
    private String subTotal_Barang;

    public ProductPenjualan(String kode_Barang, String nama_Barang, String harga_Barang, String qty_Barang, String subTotal_Barang) {
        this.kode_Barang = kode_Barang;
        this.nama_Barang = nama_Barang;
        this.harga_Barang = harga_Barang;
        this.qty_Barang = qty_Barang;
        this.subTotal_Barang = subTotal_Barang;
    }

    public String getKode_Barang() {
        return kode_Barang;
    }

    public String getNama_Barang() {
        return nama_Barang;
    }

    public String getHarga_Barang() {
        return harga_Barang;
    }

    public String getQty_Barang() {
        return qty_Barang;
    }

    public String getSubTotal_Barang() {
        return subTotal_Barang;
    }
}