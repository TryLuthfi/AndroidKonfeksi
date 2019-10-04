package indonesia.konfeksi.com.androidkonfeksi.json;

public class ProductPenjualanBarang {

    private String kodeBarcodeVarian;
    private String namaBarang;
    private String satuan;
    private String meter;
    private String harga;
    private String warna;
    private String idVarianHarga;
    private String idBarang;
    private int qty;
    private int subTotal;

    /*private String kodeBarang;
    private String diskonPersen;
    private String diskonRupiah;
    private String stokJual;*/

    public ProductPenjualanBarang(
            String kodeBarcodeVarian,
            String namaBarang,
            String satuan,
            String meter,
            String harga,
            String warna,
            String idVarianHarga,
            String idBarang,
            int qty,
            int subTotal

            /*String kodeBarang,
            String diskonPersen,
            String diskonRupiah,
            String stokJual,
            */

            ) {
        this.kodeBarcodeVarian = kodeBarcodeVarian;
        this.namaBarang = namaBarang;
        this.satuan = satuan;
        this.meter = meter;
        this.harga = harga;
        this.warna = warna;
        this.idVarianHarga = idVarianHarga;
        this.idBarang = idBarang;
        this.qty = qty;
        this.subTotal = subTotal;

        /*this.kodeBarang = kodeBarang;
        this.diskonPersen = diskonPersen;
        this.diskonRupiah = diskonRupiah;
        this.stokJual = stokJual;
        */
    }

    public String getKodeBarcodeVarian() {
        return kodeBarcodeVarian;
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

    /*
    public String getKodeBarang() {
        return kodeBarang;
    }

    public String getDiskonPersen() {
        return diskonPersen;
    }
    public String getDiskonRupiah() {
        return diskonRupiah;
    }
    public String getStokJual() {
        return stokJual;
    }

    */
}
