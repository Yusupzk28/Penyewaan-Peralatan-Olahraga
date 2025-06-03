/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TugasPraktikum.Bab9;

/**
 *
 * @author ACER
 */
public interface DataPenyewa {
    void dataNama(String Nama);
    void dataNoTelepon(String noHp);
    void dataAlat(String alat);
    void dataTanggal(String tgl);
    void dataLama(String lama);
    void dataAlamat(String alamat);
    String[] toRow();
}

