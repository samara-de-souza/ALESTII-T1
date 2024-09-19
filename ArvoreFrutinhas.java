
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
        // Encontrar a raiz (V ou |) na última linha 
        int linhaRaiz = arvore.length - 1; 
        int colunaRaiz = -1; 
        for (int coluna = 0; coluna < arvore[0].length; coluna++) { 
            if (arvore[linhaRaiz][coluna] == 'V' || arvore[linhaRaiz][coluna] == '|') { 
                colunaRaiz = coluna; 
                break; 
            } 
        } 
        if (colunaRaiz == -1) { 
            System.out.println("Raiz não encontrada."); 
            return; 
        } 
        System.out.println("Raiz encontrada na posição (" + linhaRaiz + ", " + colunaRaiz + ")"); 
        // Para cada folha, percorre o caminho da raiz até ela e soma os valores 
        for (int i = 0; i < folhas.size(); i++) { 
            int[] folha = folhas.get(i); 
            Set<String> visitados = new HashSet<>(); // Para garantir que não visitamos a mesma posição mais de uma vez 
            int somaGalho = andarNaArvoreAteFolha(arvore, linhaRaiz, colunaRaiz, folha[0], folha[1], visitados); 
            System.out.println("Soma do galho " + (i + 1) + ": " + somaGalho); 
        } 
     } 
     
    private static int andarNaArvoreAteFolha(char[][] arvore, int linhaAtual, int colunaAtual, int linhaFolha, int colunaFolha, Set<String> visitados) { 
        // Checar se chegamos à folha

        if (linhaAtual == linhaFolha && colunaAtual == colunaFolha) { 
            return Character.isDigit(arvore[linhaAtual][colunaAtual]) ? Character.getNumericValue(arvore[linhaAtual][colunaAtual]) : 0; 
        } 
        // Marcar posição atual como visitada 
        String posicaoAtual = linhaAtual + "," + colunaAtual; 
        if (visitados.contains(posicaoAtual)) { 
            return 0; 
        } 
        visitados.add(posicaoAtual); 
        // Somar o valor atual, se for um número 
        int somaAtual = Character.isDigit(arvore[linhaAtual][colunaAtual]) ? Character.getNumericValue(arvore[linhaAtual][colunaAtual]) : 0; 
        // Checar os movimentos possíveis 
        int soma = 0; 
        if (linhaAtual > 0 && colunaAtual > 0 && arvore[linhaAtual - 1][colunaAtual - 1] != '.') { // Esquerda cima 
            soma = andarNaArvoreAteFolha(arvore, linhaAtual - 1, colunaAtual - 1, linhaFolha, colunaFolha, visitados); 
        } else if (linhaAtual > 0 && arvore[linhaAtual - 1][colunaAtual] != '.') { // Cima 
            soma = andarNaArvoreAteFolha(arvore, linhaAtual - 1, colunaAtual, linhaFolha, colunaFolha, visitados); 
        } else if (linhaAtual > 0 && colunaAtual < arvore[0].length - 1 && arvore[linhaAtual - 1][colunaAtual + 1] != '.') { // Direita cima 
            soma = andarNaArvoreAteFolha(arvore, linhaAtual - 1, colunaAtual + 1, linhaFolha, colunaFolha, visitados); 
        } 
        return somaAtual + soma; 
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
            char[][] arvore = ArvoreFrutinhas.lerArquivo("casof30.txt"); 
            ArvoreFrutinhas.imprimirArvore(arvore); 

            ArvoreFrutinhas.mostrarCaminhosBifurcacoes(arvore);
            
            // Calcular e exibir a soma de cada galho 
            ArvoreFrutinhas.calcularSomasDosGalhos(arvore); 
        } catch (IOException e) { 
            System.err.println("Erro ao ler o arquivo: " + e.getMessage()); 
        } 
     } 
}
