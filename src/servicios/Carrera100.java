package servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Singleton
@Path("Carrera100")//ruta a la clase
public class Carrera100 {
	public static Semaphore s_preparados = new Semaphore(0);
	public static Semaphore s_listos = new Semaphore(0);
	static int numeroAtletas = 0;		//Pasar por parámetro a reinicio
	static List<String> listaAtletasResultado = null;
	static List<Atleta> listaAtletas = null;
	static long tiempoInicioCarrera;
	
	@GET //tipo de petición HTTP
	@Produces(MediaType.TEXT_PLAIN)
	@Path("reinicio")
	public String reinicio(@QueryParam(value = "numAtletas")int numAtletas)
	{
		Contador.restart();
		listaAtletasResultado = new ArrayList<String>();
		numeroAtletas = numAtletas;
		
		s_preparados.drainPermits();	//Pone todos los permits a 0, da igual el número
		s_listos.drainPermits();
		
		System.out.println("La Carrera se ha reiniciado con "+numeroAtletas+" atletas.\nEsperando a los atletas");

		return "";
	}
	
	@GET //tipo de petición HTTP
	@Produces(MediaType.TEXT_PLAIN)
	@Path("preparados")
	public String preparados() {
		
		Contador.increment();
		if(Contador.value() != numeroAtletas) {
			try {
				s_preparados.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			Contador.restart();
			s_preparados.release(numeroAtletas);
			System.out.println("Preparados");
		}

		return "";	
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("listos")
	public String listos() {
		
		Contador.increment();
		if(Contador.value() != numeroAtletas) {
			try {
				s_listos.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}								
		} else {
			Contador.restart();
			s_listos.release(numeroAtletas);
			System.out.println("Listos");
			tiempoInicioCarrera = System.currentTimeMillis();
			System.out.println("Ya");
		}
		
		return "";
	}
	
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("llegada")
	public String llegada(@QueryParam(value = "dorsal")int dorsal) {
		
		listaAtletasResultado.add("El atleta con dorsal: " + dorsal + " ha tardado: " + ((System.currentTimeMillis() - tiempoInicioCarrera)/1000) + " segundos");
		return "";
	}
	
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("resultados")
	public String resultados(){
		Contador.increment();
		if(Contador.value() == numeroAtletas) {
			for(String s : listaAtletasResultado) {
				System.out.println(s);
			}
		}
		return "";
	}
}
