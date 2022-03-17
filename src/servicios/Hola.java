package servicios;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
@Path("hola")//ruta a la clase
public class Hola {
	
	@GET //tipo de petición HTTP
	@Produces(MediaType.TEXT_PLAIN) //tipo de texto devuelto
	@Path("saludo") //ruta al método
	public String saludo() //el método debe retornar String
	{
	return "Hola gente!!";
	}
	
}
