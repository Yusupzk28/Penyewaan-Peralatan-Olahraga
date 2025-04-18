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
// Class AlatIndividu merupakan subclass dari PeralatanOlahraga
public class AlatIndividu extends PeralatanOlahraga {
    // Method override dari superclass untuk menampilkan informasi alat
    @Override
    public void tampilkanInfo() {
        // Memanggil method tampilkanInfo() dari class induk (superclass)
        super.tampilkanInfo();
    }

    // Mengembalikan informasi alat dalam bentuk String untuk ditampilkan di JTextArea
    public String getInfoLengkap() {
        // Mengambil dan menampilkan atribut nama, jenis, dan stok
        return "Nama Alat : " + nama +         
             "\nJenis     : " + jenis +        
             "\nStok      : " + stok;          
    }
}