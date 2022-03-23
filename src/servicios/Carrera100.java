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
import java.lang.Thread;

@Singleton
@Path("Carrera100")//ruta a la clase
public class Carrera100 {
	private static Semaphore s_preparados = new Semaphore(0);
	private static Semaphore s_listos = new Semaphore(0);
	static int numeroAtletas = 4;		//Pasar por parámetro a reinicio
	static List<String> listaAtletasResultado = null;
	static List<Atleta> listaAtletas = null;
	
	@GET //tipo de petición HTTP
	@Produces(MediaType.TEXT_PLAIN)
	@Path("reinicio")
	public String reinicio() throws Exception
	{
		Contador.restart();
		listaAtletasResultado = new ArrayList<String>();
		listaAtletas = new ArrayList<Atleta>();
		
		s_preparados.drainPermits();
		s_listos.drainPermits();
			
		for(int i=0;i<numeroAtletas;i++) {
			listaAtletas.add(new Atleta(i));
			listaAtletas.get(i).start();
		}
		
		return "La carrera se ha iniciado.";
	}
	
	@Path("preparados")
	public int preparados() {
		
		Contador.increment();
		if(Contador.value() != numeroAtletas) {
			try {
				Thread.sleep(1000);
				s_preparados.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			Contador.restart();
			s_preparados.release(numeroAtletas);
			System.out.println("Preparados");
		}

		return 0;	
	}
	
	@Path("listos")
	public int listos() {
		
		Contador.increment();
		if(Contador.value() != numeroAtletas) {
			try {
				Thread.sleep(1000);
				s_listos.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			Contador.restart();
			s_listos.release(numeroAtletas);
			System.out.println("Listos");
		}
		
		return 0;
	}
	
	
	//@GET
	//@Produces(MediaType.TEXT_PLAIN)
	@Path("llegada")
	public int llegada(@DefaultValue("none") @QueryParam(value = "item") final List<String> item) {
		
		listaAtletasResultado.add("El atleta con dorsal: " + item.get(0) + "ha tardado: " + item.get(1));
		return 0;
	}
	
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("resultados")
	public String resultados(){
		String resultado = "";
		for(String s : listaAtletasResultado) {
			resultado = resultado + s + "/n";
		}
		
		return resultado;
	}
}
