import java.io.IOException;
public class App {
    public static void main(String[] args) {
        // Nome do arquivo de entrada
        String nomeArquivo = "casof30.txt";  // Certifique-se de criar este arquivo de teste

        try {
            // Leitura da matriz do arquivo, convertendo espaços em branco para null
            char[][] matriz = ArvoreFrutinhas.lerArquivoComNull(nomeArquivo);

            // Construção da árvore a partir da matriz
            System.out.println("Iniciando a construção da árvore...");
            ArvoreFrutinhas.Nodo raiz = ArvoreFrutinhas.construirArvoreComNull(matriz);

            if (raiz != null) {
                // Imprime a estrutura da árvore
                System.out.println("Estrutura da árvore:");
                ArvoreFrutinhas.imprimirArvore(raiz, "");
            } else {
                System.out.println("Árvore não pôde ser construída. Verifique a matriz.");
            }

        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }
}