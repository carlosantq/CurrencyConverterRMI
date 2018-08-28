package br.ufrn.imd.ccrmi;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class Converter extends UnicastRemoteObject implements IConvert{
	
	//TODO: Consumir a api para pegar os valores (com exceção do USD)
	private static Double DKK = 0.0;
	private static Double NOK = 0.0;
	private static Double SEK = 0.0;
	private static Double CZK = 0.0;
	private static Double GBP = 1.28684;
	private static Double TRY = 0.0;
	private static Double INR = 0.0;
	private static Double IDR = 0.0;
	private static Double PKR = 0.0;
	private static Double THB = 0.0;
	private static final Double USD = 1.0;
	private static Double AUD = 0.0;
	private static Double CAD = 0.0;
	private static Double SAD = 0.0;
	private static Double HKD = 0.0;
	private static Double TWD = 0.0;
	private static Double NZD = 0.0;
	private static Double EUR = 0.0;
	private static Double HUF = 0.0;
	private static Double CHF = 0.0;
	private static Double JPY = 0.0;
	private static Double ILS = 0.0;
	private static Double CLP = 0.0;
	private static Double PHP = 0.0;
	private static Double MXN = 0.0;
	private static Double ZAR = 0.0;
	private static Double BRL = 0.24430;
	private static Double MYR = 0.0;
	private static Double RUB = 0.0;
	private static Double KRW = 0.0;
	private static Double CNY = 0.0;
	private static Double PLN = 0.0;

	protected Converter() throws RemoteException {
		super();
	}

	//TODO: Uma solução melhor do que fazer vários IFs
	@Override
	public Double currencyAToB(Double value, int a, int b) throws RemoteException {
		if (a == 5) {
			value *= GBP;
		}else if (a == 27) {
			value *= BRL;
		}
		
		if (b == 5) {
			value /= GBP;
		}else if (b == 27) {
			value /= BRL;
		}
		
		return value;
	}
	
	@Override
	public List<Double> currencyAToAll(Double value, int a) throws RemoteException {
		List<Double> result = new ArrayList<Double>();
		
		if (a == 5) {
			result.add(value * GBP / GBP);
			result.add(value * GBP / USD);
			result.add(value * GBP / BRL);
		}else if (a == 11) {
			result.add(value * USD / GBP);
			result.add(value * USD / USD);
			result.add(value * USD / BRL);
		}else if (a == 27) {
			result.add(value * BRL / GBP);
			result.add(value * BRL / USD);
			result.add(value * BRL / BRL);
		}
		
		return result;
	}

}
