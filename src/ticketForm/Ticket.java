/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ticketForm;

import conexion.conexion;
import java.awt.event.ItemEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.postgresql.core.SqlCommand;

/**
 *
 * @author Edwin
 */
public class Ticket extends javax.swing.JFrame {
    

    conexion con = new conexion();
    Connection co = con.conexn();
    

    /**
     * Creates new form Ticket
     */
    public Ticket() {
        initComponents();

        this.setLocationRelativeTo(null);
        this.setTitle("Ticket Edwin Auquilla");
        paneln.setVisible(false);

        //ListarEstados();
        mostrarDatos();

        actualizar.setEnabled(false);
        eliminar.setEnabled(false);
        guardar.setEnabled(false);
    }

    public void InsertarDatos() {
        try {
            String SQl = "INSERT INTO ticket(\n"
                    + "            id_ticket, descripcion_ticket, estado, prioridad, caso_asignado, \n"
                    + "            fecha, deals)\n"
                    + "    VALUES (?, ?, ?, ?, ?, \n"
                    + "            ?, ?);";

            Date fechE = datef.getDate();
            long d = fechE.getTime();
            java.sql.Date fecha = new java.sql.Date(d);
            PreparedStatement pr = co.prepareStatement(SQl);
            pr.setString(1, idTicketField.getText());
            pr.setString(2, descripcionTicketField.getText());
            pr.setString(3, estado.getSelectedItem().toString());
            pr.setString(4, prioridad.getSelectedItem().toString());
            pr.setString(5, casoAsignadoField.getText());
            pr.setDate(6, fecha);
            pr.setString(7, dealsField.getText());
            pr.execute();
            JOptionPane.showMessageDialog(null, "Ticket Guardado");
            limpiarDatos();
            mostrarDatos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro de Registro" + e.getMessage());
        }

    }

    public void ModificarDatos() {
        try {
            String SQl = "UPDATE ticket\n"
                    + "   SET id_ticket=?, descripcion_ticket=?, estado=?, prioridad=?, caso_asignado=?, \n"
                    + "       fecha=?, deals=?\n"
                    + " WHERE id_ticket=?;";
            int filasel = jTable1.getSelectedRow();
            String ds = (String) jTable1.getValueAt(filasel, 0);

            Date fechE = datef.getDate();
            long d = fechE.getTime();
            java.sql.Date fecha = new java.sql.Date(d);
            PreparedStatement pr = co.prepareStatement(SQl);
            pr.setString(1, idTicketField.getText());
            pr.setString(2, descripcionTicketField.getText());
            pr.setString(3, estado.getSelectedItem().toString());
            pr.setString(4, prioridad.getSelectedItem().toString());
            pr.setString(5, casoAsignadoField.getText());
            pr.setDate(6, fecha);
            pr.setString(7, dealsField.getText());
            pr.setString(8, ds);
            pr.execute();
            JOptionPane.showMessageDialog(null, "Ticket Actualizado");
            limpiarDatos();
            mostrarDatos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro de Actualizacion" + e.getMessage());
        }

    }

    public void limpiarDatos() {
        idTicketField.setText("");
        descripcionTicketField.setText("");
        estado.setSelectedItem("");
        prioridad.setSelectedItem("");
        datef.setDate(null);
        casoAsignadoField.setText("");
        dealsField.setText("");

    }

