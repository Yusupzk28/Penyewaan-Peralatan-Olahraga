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
// Mengimpor package untuk koneksi dan manipulasi database SQL
import java.sql.*;
// Mengimpor kelas untuk format tanggal
import java.text.SimpleDateFormat;
// Mengimpor kelas ArrayList untuk menyimpan daftar data
import java.util.ArrayList;
// Mengimpor kelas Date untuk menangani tanggal dan waktu
import java.util.Date;
// Mengimpor kelas HashMap untuk menyimpan pasangan key-value
import java.util.HashMap;
// Mengimpor kelas Logger dan Level untuk mencatat log/error dalam aplikasi
import java.util.logging.Level;
import java.util.logging.Logger;
// Mengimpor semua komponen dari package javax.swing untuk antarmuka grafis (GUI)
import javax.swing.*;
// Mengimpor kelas DefaultTableModel untuk memanipulasi data tabel dalam GUI Swing
import javax.swing.table.DefaultTableModel;

public class GUI_Sewa extends javax.swing.JFrame {
    /**
     * Creates new form GUI_Sewa12
     */

public GUI_Sewa() {
    initComponents();
    pilihan();
    tampil();
    batal();
}
// Deklarasi variabel koneksi ke database
public Connection conn;
// Variabel untuk menyimpan ID sewa sebelumnya (digunakan saat update data)
String idSewaLama = "";
// HashMap untuk menyimpan data penyewa
HashMap<String, Integer> mapPenyewa = new HashMap<>();
// HashMap untuk menyimpan data alat
HashMap<String, Integer> mapAlat = new HashMap<>();
// ArrayList untuk menyimpan daftar ID
ArrayList<Integer> listID = new ArrayList<>();
// Method untuk membuat koneksi ke database
public void koneksi() throws SQLException {
    try {
        // Mengatur nilai koneksi menjadi null
        conn = null;
        // Memuat driver JDBC untuk MySQL
        Class.forName("com.mysql.cj.jdbc.Driver");
        // Membuat koneksi ke database 
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tpoop_2318038?serverTimezone=UTC", "root", "");
    // Menangkap error
    } catch (ClassNotFoundException | SQLException e) {
        // Menampilkan pesan error
        Logger.getLogger(GUI_Sewa.class.getName()).log(Level.SEVERE, null, e);
    }
}
// Method untuk menampilkan data sewa ke dalam tabel GUI
public void tampil() {
    // Membuat model tabel baru
    DefaultTableModel model = new DefaultTableModel();
    // Menambahkan kolom "Penyewa" ke model tabel
    model.addColumn("Penyewa");
    // Menambahkan kolom "Alat" ke model tabel
    model.addColumn("Alat");
    // Menambahkan kolom "Tanggal" ke model tabel
    model.addColumn("Tanggal");
    // Menambahkan kolom "Durasi" ke model tabel
    model.addColumn("Durasi");
    try {
        // Memanggil metode koneksi
        koneksi();
        // Menyusun query SQL untuk mengambil data sewa
        String sql = "SELECT s.id_sewa, p.nama, a.nama_alat, s.tanggal, s.durasi FROM tb_sewa s JOIN tb_penyewa p ON s.id_penyewa=p.id_penyewa JOIN tb_alat a ON s.id_alat=a.id_alat";
        // Membuat statement untuk menjalankan query
        Statement st = conn.createStatement();
        // Menjalankan query dan menyimpan hasilnya ke ResultSet
        ResultSet rs = st.executeQuery(sql);
        // Mengosongkan listID sebelum menambahkan data baru
        listID.clear();
        // Melakukan iterasi terhadap hasil query
        while (rs.next()) {
            // Menambahkan baris baru ke model tabel
            model.addRow(new Object[]{
                rs.getString("nama"),         
                rs.getString("nama_alat"),    
                rs.getString("tanggal"), 
                rs.getInt("durasi")
            });
            // Menyimpan id_sewa ke dalam listID
            listID.add(rs.getInt("id_sewa"));
        }
        // Menetapkan model yang telah diisi
        TableSewa.setModel(model);
    // Menangkap error
    } catch (Exception e) {
        // Menampilkan pesan error 
        JOptionPane.showMessageDialog(null, "Gagal tampil data: " + e.getMessage());
    }
}
// Method pilihan() digunakan untuk mengisi comboBox
public void pilihan() {
    try {
        // Memanggil method koneksi
        koneksi();
        // Membuat statement untuk mengeksekusi query SQL
        Statement st = conn.createStatement();
        // Mengeksekusi query untuk mengambil semua data
        ResultSet rsPenyewa = st.executeQuery("SELECT * FROM tb_penyewa");
        // Melakukan iterasi terhadap hasil query tb_penyewa
        while (rsPenyewa.next()) {
            // Mengambil nama penyewa dari hasil query
            String nama = rsPenyewa.getString("nama");
            // Menambahkan nama penyewa ke cmb_penyewa
            cmb_penyewa.addItem(nama);
            // Menyimpan mapping antara nama penyewa dan id_penyewa ke dalam mapPenyewa
            mapPenyewa.put(nama, rsPenyewa.getInt("id_penyewa"));
        }
        // Mengeksekusi query untuk mengambil semua data
        ResultSet rsAlat = st.executeQuery("SELECT * FROM tb_alat");
        // Melakukan iterasi hasil query tb_alat
        while (rsAlat.next()) {
            // Mengambil nama alat dari hasil query
            String alat = rsAlat.getString("nama_alat");
            // Menambahkan nama alat ke cmb_alat
            cmb_alat.addItem(alat);
            // Menyimpan mapping antara nama alat dan id_alat ke dalam mapAlat
            mapAlat.put(alat, rsAlat.getInt("id_alat"));
        }
        // Membuat array berisi pilihan durasi sewa
        int[] pilihanHari = {1, 2, 3, 7, 10};
        // Menambahkan setiap pilihan durasi cmb_durasi
        for (int hari : pilihanHari) {
            cmb_durasi.addItem(String.valueOf(hari));
        }
    // Menangkap error
    } catch (Exception e) {
        // Menampilkan pesan error
        JOptionPane.showMessageDialog(null, "Gagal load combo box: " + e.getMessage());
    }
}
// Method untuk tambah data
public void tambah() {
    try {
        // Mengambil id_penyewa berdasarkan item yang dipilih dari cmb_penyewa
        int idPenyewa = mapPenyewa.get(cmb_penyewa.getSelectedItem().toString());
        // Mengambil id_alat berdasarkan item yang dipilih dari cmb_alat
        int idAlat = mapAlat.get(cmb_alat.getSelectedItem().toString());
        // Mengambil nilai tanggal dari input teks
        String tanggal = txtTanggal.getText();
        // Mengambil durasi dari combo box durasi
        String durasi = cmb_durasi.getSelectedItem().toString();
        // Membuka koneksi
        koneksi();
        // Membuat statement SQL untuk dieksekusi
        Statement st = conn.createStatement();
        // Menjalankan perintah SQL untuk tambah data
        st.executeUpdate("INSERT INTO tb_sewa (id_penyewa, id_alat, tanggal, durasi) VALUES (" + idPenyewa + ", " + idAlat + ", '" + tanggal + "', " + durasi + ")");
        // Menutup statement
        st.close();
        // Menampilkan pesan sukses kepada pengguna
        JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
        // Memperbarui tampilan data pada tabel
        tampil();
        // Mengosongkan form input
        batal();
    // Menangkap error
    } catch (Exception e) {
        // Menampilkan pesan error
        JOptionPane.showMessageDialog(null, "Gagal insert: " + e.getMessage());
    }
}
// Method untuk memperbarui data
public void ubah() {
    try {
        // Mengecek apakah data telah dipilih
        if (idSewaLama.isEmpty()) 
            // jika tidak tampilkan pesan
            throw new IllegalStateException("Pilih data terlebih dahulu");
        // Mengambil ID penyewa dari map berdasarkan item yang dipilih di cmb_penyewa
        int idPenyewa = mapPenyewa.get(cmb_penyewa.getSelectedItem().toString());
        // Mengambil ID alat dari map berdasarkan item yang dipilih di cmb_alat
        int idAlat = mapAlat.get(cmb_alat.getSelectedItem().toString());
        // Mengambil tanggal dari input text field
        String tanggal = txtTanggal.getText();
        // Mengambil durasi dari cmb_durasi
        String durasi = cmb_durasi.getSelectedItem().toString();
        // Melakukan koneksi ke database
        koneksi();
        // Membuat statement SQL
        Statement st = conn.createStatement();
        // Menjalankan perintah SQL untuk update data
        st.executeUpdate("UPDATE tb_sewa SET id_penyewa=" + idPenyewa + ", id_alat=" + idAlat + ", tanggal='" + tanggal + "', durasi=" + durasi + " WHERE id_sewa=" + idSewaLama);
        // Menutup statement
        st.close();
        // Menampilkan pesan sukses
        JOptionPane.showMessageDialog(null, "Data berhasil diperbarui");
        // Menampilkan data ke tabel 
        tampil();
        // Mengosongkan input field
        batal();
    // Menangkap error
    } catch (Exception e) {
        // Menampilkan pesan error
        JOptionPane.showMessageDialog(null, "Gagal update: " + e.getMessage());
    }
}
// Method untuk menghapus data
public void hapus() {
    try {
        // Mengecek apakah idSewaLama kosong
        if (idSewaLama.isEmpty()) 
           // jika iya maka lempar pesan
           throw new IllegalStateException("Pilih data terlebih dahulu");
        // Memanggil method koneksi
        koneksi();
        // Membuat statement SQL
        Statement st = conn.createStatement();
        // Menjalankan perintah SQL untuk menghapus data
        st.executeUpdate("DELETE FROM tb_sewa WHERE id_sewa=" + idSewaLama);
        // Menutup statement
        st.close();
        // Menampilkan pesan data berhasil dihapus
        JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
        // Memanggil method tampil untuk memperbarui tampilan data di UI
        tampil();
        // Mengosongkan form input
        batal();
    // Menangkap error
    } catch (Exception e) {
        // Menampilkan pesan error
        JOptionPane.showMessageDialog(null, "Gagal hapus: " + e.getMessage());
    }
}
// Method untuk mengosongkan form input
public void batal() {
    // Mengatur cmb_penyewa ke indeks pertama
    cmb_penyewa.setSelectedIndex(0);
    // Mengatur cmb_alat ke indeks pertama
    cmb_alat.setSelectedIndex(0);
    // Mengatur cmb_durasi ke indeks pertama
    cmb_durasi.setSelectedIndex(0);
    // Membuat objek  untuk tanggal
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    // Mendapatkan tanggal
    String today = sdf.format(new Date());
    // Mengatur teks pada field txtTanggal menjadi tanggal hari ini
    txtTanggal.setText(today);
    // Mengosongkan variabel idSewaLama
    idSewaLama = "";
}
// Method untuk mengambil data dari baris yang dipilih
public void itempilih() {
    // Mendapatkan indeks baris yang dipilih
    int baris = TableSewa.getSelectedRow();
    // Mengecek apakah ada baris yang dipilih
    if (baris >= 0) {
        // Menyimpan ID sewa dari listID
        idSewaLama = listID.get(baris).toString();
        // Mengatur nilai cmb_penyewa berdasarkan nilai di kolom 0
        cmb_penyewa.setSelectedItem(TableSewa.getValueAt(baris, 0).toString());
        // Mengatur nilai cmb_alat berdasarkan nilai di kolom 1
        cmb_alat.setSelectedItem(TableSewa.getValueAt(baris, 1).toString());
        // Mengatur teks tanggal berdasarkan nilai di kolom 2
        txtTanggal.setText(TableSewa.getValueAt(baris, 2).toString());
        // Mengatur nilai cmb_durasi berdasarkan nilai di kolom 3
        cmb_durasi.setSelectedItem(TableSewa.getValueAt(baris, 3).toString());
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

        jScrollPane1 = new javax.swing.JScrollPane();
        TableSewa = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cmb_penyewa = new javax.swing.JComboBox();
        cmb_alat = new javax.swing.JComboBox();
        txtTanggal = new javax.swing.JTextField();
        btnSimpan = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        cmb_durasi = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        TableSewa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Penyewa", "Alat", "Tanggal", "Durasi"
            }
        ));
        TableSewa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableSewaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TableSewa);

        jLabel1.setText("Penyewa");

        jLabel2.setText("Alat");

        jLabel3.setText("Tanggal");

        jLabel4.setText("Durasi (Hari)");

        cmb_penyewa.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NAMA PENYEWA" }));

        cmb_alat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NAMA ALAT" }));
        cmb_alat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_alatActionPerformed(evt);
            }
        });

        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
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

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel5.setText("SEWA PERALATAN OLAHRAGA");

        cmb_durasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "DURASI" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cmb_penyewa, javax.swing.GroupLayout.Alignment.TRAILING, 0, 138, Short.MAX_VALUE)
                    .addComponent(cmb_alat, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTanggal, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cmb_durasi, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnSimpan)
                        .addGap(26, 26, 26)
                        .addComponent(btnUpdate)
                        .addGap(36, 36, 36)
                        .addComponent(btnHapus)
                        .addGap(29, 29, 29)
                        .addComponent(btnBatal))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(93, 93, 93))
            .addGroup(layout.createSequentialGroup()
                .addGap(98, 98, 98)
                .addComponent(jLabel5)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmb_penyewa))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmb_alat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmb_durasi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan)
                    .addComponent(btnUpdate)
                    .addComponent(btnHapus)
                    .addComponent(btnBatal))
                .addGap(30, 30, 30))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // TODO add your handling code here:
        tambah();
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void TableSewaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableSewaMouseClicked
        // TODO add your handling code here:
        itempilih();
    }//GEN-LAST:event_TableSewaMouseClicked

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        ubah();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        // TODO add your handling code here:
        hapus();
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        // TODO add your handling code here:
        batal();
    }//GEN-LAST:event_btnBatalActionPerformed

    private void cmb_alatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_alatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmb_alatActionPerformed

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
            java.util.logging.Logger.getLogger(GUI_Sewa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI_Sewa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI_Sewa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI_Sewa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI_Sewa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TableSewa;
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox cmb_alat;
    private javax.swing.JComboBox cmb_durasi;
    private javax.swing.JComboBox cmb_penyewa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtTanggal;
    // End of variables declaration//GEN-END:variables
}
