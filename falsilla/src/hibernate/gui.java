package hibernate;

public class gui {
	package com.hibernate.gui;

	//en la cosa esa de errores (ENE|FEB|MAR...)2[7-9]
	//BOOLEAN -> tinyInt
	//checkBox.selected

	import java.awt.EventQueue;
	import java.awt.event.MouseAdapter;
	import java.awt.event.MouseEvent;
	import java.util.List;

	import javax.swing.JFrame;
	import javax.swing.JTable;
	import javax.swing.JLabel;
	import javax.swing.JScrollPane;
	import javax.swing.JTextField;
	import javax.swing.table.DefaultTableModel;
	import javax.swing.table.TableModel;
	import javax.swing.JComboBox;
	import javax.swing.JCheckBox;
	import javax.swing.JButton;

	import com.hibernate.dao.*;
	import com.hibernate.model.*;
	import java.awt.event.ActionListener;
	import java.awt.event.ActionEvent;

	public class Farmacia {

		private JFrame frame;
		private JTable table;
		private JTextField txtId;
		private JTextField txtNombre;
		private JTextField txtCaducidad;
		private JComboBox comboBox;
		private JCheckBox checkExistencias;
		private JTable tableSinStock;

		/**
		 * Launch the application.
		 */
		public static void main(String[] args) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						Farmacia window = new Farmacia();
						window.frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}

		/**
		 * Create the application.
		 */
		public Farmacia() {
			initialize();
		}

