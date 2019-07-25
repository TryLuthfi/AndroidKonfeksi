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
    private int qty;
    private int subTotal;

    public ProductPenjualanBarang(String idBarang, String kodeBarang, String namaBarang, String diskonPersen, String diskonRupiah, String idVarianHarga, String ukuran, String meter, String warna, String stokJual, String harga, int qty, int subTotal) {
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
        this.qty = qty;
        this.subTotal = subTotal;
    }

    public String getIdBarang() {
        return idBarang;
    }

    public String getKodeBarang() {
        return kodeBarang;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public String getDiskonPersen() {
        return diskonPersen;
    }

    public String getDiskonRupiah() {
        return diskonRupiah;
    }

    public String getIdVarianHarga() {
        return idVarianHarga;
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

    public String getStokJual() {
        return stokJual;
    }

    public String getHarga() {
        return harga;
    }

    public int getQty() {
        return qty;
    }

    public int getSubTotal() {
        return subTotal;
    }
}
