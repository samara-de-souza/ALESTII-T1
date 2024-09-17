import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArvoreFrutinhas {
    static class Nodo {
        int valor;
        Nodo esquerda, direita, centro;

        public Nodo(int valor) {
            this.valor = valor;
            this.esquerda = null;
            this.direita = null;
            this.centro = null;
        }
    }

    public static class Resultado {
        public String melhorCaminho;
        public int maiorSoma;

        public Resultado(String melhorCaminho, int maiorSoma) {
            this.melhorCaminho = melhorCaminho;
            this.maiorSoma = maiorSoma;
        }
    }

    public static int somarValoresDosGalhos(Nodo raiz) {
        if (raiz == null) {
            return 0;  // Se a raiz for nula, retorne 0
        }

        System.out.println("Somando valores dos galhos...");

        // Lista temporária para armazenar os nodos de cada galho
        List<Nodo> galhoTemporario = new ArrayList<>();
        List<Nodo> melhorGalho = new ArrayList<>();
        int somaMelhorGalho = 0;

        // Percorrer a árvore e somar os valores de cada galho
        percorrerArvore(raiz, galhoTemporario, 0, melhorGalho);

        // Calcular a soma dos valores do melhor galho
        for (Nodo nodo : melhorGalho) {
            somaMelhorGalho += nodo.valor;
        }

        System.out.println("Melhor caminho: ");
        for (Nodo nodo : melhorGalho) {
            System.out.print(nodo.valor + " ");
        }
        System.out.println();

        return somaMelhorGalho;  // Retorna a soma do melhor galho
    }

    private static void percorrerArvore(Nodo nodo, List<Nodo> galhoAtual, int somaAtual, List<Nodo> melhorGalho) {
        if (nodo == null) {
            return;
        }
        
        galhoAtual.add(nodo);

        somaAtual += nodo.valor;

        // Se o nodo atual não tiver filhos (é uma folha), verificamos se é o melhor caminho
        if (nodo.esquerda == null && nodo.centro == null && nodo.direita == null) {
            // Se o caminho atual for maior, atualiza o melhor caminho
            if (somaAtual > calcularSoma(melhorGalho)) {
                melhorGalho.clear();
                melhorGalho.addAll(galhoAtual);
            }
        } else {
            // Continua percorrendo para os filhos (esquerda, centro, direita)
            percorrerArvore(nodo.esquerda, galhoAtual, somaAtual, melhorGalho);
            percorrerArvore(nodo.centro, galhoAtual, somaAtual, melhorGalho);
            percorrerArvore(nodo.direita, galhoAtual, somaAtual, melhorGalho);
        }

        galhoAtual.remove(galhoAtual.size() - 1);
    }

    private static int calcularSoma(List<Nodo> galho) {
        int soma = 0;
        for (Nodo nodo : galho) {
            soma += nodo.valor;
        }
        return soma;
    }

    public static void testarLeituraUltimaLinha(char[][] matriz) {
    int linhas = matriz.length;
    int colunas = matriz[0].length;

    // Imprime a última linha da matriz
    System.out.println("Última linha da matriz:");
    for (int j = 0; j < colunas; j++) {
        System.out.print(matriz[linhas - 1][j]);  // Lê e imprime cada caractere da última linha
    }
    System.out.println();  // Pula para a próxima linha no console
}

    public static char[][] lerArquivoEPreencherMatriz(String nomeArquivo) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo));
        String linha;

        // Leitura da primeira linha que contém as dimensões da matriz
        linha = reader.readLine();
        String[] dimensoes = linha.split(" ");
        int linhas = Integer.parseInt(dimensoes[0]);
        int colunas = Integer.parseInt(dimensoes[1]);

        // Inicializando a matriz
        char[][] matriz = new char[linhas][colunas];

        // Preenchendo a matriz com o conteúdo do arquivo linha por linha
        int i = 0;
        while ((linha = reader.readLine()) != null && i < linhas) {
            for (int j = 0; j < Math.min(linha.length(), colunas); j++) {
                matriz[i][j] = linha.charAt(j);  // Populando a matriz com cada caractere da linha
            }
            i++;
        }
        reader.close();

        // Imprimir a matriz para verificar se está correta
        System.out.println("Matriz da árvore lida do arquivo:");
        imprimirMatriz(matriz);

        return matriz;
    }

    public static void imprimirMatriz(char[][] matriz) {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static Nodo construirArvoreAPartirDaRaiz(char[][] matriz) {
        int linhas = matriz.length;
        int colunas = matriz[0].length;
        Nodo[][] nodos = new Nodo[linhas][colunas];  // Matriz para armazenar os nodos criados
    
        // Primeiro, identificar a raiz na última linha da matriz
        Nodo raiz = null;
        for (int j = 0; j < colunas; j++) {
            if (matriz[linhas - 1][j] != ' ') {  // Qualquer coisa que não seja espaço vazio é considerada raiz
                raiz = new Nodo(0);  // Nodo vazio para representar a raiz
                nodos[linhas - 1][j] = raiz;
                System.out.println("Raiz encontrada na posição: " + (linhas - 1) + ", " + j);
                break;
            }
        }
    
        if (raiz == null) {
            System.out.println("Nenhuma raiz encontrada. Verifique a matriz.");
            return null;
        }
    
        // Agora, subimos pela matriz para construir os galhos da árvore
        for (int i = linhas - 2; i >= 0; i--) {  // Começamos na linha anterior à última
            for (int j = 0; j < colunas; j++) {
                char atual = matriz[i][j];
    
                // Ignorar espaços em branco
                if (atual == ' ') {
                    continue;  // Espaço em branco, pula para o próximo
                }
    
                // Criar nodos numerados
                if (Character.isDigit(atual)) {
                    int valor = Character.getNumericValue(atual);
                    nodos[i][j] = new Nodo(valor);
                    System.out.println("Nodo com valor " + valor + " criado na posição: " + i + ", " + j);
                }
    
                // Criar folhas
                if (atual == '#') {
                    nodos[i][j] = new Nodo(0);  // Folha tem valor 0
                    System.out.println("Folha criada na posição: " + i + ", " + j);
                }
    
                // Criar nodos vazios para |, /, \, V, W
                if (atual == '|' || atual == '/' || atual == '\\' || atual == 'V' || atual == 'W') {
                    nodos[i][j] = new Nodo(0);  // Nodo vazio
                    System.out.println("Nodo vazio criado na posição: " + i + ", " + j);
                }
            }
        }
    
        // Conectar os nodos com base nos ponteiros, subindo até a segunda linha da matriz
        for (int i = linhas - 2; i >= 0; i--) {  // Iniciamos da linha anterior à última e subimos
            for (int j = 0; j < colunas; j++) {
                if (nodos[i][j] != null) {
                    char atual = matriz[i][j];
    
                    // Conectar nodos vazios e numerados com base nos símbolos
                    if (atual == '|') {
                        if (i - 1 >= 0) {  // Verifica se não estamos na primeira linha
                            nodos[i][j].centro = nodos[i - 1][j];  // Nodo diretamente acima
                            System.out.println("Conectando nodo " + i + ", " + j + " ao nodo acima.");
                        }
                    }
                    if (atual == '\\') {
                        if (i - 1 >= 0 && j - 1 >= 0) {  // Verifica se não estamos na borda esquerda
                            nodos[i][j].esquerda = nodos[i - 1][j - 1];  // Nodo à esquerda acima
                            System.out.println("Conectando nodo " + i + ", " + j + " ao nodo à esquerda.");
                        }
                    }
                    if (atual == '/') {
                        if (i - 1 >= 0 && j + 1 < colunas) {  // Verifica se não estamos na borda direita
                            nodos[i][j].direita = nodos[i - 1][j + 1];  // Nodo à direita acima
                            System.out.println("Conectando nodo " + i + ", " + j + " ao nodo à direita.");
                        }
                    }
    
                    if (Character.isDigit(atual)) {
                        // Conectar nodos numerados com base nos símbolos acima
                        if (i - 1 >= 0 && matriz[i - 1][j] == '|') {
                            nodos[i][j].centro = nodos[i - 1][j];  // Nodo acima
                            System.out.println("Conectando nodo " + i + ", " + j + " ao centro.");
                        }
                        if (i - 1 >= 0 && j - 1 >= 0 && matriz[i - 1][j - 1] == '\\') {
                            nodos[i][j].esquerda = nodos[i - 1][j - 1];  // Nodo à esquerda
                            System.out.println("Conectando nodo " + i + ", " + j + " à esquerda.");
                        }
                        if (i - 1 >= 0 && j + 1 < colunas && matriz[i - 1][j + 1] == '/') {
                            nodos[i][j].direita = nodos[i - 1][j + 1];  // Nodo à direita
                            System.out.println("Conectando nodo " + i + ", " + j + " à direita.");
                        }
                    }
                }
            }
        }
    
        return raiz;
    }    
    
    public static void imprimirArvore(Nodo nodo, String prefixo) {
        if (nodo == null) {
            return;  
        }
        if (nodo.valor == 0) {
            System.out.println(prefixo + "+-- [vazio]");
        } else {
            System.out.println(prefixo + "+-- " + nodo.valor);
        }
        if (nodo.esquerda != null) {
            imprimirArvore(nodo.esquerda, prefixo + "|   ");
        }
        if (nodo.centro != null) {
            imprimirArvore(nodo.centro, prefixo + "|   ");
        }
        if (nodo.direita != null) {
            imprimirArvore(nodo.direita, prefixo + "|   ");
        }
    }

}