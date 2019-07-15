package indonesia.konfeksi.com.androidkonfeksi.json;

public class ProductIsiKonfirmasiKasir {

    private String id_detail_penjualan;
    private String id_penjualan;
    private String id_barang;
    private String id_varian_harga;
    private String qty;
    private String total_harga;
    private String ket;
    private String kode_barang;
    private String id_karyawan;
    private String diskon_persen;
    private String diskon_rupiah;
    private String nama_barang;
    private String kode_barcode;
    private String image;
    private String konsinasi;
    private String date_input;
    private String date_edit;
    private String ukuran;
    private String meter;
    private String warna;
    private String stok_jual;
    private String harga;

    public ProductIsiKonfirmasiKasir(String id_detail_penjualan, String id_penjualan, String id_barang, String id_varian_harga, String qty, String total_harga, String ket, String kode_barang, String id_karyawan, String diskon_persen, String diskon_rupiah, String nama_barang, String kode_barcode, String image, String konsinasi, String date_input, String date_edit, String ukuran, String meter, String warna, String stok_jual, String harga) {
        this.id_detail_penjualan = id_detail_penjualan;
        this.id_penjualan = id_penjualan;
        this.id_barang = id_barang;
        this.id_varian_harga = id_varian_harga;
        this.qty = qty;
        this.total_harga = total_harga;
        this.ket = ket;
        this.kode_barang = kode_barang;
        this.id_karyawan = id_karyawan;
        this.diskon_persen = diskon_persen;
        this.diskon_rupiah = diskon_rupiah;
        this.nama_barang = nama_barang;
        this.kode_barcode = kode_barcode;
        this.image = image;
        this.konsinasi = konsinasi;
        this.date_input = date_input;
        this.date_edit = date_edit;
        this.ukuran = ukuran;
        this.meter = meter;
        this.warna = warna;
        this.stok_jual = stok_jual;
        this.harga = harga;
    }

    public String getId_detail_penjualan() {
        return id_detail_penjualan;
    }

    public String getId_penjualan() {
        return id_penjualan;
    }

    public String getId_barang() {
        return id_barang;
    }

    public String getId_varian_harga() {
        return id_varian_harga;
    }

    public String getQty() {
        return qty;
    }

    public String getTotal_harga() {
        return total_harga;
    }

    public String getKet() {
        return ket;
    }

    public String getKode_barang() {
        return kode_barang;
    }

    public String getId_karyawan() {
        return id_karyawan;
    }

    public String getDiskon_persen() {
        return diskon_persen;
    }

    public String getDiskon_rupiah() {
        return diskon_rupiah;
    }

    public String getNama_barang() {
        return nama_barang;
    }

    public String getKode_barcode() {
        return kode_barcode;
    }

    public String getImage() {
        return image;
    }

    public String getKonsinasi() {
        return konsinasi;
    }

    public String getDate_input() {
        return date_input;
    }

    public String getDate_edit() {
        return date_edit;
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

    public String getHarga() {
        return harga;
    }
}
