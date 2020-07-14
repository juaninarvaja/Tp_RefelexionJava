import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Scanner;

import servicios.Consultas;
import utilidades.UBean;

public class Program {
	public static void main (String[] args) {


		Persona p1 = new Persona();
		p1.setNombre("juan");
		p1.setApellido("prueba");
		p1.setDocumento( 123456);
		p1.setId(101);
		
		Maquina m1 = new Maquina("Jhon","Dhere",1001);
		
		
		boolean seguir = true;

		System.out.println("Seleccione una opción a probar");
		System.out.println("1.Guardar objeto prueba en la base de datos");
		System.out.println("2. Modificar los datos del objeto de prueba");
		System.out.println("3. Eliminar objeto de prueba");
		System.out.println("4.traer objeto por id");
		System.out.println("5.salir");
		int opcion = 0;
		Scanner myObj = new Scanner(System.in);
		opcion =  Integer.valueOf(myObj.nextLine());
		System.out.println(opcion);
		switch(opcion) {
		case 1:
			Consultas.guardar(p1);
			Consultas.guardar(m1);
			break;
		case 2:
			Consultas.modificar(p1);
			Consultas.modificar(m1);
			break;
		case 3:
			Consultas.eliminar(p1);
			Consultas.eliminar(m1);
			break;
		case 4: 
			if(Consultas.obtenerPorId(Persona.class, 101) != null) {
				Persona p3 = (Persona) Consultas.obtenerPorId(Persona.class, 101);
				System.out.println(p3.toString());
			}
			else {
				System.out.println("error no existe persona con ese id");
			}
			if(Consultas.obtenerPorId(Maquina.class, 1001) != null) {
			Maquina m3 = (Maquina) Consultas.obtenerPorId(Maquina.class, 1001);
			System.out.println(m3.toString());
			}
			else {
				System.out.println("error no e maquina xiste con ese id");
			}
			break;
		case 5:
			System.out.println("entro al caso 5");
			seguir = false;
			break;
		default : seguir = false;
		System.out.println("salgo por default");
		break;
		}


	}
}
