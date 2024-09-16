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

    public static char[][] lerArquivo(String nomeArquivo) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(nomeArquivo));

        String[] dimensoes = br.readLine().split(" ");
        int linhas = Integer.parseInt(dimensoes[0]);
        int colunas = Integer.parseInt(dimensoes[1]);
        System.out.println("Dimensões da matriz: " + linhas + " x " + colunas);

        char[][] matriz = new char[linhas][colunas];

        // Lê as linhas subsequentes e preenche a matriz
        String linha;
        int index = 0;
        while ((linha = br.readLine()) != null) {
            matriz[index] = linha.toCharArray();
            System.out.println(linha); 
            index++;
        }
        br.close();

        System.out.println("Matriz final:");
        for (char[] row : matriz) {
            System.out.println(Arrays.toString(row));
        }
        return matriz;
    }

    public static Nodo construirArvore(char[][] matriz) {
        int linhas = matriz.length;
        int colunas = matriz[0].length;
        Nodo raiz = null;

        for (int j = 0; j < colunas; j++) {
            if (matriz[linhas - 1][j] == '\\' || matriz[linhas - 1][j] == '|' || matriz[linhas - 1][j] == '/' || 
                matriz[linhas - 1][j] == 'V' || matriz[linhas - 1][j] == 'W') {
                System.out.println("Iniciando construção da árvore a partir da raiz encontrada na posição: " + (linhas - 1) + ", " + j);
                raiz = construirRamos(matriz, linhas - 1, j);  
                break;  
            }
        }
    
        if (raiz == null) {
            System.out.println("Nenhuma raiz válida encontrada (/, |, \\, V ou W) na última linha da matriz.");
        }
    
        return raiz;
    }
    
    private static Nodo construirRamos(char[][] matriz, int linha, int coluna) {
        if (linha < 0 || coluna < 0 || coluna >= matriz[0].length) {
            return null;  // Se for uma posição inválida, retorna null
        }
    
        char atual = matriz[linha][coluna];
    
        // Se for um dos ponteiros (\, |, /, V, W), continua conectando os nodos
        if (atual == '\\' || atual == '|' || atual == '/' || atual == 'V' || atual == 'W') {
            System.out.println("Ponteiro encontrado na posição: " + linha + ", " + coluna);
    
            Nodo nodo = new Nodo(0);  // Nodo temporário para continuar a conexão
    
            // Para bifurcação W, conecta os três ramos (esquerda, centro e direita)
            if (atual == 'W') {
                System.out.println("Conectando bifurcação tripla (W) na posição: " + linha + ", " + coluna);
                nodo.esquerda = construirRamos(matriz, linha - 1, coluna - 1);  // Nodo à esquerda
                nodo.centro = construirRamos(matriz, linha - 1, coluna);  // Nodo diretamente acima
                nodo.direita = construirRamos(matriz, linha - 1, coluna + 1);  // Nodo à direita
            }
    
            // Para bifurcação V, conecta dois ramos (esquerda e direita)
            if (atual == 'V') {
                System.out.println("Conectando bifurcação dupla (V) na posição: " + linha + ", " + coluna);
                nodo.esquerda = construirRamos(matriz, linha - 1, coluna - 1);  // Nodo à esquerda
                nodo.direita = construirRamos(matriz, linha - 1, coluna + 1);  // Nodo à direita
            }
    
            // Verifica se há uma linha acima (|) conectando a outro número ou folha
            if (atual == '|') {
                System.out.println("Conectando nodo acima (centro) na posição: " + (linha - 1) + ", " + coluna);
                nodo.centro = construirRamos(matriz, linha - 1, coluna);  // Nodo diretamente acima
            }
    
            // Verifica se há uma linha à esquerda (\) conectando a outro número ou folha
            if (atual == '\\') {
                System.out.println("Conectando nodo à esquerda na posição: " + (linha - 1) + ", " + (coluna - 1));
                nodo.esquerda = construirRamos(matriz, linha - 1, coluna - 1);  // Nodo à esquerda
            }
    
            // Verifica se há uma linha à direita (/) conectando a outro número ou folha
            if (atual == '/') {
                System.out.println("Conectando nodo à direita na posição: " + (linha - 1) + ", " + (coluna + 1));
                nodo.direita = construirRamos(matriz, linha - 1, coluna + 1);  // Nodo à direita
            }
    
            return nodo;  // Retorna o nodo conectado
        }
    
        // Se for um número, cria um novo nodo com o valor do número
        if (Character.isDigit(atual)) {
            int valor = Character.getNumericValue(atual);
            Nodo nodo = new Nodo(valor);
            System.out.println("Nodo com valor " + valor + " criado na posição: " + linha + ", " + coluna);
            return nodo;  // Retorna o nodo criado com o valor
        }
    
        // Se for uma folha (#), indica o fim do caminho
        if (atual == '#') {
            System.out.println("Folha encontrada na posição: " + linha + ", " + coluna);
            return new Nodo(0);  // Folhas têm valor 0
        }
    
        return null;  // Se não for ponteiro nem número nem folha, retorna null
    }    
}