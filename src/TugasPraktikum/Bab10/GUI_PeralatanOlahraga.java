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

public class GUI_PeralatanOlahraga extends javax.swing.JFrame {
    /**
     * Creates new form GUI_PeralatanOlahraga
     */

    public GUI_PeralatanOlahraga() {
        initComponents();
        tampil();
    }
    // Mendeklarasikan variabel conn untuk koneksi ke database
    public Connection conn;
    // Menyimpan nilai ID dalam bentuk string
    String idAlatLama = "";
    // Membuat list untuk menyimpan ID dalam bentuk integer
    ArrayList<Integer> listID = new ArrayList<>();
    // Method untuk membuat koneksi ke database
    public void koneksi() throws SQLException {
        try {
            // Inisialisasi koneksi dengan null
            conn = null;
            // Memuat driver JDBC untuk MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Membuka koneksi ke database MySQL 
            conn = DriverManager.getConnection(        "jdbc:mysql://localhost:3306/tpoop_2318038?serverTimezone=UTC",
                "root",
                ""
            );
        // Menangani error jika driver tidak ditemukan atau gagal koneksi
        } catch (ClassNotFoundException | SQLException e) {
            // Menampilkan log error
    Logger.getLogger(GUI_Penyewa.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    // Method untuk menampilkan data
    public void tampil() {
        // Membuat objek model tabel baru
        DefaultTableModel model = new DefaultTableModel();
        // Menambahkan kolom "Nama Alat" ke model tabel
        model.addColumn("Nama Alat");
        // Menambahkan kolom "Jenis" ke model tabel
        model.addColumn("Jenis");
        // Menambahkan kolom "Stok" ke model tabel
        model.addColumn("Stok");
        try {
            // Memanggil method koneksi untuk menyambungkan ke database
            koneksi();
            // Membuat query SQL untuk mengambil semua data dari tabel `tb_alat`
            String sql = "SELECT * FROM tb_alat";
            // Membuat statement untuk menjalankan query
            Statement st = conn.createStatement();
            // Menjalankan query dan menyimpan hasilnya dalam ResultSet
            ResultSet rs = st.executeQuery(sql);
            // Menghapus semua elemen yang ada di listID agar tidak duplikat
            listID.clear();
            // Melakukan iterasi
            while (rs.next()) {
                // Membuat array untuk menampung data per baris
                Object[] row = new Object[3];
                // Mengambil nilai kolom "nama_alat" dan menyimpannya di indeks 0
                row[0] = rs.getString("nama_alat");
                // Mengambil nilai kolom "jenis" dan menyimpannya di indeks 1
                row[1] = rs.getString("jenis");
                // Mengambil nilai kolom "stok" dan menyimpannya di indeks 2
                row[2] = rs.getInt("stok");
                // Menambahkan array data sebagai baris baru dalam model tabel
                model.addRow(row);
                // Menambahkan nilai "id_alat" ke dalam listID untuk referensi
                listID.add(rs.getInt("id_alat"));
            }
            // Menampilkan model yang sudah diisi ke komponen tabel
            Table.setModel(model);
        } catch (Exception e) {
            // Menampilkan pesan error jika terjadi kesalahan saat mengambil data
            JOptionPane.showMessageDialog(null, "Gagal menampilkan data: " + e.getMessage());
        }
    }
    // Method untuk tambah data
    public void tambah() {
        // Mengambil input teks dari field Nama
        String nama = txtNama.getText();
        // Mengambil item yang dipilih dari combo box Jenis
        String jenis = cmb_jenis.getSelectedItem().toString();
        // Mengambil input teks dari field Stok
        String stok = txtStok.getText();
        try {
            // Mengecek apakah nama atau stok kosong
            if (nama.isEmpty() || stok.isEmpty()) {
                // jika iya lempar exception
                throw new IllegalArgumentException("Nama dan Stok harus diisi!");
            }
            // Memanggil method koneksi ke database
            koneksi();
            // Membuat statement SQL
            Statement st = conn.createStatement();
            // Mengeksekusi perintah INSERT untuk menyimpan data ke tabel tb_alat
            st.executeUpdate("INSERT INTO tb_alat (nama_alat, jenis, stok) VALUES ('" + nama + "', '" + jenis + "', " + stok + ")");
            // Menutup statement
            st.close();
            // Menampilkan pesan bahwa data berhasil disimpan
            JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
            // Menampilkan ulang data yang telah diperbarui ke tabel
            tampil();
            // Mereset form input setelah penyimpanan
            batal();
        // Menangkap Error
        } catch (Exception e) {
            // Menampilkan pesan kesalahan jika proses penyimpanan gagal
            JOptionPane.showMessageDialog(null, "Gagal menyimpan: " + e.getMessage());
        }
    }
    // Method untuk ubah data
    public void ubah() {
        // Mengambil teks dari input Nama
        String nama = txtNama.getText();
        // Mengambil item yang dipilih dari combobox Jenis
        String jenis = cmb_jenis.getSelectedItem().toString();
        // Mengambil teks dari input Stok
        String stok = txtStok.getText();
        try {
            // Mengecek apakah idAlatLama kosong
            if (idAlatLama.isEmpty()) {
                // Jika ya melempar exception
                throw new IllegalStateException("Pilih data dari tabel terlebih dahulu.");
            }
            // Memanggil method koneksi untuk membuat koneksi ke database
            koneksi();
            // Membuat objek Statement untuk menjalankan query SQL
            Statement st = conn.createStatement();
            // Menjalankan query SQL untuk memperbarui data pada tabel tb_alat
            st.executeUpdate("UPDATE tb_alat SET nama_alat='" + nama + "', jenis='" + jenis + "', stok=" + stok + " WHERE id_alat=" + idAlatLama);
            // Menutup Statement
            st.close();
            // Menampilkan pesan bahwa data berhasil diperbarui
            JOptionPane.showMessageDialog(null, "Data berhasil diperbarui");
            // Menampilkan kembali data ke dalam tabel setelah update
            tampil();
            // Mengosongkan field
            batal();
        // Menangkap error
        } catch (Exception e) {
            // Menampilkan pesan error 
            JOptionPane.showMessageDialog(null, "Gagal memperbarui: " + e.getMessage());
        }
    }
    // Method untuk menghapus data
    public void hapus() {
        try {
            // Mengecek apakah idAlatLama kosong
            if (idAlatLama.isEmpty()) {
                // jika ya lempar exception
                throw new IllegalStateException("Pilih data dari tabel terlebih dahulu.");
            }
            // Menampilkan pesan konfirmasi
            int konfirmasi = JOptionPane.showConfirmDialog(null, "Hapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            // Jika pengguna menekan tombol YES
            if (konfirmasi == JOptionPane.YES_OPTION) {
                // Membuka koneksi ke database
                koneksi();
                // Membuat Statement untuk menjalankan perintah SQL
                Statement st = conn.createStatement();
                // Menjalankan perintah SQL untuk menghapus data berdasarkan idAlatLama
                st.executeUpdate("DELETE FROM tb_alat WHERE id_alat=" + idAlatLama);
                // Menutup Statement
                st.close();
                // Menampilkan pesan bahwa data berhasil dihapus
                JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
                // Menampilkan ulang data setelah penghapusan
                tampil();
                // Mengosongkan field
                batal();
            }
        // Menangkap error
        } catch (Exception e) {
            // Menampilkan pesan error
            JOptionPane.showMessageDialog(null, "Gagal menghapus: " + e.getMessage());
        }
    }
    // Method untuk mengosongkan semua inputan
    public void batal() {
        // Mengosongkan field teks Nama
        txtNama.setText("");
        // Mengatur combobox ke indeks pertama
        cmb_jenis.setSelectedIndex(0);
        // Mengosongkan field teks Stok
        txtStok.setText("");
        // Mengosongkan variabel idAlatLama
        idAlatLama = "";
    }
    // Method untuk mengambil data
    public void itempilih() {
        // Mengambil indeks baris yang dipilih
        int baris = Table.getSelectedRow();
        // Memastikan bahwa ada baris yang dipilih
        if (baris >= 0) {
            // Mengambil dan menyimpan ID alat dari listID
            idAlatLama = listID.get(baris).toString();
            // Mengatur teks Nama berdasarkan kolom 0
            txtNama.setText(Table.getValueAt(baris, 0).toString());
            // Mengatur item yang dipilih pada combobox berdasarkan kolom 1
            cmb_jenis.setSelectedItem(Table.getValueAt(baris, 1).toString());
            // Mengatur teks Stok berdasarkan kolom 2
            txtStok.setText(Table.getValueAt(baris, 2).toString());
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
        txtNama = new javax.swing.JTextField();
        txtStok = new javax.swing.JTextField();
        cmb_jenis = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table = new javax.swing.JTable();
        btnSimpan = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Nama");

        jLabel2.setText("Jenis");

        jLabel3.setText("Stok");

        cmb_jenis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Alat Individu", "Alat Tim" }));

        Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Nama", "Jenis", "Stok"
            }
        ));
        Table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Table);

        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnBatal.setText("Batal");
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Vivaldi", 1, 36)); // NOI18N
        jLabel4.setText("Data Alat Olahraga");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(121, 121, 121)
                .addComponent(btnSimpan)
                .addGap(49, 49, 49)
                .addComponent(btnHapus)
                .addGap(48, 48, 48)
                .addComponent(btnBatal)
                .addGap(68, 68, 68)
                .addComponent(btnUpdate)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmb_jenis, 0, 86, Short.MAX_VALUE)
                    .addComponent(txtNama)
                    .addComponent(txtStok))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE)
                        .addGap(27, 27, 27))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jLabel4)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(cmb_jenis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(41, 41, 41)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(txtStok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan)
                    .addComponent(btnHapus)
                    .addComponent(btnBatal)
                    .addComponent(btnUpdate))
                .addGap(40, 40, 40))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // TODO add your handling code here:
    tambah();
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        // TODO add your handling code here:
    hapus();
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        // TODO add your handling code here:
    batal();
    }//GEN-LAST:event_btnBatalActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
    ubah();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void TableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableMouseClicked
        // TODO add your handling code here:
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
            java.util.logging.Logger.getLogger(GUI_PeralatanOlahraga.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI_PeralatanOlahraga.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI_PeralatanOlahraga.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI_PeralatanOlahraga.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI_PeralatanOlahraga().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Table;
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox cmb_jenis;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtStok;
    // End of variables declaration//GEN-END:variables
}
