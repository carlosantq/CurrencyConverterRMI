package br.ufrn.imd.ccrmi;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings("serial")
public class Converter extends UnicastRemoteObject implements IConvert {

	public static final String ACCESS_KEY = "f60e4668da4b6a583e6af0a82cb81089";
	public static final String BASE_URL = "http://apilayer.net/api/";
	public static final String FROM = "&from=";
	public static final String TO = "&to=";
	public static final String AMOUNT = "&amount=1";
	public static final String ENDPOINT = "live";
	public static final String CONVERT = "convert";
	public static final String STATIC_DOLAR = "USD";
	static CloseableHttpClient httpClient = HttpClients.createDefault();

	// TODO: Consumir a api para pegar os valores (com exceção do USD)
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

	/*
	 * protected Converter() throws RemoteException { super(); }
	 */

	public Converter() throws RemoteException {
		super();
	}

	// TODO: Uma solução melhor do que fazer vários IFs
	@Override
	public Double currencyAToB(Double value, String from, String to) throws RemoteException {
		Double result = sendLiveRequest(from, to);
		if (result == null) {
			System.exit(0);
		}
		return value * result;
	}

	@Override
	public List<Double> currencyAToAll(Double value, String from) throws RemoteException {
		List<Double> result = new ArrayList<Double>();
		result.add(value * sendLiveRequest(from, "DKK"));
		result.add(value * sendLiveRequest(from, "NOK"));
		result.add(value * sendLiveRequest(from, "SEK"));
		result.add(value * sendLiveRequest(from, "CZK"));
		result.add(value * sendLiveRequest(from, "GBP"));
		result.add(value * sendLiveRequest(from, "TRY"));
		result.add(value * sendLiveRequest(from, "INR"));
		result.add(value * sendLiveRequest(from, "IDR"));
		result.add(value * sendLiveRequest(from, "PKR"));
		result.add(value * sendLiveRequest(from, "THB"));
		result.add(value * sendLiveRequest(from, "USD"));
		result.add(value * sendLiveRequest(from, "AUD"));
		result.add(value * sendLiveRequest(from, "CAD"));
		result.add(value * sendLiveRequest(from, "SGD"));
		result.add(value * sendLiveRequest(from, "HKD"));
		result.add(value * sendLiveRequest(from, "TWD"));
		result.add(value * sendLiveRequest(from, "NZD"));
		result.add(value * sendLiveRequest(from, "EUR"));
		result.add(value * sendLiveRequest(from, "HUF"));
		result.add(value * sendLiveRequest(from, "CHF"));
		result.add(value * sendLiveRequest(from, "JPY"));
		result.add(value * sendLiveRequest(from, "ILS"));
		result.add(value * sendLiveRequest(from, "CLP"));
		result.add(value * sendLiveRequest(from, "PHP"));
		result.add(value * sendLiveRequest(from, "MXN"));
		result.add(value * sendLiveRequest(from, "ZAR"));
		result.add(value * sendLiveRequest(from, "BRL"));
		result.add(value * sendLiveRequest(from, "MYR"));
		result.add(value * sendLiveRequest(from, "RUB"));
		result.add(value * sendLiveRequest(from, "KRW"));
		result.add(value * sendLiveRequest(from, "CNY"));
		result.add(value * sendLiveRequest(from, "PLN"));

		/*
		 * if (a == 5) { result.add(value * GBP / GBP); result.add(value * GBP / USD);
		 * result.add(value * GBP / BRL); }else if (a == 11) { result.add(value * USD /
		 * GBP); result.add(value * USD / USD); result.add(value * USD / BRL); }else if
		 * (a == 27) { result.add(value * BRL / GBP); result.add(value * BRL / USD);
		 * result.add(value * BRL / BRL); }
		 */

		return result;
	}

	private Double sendLiveRequest(String from, String to) {

		// The following line initializes the HttpGet Object with the URL in order to
		// send a request
		Double from2 = 1.0;
		Double to2 = 1.0;
		HttpGet get = new HttpGet(BASE_URL + ENDPOINT + "?access_key=" + ACCESS_KEY);
		// HttpGet get = new HttpGet(BASE_URL + CONVERT + "?access_key=" + ACCESS_KEY +
		// FROM + from + TO + to + AMOUNT);
		try {
			CloseableHttpResponse response = httpClient.execute(get);
			HttpEntity entity = response.getEntity();

			// the following line converts the JSON Response to an equivalent Java Object
			JSONObject exchangeRates = new JSONObject(EntityUtils.toString(entity));

			System.out.println("Live Currency Exchange Rates");
			boolean sucesso = exchangeRates.getBoolean("success");

			if (!sucesso) {

				String codigoErro = exchangeRates.getJSONObject("error").getString("code");

				String infoErro = exchangeRates.getJSONObject("error").getString("info");

				System.out.println("A API chegou ao limite de suas forças.");
				System.out.println("Erro: " + codigoErro);
				System.out.println("Mensagem: " + infoErro);
				System.exit(0);
			}
			// Parsed JSON Objects are accessed according to the JSON resonse's hierarchy,
			// output strings are built

			System.out.println("1 Converting " + STATIC_DOLAR + " in " + from + ": "
					+ exchangeRates.getJSONObject("quotes").getDouble(STATIC_DOLAR + from));
			from2 = 1 / exchangeRates.getJSONObject("quotes").getDouble(STATIC_DOLAR + from);
			System.out.println(
					from + " IN USD " + 1 / exchangeRates.getJSONObject("quotes").getDouble(STATIC_DOLAR + from));
			System.out.println("2 Convertion " + STATIC_DOLAR + " in " + to + ": "
					+ exchangeRates.getJSONObject("quotes").getDouble(STATIC_DOLAR + to));
			System.out
					.println(to + " IN USD " + 1 / exchangeRates.getJSONObject("quotes").getDouble(STATIC_DOLAR + to));
			to2 = 1 / exchangeRates.getJSONObject("quotes").getDouble(STATIC_DOLAR + to);
			System.out.println(from + " IN " + to + ": " + from2 / to2);
			// System.out.println("3 converting " + from + " in " + to + ": " + 1/from + );
			// System.out.println("\n");

			response.close();
			return from2 / to2;

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
}
