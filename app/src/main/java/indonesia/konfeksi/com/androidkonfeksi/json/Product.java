package indonesia.konfeksi.com.androidkonfeksi.json;

public class Product {

    private String id_barang;
    private String kode_barang;
    private String kode_barcode;
    private String nama_barang;
    private String harga_barang;

    public Product(String id_barang, String kode_barang, String kode_barcode, String nama_barang, String harga_barang) {
        this.id_barang = id_barang;
        this.kode_barang = kode_barang;
        this.kode_barcode = kode_barcode;
        this.nama_barang = nama_barang;
        this.harga_barang = harga_barang;
    }

    public String getId_barang() {
        return id_barang;
    }

    public String getKode_barang() {
        return kode_barang;
    }

    public String getKode_barcode() {
        return kode_barcode;
    }

    public String getNama_barang() {
        return nama_barang;
    }

    public String getHarga_barang() {
        return harga_barang;
    }


}
