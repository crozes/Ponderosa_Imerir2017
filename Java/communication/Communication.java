package communication;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Permet de faire un post ou un get d'un string sur le serveur
 * @author atila
 *
 */
public class Communication {

	/**
	 * Fait une demande de get sur le serveur
	 * @param URL_Serveur l'url http de la requete
	 * @return l'information demande par le get
	 */
	public static String getRecevoir(String URL_Serveur) {

		URL url;
		HttpURLConnection connection = null;
		BufferedReader in = null;
		try {
			url = new URL(URL_Serveur);
			connection = (HttpURLConnection) url.openConnection();

			outils.ToString.toStringHTTP("< Recuperation du get");

			connection.setRequestMethod("GET");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			// DataOutputStream out = new DataOutputStream();
			// DataOutputStream(urlConn.getOutputStream());
			// String content = "MYNAME=RYANBOHNERT";
			// out.writeBytes (content);
			// out.flush();
			// out.close();

			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			String resultat = org.apache.commons.io.IOUtils.toString(in);
			outils.ToString.toStringHTTP("get recuperer = " + resultat + " >");

			return resultat;

		} catch (MalformedURLException e) {

		} catch (IOException e) {

		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}

		return " ";

	}

	/**
	 * Fait un post via http
	 * @param toPost la donner que l'on souhaite envoyer
	 * @param URL_Serveur l'url http de la requete
	 * @return la reponse du serveur
	 */
	public static String postEnvoyer(String toPost, String URL_Serveur) {
		URL url;
		HttpURLConnection connection = null;

		try {
			url = new URL(URL_Serveur);
			connection = (HttpURLConnection) url.openConnection();

			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");

			connection.setRequestProperty("Content-Length", "" + Integer.toString(toPost.length()));
			connection.setRequestProperty("charset", "utf-8");

			connection.setInstanceFollowRedirects(false);
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			// Send request
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());

			wr.write(toPost.getBytes());
			wr.flush();
			wr.close();

			outils.ToString.toStringHTTP("Envoie: " + toPost);
			int reponse = connection.getResponseCode();
			outils.ToString.toStringHTTP("Code reponse " + reponse + " Reponse " + connection.getResponseMessage());

			InputStream in = null;

			if (reponse == 200) {
				in = new BufferedInputStream(connection.getInputStream());
			} else {
				in = new BufferedInputStream(connection.getErrorStream());
				System.out.println("\nMauvaise data : ");
			}
			String result = org.apache.commons.io.IOUtils.toString(in, "UTF-8");
			outils.ToString.toStringHTTP("result " + result);
			return result;

		} catch (Exception e) {

			e.printStackTrace();
			return "Error in post : " + toPost + " url : " + URL_Serveur;
		}
	}

}
