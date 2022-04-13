import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) throws IOException, InterruptedException {

        Scanner sc = new Scanner(System.in);

        String cepin;
        CEP cep = new CEP();

        System.out.print("Digite um CEP válido: ");
        cepin = sc.next();
        System.out.println();

        String url = "https://viacep.com.br/ws/" + cepin + "/json/";
        System.out.println(url);
        System.out.println();

        HttpClient client = HttpClient.newHttpClient();

        URI uri = URI.create(url);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        var bodyHandler = HttpResponse.BodyHandlers.ofString();

        HttpResponse<String> response = client.send(request, bodyHandler);

        String json = response.body().replace("\n","")
                .replace("{","")
                .replace("}","")
                .replace("\"","");

        Map<String, String> jsonObject = new HashMap<>();

        String[] linha = json.split(",");
        for (int i = 0 ;i < linha.length; i++){
            String[] coluna = linha[i].split(":");
            String key = coluna[0].replace(" ","");
            String value = coluna[1].replace(" ","");
            jsonObject.put(key , value);
        }

        cep.setCep(jsonObject.get("cep"));
        cep.setLogradouro(jsonObject.get("logradouro"));
        cep.setBairro(jsonObject.get("bairro"));
        cep.setLocalidade(jsonObject.get("localidade"));
        cep.setUf(jsonObject.get("uf"));
        cep.setIbge(jsonObject.get("ibge"));
        cep.setGia(jsonObject.get("gia"));
        cep.setDdd(jsonObject.get("ddd"));
        cep.setSiafi(jsonObject.get("siafi"));

        System.out.println(cep.toString());
    }
}