		/**
		 * Initialize the contents of the frame.
		 */
		private void initialize() {
			
			    MedicamentoDAO medicamentoDAO = new MedicamentoDAO();
			    Medicamento producto = null;

			    // 1. PRIMERO CREAMOS EL FRAME (Para que no de NullPointerException)
			    frame = new JFrame();
			    frame.setBounds(100, 100, 699, 418);
			    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			    frame.getContentPane().setLayout(null);

			    // 2. OBTENEMOS DATOS
			    List<Medicamento> ls = MedicamentoDAO.selectAllMed();

			    DefaultTableModel model = new DefaultTableModel();
			    model.addColumn("id");
			    model.addColumn("nombre");
			    model.addColumn("formato");
			    model.addColumn("caducidad");
			    model.addColumn("existencias");

			    if (ls != null) {
			        for (Medicamento med : ls) {
			             Object[] row = new Object[5];
			             row[0] = med.getId();
			             row[1] = med.getNombre();
			             row[2] = med.getFormato();
			             row[3] = med.getCaducidad();
			             row[4] = med.getExistencias();
			             model.addRow(row);
			        }
			    }
			    
			    // 3. CREAMOS LA TABLA Y EL SCROLL
			    table = new JTable(model); 
			    table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

			    JScrollPane scrollPane = new JScrollPane(table);
			    scrollPane.setBounds(29, 12, 576, 131);
			    frame.getContentPane().add(scrollPane);
			    
			    //SEGUNDA TABLA
			    JScrollPane scrollPaneSinStock = new JScrollPane();
			    scrollPaneSinStock.setBounds(328, 213, 306, 131);
			    frame.getContentPane().add(scrollPaneSinStock);
			    
			    tableSinStock = new JTable();
			    scrollPaneSinStock.setViewportView(tableSinStock);
			    
			    // 4. CONFIGURAMOS EL COMBO (Usando la variable de la clase)
			    comboBox = new JComboBox();
			    comboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"Pastillas", "Jarabe", "Pomada"}));
			    comboBox.setBounds(89, 225, 114, 26);
			    frame.getContentPane().add(comboBox);
			    
			    // 5. EVENTO MOUSE CLICK
			    table.addMouseListener(new MouseAdapter() {
			        @Override
			        public void mouseClicked(MouseEvent e) {
			            int index = table.getSelectedRow();
			            txtId.setText(table.getValueAt(index, 0).toString());
			            txtNombre.setText(table.getValueAt(index, 1).toString());
			            comboBox.setSelectedItem(table.getValueAt(index, 2).toString());
			            txtCaducidad.setText(table.getValueAt(index, 3).toString());
			            checkExistencias.setSelected((boolean) table.getValueAt(index, 4));
			        }
			    });
			    
			    // 6. RESTO DE COMPONENTES
			    JLabel lblId = new JLabel("Id:");
			    lblId.setBounds(29, 155, 60, 17);
			    frame.getContentPane().add(lblId);
			    
			    txtId = new JTextField();
			    txtId.setEditable(false); // Recomendado: el ID no se edita a mano
			    txtId.setBounds(54, 155, 114, 21);
			    frame.getContentPane().add(txtId);
			    txtId.setColumns(10);
			    
			    JLabel lblNewLabel = new JLabel("Nombre:");
			    lblNewLabel.setBounds(29, 188, 60, 17);
			    frame.getContentPane().add(lblNewLabel);
			    
			    txtNombre = new JTextField();
			    txtNombre.setBounds(89, 186, 114, 21);
			    frame.getContentPane().add(txtNombre);
			    txtNombre.setColumns(10);
			    
			    JLabel lblFormato = new JLabel("Formato:");
			    lblFormato.setBounds(29, 225, 60, 17);
			    frame.getContentPane().add(lblFormato);
			    
			    JLabel lblCaducidad = new JLabel("Caducidad:");
			    lblCaducidad.setBounds(29, 263, 76, 17);
			    frame.getContentPane().add(lblCaducidad);
			    
			    txtCaducidad = new JTextField();
			    txtCaducidad.setBounds(109, 261, 114, 21);
			    frame.getContentPane().add(txtCaducidad);
			    txtCaducidad.setColumns(10);
			    
			    checkExistencias = new JCheckBox("Existencias");
			    checkExistencias.setBounds(54, 298, 114, 25);
			    frame.getContentPane().add(checkExistencias);
			    
			    // BOTONES
			    JButton btnMostrar = new JButton("Mostrar");
			    btnMostrar.addActionListener(new java.awt.event.ActionListener() {
			        public void actionPerformed(java.awt.event.ActionEvent e) {
			            List<Medicamento> lista = MedicamentoDAO.selectAllMed();
			            DefaultTableModel modelNuevo = (DefaultTableModel) table.getModel();
			            modelNuevo.setRowCount(0); // Borra lo que haya

			            for (Medicamento med : lista) {
			                Object[] row = new Object[5];
			                row[0] = med.getId();
			                row[1] = med.getNombre();
			                row[2] = med.getFormato();
			                row[3] = med.getCaducidad();
			                row[4] = med.getExistencias();
			                modelNuevo.addRow(row);
			            }
			            
			         // --- SEGUNDA TABLA (Sin Stock) ---
			            List<SinStock> listaSS = MedicamentoDAO.selectAllSinStock();
			            DefaultTableModel modelSS = new DefaultTableModel();
			            modelSS.addColumn("ID Agotado");
			            
			            if (listaSS != null) {
			                for (SinStock ss : listaSS) {
			                    Object[] row = { ss.getId() };
			                    modelSS.addRow(row);
			                }
			            }
			            tableSinStock.setModel(modelSS);
			        
			        }
			    });
			    btnMostrar.setBounds(225, 155, 105, 27);
			    frame.getContentPane().add(btnMostrar);
			    
			    JButton btnAadir = new JButton("Añadir");
			    btnAadir.addActionListener(new java.awt.event.ActionListener() {
			        public void actionPerformed(java.awt.event.ActionEvent e) {
			            String cad = txtCaducidad.getText();
			            if (!cad.matches("(ENE|FEB|MAR|ABR|MAY|JUN|JUL|AGO|SEP|OCT|NOV|DIC)(2[7-9])")) {
			                javax.swing.JOptionPane.showMessageDialog(frame, "Caducidad incorrecta (Ej: MAR27)");
			                return;
			            }

			            try {
			                // 1. Creamos el objeto
			                Medicamento m = new Medicamento();
			                m.setNombre(txtNombre.getText());
			                m.setFormato(comboBox.getSelectedItem().toString());
			                m.setCaducidad(txtCaducidad.getText());
			                m.setExistencias(checkExistencias.isSelected());

			                // 2. INSERTAMOS EN TABLA PRINCIPAL
			                medicamentoDAO.insertMed(m);
			                
			                // 3. SI NO HAY STOCK, INSERTAMOS EN LA OTRA
			                // (m.getId() ya tiene el valor que le dio la base de datos)
			                if (!m.getExistencias()) {
			                    SinStock ss = new SinStock(m.getId());
			                    medicamentoDAO.insertSinStock(ss);
			                }

			                // 4. AVISO Y REFRESCAR
			                javax.swing.JOptionPane.showMessageDialog(frame, "Medicamento añadido");
			                btnMostrar.doClick(); 

			            } catch (Exception ex) {
			                javax.swing.JOptionPane.showMessageDialog(frame, "Error al añadir: " + ex.getMessage());
			                ex.printStackTrace();
			            }
			        }
			    });
			    btnAadir.setBounds(342, 155, 105, 27);
			    frame.getContentPane().add(btnAadir);
			    
			    JButton btnBorrar = new JButton("Borrar");
			    btnBorrar.addActionListener(new java.awt.event.ActionListener() {
			        public void actionPerformed(java.awt.event.ActionEvent e) {
			            try {
			                // Pillamos el id del campo de texto
			                int id = Integer.parseInt(txtId.getText());
			                
			                // Llamamos a tu método del DAO
			                medicamentoDAO.deleteMed(id);
			                
			                // Refrescamos la tabla y avisamos
			                btnMostrar.doClick();
			                javax.swing.JOptionPane.showMessageDialog(frame, "Medicamento borrado");
			                
			                // Limpiamos campos
			                txtId.setText("");
			                txtNombre.setText("");
			                txtCaducidad.setText("");
			            } catch (Exception ex) {
			                javax.swing.JOptionPane.showMessageDialog(frame, "Selecciona un medicamento de la tabla");
			            }
			        }
			    });
			    btnBorrar.setBounds(459, 155, 105, 27);
			    frame.getContentPane().add(btnBorrar);
			    
			    JButton btnActualizar = new JButton("Actualizar");
			    btnActualizar.addActionListener(new java.awt.event.ActionListener() {
			        public void actionPerformed(java.awt.event.ActionEvent e) {
			            try {
			                // Validamos caducidad antes de nada (importante en examen)
			                if (!txtCaducidad.getText().matches("(ENE|FEB|MAR|ABR|MAY|JUN|JUL|AGO|SEP|OCT|NOV|DIC)(2[7-9])")) {
			                    javax.swing.JOptionPane.showMessageDialog(frame, "Caducidad incorrecta");
			                    return;
			                }

			                // Creamos el objeto con los datos actuales
			                Medicamento m = new Medicamento();
			                m.setId(Integer.parseInt(txtId.getText())); // IMPRESCINDIBLE para que sepa cuál actualizar
			                m.setNombre(txtNombre.getText());
			                m.setFormato(comboBox.getSelectedItem().toString());
			                m.setCaducidad(txtCaducidad.getText());
			                m.setExistencias(checkExistencias.isSelected());

			                // Llamamos a tu método del DAO (el que usa merge)
			                medicamentoDAO.updateMed(m);

			                medicamentoDAO.updateMed(m); // Ya lo tienes

				             // Actualizamos la tabla secundaria: primero borramos por si acaso y re-insertamos si sigue sin stock
				             medicamentoDAO.deleteSinStock(m.getId()); 
				             if (!m.getExistencias()) {
				                 medicamentoDAO.insertSinStock(new SinStock(m.getId()));
				             }
				             btnMostrar.doClick();
				             javax.swing.JOptionPane.showMessageDialog(frame, "Medicamento actualizado");

			            } catch (Exception ex) {
			                javax.swing.JOptionPane.showMessageDialog(frame, "Error al actualizar: " + ex.getMessage());
			            }
			        }
			    });
			    btnActualizar.setBounds(576, 155, 105, 27);
			    frame.getContentPane().add(btnActualizar);
			    
			   
			}
	}
}
