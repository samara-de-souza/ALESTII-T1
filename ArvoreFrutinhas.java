
import java.io.*; 
import java.util.*;
 
public class ArvoreFrutinhas { 

    // Métodos para ler o arquivo // 
    public static char[][] lerArquivo(String nomeArquivo) throws IOException { 
        BufferedReader br = new BufferedReader(new FileReader(nomeArquivo)); 
        List<char[]> linhas = new ArrayList<>(); 
        String linha; 
        int maxColunas = 0; 
         
        // Ignorar a primeira linha 
        br.readLine(); 
         
        // Ler o arquivo e encontrar o número máximo de colunas 
        while ((linha = br.readLine()) != null) { 
            // Substituir espaços em branco por pontos 
            linha = linha.replace(' ', '.'); 
            if (linha.length() > maxColunas) { 
                maxColunas = linha.length(); 
            } 
            linhas.add(linha.toCharArray()); 
        } 
        br.close(); 
     
        // Preencher a matriz com pontos e manter a estrutura da árvore 
        char[][] arvore = new char[linhas.size()][maxColunas]; 
        for (int i = 0; i < linhas.size(); i++) { 
            char[] linhaAtual = linhas.get(i); 
            for (int j = 0; j < maxColunas; j++) { 
                if (j < linhaAtual.length) { 
                    arvore[i][j] = linhaAtual[j]; 
                } else { 
                    arvore[i][j] = '.'; // Preencher com pontos 
                } 
            } 
        } 
 
        imprimirMatrizEmArquivo(arvore, "saida.txt"); 
 
        return arvore; 
    } 
 
    private static void imprimirMatrizEmArquivo(char[][] matriz, String nomeArquivo) throws IOException { 
        BufferedWriter bw = new BufferedWriter(new FileWriter(nomeArquivo)); 
        for (char[] linha : matriz) { 
            bw.write(linha); 
            bw.newLine(); 
        } 
        bw.close(); 
    } 
    // Métodos para ler o arquivo // 
 
    // Método de Teste // 
    public static void imprimirArvore(char[][] arvore) { 
        System.out.println("Estrutura da árvore:"); 
        for (int i = 0; i < arvore.length; i++) { 
            for (int j = 0; j < arvore[0].length; j++) { 
                System.out.print(arvore[i][j] + " "); 
            } 
            System.out.println(); 
        } 
    } 
    // Método de Teste // 
 
    public static void calcularSomasDosGalhos(char[][] arvore) {
        // Listar todas as posições de folhas (marcadas com '#')
        List<int[]> folhas = new ArrayList<>();
        for (int linha = 0; linha < arvore.length; linha++) {
            for (int coluna = 0; coluna < arvore[0].length; coluna++) {
                if (arvore[linha][coluna] == '#') {
                    folhas.add(new int[]{linha, coluna}); // Adiciona a posição da folha
                }
            }
        }
    
        // Encontrar a raiz (V, W ou |) na última linha
        int linhaRaiz = arvore.length - 1;
        int colunaRaiz = -1;
        for (int coluna = 0; coluna < arvore[0].length; coluna++) {
            if (arvore[linhaRaiz][coluna] == 'V' || arvore[linhaRaiz][coluna] == 'W' || arvore[linhaRaiz][coluna] == '|') {
                colunaRaiz = coluna;
                break;
            }
        }
        if (colunaRaiz == -1) {
            System.out.println("Raiz não encontrada.");
            return;
        }
        System.out.println("Raiz encontrada na posição (" + linhaRaiz + ", " + colunaRaiz + ")");
    
        int somaTotal = 0;

        // Para cada folha, percorre o caminho da raiz até ela e soma os valores
        for (int i = 0; i < folhas.size(); i++) {
            int[] folha = folhas.get(i);
            Set<String> visitados = new HashSet<>(); // Para garantir que não visitamos a mesma posição mais de uma vez
            somaTotal = andarDaRaizAteFolha(arvore, linhaRaiz, colunaRaiz, folha[0], folha[1], visitados);
        }
        System.out.println("Soma do melhor caminho: " + somaTotal);
    }
        
