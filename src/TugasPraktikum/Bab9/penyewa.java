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
public class penyewa implements DataPenyewa {
    String nama, noTelepon, alatDisewa, tanggalSewa, lamaSewa, alamat;

    public void dataNama(String Nama) { 
        this.nama = Nama; 
    }
    public void dataNoTelepon(String noHp) { 
        this.noTelepon = noHp; 
    }
    public void dataAlat(String alat) { this.alatDisewa = alat;
    
    }
    public void dataTanggal(String tgl) { 
        this.tanggalSewa = tgl;
    }
    public void dataLama(String lama) { 
        this.lamaSewa = lama; 
    }
    public void dataAlamat(String alamat) { 
        this.alamat = alamat;
    }
    public String[] toRow() {
        return new String[]{nama, noTelepon, alatDisewa, tanggalSewa, lamaSewa, alamat};
    }
}