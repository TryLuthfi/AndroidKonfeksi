package indonesia.konfeksi.com.androidkonfeksi.json;

public class VProduct {

    private String id_barang;
    private String kode_barang;
    private String kode_barcode;
    private String nama_barang;
    private String diskon_persen;
    private String diskon_rupiah;
    private String ukuran;
    private String meter;
    private String warna;
    private String stok_jual;
    private String date_input;
    private String date_edit;
    private String harga_barang;

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

    public String getDiskon_persen() {
        return diskon_persen;
    }

    public String getDiskon_rupiah() {
        return diskon_rupiah;
    }

    public String getUkuran() {
        return ukuran;
    }

    public String getMeter() {
        return meter;
    }

    public String getWarna() {
        return warna;
    }

    public String getStok_jual() {
        return stok_jual;
    }

    public String getDate_input() {
        return date_input;
    }

    public String getDate_edit() {
        return date_edit;
    }

    public String getHarga_barang() {
        return harga_barang;
    }

    public VProduct(String id_barang, String kode_barang, String kode_barcode, String nama_barang, String diskon_persen, String diskon_rupiah, String ukuran, String meter, String warna, String stok_jual, String date_input, String date_edit, String harga_barang) {
        this.id_barang = id_barang;
        this.kode_barang = kode_barang;
        this.kode_barcode = kode_barcode;
        this.nama_barang = nama_barang;
        this.diskon_persen = diskon_persen;
        this.diskon_rupiah = diskon_rupiah;
        this.ukuran = ukuran;
        this.meter = meter;
        this.warna = warna;
        this.stok_jual = stok_jual;
        this.date_input = date_input;
        this.date_edit = date_edit;
        this.harga_barang = harga_barang;
    }
}
