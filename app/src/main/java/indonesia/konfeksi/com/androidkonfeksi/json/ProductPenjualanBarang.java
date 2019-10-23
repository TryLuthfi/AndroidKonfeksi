package indonesia.konfeksi.com.androidkonfeksi.json;

public class ProductPenjualanBarang {

    private String kodeBarcodeVarian;
    private String kodeBarang;
    private String namaBarang;
    private String satuan;
    private String meter;
    private String hargaAwal;
    private String harga;
    private String warna;
    private String idVarianHarga;
    private String idBarang;
    private int qty;
    private int subTotal;
    private String stokGudang;

    public ProductPenjualanBarang(
            String kodeBarcodeVarian,
            String kodeBarang,
            String namaBarang,
            String satuan,
            String meter,
            String hargaAwal,
            String harga,
            String warna,
            String idVarianHarga,
            String idBarang,
            int qty,
            int subTotal,
            String stokGudang
            ) {
        this.kodeBarcodeVarian = kodeBarcodeVarian;
        this.kodeBarang = kodeBarang;
        this.namaBarang = namaBarang;
        this.satuan = satuan;
        this.meter = meter;
        this.hargaAwal = hargaAwal;
        this.harga = harga;
        this.warna = warna;
        this.idVarianHarga = idVarianHarga;
        this.idBarang = idBarang;
        this.qty = qty;
        this.subTotal = subTotal;
        this.stokGudang = stokGudang;
    }

    public String getKodeBarcodeVarian() {
        return kodeBarcodeVarian;
    }
    public String getKodeBarang() {
        return kodeBarang;
    }
    public String getNamaBarang() {
        return namaBarang;
    }
    public String getSatuan() {
        return satuan;
    }
    public String getMeter() {
        return meter;
    }
    public String getHargaAwal() {
        return hargaAwal;
    }
    public String getHarga() {
        return harga;
    }
    public String getWarna() {
        return warna;
    }
    public String getIdVarianHarga() {
        return idVarianHarga;
    }
    public String getIdBarang() {
        return idBarang;
    }
    public int getQty() {
        return qty;
    }
    public int getSubTotal() {
        return subTotal;
    }
    public String getStokGudang(){
        return stokGudang;
    }

    public void setHarga(String harga){
        this.harga = harga;
    }
    public void setQty(int qty) {
        this.qty = qty;
    }
    public void setSubTotal(int subTotal) {
        this.subTotal = subTotal;
    }

}
