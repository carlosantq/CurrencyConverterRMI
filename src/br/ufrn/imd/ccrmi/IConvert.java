package br.ufrn.imd.ccrmi;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Interface remota para ser utilizada pelo programa cliente
 * @author carlosant
 * @author pedrohcavalcante
 */
public interface IConvert extends Remote{
	
	/**
	 * Converte um dado valor de uma moeda A para uma moeda B.
	 * @param value - valor a ser convertido
	 * @param from - moeda de origem
	 * @param to - moeda de destino
	 * @return Valor convertido
	 * @throws RemoteException
	 */
	public Double currencyAToB(Double value, String from, String to) throws RemoteException;
	
	/**
	 * Converte o valor de uma moeda A para todas as 20 moedas da base de dados.
	 * @param value - valor a ser convertido
	 * @param from - moeda de origem
	 * @return Lista com valores convertidos
	 * @throws RemoteException
	 */
	public List<Double> currencyAToAll(Double value, String from) throws RemoteException;
	
}
