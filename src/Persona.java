import anotaciones.Columna;
import anotaciones.Id;
import anotaciones.Tabla;

@Tabla(nombre = "persona")
public class Persona {
	@Columna(nombre = "nombre")
	private String nombre;
	@Columna(nombre = "apellido")
	private String apellido;
	@Columna(nombre = "dni")
	private Integer documento;
	@Id
	@Columna(nombre = "id")
	private Integer id;
	public Persona(String nombre, String apellido, Integer documento) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.documento = documento;
	}
	public Persona(String nombre, String apellido,Integer documento, Integer id) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.documento = documento;
		this.id = id;
	}
	public Persona() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public Integer getDocumento() {
		return documento;
	}
	public void setDocumento(Integer documento) {
		//System.out.println("entro al set Documento");
		this.documento = documento;
	}
	@Override
	public String toString() {
		return "Persona [nombre=" + nombre + ", apellido=" + apellido + ", documento=" + this.documento + "]" +", id=" + id;
	}
	
	
}
