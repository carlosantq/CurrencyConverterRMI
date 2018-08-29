package br.ufrn.imd.ccrmi;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * Classe criada para rodar o servidor remoto.
 * @author carlosant
 * @author pedrohcavalcante
 */
public class Server {
	
	/**
	 * Método principal.
	 * @param args
	 * @throws RemoteException
	 * @throws MalformedURLException
	 */
	public static void main (String [] args) throws RemoteException, MalformedURLException {
		//Execução do módulo de referência remota
		LocateRegistry.createRegistry(1099);
		
		//Instanciação do objeto
		Converter converter = new Converter();
		
		//Registro no módulo de referência remota
		Naming.rebind("rmi://localhost/CurrencyConverter", converter);
		
		//Mensagem impressa em console para sinalizar que o servidor está funcionando
		System.out.println("Running!");
	}
}
