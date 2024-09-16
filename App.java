import java.io.IOException;
public class App {
    public static void main(String[] args) {
        // Nome do arquivo de entrada
        String nomeArquivo = "casof60.txt";  // Certifique-se de criar este arquivo de teste

        try {
            // Leitura da matriz do arquivo
            char[][] matriz = ArvoreFrutinhas.lerArquivo(nomeArquivo);

            // Construção da árvore a partir da matriz
            System.out.println("Iniciando a construção da árvore...");
            ArvoreFrutinhas.Nodo raiz = ArvoreFrutinhas.construirArvore(matriz);

            if (raiz != null) {
                // Busca pelo melhor caminho a partir da raiz
                System.out.println("Iniciando busca pelo melhor caminho...");
                ArvoreFrutinhas.Resultado resultado = ArvoreFrutinhas.buscarMelhorCaminho(raiz);

                // Exibição do resultado
                System.out.println("Melhor caminho: " + resultado.melhorCaminho);
                System.out.println("Soma total: " + resultado.maiorSoma);
            } else {
                System.out.println("Árvore não pôde ser construída. Verifique a matriz.");
            }

        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }
}
