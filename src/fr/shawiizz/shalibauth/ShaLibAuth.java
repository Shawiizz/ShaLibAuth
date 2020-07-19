package fr.shawiizz.shalibauth;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

public class ShaLibAuth {
    public static String username = "";
    public static String token = "";
    public static String uuid = "";
    public static String clientToken = "";

    public static void login(String email, String password, AuthType type) throws Exception {
        if(type.equals(AuthType.MOJANG)) {
            HttpURLConnection con = (HttpURLConnection) new URL("https://authserver.mojang.com/authenticate").openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.addRequestProperty("content-type", "application/json");
            con.setDoOutput(true);

            OutputStream os = con.getOutputStream();
            os.write(("{\"agent\":{\"name\":\"Minecraft\",\"version\":1},\"username\":\"" + email + "\",\"password\":\"" + password + "\"}").getBytes("UTF-8"));
            os.flush();
            os.close();

            if(con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
              String[] strings = in.readLine().split("\",\"");
              token = strings[0].replace("{\"accessToken\":\"", "");
              clientToken = strings[1].replace("clientToken\":\"", "");
              username = strings[2].substring(strings[2].lastIndexOf("\"name\":\"") + 8);
              String uuidTmp = strings[3].replace("id\":\"", "");
              uuid = uuidTmp.substring(0, uuidTmp.indexOf("\""));
              in.close();
            } else System.out.println("Auth Failed");
        } else {
            username = email;
            uuid = UUID.randomUUID().toString().replace("-", "");
            token = email+"."+generateNewToken();
        }
    }

  private static String generateNewToken() {
    byte[] randomBytes = new byte[24];
    new SecureRandom().nextBytes(randomBytes);
    return Base64.getUrlEncoder().encodeToString(randomBytes);
  }

}
