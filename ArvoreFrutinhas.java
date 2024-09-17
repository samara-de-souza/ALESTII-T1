import java.io.*;
import java.util.*;

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

    public static Resultado buscarMelhorCaminho(Nodo nodo) {
        if (nodo == null) {
            System.out.println("Nodo nulo, sem mais caminho a seguir.");
            return new Resultado("", 0);  // Se o nodo for nulo, não há mais caminho a seguir
        }

        // Se o nodo for uma folha, retorna o valor 0 e termina o caminho
        if (nodo.valor == 0) {
            System.out.println("Folha encontrada: #");
            return new Resultado("#", 0);  // Folha é o fim do caminho
        }

        System.out.println("Percorrendo nodo com valor: " + nodo.valor);

        // Busca recursivamente os melhores caminhos à esquerda, centro e direita
        Resultado esquerda = buscarMelhorCaminho(nodo.esquerda);
        Resultado direita = buscarMelhorCaminho(nodo.direita);
        Resultado centro = buscarMelhorCaminho(nodo.centro);

        // Escolhe o caminho com a maior soma
        Resultado melhor = esquerda;  // Assume que o caminho pela esquerda é o melhor
        if (direita.maiorSoma > melhor.maiorSoma) {
            melhor = direita;  // Se o caminho pela direita tiver uma soma maior, escolhe-o
        }
        if (centro.maiorSoma > melhor.maiorSoma) {
            melhor = centro;  // Se o caminho pelo centro tiver uma soma maior, escolhe-o
        }

        // Retorna o caminho atual, adicionando o valor do nodo atual ao melhor caminho encontrado
        String caminhoAtual = nodo.valor + " -> " + melhor.melhorCaminho;
        int somaAtual = nodo.valor + melhor.maiorSoma;

        System.out.println("Caminho até agora: " + caminhoAtual + " com soma: " + somaAtual);

        return new Resultado(caminhoAtual, somaAtual);
    }

    public static char[][] lerArquivoComNull(String nomeArquivo) throws IOException {
        List<char[]> linhas = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo));
        
        String linha;
        while ((linha = reader.readLine()) != null) {
            // Converte espaços em branco para null (representado como ' ')
            char[] linhaArray = new char[linha.length()];
            for (int i = 0; i < linha.length(); i++) {
                if (linha.charAt(i) == ' ') {
                    linhaArray[i] = ' ';  // Espaços em branco
                } else {
                    linhaArray[i] = linha.charAt(i);  // Outros caracteres
                }
            }
            linhas.add(linhaArray);
        }
        reader.close();
        
        return linhas.toArray(new char[0][]);  // Converte para matriz 2D
    }    

    public static Nodo construirArvoreComNull(char[][] matriz) {
        int linhas = matriz.length;
        int colunas = matriz[0].length;
        Nodo[][] nodos = new Nodo[linhas][colunas];  

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                char atual = matriz[i][j];
                if (atual == ' ') {
                    continue; 
                }
                if (Character.isDigit(atual)) {
                    int valor = Character.getNumericValue(atual);
                    nodos[i][j] = new Nodo(valor);
                    System.out.println("Nodo com valor " + valor + " criado na posição: " + i + ", " + j);
                }
                if (atual == '#') {
                    nodos[i][j] = new Nodo(0);  // Folha tem valor 0
                    System.out.println("Folha criada na posição: " + i + ", " + j);
                }
            }
        }
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                char atual = matriz[i][j];
                if (nodos[i][j] != null) {
                    if (i > 1 && matriz[i - 1][j] == '|') {
                        nodos[i][j].centro = nodos[i - 2][j];  // Conectar ao nodo superior
                        System.out.println("Conectando nodo " + i + ", " + j + " ao centro.");
                    }
                    if (i > 1 && j > 1 && matriz[i - 1][j - 1] == '\\') {
                        nodos[i][j].esquerda = nodos[i - 2][j - 2];  // Conectar ao nodo à esquerda
                        System.out.println("Conectando nodo " + i + ", " + j + " à esquerda.");
                    }
                    if (i > 1 && j < colunas - 2 && matriz[i - 1][j + 1] == '/') {
                        nodos[i][j].direita = nodos[i - 2][j + 2];  // Conectar ao nodo à direita
                        System.out.println("Conectando nodo " + i + ", " + j + " à direita.");
                    }
                    if (atual == 'V' && i > 0) {
                        if (j > 0) {
                            nodos[i][j].esquerda = nodos[i - 1][j - 1];  // Conectar à esquerda
                        }
                        if (j < colunas - 1) {
                            nodos[i][j].direita = nodos[i - 1][j + 1];  // Conectar à direita
                        }
                        System.out.println("Conectando bifurcação V na posição " + i + ", " + j);
                    }
                    if (atual == 'W' && i > 0) {
                        if (j > 0) {
                            nodos[i][j].esquerda = nodos[i - 1][j - 1];  // Conectar à esquerda
                        }
                        nodos[i][j].centro = nodos[i - 1][j];  // Conectar ao centro
                        if (j < colunas - 1) {
                            nodos[i][j].direita = nodos[i - 1][j + 1];  // Conectar à direita
                        }
                        System.out.println("Conectando bifurcação W na posição " + i + ", " + j);
                    }
                }
            }
        }
        for (int j = 0; j < colunas; j++) {
            if (nodos[linhas - 1][j] != null) {
                System.out.println("Raiz encontrada na posição " + (linhas - 1) + ", " + j);
                return nodos[linhas - 1][j];  // Retorna a raiz encontrada na última linha
            }
        }
        System.out.println("Nenhuma raiz encontrada. Verifique a matriz.");
        return null;  
    }    
}