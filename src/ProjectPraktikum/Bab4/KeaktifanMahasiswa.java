/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProjectPraktikum.Bab4;

/**
 *
 * @author ACER
 */
public class KeaktifanMahasiswa extends Penilaian{
    int nilai_keaktifan;
    public KeaktifanMahasiswa() {
        this.nilai_keaktifan = 0;
    }

    @Override
    public double nilaiKeaktifan() {
        return ((nilai_keaktifan * 0.1) + nilaiAkhir());
    }
    
}