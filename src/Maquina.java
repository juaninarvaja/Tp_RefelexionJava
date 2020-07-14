import anotaciones.Columna;
import anotaciones.Id;
import anotaciones.Tabla;

@Tabla(nombre = "maquina")
public class Maquina {
	@Columna(nombre = "marca")
	private String marca;
	@Columna(nombre = "modelo")
	private String modelo;
	@Id
	@Columna(nombre = "id")
	private Integer numeroChasis;

	public Maquina() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Maquina(String marca, String modelo, Integer numeroChasis) {
		super();
		this.marca = marca;
		this.modelo = modelo;
		this.numeroChasis = numeroChasis;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public Integer getNumeroChasis() {
		return numeroChasis;
	}
	public void setNumeroChasis(Integer numeroChasis) {
		this.numeroChasis = numeroChasis;
	}
	@Override
	public String toString() {
		return "Maquina [marca=" + marca + ", modelo=" + modelo + ", numeroChasis= "+ numeroChasis + "]";
	}
	
	
}