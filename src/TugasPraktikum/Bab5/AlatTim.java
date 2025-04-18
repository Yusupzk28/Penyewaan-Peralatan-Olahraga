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
public class AlatTim extends PeralatanOlahraga {
    // Override method tampilkanInfo() dari superclass
    @Override
    public void tampilkanInfo() {
        // Menampilkan String TIm
        System.out.println("[TIM]"); 
        // Memanggil method tampilkanInfo() dari superclass
        super.tampilkanInfo(); 
    }

    // Overloading method tampilkanInfo() dengan parameter tambahan
    public void tampilkanInfo(String tambahan) {
        // Menampilkan String Tim dengan informasi tambahan
        System.out.println("[TIM - " + tambahan + "]");
        // Memanggil method tampilkanInfo() dari superclass
        super.tampilkanInfo(); 
    }

    // Method untuk mengembalikan informasi dalam bentuk array 
    public String[] toRow() {
        // Mengembalikan array yang berisi informasi alat tim
        return new String[]{"Tim", getNama(), getJenis(), String.valueOf(getStok())}; 
    }
}