    public void eliminarDatos() {
        int filasel = jTable1.getSelectedRow();
        try {
            String SQL = "DELETE FROM ticket WHERE id_ticket= '" + jTable1.getValueAt(filasel, 0) + "'";
            Statement st = co.createStatement();

            int c = st.executeUpdate(SQL);
            if (c >= 0) {
                JOptionPane.showMessageDialog(null, "Ticket Eliminado");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro al Eliminar Ticket" + e.getMessage());
        }

    }

    public void mostrarDatos() {
         String[] campos = {"ID Ticket", "Descripyion", "Status", "Priority", "Case Owner", "Date", "Detail"};
        String[] registros = new String[7];
        DefaultTableModel modelo = new DefaultTableModel(null, campos);
        String SQl = "select *from ticket order by id_ticket";

        try {
            Statement st = co.createStatement();
            ResultSet rs = st.executeQuery(SQl);

            while (rs.next()) {
                registros[0] = rs.getString("id_ticket");
                registros[1] = rs.getString("descripcion_ticket");
                registros[2] = rs.getString("estado");
                registros[3] = rs.getString("prioridad");
                registros[4] = rs.getString("caso_asignado");
                registros[5] = rs.getString("fecha");
                registros[6] = rs.getString("deals");

                modelo.addRow(registros);

            }
            jTable1.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro al mostrar Tickets" + e.getMessage());
        }
    }

    public void BuscarDatos(String estado) {
        String[] campos = {"ID Ticket", "Descripyion", "Status", "Priority", "Case Owner", "Date", "Detail"};
        String[] registros = new String[7];
        DefaultTableModel modelo = new DefaultTableModel(null, campos);
        if (estado.equals("All Tickets ")) {
            String SQL = "select *from ticket";
        } 
            String SQL = "select *from ticket where estado like '%" + estado + "%'";
        
        try {
            Statement st = co.createStatement();
            ResultSet rs = st.executeQuery(SQL);

            while (rs.next()) {
                registros[0] = rs.getString("id_ticket");
                registros[1] = rs.getString("descripcion_ticket");
                registros[2] = rs.getString("estado");
                registros[3] = rs.getString("prioridad");
                registros[4] = rs.getString("caso_asignado");
                registros[5] = rs.getString("fecha");
                registros[6] = rs.getString("deals");

                modelo.addRow(registros);

            }
            jTable1.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro al mostrar Tickets" + e.getMessage());
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
        jTable1 = new javax.swing.JTable();
        guardar = new javax.swing.JButton();
        eliminar = new javax.swing.JButton();
        actualizar = new javax.swing.JButton();
        nuevo = new javax.swing.JButton();
        paneln = new javax.swing.JPanel();
        idTicketLabel = new javax.swing.JLabel();
        prioridadLabel = new javax.swing.JLabel();
        datef = new com.toedter.calendar.JDateChooser();
        casoAsignadoLabel = new javax.swing.JLabel();
        casoAsignadoField = new javax.swing.JTextField();
        fechaLabel = new javax.swing.JLabel();
        dealsLabel = new javax.swing.JLabel();
        dealsField = new javax.swing.JTextField();
        descripcionTicketLabel = new javax.swing.JLabel();
        descripcionTicketField = new javax.swing.JTextField();
        estadoLabel = new javax.swing.JLabel();
        idTicketField = new javax.swing.JTextField();
        estado = new javax.swing.JComboBox<>();
        prioridad = new javax.swing.JComboBox<>();
        cobestado = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        guardar.setText("Save");
        guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarActionPerformed(evt);
            }
        });

