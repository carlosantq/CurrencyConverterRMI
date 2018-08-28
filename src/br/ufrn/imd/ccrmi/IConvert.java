package br.ufrn.imd.ccrmi;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IConvert extends Remote{
	
	public Double currencyAToB(Double value, String from, String to) throws RemoteException;
	
	public List<Double> currencyAToAll(Double value, String from) throws RemoteException;
	
}
