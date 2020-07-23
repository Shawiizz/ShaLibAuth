package fr.shawiizz.shalibauth;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

public class ShaLibAuth {
  public static String username = "";
  public static String token = "";
  public static String uuid = "";
  public static String clientToken = "";
  public static boolean ShaLogger = true;

  public static boolean login(String email, String password, AuthType type) {
    try {
      if(type.equals(AuthType.MOJANG)) {
        HttpURLConnection con = (HttpURLConnection) new URL("https://authserver.mojang.com/authenticate").openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        con.addRequestProperty("content-type", "application/json");
        con.setDoOutput(true);

        OutputStream os = con.getOutputStream();
        os.write(("{\"agent\":{\"name\":\"Minecraft\",\"version\":1},\"username\":\"" + email + "\",\"password\":\"" + password + "\"}").getBytes(StandardCharsets.UTF_8));
        os.flush();
        os.close();

        if(con.getResponseCode() == 200) {
          BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
          String[] strings = in.readLine().split("\",\"");
          token = strings[0].replace("{\"accessToken\":\"", "");
          clientToken = strings[1].replace("clientToken\":\"", "");
          username = strings[2].substring(strings[2].lastIndexOf("\"name\":\"") + 8);
          String uuidTmp = strings[3].replace("id\":\"", "");
          uuid = uuidTmp.substring(0, uuidTmp.indexOf("\""));
          in.close();
          return true;
        } else {
          log("Invalid credentials or too much login attempts.");
          return false;
        }
      } else {
        username = email;
        uuid = UUID.randomUUID().toString().replace("-", "");
        token = email + "." + generateNewToken();
        return true;
      }
    } catch (Exception e) {
      log("Can't connect to Mojang's authentication servers.");
      return false;
    }
  }

  private static String generateNewToken() {
    byte[] randomBytes = new byte[24];
    new SecureRandom().nextBytes(randomBytes);
    return Base64.getUrlEncoder().encodeToString(randomBytes);
  }

  private static void log(String msg) {
    if(ShaLogger) System.out.println(msg);
  }

}
