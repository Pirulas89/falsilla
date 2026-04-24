package falsilla;

package pruebajdvc;

import java.awt.EventQueue;
import java.awt.Frame;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.mysql.cj.jdbc.exceptions.MySQLQueryInterruptedException;
import com.mysql.cj.result.Row;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class ConnectionSingleton {
	private static Connection con;

	public static Connection getConnection() throws SQLException {
		String url = "jdbc:mysql://127.0.0.1:3307/practicajdvc";
		String user = "alumno";
		String password = "alumno";
		if (con == null || con.isClosed()) {
			con = DriverManager.getConnection(url, user, password);
		}
		return con;
	}
}

public class Pruebajdvc {

	private JFrame frame;
	private JTextField txtidDefault;
	private JTextField txtNombreDefault;
	private JTextField txtEnteroDefault;
	private JTable tablaDefault;


	static void cargarTabla(DefaultTableModel cargarTabla, Frame f) {

		try {
			Connection con = ConnectionSingleton.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM ingrediente");
			cargarTabla.setRowCount(0);
			while (rs.next()) {
				Object[] row = new Object[3];
				row[0] = rs.getInt("idIngrediente");
				row[1] = rs.getString("nombre");
				row[2] = rs.getInt("calorias");
				cargarTabla.addRow(row);
			}
			rs.close();
			stmt.close();
			con.close();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(f, e.getMessage(), "Al Cargar la tabla", JOptionPane.ERROR_MESSAGE);
		}
	}



	static void borrarTextoDeTxt(JTextField id, JTextField nombre, JTextField x) {
		id.setText("");
		nombre.setText("");
		x.setText("");
	}

	public boolean validarCamposConInt( JTextField txt1, JTextField txt2, JFrame frame) {

	    // 1. Comprobar si están vacíos
	    if ( txt1.getText().isEmpty() || txt2.getText().isEmpty()) {
	        JOptionPane.showMessageDialog(frame, "Todos los campos deben estar rellenos","Campo De Texto",JOptionPane.ERROR_MESSAGE);
	        return false;
	    }

	    // 2. Comprobar que txt2 sea solo texto (letras y espacios)
	    if (!txt1.getText().matches("^[a-z,A-Z]+$")) {
	        JOptionPane.showMessageDialog(frame, "El segundo campo solo puede contener letras","Campo De Texto",JOptionPane.ERROR_MESSAGE);
	        return false;
	    }

	    // 3. Comprobar que txt3 sea número
	    try {
	        Integer.parseInt(txt2.getText());
	    } catch (NumberFormatException e) {
	        JOptionPane.showMessageDialog(frame, "El tercer campo debe ser un número","Campo numerico",JOptionPane.ERROR_MESSAGE);
	        return false;
	    }

	    return true;
	}


