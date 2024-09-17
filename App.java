import java.io.IOException;

public class App {
    public static void main(String[] args) {
        // Nome do arquivo de entrada
        String nomeArquivo = "casof30.txt";  // Certifique-se de usar o arquivo correto

        try {
            // Ler o arquivo e preencher a matriz
            char[][] matriz = ArvoreFrutinhas.lerArquivoEPreencherMatriz(nomeArquivo);

            // Imprime a matriz original como foi lida do arquivo
            System.out.println("Matriz da árvore lida do arquivo:");
            ArvoreFrutinhas.imprimirMatriz(matriz);

            // Testa a leitura da última linha da matriz
            ArvoreFrutinhas.testarLeituraUltimaLinha(matriz);

            System.out.println("Iniciando a construção da árvore...");
            ArvoreFrutinhas.Nodo raiz = ArvoreFrutinhas.construirArvoreAPartirDaRaiz(matriz);  // Modificação feita aqui
            if (raiz != null) {
                System.out.println("Estrutura da árvore:");
                ArvoreFrutinhas.imprimirMatriz(matriz);  // Pode ser substituído por um método para imprimir a árvore em vez da matriz

                // Soma os valores dos galhos e encontra o melhor caminho
                int somaTotal = ArvoreFrutinhas.somarValoresDosGalhos(raiz);
                System.out.println("Soma total dos valores: " + somaTotal);
            } else {
                System.out.println("Árvore não pôde ser construída. Verifique a matriz.");
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }
}
