/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TugasPraktikum.Bab6;

/**
 *
 * @author ACER
 */
// SubClass dari PeralatanOlahraga
public class AlatTim extends PeralatanOlahraga {
    public AlatTim() {}

    public AlatTim(String nama, String jenis, int stok) {
        super(nama, jenis, stok);
    }

    @Override
    public void tampilkanInfo() {
        System.out.println("[TIM]");
        System.out.println("Nama Alat : " + getNama());
        System.out.println("Jenis     : " + getJenis());
        System.out.println("Stok      : " + getStok());
    }

    public void tampilkanInfo(String tambahan) {
        System.out.println("[TIM - " + tambahan + "]");
        tampilkanInfo();
    }

    public String[] toRow() {
        return new String[]{"Tim", getNama(), getJenis(), String.valueOf(getStok())};
    }
}
