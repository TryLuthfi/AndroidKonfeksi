package indonesia.konfeksi.com.androidkonfeksi.konfigurasi;

public class konfigurasi {
    public static final String LOGIN_URL = "https://nuruddinhady.com/laris32/api/login.php";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String LOGIN_SUCCESS = "success";

    public static final String masterUrl = "https://nuruddinhady.com/";

    public static final String URL_GET_BARANG = masterUrl + "laris32/api/databarang.php";
    public static final String URL_GET_AMBIL_BARANG = masterUrl + "laris32/api/ambilbarang";
    public static final String URL_GET_HISTORYPENJUALAN = masterUrl + "laris32/api/historypenjualan";
    public static final String URL_GET_HISTORYPEMBELIAN = masterUrl + "laris32/api/historypembelian";
    public static final String URL_GET_NONOTA = masterUrl + "laris32/api/nonota";
    public static final String URL_GET_NONOTAPENJUALAN = masterUrl + "laris32/api/nonotapenjualan";
    public static final String URL_GET_LISTKARYAWAN = masterUrl + "laris32/api/get_karyawan";
    public static final String URL_TAMBAH_PEMBELIAN = masterUrl + "laris32/api/pembelian";
    public static final String URL_TAMBAH_PENJUALAN = masterUrl + "laris32/api/testpenjualan";
    public static final String URL_KONFIRMASI_KASIR = masterUrl + "laris32/api/konfirmasibayar";
    public static final String URL_DETAIL_KONFIRMASI_KASIR = masterUrl + "laris32/api/detailhistory/";
    public static final String URL_UPDATE_STATUS = masterUrl + "laris32/api/updatestatus.php";

    // New Api
    public static final String url_nota_penjualan = masterUrl + "laris32/Penjualan/nota";
    public static final String url_karyawan_penjualan = masterUrl + "laris32/Penjualan/karyawan";
    public static final String url_pelanggan_penjualan = masterUrl + "laris32/Penjualan/pelanggan";
    public static final String url_potongan_pelanggan_penjualan = masterUrl + "laris32/Penjualan/getDiskon";
    public static final String url_top_pelanggan_penjualan = masterUrl + "laris32/Penjualan/getTop";
    public static final String url_barang_penjualan = masterUrl + "laris32/Penjualan/getDataBarang";
    public static final String url_simpan_penjualan = masterUrl + "laris32/Penjualan/simpanTransaksi";

    public static final String url_kasir_list_pos = masterUrl + "laris32/Penjualan/get_penjualan_crud";

    public static final String url_diskon_pelanggan_penjualan = masterUrl + "laris32/Penjualan/getDiskon";
    public static final String url_keuangan_penjualan = masterUrl + "laris32/Penjualan/keuangan";

}