package servicios;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.lang.reflect.Constructor;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import anotaciones.Columna;
import anotaciones.Id;
import anotaciones.Tabla;
import utilidades.UBean;
import utilidades.UConexion;

public class Consultas {

	public static void guardar(Object o) {
		String consulta = "Insert into `";
		String valores = " VALUES (";
		Class c = o.getClass();
		Tabla t = (Tabla) c.getAnnotation(Tabla.class);
		String nombreTabla = t.nombre();
		consulta = consulta + nombreTabla + "`";
		//System.out.println(consulta);
		List<Field> atributos = UBean.obtenerAtributos(o);
		String columnas = "(";
		for(Field f: atributos) {
			columnas = columnas +"`" +f.getAnnotation(Columna.class).nombre()+"`,";
			valores = valores + "?,";		
		}
		columnas = columnas.substring(0,columnas.lastIndexOf(","));
		valores = valores.substring(0,valores.lastIndexOf(","));
		valores = valores + ")";
		columnas = columnas + ")";
		//System.out.println(consulta + columnas + valores);
		UConexion co = new UConexion();
		Connection con = co.Open(); //el abrir de conexion
		PreparedStatement pst;
		try {
			pst = con.prepareCall(consulta+columnas+ valores);
			int i=1;
			for(Field f : atributos) {
				pst.setObject(i, UBean.ejecutarGet(o,f.getName()) );
				i++;
			}
			pst.execute();
		}
		catch( MySQLIntegrityConstraintViolationException e1){
			System.out.println("Error! Id Duplicado");
		}
		catch (SQLException e ) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		//sacar la ultima coma
		//contactenarle los ? , ? 
	}

	public static boolean modificar(Object o){
		String consulta = "UPDATE `";
		String valores = " VALUES (";
		Integer valorId = null;
		String nombreId = null;
		Class c = o.getClass();
		Tabla t = (Tabla) c.getAnnotation(Tabla.class);
		String nombreTabla = t.nombre();
		consulta = consulta + nombreTabla + "` SET";
		List<Field> atributos = UBean.obtenerAtributos(o);
		String columnas = "";
		for(Field f: atributos) {
			if(f.getAnnotation(Id.class) == null) {
				columnas = columnas +"`" +f.getAnnotation(Columna.class).nombre()+"`=";
				if(f.getType().toString().equals("class java.lang.String")){
					columnas = columnas  + "'";
					columnas = columnas + UBean.ejecutarGet(o,f.getName());
					columnas = columnas  + "' ,";
				}
				else {
					columnas = columnas + UBean.ejecutarGet(o,f.getName());
					columnas = columnas  + ",";
				}

			}
			else {
				nombreId = f.getAnnotation(Columna.class).nombre();
				valorId = (Integer) UBean.ejecutarGet(o,f.getName());
			}
		}

		if(valorId != null) {
			columnas = columnas.substring(0,columnas.lastIndexOf(","));
			columnas = columnas + " WHERE `"+ nombreId +"`";
			columnas = columnas + " = " + valorId;	

			System.out.println(consulta + columnas);
		}
		else {
			return false;
		}

		UConexion co = new UConexion();
		Connection con = co.Open(); //el abrir de conexion
		PreparedStatement pst;
		try {
			pst = con.prepareCall(consulta+columnas);
			pst.execute();
		}
		catch( MySQLIntegrityConstraintViolationException e1){
			System.out.println("Error! Id Duplicado");
		}
		catch (SQLException e ) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//System.out.println(columnas);
		return true;
		//UPDATE `persona` SET `nombre`= "ejemplo",`apellido`="de un apdate",`dni`=696969 WHERE `id`= 1
	}

	public static boolean eliminar(Object o) {
		Class c = o.getClass();
		List<Field> atributos = UBean.obtenerAtributos(o);
		Integer idAux = null;
		String consulta;
		String nombreId = null;
		Tabla t = (Tabla) c.getAnnotation(Tabla.class);
		String nombreTabla = t.nombre();
		for(Field f: atributos) {
			//System.out.println(f.getAnnotation(Columna.class).nombre());
			if(f.getAnnotation(Id.class) != null) {
				System.out.println("estoy en id");
				idAux = (Integer) UBean.ejecutarGet(o,f.getName());
				nombreId = f.getAnnotation(Columna.class).nombre();
			}

		}
		if(idAux != null) {
			System.out.println("tengo un id" + idAux);
			consulta = "DELETE FROM `" + nombreTabla +"` WHERE`" + nombreId +"` =" + idAux;
			UConexion co = new UConexion();
			Connection con = co.Open(); //el abrir de conexion
			PreparedStatement pst;
			try {
				System.out.println(consulta);
				pst = con.prepareCall(consulta);
				pst.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}
		else {
			return false;
		}
		return true;
	}

	/*obtener por id recibe una clase y un id para buscar dentro de esa clase, consulta en la base de datos por ese id
	 * return: retorna un objeto de la clase buscada
	 * 
	 */
	public static Object obtenerPorId(Class c, Object id) {
		//SELECT `nombre`, `apellido`, `dni`, `id` FROM `persona` WHERE `id` = 2
		UConexion co = new UConexion();
		Connection con = co.Open(); //el abrir de conexion
		PreparedStatement pst;
		Object obj = null;
		try {
			Field[] atributos = c.getDeclaredFields();
			String consulta = "SELECT ";
			String columnas = "";
			Tabla t = (Tabla) c.getAnnotation(Tabla.class);
			String nombreTabla = t.nombre();
			String from = "FROM `" + nombreTabla + "`";
			String where = " WHERE ";
			String nombreID = null;
			//System.out.println(from);
			//System.out.println(c.toString());
			for(Field f: atributos) {
				if(f.getAnnotation(Id.class) == null) {
					columnas = columnas  + "`";
					columnas = columnas + f.getAnnotation(Columna.class).nombre();
					columnas = columnas  + "` ,";
				}
				else {
					columnas = columnas  + "`";
					columnas = columnas + f.getAnnotation(Columna.class).nombre();
					columnas = columnas  + "` ,";
					nombreID = f.getAnnotation(Columna.class).nombre();
				}
				//System.out.println(f.getName());

			}
			where = where + "`" + nombreID + "` = " + id.toString() ;
			columnas = columnas.substring(0,columnas.lastIndexOf(","));
			//System.out.println(consulta + columnas + from + where);
			pst = con.prepareCall(consulta + columnas + from + where);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				//System.out.println(atributos.length);
				for(Constructor constructor :c.getConstructors()) {
					if(constructor.getParameterCount() == 0) {
						try {
							//System.out.println("hay con contsrucctor con 4");
							obj = constructor.newInstance();
							int i = 1;
							for(Field f : atributos) {

								UBean.ejecutarSet(obj, f.getName(),rs.getObject(i));				
								i++;
							}

						} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
								| InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				return obj;
				//System.out.println(obj.toString());

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}


}
