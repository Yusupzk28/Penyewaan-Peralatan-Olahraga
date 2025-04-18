/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TugasPraktikum.Bab5;

/**
 *
 * @author ACER
 */
// SuperClass
public class PeralatanOlahraga {
    // Atribut 
    // Untuk menyimpan nama peralatan
    private String nama;
    // Untuk menyimpan jenis peralatan
    private String jenis;
    // Untuk menyimpan jumlah stok peralatan
    private int stok;

    // Constructor kosong
    public PeralatanOlahraga() {}

    // Overloading constructor dengan parameter untuk menginisialisasi atribut
    public PeralatanOlahraga(String nama, String jenis, int stok) {
        // Menetapkan nilai nama ke atribut nama
        this.nama = nama; 
        // Menetapkan nilai jenis ke atribut jenis
        this.jenis = jenis; 
        // Menetapkan nilai stok ke atribut stok
        this.stok = stok; 
    }

    // Setter 
    // Untuk mengatur nama peralatan
    public void setNama(String nama) { 
        // Menetapkan nilai nama ke atribut nama
        this.nama = nama;
    }
    // Untuk mengatur jenis peralatan
    public void setJenis(String jenis) { 
        this.jenis = jenis; // Menetapkan nilai jenis ke atribut jenis
    }
    // Untuk mengatur jumlah stok peralatan
    public void setStok(int stok) { 
        this.stok = stok; // Menetapkan nilai stok ke atribut stok
    }
    
    // Getter
    // Untuk mendapatkan nama peralatan
    public String getNama() { 
        // Mengembalikan nilai atribut nama
        return nama; 
    }
    // Untuk mendapatkan jenis peralatan
    public String getJenis() { 
        // Mengembalikan nilai atribut jenis
        return jenis; 
    }
    // Untuk mendapatkan jumlah stok peralatan
    public int getStok() { 
        // Mengembalikan nilai atribut stok
        return stok; 
    }

    // Method untuk menampilkan informasi umum peralatan
    public void tampilkanInfo() {
        // Menampilkan nama peralatan
        System.out.println("Nama Alat : " + nama); 
        // Menampilkan jenis peralatan
        System.out.println("Jenis     : " + jenis); 
        // Menampilkan jumlah stok peralatan
        System.out.println("Stok      : " + stok); 
    }

    // Overloading Method untuk menampilkan informasi dengan tambahan string
    public void tampilkanInfo(String tambahan) {
        // Menampilkan tambahan informasi
        System.out.println(tambahan); 
        // Memanggil Method  tampilkanInfo() untuk menampilkan informasi umum
        tampilkanInfo(); 
    }
}