package utilidades;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

//punto 4, 
public class UConexion {
	// un metodo para abrir la conexion y uno para cerrar
	// si es singleton mucho mejor
	private static Connection connection;

	@SuppressWarnings("finally")
	public Connection Open() {
		try {
			if (connection == null || connection.isClosed()) {
				ResourceBundle rsb = ResourceBundle.getBundle("framework");
				// System.out.println(rsb.getString("driver"));
				try {
					try {
						Class.forName(rsb.getString("driver"));
						connection = DriverManager.getConnection(rsb.getString("url"), rsb.getString("user"),
								rsb.getString("pass"));
						System.out.println("abrio correctamente!");
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return null;
					}
				} finally {
					return connection;
				}
			} else {
				return connection;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;

	}

	public boolean Close() {
		try {
			if (connection != null || connection.isClosed()) {
				try {
					//System.out.println("cierro consulta");
					connection.close();
					return true;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

	public ArrayList<String> obtenerPropiedades() {
		return null;

	}
}
