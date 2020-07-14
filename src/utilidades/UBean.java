package utilidades;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class UBean {

	public static ArrayList<Field> obtenerAtributos(Object o) {
		ArrayList<Field> atributos = new ArrayList<>();
		if (o != null) {
			Class z = o.getClass();
			// Class clazz = null;

			try {
				// System.out.println(z.getName());
				// clazz = Class.forName(o.getClass().toString());
				for (Field f : z.getDeclaredFields()) {
					if (f.getName() != null) {
						atributos.add(f);
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("sale por la excep");
				e.printStackTrace();
			}
		}
		return atributos;

	}

	public static void ejecutarSet(Object o, String att, Object valor) {


		if (o != null && valor != null) {

			att = att.substring(0, 1).toUpperCase() + att.substring(1);
			Class z = o.getClass();
			for (Method m : z.getDeclaredMethods()) {
				if (m.getName().startsWith("set") && m.getName().contains(att)) {
					//if (m.getParameterTypes()[0].getSimpleName().equals("String")) {

					//paramet = valor;
					//System.out.println("el parametro es" + paramet);
					try {

						//System.out.println(m.getParameterTypes()[0]);
						m.invoke(o,valor);


						//m.invoke(o,valor);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//}
					//}
				}
			}
		}
	}

	public static Object ejecutarGet(Object o, String att) {
		//System.out.println(att);
		// retorno el valor que me llega.
		Object obj = null;
		if (o != null && att != null) {
			att = att.substring(0, 1).toUpperCase() + att.substring(1);
			Class z = o.getClass();
			for (Method m : z.getDeclaredMethods()) {
				if (m.getParameterCount() == 0) {
					if (m.getName().startsWith("get") && m.getName().contains(att) ) {
						try {
							obj = m.invoke(o, new Object[0]);
							//System.out.println("retorno" + obj);
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
		return obj;

	}
}
