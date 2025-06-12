import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Config {
    public String botName;
    public String serverUrl;

    public Config(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while((line = br.readLine()) != null){
                if(line.startsWith("bot_name=")){
                    botName = line.split("=", 2)[1].trim();
                } else if(line.startsWith("server_url=")){
                    serverUrl = line.split("=", 2)[1].trim();
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading config: " + e.getMessage());
        }
    }
}
