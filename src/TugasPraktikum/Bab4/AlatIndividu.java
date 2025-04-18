/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TugasPraktikum.Bab4;

/**
 *
 * @author ACER
 */
// Subclass: alat olahraga individu
public class AlatIndividu extends PeralatanOlahraga {
    // Override method tampilkanInfo()
    @Override
    public void tampilkanInfo() {
         // Memanggil metode tampilkanInfo() dari superclass PeralatanOlahraga
        super.tampilkanInfo();
    }

    // Method untuk mendapatkan format informasi alat GUI
    public String getInfoLengkap() {
        // Mengembalikan string yang berisi informasi alat
        return "Nama Alat : " + getNama() + 
             "\nJenis     : " + getJenis() + 
             "\nStok      : " + getStok(); 
    }
}