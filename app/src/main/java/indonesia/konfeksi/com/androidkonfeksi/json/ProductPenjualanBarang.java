package indonesia.konfeksi.com.androidkonfeksi.json;

public class ProductPenjualanBarang {
    private String idBarang;
    private String kodeBarang;
    private String namaBarang;
    private String diskonPersen;
    private String diskonRupiah;
    private String idVarianHarga;
    private String ukuran;
    private String meter;
    private String warna;
    private String stokJual;
    private String harga;

    public ProductPenjualanBarang(String idBarang, String kodeBarang, String namaBarang, String diskonPersen, String diskonRupiah, String idVarianHarga, String ukuran, String meter, String warna, String stokJual, String harga) {
        this.idBarang = idBarang;
        this.kodeBarang = kodeBarang;
        this.namaBarang = namaBarang;
        this.diskonPersen = diskonPersen;
        this.diskonRupiah = diskonRupiah;
        this.idVarianHarga = idVarianHarga;
        this.ukuran = ukuran;
        this.meter = meter;
        this.warna = warna;
        this.stokJual = stokJual;
        this.harga = harga;
    }

    public String getIdBarang() {
        return idBarang;
    }

    public void setIdBarang(String idBarang) {
        this.idBarang = idBarang;
    }

    public String getKodeBarang() {
        return kodeBarang;
    }

    public void setKodeBarang(String kodeBarang) {
        this.kodeBarang = kodeBarang;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public String getDiskonPersen() {
        return diskonPersen;
    }

    public void setDiskonPersen(String diskonPersen) {
        this.diskonPersen = diskonPersen;
    }

    public String getDiskonRupiah() {
        return diskonRupiah;
    }

    public void setDiskonRupiah(String diskonRupiah) {
        this.diskonRupiah = diskonRupiah;
    }

    public String getIdVarianHarga() {
        return idVarianHarga;
    }

    public void setIdVarianHarga(String idVarianHarga) {
        this.idVarianHarga = idVarianHarga;
    }

    public String getUkuran() {
        return ukuran;
    }

    public void setUkuran(String ukuran) {
        this.ukuran = ukuran;
    }

    public String getMeter() {
        return meter;
    }

    public void setMeter(String meter) {
        this.meter = meter;
    }

    public String getWarna() {
        return warna;
    }

    public void setWarna(String warna) {
        this.warna = warna;
    }

    public String getStokJual() {
        return stokJual;
    }

    public void setStokJual(String stokJual) {
        this.stokJual = stokJual;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }
}
