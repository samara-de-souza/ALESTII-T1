import java.io.*;
import java.util.*;

public class ArvoreFrutinhas {

    public static class Nodo {
        int valor;
        Nodo esquerda, direita;

        public Nodo(int valor) {
            this.valor = valor;
            this.esquerda = null;
            this.direita = null;
        }
    }

    public static char[][] lerArquivo(String nomeArquivo) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(nomeArquivo));
        List<char[]> linhas = new ArrayList<>();
        String linha;
        
        while ((linha = br.readLine()) != null) {
            linhas.add(linha.toCharArray());
        }
        br.close();
        
        return linhas.toArray(new char[0][0]);
    }

}
