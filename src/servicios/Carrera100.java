package servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import javax.inject.Singleton;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Singleton
@Path("Carrera100")//ruta a la clase
public class Carrera100 {
	private static Semaphore s_preparados = new Semaphore(0);
	private static Semaphore s_listos = new Semaphore(0);
	static int numPreparados=0;
	static int numListos=0;
	static int numeroAtletas = 4;
	static List<String> listaAtletasResultado = null;
	
	@GET //tipo de petición HTTP
	@Produces(MediaType.TEXT_PLAIN)
	@Path("reinicio")
	public String reinicio() throws Exception
	{
		numPreparados=0;
		numListos=0;
		Contador.restart();
		listaAtletasResultado = new ArrayList<String>();
		
		Atleta atleta[] = new Atleta[numeroAtletas];
	
		for(int i=0;i<numeroAtletas;i++) {
			atleta[i] = new Atleta(i+1);
		}
		for(int i=0;i<numeroAtletas;i++) {
			atleta[i].start();
		}
		//Cambiar ya que se queda pillado en el bucle///////////
		while(Contador.value() != numeroAtletas) {}
		System.out.println("Preparados!!!");
		Contador.restart();
		s_preparados.release(numeroAtletas);
		while(Contador.value() != numeroAtletas) {}
		System.out.println("Listos!!!");
		s_preparados.release(numeroAtletas);
		System.out.println("YA!!!");
		////////////////////////////////////////////////////////
		return "La carrera se ha reiniciado.";
	}
	
	@Path("preparados")
	public int preparados() throws InterruptedException {
		
		Contador.increment();
		s_preparados.acquire();
		
		return 0;
		
	}
	@Path("listos")
	public int listos() throws InterruptedException {
		Contador.increment();
		s_listos.acquire();
		return 0;
		
	}
	//Hola
	
	@Path("llegada")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public int llegada(@DefaultValue("none") @QueryParam(value="num") String num) {
		
		listaAtletasResultado.add("El atleta con dorsal: " + num + "ha tardado: " + "");
		return 0;
		
	}
	
	@Path("resultados")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String resultados(){
		String resultado = "";
		for(String s : listaAtletasResultado) {
			resultado = resultado + s + "/n";
		}
		return resultado;
		
	}
}