    private static int andarDaRaizAteFolha(char[][] arvore, int linhaAtual, int colunaAtual, int linhaFolha, int colunaFolha, Set<String> visitados) {
        // Checar se chegamos à folha
        if (linhaAtual == linhaFolha && colunaAtual == colunaFolha) {
            return Character.isDigit(arvore[linhaAtual][colunaAtual]) ? Character.getNumericValue(arvore[linhaAtual][colunaAtual]) : 0;
        }
    
        // Marcar a posição atual como visitada
        String posicaoAtual = linhaAtual + "," + colunaAtual;
        if (visitados.contains(posicaoAtual)) {
            return 0; // Já foi visitado, evitar revisitar
        }
        visitados.add(posicaoAtual);
    
        // Adicionar o valor atual à soma, se for um número
        int somaAtual = Character.isDigit(arvore[linhaAtual][colunaAtual]) ? Character.getNumericValue(arvore[linhaAtual][colunaAtual]) : 0;
    
        // Continuar descendo para os próximos nós (esquerda, cima, direita)
        int somaEsquerda = 0, somaCentro = 0, somaDireita = 0;
    
        if (linhaAtual > 0 && colunaAtual > 0 && arvore[linhaAtual - 1][colunaAtual - 1] != '.') { // Esquerda cima
            somaEsquerda = andarDaRaizAteFolha(arvore, linhaAtual - 1, colunaAtual - 1, linhaFolha, colunaFolha, visitados);
        }
    
        if (linhaAtual > 0 && arvore[linhaAtual - 1][colunaAtual] != '.') { // Cima
            somaCentro = andarDaRaizAteFolha(arvore, linhaAtual - 1, colunaAtual, linhaFolha, colunaFolha, visitados);
        }
    
        if (linhaAtual > 0 && colunaAtual < arvore[0].length - 1 && arvore[linhaAtual - 1][colunaAtual + 1] != '.') { // Direita cima
            somaDireita = andarDaRaizAteFolha(arvore, linhaAtual - 1, colunaAtual + 1, linhaFolha, colunaFolha, visitados);
        }
    
        // Retorna o valor atual mais a soma do melhor caminho (maior soma)
        return somaAtual + Math.max(somaEsquerda, Math.max(somaCentro, somaDireita));
    }
       

     public static void mostrarCaminhosBifurcacoes(char[][] arvore) {
        // Percorrer a árvore para encontrar bifurcações (V ou W)
        for (int linha = 0; linha < arvore.length; linha++) {
            for (int coluna = 0; coluna < arvore[0].length; coluna++) {
                if (arvore[linha][coluna] == 'V' || arvore[linha][coluna] == 'W') {
                    System.out.println("\nBifurcação encontrada em (" + linha + ", " + coluna + ")");
    
                    // Exibir os caminhos a partir dessa bifurcação
                    Set<String> visitados = new HashSet<>();
                    if (linha > 0 && coluna > 0 && arvore[linha - 1][coluna - 1] != '.') { // Esquerda
                        System.out.println("Caminho à esquerda:");
                        mostrarCaminho(arvore, linha - 1, coluna - 1, visitados);
                    }
                    if (linha > 0 && arvore[linha - 1][coluna] != '.') { // Centro (para W)
                        if (arvore[linha][coluna] == 'W') {
                            System.out.println("Caminho central:");
                            mostrarCaminho(arvore, linha - 1, coluna, visitados);
                        }
                    }
                    if (linha > 0 && coluna < arvore[0].length - 1 && arvore[linha - 1][coluna + 1] != '.') { // Direita
                        System.out.println("Caminho à direita:");
                        mostrarCaminho(arvore, linha - 1, coluna + 1, visitados);
                    }
                }
            }
        }
    }
    
    private static void mostrarCaminho(char[][] arvore, int linhaAtual, int colunaAtual, Set<String> visitados) {
        // Marcar a posição atual como visitada
        String posicaoAtual = linhaAtual + "," + colunaAtual;
        if (visitados.contains(posicaoAtual)) {
            return;
        }
        visitados.add(posicaoAtual);
    
        // Exibir a posição atual
        System.out.println("Posição: (" + linhaAtual + ", " + colunaAtual + ") - Valor: " +
                (Character.isDigit(arvore[linhaAtual][colunaAtual]) ? arvore[linhaAtual][colunaAtual] : "Sem valor"));
    
        // Continuar para o próximo nó no caminho
        if (linhaAtual > 0 && colunaAtual > 0 && arvore[linhaAtual - 1][colunaAtual - 1] != '.') { // Esquerda
            mostrarCaminho(arvore, linhaAtual - 1, colunaAtual - 1, visitados);
        }
        if (linhaAtual > 0 && arvore[linhaAtual - 1][colunaAtual] != '.') { // Centro
            mostrarCaminho(arvore, linhaAtual - 1, colunaAtual, visitados);
        }
        if (linhaAtual > 0 && colunaAtual < arvore[0].length - 1 && arvore[linhaAtual - 1][colunaAtual + 1] != '.') { // Direita
            mostrarCaminho(arvore, linhaAtual - 1, colunaAtual + 1, visitados);
        }
    }
    

     public static void main(String[] args) { 
        try { 
            // Ler o arquivo e obter a matriz da árvore 
            char[][] arvore = ArvoreFrutinhas.lerArquivo("casof500.txt"); 
            ArvoreFrutinhas.imprimirArvore(arvore); 


            ArvoreFrutinhas.mostrarCaminhosBifurcacoes(arvore);

            // Calcular e exibir a soma de cada galho 
            ArvoreFrutinhas.calcularSomasDosGalhos(arvore); 
        } catch (IOException e) { 
            System.err.println("Erro ao ler o arquivo: " + e.getMessage()); 
        } 
     } 
}
