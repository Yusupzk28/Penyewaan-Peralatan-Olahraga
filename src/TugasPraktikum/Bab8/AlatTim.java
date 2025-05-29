/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TugasPraktikum.Bab8;

/**
 *
 * @author ACER
 */
public class AlatTim extends PeralatanOlahraga {
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

    @Override
    public void tampilkanInfo(String tambahan) {
        System.out.println("[TIM - " + tambahan + "]");
        tampilkanInfo();
    }

    @Override
    public String[] toRow() {
        return new String[]{"Alat Tim", getNama(), getJenis(), String.valueOf(getStok())};
    }
}
