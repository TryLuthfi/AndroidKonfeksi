package indonesia.konfeksi.com.androidkonfeksi;

public class Product {
    private String kode_barang;
    private String kode_barcode;

    public Product(String kode_barang, String kode_barcode) {
        this.kode_barang = kode_barang;
        this.kode_barcode = kode_barcode;
    }

    public String getKode_barang() {
        return kode_barang;
    }

    public String getKode_barcode() {
        return kode_barcode;
    }
}
