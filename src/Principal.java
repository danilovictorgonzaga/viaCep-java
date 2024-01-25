import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Principal {
    public static void main(String[] args) throws IOException {
        Scanner leitura = new Scanner(System.in);
        boolean continuar = true;
        ConsultaCep consultaCep = new ConsultaCep();
        Set<Endereco> enderecos = new HashSet<>();
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .create();

        while (continuar) {
            System.out.println("Digite o numero do cep:");
            var busca = leitura.next();
            busca = busca.replaceAll("[-.]", "");

            try {
                Endereco novoCep = consultaCep.buscaCep(busca);
                System.out.println(novoCep);
                enderecos.add(novoCep);

            } catch (RuntimeException ex){
                System.out.println(ex.getMessage());
            }

            String proximo;
            while (true) {
                System.out.println("Aceita fazer uma nova consulta? S/N.");
                proximo = leitura.next().toUpperCase();

                if (proximo.equals("S")) {
                    continuar = true;
                    break;
                } else if (proximo.equals("N")) {
                    continuar = false;
                    System.out.println("Finalizando a aplicação.");
                    break;
                } else {
                    System.out.println("Resposta invalida. Digite 'S' ou 'N'.");
                }
            }
        }

        FileWriter escrita = new FileWriter("enderecos.json");
        escrita.write(gson.toJson(enderecos));
        escrita.close();
    }
}
