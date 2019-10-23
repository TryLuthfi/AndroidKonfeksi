package indonesia.konfeksi.com.androidkonfeksi.json;


public class ProductKonfirmasiKasir {

    private String id_penjualan;
    private String id_karyawan;
    private String nama_karyawan;
    private String id_pelanggan;
    private String nama_pelanggan;
    private String date;
    private String time;
    private String no_nota;
    private String total_harga;
    private String biaya;
    private String biaya_debit;
    private String selisih;
    private String id_karyawan_pengambil;
    private String status;
    private String tgl_jatuh_tempo;


    public ProductKonfirmasiKasir(
            String id_penjualan,
            String id_karyawan,
            String nama_karyawan,
            String id_pelanggan,
            String nama_pelanggan,
            String date,
            String time,
            String no_nota,
            String total_harga,
            String biaya,
            String biaya_debit,
            String selisih,
            String id_karyawan_pengambil,
            String status,
            String tgl_jatuh_tempo) {
        this.id_penjualan = id_penjualan;
        this.id_karyawan = id_karyawan;
        this.nama_karyawan = nama_karyawan;
        this.id_pelanggan = id_pelanggan;
        this.nama_pelanggan = nama_pelanggan;
        this.date = date;
        this.time = time;
        this.no_nota = no_nota;
        this.total_harga = total_harga;
        this.biaya = biaya;
        this.biaya_debit = biaya_debit;
        this.selisih = selisih;
        this.id_karyawan_pengambil = id_karyawan_pengambil;
        this.status = status;
        this.tgl_jatuh_tempo = tgl_jatuh_tempo;
    }

    public String getId_penjualan() {
        return id_penjualan;
    }

    public String getId_karyawan() {
        return id_karyawan;
    }

    public String getNama_karyawan() {
        return nama_karyawan;
    }

    public String getId_pelanggan() {
        return id_pelanggan;
    }

    public String getNama_pelanggan() {
        return nama_pelanggan;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getNo_nota() {
        return no_nota;
    }

    public String getTotal_harga() {
        return total_harga;
    }

    public String getBiaya() {
        return biaya;
    }

    public String getBiaya_debit() {
        return biaya_debit;
    }

    public String getSelisih() {
        return selisih;
    }

    public String getId_karyawan_pengambil() {
        return id_karyawan_pengambil;
    }

    public String getStatus() {
        return status;
    }

    public String getTgl_jatuh_tempo(){
        return tgl_jatuh_tempo;
    }
}

