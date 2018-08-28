package br.ufrn.imd.ccrmi;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IConvert extends Remote{
	
	public Double currencyAToB(Double value, int a, int b) throws RemoteException;
	
	public List<Double> currencyAToAll(Double value, int a) throws RemoteException;
	
}
