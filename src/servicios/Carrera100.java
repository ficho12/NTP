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
	static int x = 0;
	
	@GET //tipo de petición HTTP
	@Produces(MediaType.TEXT_PLAIN)
	@Path("reinicio")
	public String reinicio() throws Exception
	{
		numPreparados=0;
		numListos=0;
		x = Contador.restart();
		listaAtletasResultado = new ArrayList<String>();
		
		Atleta atleta[] = new Atleta[numeroAtletas];
	
		for(int i=0;i<numeroAtletas;i++) {
			atleta[i] = new Atleta(i+1);
		}
		for(int i=0;i<numeroAtletas;i++) {
			atleta[i].start();
		}
		
		return "La carrera se ha iniciado.";
	}
	
	@Path("preparados")
	public int preparados() throws InterruptedException {
		
		x = Contador.increment();
		if(Contador.value() != numeroAtletas) {
			s_preparados.acquire();
		} else {
			s_preparados.release(numeroAtletas-1);
			x = Contador.restart();
			System.out.println("Preparados");
		}

		return 0;
		
	}
	@Path("listos")
	public int listos() throws InterruptedException {
		
		x = Contador.increment();
		if(Contador.value() != numeroAtletas) {
			s_listos.acquire();
		} else {
			s_preparados.release(numeroAtletas-1);
			x = Contador.restart();
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
