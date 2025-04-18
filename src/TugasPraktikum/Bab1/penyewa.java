/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TugasPraktikum.Bab1;

/**
 *
 * @author ACER
 */
// Class penyewa menyimpan data-data penyewa peralatan olahraga
public class penyewa {
    // Atribut untuk menyimpan informasi penyewa
    String nama, noTelepon, alatDisewa, tanggalSewa, lamaSewa, alamat;

    // Method untuk mengisi nama penyewa
    void dataNama(String Nama) {
        this.nama = Nama;
    }
    // Method untuk mengisi nomor telepon penyewa
    void dataNoTelepon(String noHp) {
        this.noTelepon = noHp;
    }
    // Method untuk mengisi nama alat yang disewa
    void dataAlat(String alat) {
        this.alatDisewa = alat;
    }
    // Method untuk mengisi tanggal penyewaan
    void dataTanggal(String tgl) {
        this.tanggalSewa = tgl;
    }
    // Method untuk mengisi durasi atau lama penyewaan
    void dataLama(String lama) {
        this.lamaSewa = lama;
    }
    // Method untuk mengisi alamat penyewa
    void dataAlamat(String alamat) {
        this.alamat = alamat;
    }
    
    // Method untuk mencetak atau mengembalikan nama penyewa
    String cetakNama() {
        return nama;
    }
    // Method untuk mencetak nomor telepon penyewa
    String cetakNoTelepon() {
        return noTelepon;
    }
    // Method untuk mencetak alat yang disewa
    String cetakAlat() {
        return alatDisewa;
    }
    // Method untuk mencetak tanggal penyewaan
    String cetakTanggal() {
        return tanggalSewa;
    }
    // Method untuk mencetak lama/durasi penyewaan
    String cetakLama() {
        return lamaSewa;
    }
    // Method untuk mencetak alamat penyewa
    String cetakAlamat() {
        return alamat;
    }
}