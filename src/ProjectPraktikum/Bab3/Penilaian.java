/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProjectPraktikum.Bab3;

/**
 *
 * @author ACER
 */
public class Penilaian {
    String NIM,nama,kode_mk;
    int NP1,NP2,NP3,NilaiPrak,UTS,UAS;
    double nilaiProses(){
        return ((NP1*0.1)+(NP2*0.2)+(NP3*0.1)+(UTS*0.2)+(NilaiPrak*0.4));           
    }
    double nilaiAkhir(){
       return  (nilaiProses()*0.6) +(UAS*0.3);
    }
    double tampilNA(){
        return nilaiAkhir();
    }
    double nilaiKeaktifan(){
        return 0;   
    }
}

