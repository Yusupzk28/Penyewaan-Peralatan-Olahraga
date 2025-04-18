/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TugasPraktikum.Bab2;

/**
 *
 * @author ACER
 */
// Class data_alat menyimpan informasi tentang peralatan olahraga
public class Alat {
    // Atribut untuk menyimpan nama alat, jenis alat, dan jumlah stok
    String namaAlat, jenisAlat;
    int stok;

    // Constructor dengan Parameter(nama alat, jenis alat, jumlah stok)
    public Alat(String nama, String jenis, int stok) {
        this.namaAlat = nama;     // Menyimpan nama alat ke atribut namaAlat
        this.jenisAlat = jenis;   // Menyimpan jenis alat ke atribut jenisAlat
        this.stok = stok;         // Menyimpan jumlah stok ke atribut stok
    }

    // Method untuk mengambil nilai nama alat
    public String getNamaAlat() {
        return namaAlat;
    }
    // Method untuk mengambil nilai jenis alat
    public String getJenisAlat() {
        return jenisAlat;
    }
    // Method untuk mengambil nilai stok
    public int getStok() {
        return stok;
    }

    // Method untuk mengubah nilai stok dengan Parameter (stok)
    public void setStok(int stok) {
        this.stok = stok;
    }
}