        eliminar.setText("Delete");
        eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarActionPerformed(evt);
            }
        });

        actualizar.setText("Update");
        actualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actualizarActionPerformed(evt);
            }
        });

        nuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ticket_86147.png"))); // NOI18N
        nuevo.setText("New Ticket");
        nuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevoActionPerformed(evt);
            }
        });

        idTicketLabel.setText("Id Ticket:");

        prioridadLabel.setText("Priority:");

        datef.setDateFormatString("yyyy/MM/dd HH:mm:ss");

        casoAsignadoLabel.setText("Case Owner:");

        casoAsignadoField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                casoAsignadoFieldActionPerformed(evt);
            }
        });

        fechaLabel.setText("Date:");

        dealsLabel.setText("Deals:");

        descripcionTicketLabel.setText("Description Ticket:");

        estadoLabel.setText("Status:");

        estado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Open Tickets", "Close Tickets", "Overdue Tickets", "Escalated Tickets", "Hight Priority" }));
        estado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                estadoActionPerformed(evt);
            }
        });

        prioridad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "High", "Low" }));

        javax.swing.GroupLayout panelnLayout = new javax.swing.GroupLayout(paneln);
        paneln.setLayout(panelnLayout);
        panelnLayout.setHorizontalGroup(
            panelnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelnLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(idTicketLabel)
                    .addGroup(panelnLayout.createSequentialGroup()
                        .addGroup(panelnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(descripcionTicketLabel)
                            .addComponent(estadoLabel)
                            .addComponent(prioridadLabel)
                            .addComponent(casoAsignadoLabel)
                            .addComponent(fechaLabel)
                            .addComponent(dealsLabel))
                        .addGap(52, 52, 52)
                        .addGroup(panelnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dealsField, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(casoAsignadoField, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(descripcionTicketField, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(datef, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                                .addComponent(idTicketField, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(estado, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(prioridad, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        panelnLayout.setVerticalGroup(
            panelnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelnLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idTicketLabel)
                    .addComponent(idTicketField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(descripcionTicketLabel)
                    .addComponent(descripcionTicketField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(estadoLabel)
                    .addComponent(estado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(prioridadLabel)
                    .addComponent(prioridad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(casoAsignadoLabel)
                    .addComponent(casoAsignadoField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fechaLabel)
                    .addComponent(datef, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dealsLabel)
                    .addComponent(dealsField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        cobestado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All Tickets ", "Open Tickets", "Close Tickets", "Overdue Tickets", "Escalated Tickets", "Hight Priority" }));
        cobestado.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cobestadoItemStateChanged(evt);
            }
        });
        cobestado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cobestadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addGap(10, 10, 10))
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(paneln, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(cobestado, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(guardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(eliminar)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(actualizar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(nuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(36, 36, 36))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(paneln, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(guardar)
                    .addComponent(eliminar)
                    .addComponent(actualizar)
                    .addComponent(nuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cobestado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void casoAsignadoFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_casoAsignadoFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_casoAsignadoFieldActionPerformed

    private void guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarActionPerformed
        InsertarDatos();
        paneln.setVisible(false);


    }//GEN-LAST:event_guardarActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        paneln.setVisible(true);
        actualizar.setEnabled(true);
        eliminar.setEnabled(true);

        SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
        Date fechaDate = datef.getDate();
        int filaselect = jTable1.rowAtPoint(evt.getPoint());
        idTicketField.setText(jTable1.getValueAt(filaselect, 0).toString());
        descripcionTicketField.setText(jTable1.getValueAt(filaselect, 1).toString());
        estado.setSelectedItem(jTable1.getValueAt(filaselect, 2).toString());
        prioridad.setSelectedItem(jTable1.getValueAt(filaselect, 3).toString());
        casoAsignadoField.setText(jTable1.getValueAt(filaselect, 4).toString());
        datef.setDate(Timestamp.valueOf(jTable1.getValueAt(filaselect, 5).toString()));
        dealsField.setText(jTable1.getValueAt(filaselect, 6).toString());

    }//GEN-LAST:event_jTable1MouseClicked

    private void nuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevoActionPerformed
        // TODO add your handling code here:
        paneln.setVisible(true);
        actualizar.setEnabled(true);
        eliminar.setEnabled(true);
        guardar.setEnabled(true);
        limpiarDatos();
    }//GEN-LAST:event_nuevoActionPerformed

    private void eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarActionPerformed
        // TODO add your handling code here:
        eliminarDatos();
        mostrarDatos();
        paneln.setVisible(false);
    }//GEN-LAST:event_eliminarActionPerformed

    private void actualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actualizarActionPerformed
        // TODO add your handling code here:
        ModificarDatos();
        mostrarDatos();
        paneln.setVisible(false);
    }//GEN-LAST:event_actualizarActionPerformed

    private void cobestadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cobestadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cobestadoActionPerformed

    private void cobestadoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cobestadoItemStateChanged
        BuscarDatos(cobestado.getSelectedItem().toString());
    }//GEN-LAST:event_cobestadoItemStateChanged

    private void estadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_estadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_estadoActionPerformed

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
            java.util.logging.Logger.getLogger(Ticket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ticket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ticket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ticket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Ticket().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton actualizar;
    private javax.swing.JTextField casoAsignadoField;
    private javax.swing.JLabel casoAsignadoLabel;
    private javax.swing.JComboBox<String> cobestado;
    private com.toedter.calendar.JDateChooser datef;
    private javax.swing.JTextField dealsField;
    private javax.swing.JLabel dealsLabel;
    private javax.swing.JTextField descripcionTicketField;
    private javax.swing.JLabel descripcionTicketLabel;
    private javax.swing.JButton eliminar;
    private javax.swing.JComboBox<String> estado;
    private javax.swing.JLabel estadoLabel;
    private javax.swing.JLabel fechaLabel;
    private javax.swing.JButton guardar;
    private javax.swing.JTextField idTicketField;
    private javax.swing.JLabel idTicketLabel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton nuevo;
    private javax.swing.JPanel paneln;
    private javax.swing.JComboBox<String> prioridad;
    private javax.swing.JLabel prioridadLabel;
    // End of variables declaration//GEN-END:variables
}
