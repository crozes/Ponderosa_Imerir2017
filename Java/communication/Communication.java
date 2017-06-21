package communication;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.commons.io.IOUtils;

/**
 * 
 * @author atila
 *
 */
public class Communication {
	
	private HttpURLConnection connection =null;
	
	
	/**
	 * Constructeur.
	 * On prefere utuliser une seule connection pour toute les requetes.
	 * @param URL_Serveur
	 */
	public Communication(String URL_Serveur) {
		super();
		URL url;
		
		try {
			url= new URL(URL_Serveur);
			this.connection = (HttpURLConnection) url.openConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Erreur lors de la creation de la connection");
		}
		
		
	}
	
	
	/**
	 * Destructeur.
	 * On deconnecte la connection à la fin.
	 */
	public void finalize(){
		if(this.connection != null){
			this.connection.disconnect();
		}
	}




	/**
	 * 
	 * @return
	 */
	public String getRecevoir() {


		BufferedReader in;

		try {
			System.out.println("< Recuperation du get");

			this.connection.setRequestMethod("GET");
			this.connection.setDoInput(true);
			this.connection.setDoOutput(true);
			this.connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			// DataOutputStream out = new
			// DataOutputStream(urlConn.getOutputStream());
			// String content = "MYNAME=RYANBOHNERT";
			// out.writeBytes (content);
			// out.flush();
			// out.close();

			in = new BufferedReader(new InputStreamReader(this.connection.getInputStream()));

			System.out.println("get recuperer >"+org.apache.commons.io.IOUtils.toString(in));
			System.out.println("Dans fonction get la valeur est ->"+in.toString());
			return in.toString();

		} catch (MalformedURLException e) {

		} catch (IOException e) {

		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}
		
		return " ";
		
	}
	
	
	/**
	 * 
	 * @param toPost
	 */
	public String postEnvoyer(String toPost) {

		try {

			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			connection.setRequestProperty("Content-Length", "" + Integer.toString(toPost.getBytes().length));
			connection.setRequestProperty("Content-Language", "en-US");

			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			// Send request
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.writeBytes(toPost);
			wr.flush();
			wr.close();

			// Get Response
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;
			StringBuffer response = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			return response.toString();

		} catch (Exception e) {

			e.printStackTrace();
			return null;

		} 
	}


	
}
