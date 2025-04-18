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
// Class utama untuk mencetak struk penyewaan melalui tampilan CLI (Console)
public class CLI_CetakStruk {
    public static void main(String[] args) {
        // Membuat objek dari class penyewa
        penyewa p = new penyewa();

        // Menginisialisasi nama penyewa
        p.dataNama("Rizky Maulana");
        // Menginisialisasi nomor telepon penyewa
        p.dataNoTelepon("081234567890");
        // Menginisialisasi nama alat yang disewa
        p.dataAlat("Raket Badminton");
        // Menginisialisasi tanggal penyewaan
        p.dataTanggal("11 April 2025");
        // Menginisialisasi lama penyewaan
        p.dataLama("2 Hari");
        // Menginisialisasi alamat penyewa
        p.dataAlamat("Jl. Kemerdekaan No. 45");

        // Menampilkan output struk penyewaan
        System.out.println("STRUK PENYEWAAN PERALATAN OLAHRAGA");
        System.out.println("-----------------------------------");
        // Menampilkan nama penyewa
        System.out.println("Nama       : " + p.cetakNama());
        // Menampilkan nomor telepon
        System.out.println("No. Telepon: " + p.cetakNoTelepon());
        // Menampilkan alat yang disewa
        System.out.println("Alat Disewa: " + p.cetakAlat());
        // Menampilkan tanggal sewa
        System.out.println("Tanggal    : " + p.cetakTanggal());
        // Menampilkan durasi sewa
        System.out.println("Durasi     : " + p.cetakLama());
        // Menampilkan alamat penyewa
        System.out.println("Alamat     : " + p.cetakAlamat());
    }
}