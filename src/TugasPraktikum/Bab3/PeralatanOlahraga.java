/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TugasPraktikum.Bab3;

/**
 *
 * @author ACER
 */
// Class PeralatanOlahraga digunakan sebagai induk
public class PeralatanOlahraga {
    // Atribut umum yang dimiliki semua peralatan olahraga
    // Menyimpan nama, dan jenis alat
    String nama, jenis;  
    // Menyimpan jumlah stok alat yang tersedia
    int stok;      

    // Method untuk menampilkan informasi peralatan
    public void tampilkanInfo() {
        // Menampilkan nama alat
        System.out.println("Nama Alat : " + nama);
        // Menampilkan jenis alat
        System.out.println("Jenis     : " + jenis);
        // Menampilkan jumlah stok
        System.out.println("Stok      : " + stok);
    }
}