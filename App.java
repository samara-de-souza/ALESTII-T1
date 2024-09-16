import java.io.IOException;
public class App {
    public static void main(String[] args) {
 
        String nomeArquivo = "casof30.txt";
        try {
            char[][] matriz = ArvoreFrutinhas.lerArquivo(nomeArquivo);
           
            ArvoreFrutinhas.Nodo raiz = ArvoreFrutinhas.construirArvore(matriz);
           
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }
 }