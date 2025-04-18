/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProjectPraktikum.Bab2;

/**
 *
 * @author ACER
 */
public class Data_Matkul {
    String kode_mk;
    String nama_mk;
    String dosen_pengampu;
    int jml_sks;

    // Konstruktor
    public Data_Matkul(String kd, String mk, String dsn, int jmlsks) {
        this.kode_mk = kd;
        this.nama_mk = mk;
        this.dosen_pengampu = dsn;
        this.jml_sks = jmlsks;
    }
}