	public boolean validarCamposStrings( JTextField txt2, JTextField txt3, JFrame frame) {

		// Comprobar si están vacíos
		if  (txt2.getText().isEmpty() || txt3.getText().isEmpty()) {
			JOptionPane.showMessageDialog(frame, "Todos los campos deben estar rellenos","Campo Vacio",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		
		if(!txt2.getText().matches("^\\w+$")) {
			JOptionPane.showMessageDialog(frame,"El campo x tiene que ser un String", "Error en x", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if(!txt3.getText().matches("^\\w+$")) {
			JOptionPane.showMessageDialog(frame,"El campo x tiene que ser un String", "Error en x", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Pruebajdvc window = new Pruebajdvc();
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
	public Pruebajdvc() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1120, 493);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("id");
		lblNewLabel.setBounds(62, 234, 46, 14);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Nombre :");
		lblNewLabel_1.setBounds(62, 277, 46, 14);
		frame.getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("Calorias :");
		lblNewLabel_1_1.setBounds(62, 317, 46, 14);
		frame.getContentPane().add(lblNewLabel_1_1);

		txtidDefault = new JTextField();
		txtidDefault.setBounds(139, 231, 86, 20);
		txtidDefault.setEditable(false);
		frame.getContentPane().add(txtidDefault);
		txtidDefault.setColumns(10);

		txtNombreDefault = new JTextField();
		txtNombreDefault.setBounds(139, 274, 86, 20);
		frame.getContentPane().add(txtNombreDefault);
		txtNombreDefault.setColumns(10);

		txtEnteroDefault = new JTextField();
		txtEnteroDefault.setBounds(139, 314, 86, 20);
		txtEnteroDefault.setColumns(10);
		frame.getContentPane().add(txtEnteroDefault);

		// Crear Tabla completa

		DefaultTableModel modelDefault = new DefaultTableModel();

		
		modelDefault.addColumn("ID");
		modelDefault.addColumn("Nombre");
		modelDefault.addColumn("Calorias");

		cargarTabla(modelDefault, frame);

		tablaDefault = new JTable(modelDefault);
		tablaDefault.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tablaDefault.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			
				//cambiar
				int indexDefault = tablaDefault.getSelectedRow();
				TableModel modelDefault = tablaDefault.getModel();
				// ID NAME AGE CITY
				txtidDefault.setText(modelDefault.getValueAt(indexDefault, 0).toString());
				txtNombreDefault.setText(modelDefault.getValueAt(indexDefault, 1).toString());
				txtEnteroDefault.setText(modelDefault.getValueAt(indexDefault, 2).toString());

			}
		});
		
		JScrollPane scrollPane = new JScrollPane(tablaDefault);
		scrollPane.setBounds(26, 35, 261, 137);
		frame.getContentPane().add(scrollPane);
		scrollPane.setViewportView(tablaDefault);
		

//Aqui acaba la creacion de la tabla solo hay que cambiar los parametros 
	

		JButton btnDefault = new JButton("Borrar");
		btnDefault.setBounds(252, 370, 89, 23);
		btnDefault.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (txtidDefault.getText().isEmpty() == false) {

					try {
						Connection con = ConnectionSingleton.getConnection();

						PreparedStatement eliminarDefault = con
								.prepareStatement("DELETE FROM Default WHERE Default = ?");
						eliminarDefault.setInt(1, Integer.valueOf(txtidDefault.getText()));
						int rowsDeleted = eliminarDefault.executeUpdate();
						eliminarDefault.close();
						con.close();

						borrarTextoDeTxt(txtidDefault, txtNombreDefault, txtEnteroDefault);
						cargarTabla(modelDefault, frame);
						
					} catch (SQLException t) {

						JOptionPane.showMessageDialog(frame, t.getMessage(), "Error al Eliminar Default",
								JOptionPane.PLAIN_MESSAGE);
					}

				}else {
					JOptionPane.showMessageDialog(frame,"El campo Id vacio", "Error al Eliminar Default",
							JOptionPane.ERROR_MESSAGE);
					
				}

			}
		});
		frame.getContentPane().add(btnDefault);

		JButton btnDefaultActualizar = new JButton("Actualizar");
		btnDefaultActualizar.setBounds(139, 370, 89, 23);
		btnDefaultActualizar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (validarCamposConInt( txtNombreDefault, txtEnteroDefault,
						frame) == true) {

					try {

						Connection con = ConnectionSingleton.getConnection();

						PreparedStatement actualizarbtnDefaultActualizar = con.prepareStatement(
								"UPDATE ingrediente SET nombre = ?, calorias = ? WHERE idIngrediente = ?");
						actualizarbtnDefaultActualizar.setString(1, txtNombreDefault.getText());
						actualizarbtnDefaultActualizar.setInt(2, Integer.valueOf(txtEnteroDefault.getText()));
						actualizarbtnDefaultActualizar.setInt(3, Integer.valueOf(txtidDefault.getText()));
						int rowsUpdated = actualizarbtnDefaultActualizar.executeUpdate();
						actualizarbtnDefaultActualizar.close();

						con.close();
						borrarTextoDeTxt(txtidDefault, txtNombreDefault, txtEnteroDefault);
						cargarTabla(modelDefault, frame);

					} catch (SQLException t) {

						JOptionPane.showMessageDialog(frame, t.getMessage(), "Error de Conexion",
								JOptionPane.ERROR_MESSAGE);
					}

				}

			}
		});
		frame.getContentPane().add(btnDefaultActualizar);

		JButton btnInsertar = new JButton("Insertar");
		btnInsertar.setBounds(26, 370, 89, 23);
		btnInsertar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {



					if (validarCamposConInt(txtNombreDefault, txtEnteroDefault,
							frame) == true) {

						try {
							Connection con = ConnectionSingleton.getConnection();

							PreparedStatement insertarDefault = con
									.prepareStatement("INSERT INTO Default ( nombre, x) VALUES ( ?, ?)");

							insertarDefault.setString(1, txtNombreDefault.getText());
							insertarDefault.setInt(2, Integer.valueOf(txtEnteroDefault.getText()));
							int rowsInserted = insertarDefault.executeUpdate();
							insertarDefault.close();
							con.close();
							borrarTextoDeTxt(txtidDefault, txtNombreDefault, txtEnteroDefault);
							cargarTabla(modelDefault, frame);

						} catch (SQLException t) {

							JOptionPane.showMessageDialog(frame, t.getMessage(), "Error de Conexion",
									JOptionPane.ERROR_MESSAGE);
						}
					
					}

			}
		});
		frame.getContentPane().add(btnInsertar);

		

	}

}