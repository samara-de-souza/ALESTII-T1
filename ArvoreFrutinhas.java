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
 
   public static char[][] lerArquivo(String nomeArquivo) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(nomeArquivo));
 
       
        String[] dimensoes = br.readLine().split(" ");
        int linhas = Integer.parseInt(dimensoes[0]);
        int colunas = Integer.parseInt(dimensoes[1]);
        System.out.println("Dimensões da matriz: " + linhas + " x " + colunas);  
       
        char[][] matriz = new char[linhas][colunas];
       
        String linha;
        int index = 0;
        while ((linha = br.readLine()) != null) {
            matriz[index] = linha.toCharArray();
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
        int colunaRaiz = -1;  
       
        for (int i = 0; i < colunas; i++) {
            if (matriz[linhas - 1][i] == '#') {
                raiz = new Nodo(0);
                colunaRaiz = i;  
                System.out.println("Raiz criada na posição: " + (linhas - 1) + ", " + i);
                construirRamos(matriz, linhas - 2, i, raiz);
                break;
            }
        }
        return raiz;
    }
    private static void construirRamos(char[][] matriz, int linha, int coluna, Nodo nodo) {
        if (linha < 0 || coluna < 0 || coluna >= matriz[0].length) {
            return;
        }
        char atual = matriz[linha][coluna];
        
        if (atual == '#') {
            nodo.valor = 0;
            return;
        }
        
        if (Character.isDigit(atual)) {
            nodo.valor = Character.getNumericValue(atual);
        }
        
        if (atual == 'V' || atual == 'W') {
        
            if (atual == 'W') {
                nodo.centro = new Nodo(0);
                construirRamos(matriz, linha - 1, coluna, nodo.centro);
            }
  
            nodo.esquerda = new Nodo(0);
            nodo.direita = new Nodo(0);
            construirRamos(matriz, linha - 1, coluna - 1, nodo.esquerda);
            construirRamos(matriz, linha - 1, coluna + 1, nodo.direita);
        }
        
        if (coluna > 0 && (matriz[linha][coluna - 1] == '/' || matriz[linha][coluna - 1] == '\\')) {
            nodo.esquerda = new Nodo(0);
            construirRamos(matriz, linha - 1, coluna - 1, nodo.esquerda);
        }
        
        if (coluna < matriz[0].length - 1 && (matriz[linha][coluna + 1] == '/' || matriz[linha][coluna + 1] == '\\')) {
            nodo.direita = new Nodo(0);
            construirRamos(matriz, linha - 1, coluna + 1, nodo.direita);
        }
    }
  
}
