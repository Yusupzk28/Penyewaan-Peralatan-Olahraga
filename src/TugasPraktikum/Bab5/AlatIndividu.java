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
// SubClass dari PeralatanOlahraga
public class AlatIndividu extends PeralatanOlahraga {
    // Override method tampilkanInfo() dari superclass
    @Override
    public void tampilkanInfo() {
        // Menampilkan String individu
        System.out.println("[INDIVIDU]"); 
        // Memanggil Method tampilkanInfo() dari superclass
        super.tampilkanInfo(); 
    }

    // Overloading method tampilkanInfo() dengan parameter tambahan
    public void tampilkanInfo(String tambahan) {
        // Menampilkan String Individu dengan informasi tambahan
        System.out.println("[INDIVIDU - " + tambahan + "]"); 
        // Memanggil Method tampilkanInfo() dari superclass 
        super.tampilkanInfo(); 
    }

    // Method untuk mengembalikan informasi dalam bentuk array String
    public String[] toRow() {
        // Mengembalikan array yang berisi informasi alat individu
        return new String[]{"Individu", getNama(), getJenis(), String.valueOf(getStok())}; 
    }
}