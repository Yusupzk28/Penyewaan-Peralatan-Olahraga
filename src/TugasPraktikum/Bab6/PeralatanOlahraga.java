/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TugasPraktikum.Bab6;

/**
 *
 * @author ACER
 */
// File: PeralatanOlahraga.java
public abstract class PeralatanOlahraga {
    private String nama;
    private String jenis;
    private int stok;

    public PeralatanOlahraga() {}

    public PeralatanOlahraga(String nama, String jenis, int stok) {
        this.nama = nama;
        this.jenis = jenis;
        this.stok = stok;
    }

    public void setNama(String nama) { 
        this.nama = nama; 
    }
    public void setJenis(String jenis) {
        this.jenis = jenis; 
    }
    public void setStok(int stok) {
        this.stok = stok; 
    }

    public String getNama() {
        return nama; 
    }
    public String getJenis() {
        return jenis; 
    }
    public int getStok() {
        return stok; 
    }

    public abstract void tampilkanInfo();

    public void tampilkanInfo(String tambahan) {
        System.out.println(tambahan);
        tampilkanInfo();
    }
}
