package indonesia.konfeksi.com.androidkonfeksi.json;

public class ProductHistoryPembelian {

    private String id_pembelian;
    private String date;
    private String time;
    private String total_harga;
    private String no_faktur;
    private String no_nota;
    private String biaya;
    private String id_karyawan;
    private String kode_karyawan;
    private String nama_karyawan;
    private String email_karyawan;
    private String id_supplier;
    private String nama_supplier;
    private String kode_supplier;

    public ProductHistoryPembelian(String id_pembelian, String date, String time, String total_harga, String no_faktur, String no_nota, String biaya, String id_karyawan, String kode_karyawan, String nama_karyawan, String email_karyawan, String id_supplier, String nama_supplier, String kode_supplier) {
        this.id_pembelian = id_pembelian;
        this.date = date;
        this.time = time;
        this.total_harga = total_harga;
        this.no_faktur = no_faktur;
        this.no_nota = no_nota;
        this.biaya = biaya;
        this.id_karyawan = id_karyawan;
        this.kode_karyawan = kode_karyawan;
        this.nama_karyawan = nama_karyawan;
        this.email_karyawan = email_karyawan;
        this.id_supplier = id_supplier;
        this.nama_supplier = nama_supplier;
        this.kode_supplier = kode_supplier;
    }

    public String getId_pembelian() {
        return id_pembelian;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getTotal_harga() {
        return total_harga;
    }

    public String getNo_faktur() {
        return no_faktur;
    }

    public String getNo_nota() {
        return no_nota;
    }

    public String getBiaya() {
        return biaya;
    }

    public String getId_karyawan() {
        return id_karyawan;
    }

    public String getKode_karyawan() {
        return kode_karyawan;
    }

    public String getNama_karyawan() {
        return nama_karyawan;
    }

    public String getEmail_karyawan() {
        return email_karyawan;
    }

    public String getId_supplier() {
        return id_supplier;
    }

    public String getNama_supplier() {
        return nama_supplier;
    }

    public String getKode_supplier() {
        return kode_supplier;
    }
}
