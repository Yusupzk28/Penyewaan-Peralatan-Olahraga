/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TugasPraktikum.Bab10;

/**
 *
 * @author ACER
 */
// Mengimpor package java.sql untuk koneksi dan operasi database
import java.sql.*;
// Mengimpor ArrayList dari Java Collection Framework
import java.util.ArrayList;
// Mengimpor Logger untuk mencatat log kesalahan
import java.util.logging.Level;
import java.util.logging.Logger;
// Mengimpor package javax.swing untuk membuat GUI
import javax.swing.*;
// Mengimpor DefaultTableModel untuk pengelolaan data tabel
import javax.swing.table.DefaultTableModel;
public class GUI_Penyewa extends javax.swing.JFrame {
    /**
     * Creates new form GUI_Sewa
     */
    public GUI_Penyewa() {
        initComponents();
        tampil();
    }
    // Mendeklarasikan variabel conn untuk koneksi ke database
    public Connection conn;
    // Menyimpan nilai ID penyewa lama dalam bentuk string
    String idPenyewaLama = "";
    // Membuat list untuk menyimpan ID dalam bentuk integer
    ArrayList<Integer> listID = new ArrayList<>();
    public void koneksi() throws SQLException {
        try {
            // Inisialisasi koneksi dengan null
            conn = null;
            // Memuat driver JDBC untuk MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Membuka koneksi ke database MySQL 
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tpoop_2318038?serverTimezone=UTC",
                "root",
                ""
            );
        // Menangani error jika driver tidak ditemukan atau gagal koneksi
        } catch (ClassNotFoundException | SQLException e) {
            // Menampilkan log error
    Logger.getLogger(GUI_Penyewa.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    public void tampil(){
        // Membuat model tabel baru
        DefaultTableModel model = new DefaultTableModel();
        // Menambahkan kolom "Nama" ke model tabel
        model.addColumn("Nama");
        // Menambahkan kolom "No. Telepon" ke model tabel
        model.addColumn("No. Telepon");
        // Menambahkan kolom "Alamat" ke model tabel
        model.addColumn("Alamat");
        try {
            // Memanggil metode koneksi ke database
            koneksi();
            // Membuat perintah SQL untuk mengambil semua data dari tabel tb_penyewa
            String sql = "SELECT * FROM tb_penyewa";
            // Membuat objek Statement untuk menjalankan perintah SQL
            Statement st = conn.createStatement();
            // Menjalankan perintah SQL dan menyimpan hasilnya dalam ResultSet
            ResultSet rs = st.executeQuery(sql);
            // Menghapus semua isi listID agar tidak menyimpan ID ganda
            listID.clear();
            // Iterasi untuk setiap baris hasil query
            while (rs.next()) {
                // Membuat array objek untuk menyimpan satu baris data
                Object[] row = new Object[3];
                // Mengisi array dengan nama
                row[0] = rs.getString("nama");
                // Mengisi array dengan nomor telepon
                row[1] = rs.getString("notelp");
                // Mengisi array dengan alamat
                row[2] = rs.getString("alamat");
                // Menambahkan baris data ke dalam model tabel
                model.addRow(row);
                // Menyimpan ID penyewa ke dalam listID
                listID.add(rs.getInt("id_penyewa"));
            }
            // Menetapkan model tabel ke komponen JTable
            Table.setModel(model);

        // Menangkap error 
        } catch (Exception e) {
            // Menampilkan pesan error jika terjadi kesalahan
            JOptionPane.showMessageDialog(null, "Gagal menampilkan data: " + e.getMessage());
        }
    }
    // Method untuk menyimpan data penyewa
    public void tambah() {
        // Mengambil input teks dari field nama
        String nama = txtNama.getText();
        // Mengambil input teks dari field telepon
        String notelp = txtTelepon.getText();
        // Mengambil input teks dari field alamat
        String alamat = txtAlamat.getText();
        try {
            // Mengecek apakah salah satu field kosong
            if (nama.isEmpty() || notelp.isEmpty() || alamat.isEmpty()) {
                // Melempar exception jika ada field yang kosong
                throw new IllegalArgumentException("Semua field harus diisi!");
            }
            // Memanggil method koneksi untuk membuka koneksi ke database
            koneksi();
            // Membuat statement SQL untuk menjalankan query
            Statement st = conn.createStatement();
            // Menjalankan perintah INSERT untuk menyimpan data ke tabel tb_penyewa
            st.executeUpdate("INSERT INTO tb_penyewa (nama, notelp, alamat) VALUES ('"
                    + nama + "', '" + notelp + "', '" + alamat + "')");
            // Menutup statement
            st.close();
            // Menampilkan pesan sukses kepada pengguna
            JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
            // Memanggil method tampil untuk menampilkan data ke tabel
            tampil();
            // Memanggil method reset untuk membersihkan form input
            batal();
        // Menangkap error
        } catch (Exception e) {
            // Menampilkan pesan error jika terjadi exception
            JOptionPane.showMessageDialog(null, "Gagal menyimpan: " + e.getMessage());
        }
    }
    // Method untuk memperbarui data penyewa
    public void ubah() {
        // Mengambil teks dari field txtNama dan menyimpannya dalam variabel nama
        String nama = txtNama.getText();
        // Mengambil teks dari field txtTelepon dan menyimpannya dalam variabel notelp
        String notelp = txtTelepon.getText();
        // Mengambil teks dari field txtAlamat dan menyimpannya dalam variabel alamat
        String alamat = txtAlamat.getText();
        try {
            // Mengecek apakah ID penyewa lama kosong
            if (idPenyewaLama.isEmpty()) {
                // Jika belum memilih data, lempar exception
                throw new IllegalStateException("Pilih data dari tabel terlebih dahulu.");
            }
            // Memanggil method koneksi untuk menghubungkan ke database
            koneksi();
            // Membuat statement SQL untuk eksekusi perintah update
            Statement st = conn.createStatement();
            // Menjalankan perintah SQL untuk memperbarui data penyewa berdasarkan ID
            st.executeUpdate("UPDATE tb_penyewa SET nama='" + nama + "', notelp='" + notelp +
                    "', alamat='" + alamat + "' WHERE id_penyewa=" + idPenyewaLama);
            // Menutup statement
            st.close();
            // Menampilkan pesan bahwa data berhasil diperbarui
            JOptionPane.showMessageDialog(null, "Data berhasil diperbarui");
            // Memanggil method tampil()
            // Memanggil method reset()
            batal();
        // Menangkap error
        } catch (Exception e) {
            // Menampilkan pesan error
            JOptionPane.showMessageDialog(null, "Gagal memperbarui: " + e.getMessage());
        }
    }
    // Method untuk menghapus data penyewa 
    public void hapus() {
        try {
            // Mengecek apakah id kosong?
            if (idPenyewaLama.isEmpty()) {
                // Jika belum ada data yang dipilih, lempar exception
                throw new IllegalStateException("Pilih data dari tabel terlebih dahulu.");
            }
            // Menampilkan pesan konfirmasi hapus data
            int konfirmasi = JOptionPane.showConfirmDialog(null, "Hapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            // Jika memilih YA, maka lanjutkan proses penghapusan
            if (konfirmasi == JOptionPane.YES_OPTION) {
                // Memanggil method koneksi() untuk menghubungkan ke database
                koneksi();
                // Membuat statement SQL untuk menjalankan query
                Statement st = conn.createStatement();
                // Menjalankan query SQL untuk menghapus data berdasarkan id_penyewa
                st.executeUpdate("DELETE FROM tb_penyewa WHERE id_penyewa=" + idPenyewaLama);
                // Menutup statement
                st.close();
                // Menampilkan pesan bahwa data berhasil dihapus
                JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
                // Memanggil method tampil() untuk memperbarui data di tabel
                tampil();
                // Memanggil method reset() untuk membersihkan input form
                batal();
            }
        // Menangkap semua error
        } catch (Exception e) {
            // Menampilkan pesan error
            JOptionPane.showMessageDialog(null, "Gagal menghapus: " + e.getMessage());
        }
    }
    // Method untuk mengosongkan semua inputan
    public void batal() {
        // Mengosongkan field teks Nama
        txtNama.setText("");
        // Mengosongkan field teks Telepon
        txtTelepon.setText("");
        // Mengosongkan field teks Alamat
        txtAlamat.setText("");
        // Mengosongkan variabel idPenyewaLama
        idPenyewaLama = "";
    }
    // Method untuk mengambil data dari tabel
    public void itempilih() {
        // Mengambil indeks yang dipilih dari tabel
        int baris = Table.getSelectedRow();
        // Mengecek bahwa ada baris yang dipilih 
        if (baris >= 0) {
            // Mengambil dan menyimpan ID penyewa dari listID
            idPenyewaLama = listID.get(baris).toString();
            // Mengatur teks Nama berdasarkan kolom 0 
            txtNama.setText(Table.getValueAt(baris, 0).toString());
            // Mengatur teks Telepon berdasarkan kolom 1
            txtTelepon.setText(Table.getValueAt(baris, 1).toString());
            // Mengatur teks Alamat berdasarkan kolom 2
            txtAlamat.setText(Table.getValueAt(baris, 2).toString());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtNama = new javax.swing.JTextField();
        txtTelepon = new javax.swing.JTextField();
        txtAlamat = new javax.swing.JTextField();
        btnSimpan = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        Table = new javax.swing.JTable();
        btnHapus = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Vivaldi", 1, 36)); // NOI18N
        jLabel1.setText("Data Penyewa");

        jLabel2.setText("Nama");

        jLabel3.setText("No. Telepon");

        jLabel6.setText("Alamat");

        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Nama", "No. Telepon", "Alamat"
            }
        ));
        Table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(Table);

        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnBatal.setText("Batal");
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2))
                                .addGap(41, 41, 41)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtNama, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                                    .addComponent(txtTelepon)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                                .addComponent(txtAlamat, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(63, 63, 63)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnHapus)
                                .addGap(18, 18, 18)
                                .addComponent(btnUpdate)
                                .addGap(18, 18, 18)
                                .addComponent(btnBatal))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(212, 212, 212)
                        .addComponent(jLabel1)))
                .addContainerGap(121, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtNama))
                        .addGap(56, 56, 56)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(txtTelepon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(56, 56, 56)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(txtAlamat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan)
                    .addComponent(btnHapus)
                    .addComponent(btnUpdate)
                    .addComponent(btnBatal))
                .addGap(105, 105, 105))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // TODO add your handling code here:
        tambah();
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        // TODO add your handling code here:
        batal();
    }//GEN-LAST:event_btnBatalActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        // TODO add your handling code here:
        hapus();
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        ubah();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void TableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableMouseClicked
        // TODO add your handling code here:
        int baris = Table.getSelectedRow();
        idPenyewaLama = listID.get(baris).toString();
        txtNama.setText(Table.getValueAt(baris, 0).toString());
        txtTelepon.setText(Table.getValueAt(baris, 1).toString());
        txtAlamat.setText(Table.getValueAt(baris, 2).toString());
        itempilih();
    }//GEN-LAST:event_TableMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI_Penyewa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI_Penyewa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI_Penyewa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI_Penyewa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI_Penyewa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Table;
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField txtAlamat;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtTelepon;
    // End of variables declaration//GEN-END:variables
}
