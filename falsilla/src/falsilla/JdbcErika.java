package falsilla;

package repasoJDBC;


//NO FUNCIONA COMBOBOX Y CHECKBOX Y SEGUNDA TABLA
//en la cosa esa de errores (ENE|FEB|MAR...)2[7-9]
//BOOLEAN -> tinyInt
//checkBox.selected


import java.awt.EventQueue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.event.AncestorListener;
import javax.swing.event.AncestorEvent;
import javax.swing.JCheckBox;

class ConnectionSingleton {
	private static Connection con;

	public static Connection getConnection() throws SQLException {
		String url = "jdbc:mysql://192.168.1.212:3306/farmacia";
		String user = "alumno";
		String password = "alumno";
		if (con == null || con.isClosed()) {
			con = DriverManager.getConnection(url, user, password);
		}
		return con;
	}
}




public class Farmacia {

	private JFrame frame;
	private JTextField txtNombre;
	private JTextField txtCaducidad;
	private JTable table;
	private JTextField txtId;
	private JTable table3;

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

	public Farmacia() {
		initialize();
	}
	
	private void actualizarSinStock(int id, boolean existencias) throws SQLException {
	    Connection con = ConnectionSingleton.getConnection();

	    if (!existencias) {
	        PreparedStatement ps = con.prepareStatement(
	            "INSERT INTO sin_stock (idmedicamentos) VALUES (?)"
	        );
	        ps.setInt(1, id);
	        ps.executeUpdate();
	        ps.close();
	    } else {
	        PreparedStatement ps = con.prepareStatement(
	            "DELETE FROM sin_stock WHERE idmedicamentos = ?"
	        );
	        ps.setInt(1, id);
	        ps.executeUpdate();
	        ps.close();
	    }
	}
	
	private void cargarSinStock(DefaultTableModel model2) {
	    try {
	        Connection con = ConnectionSingleton.getConnection();
	        Statement stmt = con.createStatement();

	        ResultSet rs = stmt.executeQuery("SELECT * FROM sin_stock");

	        model2.setRowCount(0);

	        while (rs.next()) {
	            Object[] row = new Object[1];
	            row[0] = rs.getInt("idmedicamentos");
	            model2.addRow(row);
	        }

	        rs.close();
	        stmt.close();

	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	}
	
	private boolean validarCaducidad(String cad) {

	    return cad.matches("(ENE|FEB|MAR|ABR|MAY|JUN|JUL|AGO|SEP|OCT|NOV|DIC)(2[7-9])");
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 524, 387);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("id");
		model.addColumn("Nombre");
		model.addColumn("Formato");
		model.addColumn("Caducidad");
		model.addColumn("Existencias");


		try {
			Connection con = ConnectionSingleton.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM medicamentos");
			while (rs.next()) {
				Object[] row = new Object[5];
				row[0] = rs.getInt("idmedicamentos");
				row[1] = rs.getString("nombre");
				row[2] = rs.getString("formato");
				row[3] = rs.getString("caducidad");
				row[4] = rs.getBoolean("existencias");
				model.addRow(row);
			}
			rs.close();
			stmt.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		
		DefaultTableModel model2 = new DefaultTableModel();
		model2.addColumn("id");
		
		table3 = new JTable(model2);
		JScrollPane scrollPane2 = new JScrollPane(table3);
		scrollPane2.setBounds(260, 12, 215, 112);
		frame.getContentPane().add(scrollPane2);
		
		table = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(26, 12, 215, 112);
		frame.getContentPane().add(scrollPane);
		
		
		JLabel lblNombre = new JLabel("Nombre: ");
		lblNombre.setBounds(26, 165, 60, 17);
		frame.getContentPane().add(lblNombre);
		
		txtId = new JTextField();
		txtId.setEditable(false);
		txtId.setBounds(97, 136, 114, 21);
		frame.getContentPane().add(txtId);
		txtId.setColumns(10);
		
		JLabel lblCaducidad = new JLabel("Caducidad:");
		lblCaducidad.setBounds(26, 223, 70, 17);
		frame.getContentPane().add(lblCaducidad);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(97, 163, 114, 21);
		frame.getContentPane().add(txtNombre);
		
		txtCaducidad = new JTextField();
		txtCaducidad.setBounds(97, 221, 114, 21);
		frame.getContentPane().add(txtCaducidad);
		
		JCheckBox checkExis = new JCheckBox("Existencias");
		checkExis.setBounds(62, 248, 114, 25);
		frame.getContentPane().add(checkExis);
		
		JLabel lblFormato = new JLabel("Formato:");
		lblFormato.setBounds(26, 194, 60, 17);
		frame.getContentPane().add(lblFormato);
		
		
		JComboBox comboBox = new JComboBox();
      comboBox.setModel(new DefaultComboBoxModel(new String[] { "Pastillas", "Jarabe", "Pomada" }));

		comboBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		comboBox.setBounds(97, 189, 114, 26);
		frame.getContentPane().add(comboBox);
		
		table.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        int fila = table.getSelectedRow();
		        txtId.setText(table.getValueAt(fila, 0).toString());
		        txtNombre.setText(table.getValueAt(fila, 1).toString());
		        txtCaducidad.setText(table.getValueAt(fila, 3).toString());

		        comboBox.setSelectedItem(table.getValueAt(fila, 2).toString());
		        checkExis.setSelected((Boolean) table.getValueAt(fila, 4));
		    }
		});
		
		
		JButton btnMostrar = new JButton("Mostrar");
		btnMostrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Connection con = ConnectionSingleton.getConnection();
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery("SELECT * FROM medicamentos");
					model.setRowCount(0);
					while (rs.next()) {
						Object[] row = new Object[5];
						row[0] = rs.getInt("idmedicamentos");
						row[1] = rs.getString("nombre");
						row[2] = rs.getString("formato");
						row[3] = rs.getString("caducidad");
						row[4] = rs.getBoolean("existencias");
						model.addRow(row);
					}
					
					cargarSinStock(model2);
					rs.close();
					stmt.close();
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnMostrar.setBounds(136, 320, 105, 27);
		frame.getContentPane().add(btnMostrar);
			
		
		JButton btnAñadir = new JButton("Añadir");
		btnAñadir.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {

		        try {

		            // 1. VALIDACIONES PRIMERO
		            if (txtNombre.getText().isEmpty() || txtCaducidad.getText().isEmpty()) {
		                JOptionPane.showMessageDialog(null, "Rellena todos los campos");
		                return;
		            }

		            if (!validarCaducidad(txtCaducidad.getText())) {
		                JOptionPane.showMessageDialog(null, "Caducidad incorrecta (ENE27 - DIC29)");
		                return;
		            }

		            // 2. CONEXIÓN Y SQL
		            Connection con = ConnectionSingleton.getConnection();

		            PreparedStatement ins_pstmt = con.prepareStatement(
		                "INSERT INTO medicamentos (nombre, formato, caducidad, existencias) VALUES (?,?,?,?)",
		                Statement.RETURN_GENERATED_KEYS
		            );

		            ins_pstmt.setString(1, txtNombre.getText());
		            ins_pstmt.setString(2, comboBox.getSelectedItem().toString());
		            ins_pstmt.setString(3, txtCaducidad.getText());
		            ins_pstmt.setBoolean(4, checkExis.isSelected());

		            ins_pstmt.executeUpdate();

		            // 3. OBTENER ID
		            ResultSet rs = ins_pstmt.getGeneratedKeys();
		            int idGenerado = -1;

		            if (rs.next()) {
		                idGenerado = rs.getInt(1);
		            }

		            rs.close();
		            ins_pstmt.close();

		            // 4. SIN STOCK
		            if (idGenerado != -1) {
		                actualizarSinStock(idGenerado, checkExis.isSelected());
		            }

		            // 5. UI
		            btnMostrar.doClick();
		            cargarSinStock(model2);

		            JOptionPane.showMessageDialog(null, "Fármaco añadido");

		        } catch (SQLException ex) {
		            JOptionPane.showMessageDialog(null, "Error SQL: " + ex.getMessage());
		        }
		    }
		});
			
		btnAñadir.setBounds(26, 281, 88, 27);
		frame.getContentPane().add(btnAñadir);
		
		JButton btnBorrar = new JButton("Borrar");
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Connection con = ConnectionSingleton.getConnection();
					PreparedStatement dele_pstmt = con.prepareStatement("DELETE FROM medicamentos WHERE idmedicamentos = ?");
					dele_pstmt.setInt(1, Integer.parseInt(txtId.getText()));
					dele_pstmt.executeUpdate();
					dele_pstmt.close();
					
					cargarSinStock(model2);
					btnMostrar.doClick();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Selecciona un medicamento válido", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnBorrar.setBounds(136, 281, 105, 27);
		frame.getContentPane().add(btnBorrar);
		
		JButton btnActualizar = new JButton("Actualizar");
		btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (txtNombre.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "El nombre no puede estar vacío", "Error", JOptionPane.ERROR_MESSAGE);
					if (!validarCaducidad(txtCaducidad.getText())) {
						JOptionPane.showMessageDialog(null, "Caducidad incorrecta");
						return;
					}
					
					} else {
						Connection con = ConnectionSingleton.getConnection();
						PreparedStatement upd_pstmt = con.prepareStatement("UPDATE medicamentos SET nombre = ?, formato = ?, caducidad = ?, existencias = ? WHERE idmedicamentos = ?");
						upd_pstmt.setString(1, txtNombre.getText());
						upd_pstmt.setString(2, comboBox.getSelectedItem().toString());
						upd_pstmt.setString(3, txtCaducidad.getText());
						upd_pstmt.setBoolean(4, checkExis.isSelected());
						upd_pstmt.setInt(5, Integer.parseInt(txtId.getText()));
						upd_pstmt.executeUpdate();
						upd_pstmt.close();
						
						actualizarSinStock(
							    Integer.parseInt(txtId.getText()),
							    checkExis.isSelected()
							);
						cargarSinStock(model2);
						btnMostrar.doClick();
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Error al actualizar: verifique los datos", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnActualizar.setBounds(26, 320, 105, 27);
		frame.getContentPane().add(btnActualizar);
		
		JLabel lblId = new JLabel("Id:");
		lblId.setBounds(26, 136, 60, 17);
		frame.getContentPane().add(lblId);
		
		
		
		
		
		
		
		
	}
}