/*
 * Universidade Federal de Santa Catarina - UFSC
 * Departamento de Informática e Estatística - INE
 * Programa de Pós-Graduação em Ciências da Computação - PROPG
 * Disciplinas: Projeto e Análise de Algoritmos
 * Prof Alexandre Gonçalves da Silva 
 * Baseado nos slides da aula 03/11/2017 
 */

/**
 *
 * @author Osmar de Oliveira Braz Junior
 */
public class Principal {

    static int m[][];
    static int s[][];

    /**
     * Algoritmo multiplicacao de matrizes página 56 Cormen 3ed.
     *
     * @param A Matriz A
     * @param B Matriz B
     * @return Uma matriz com o resultado da multiplicação da matriz A por B
     */
    public static int[][] multiplica(int A[][], int B[][]) {
        //Numero de colunas de A diferente do numero de linhas de B
        //A.columns != B.rows
        if (A.length != B[0].length) {
            System.out.println("Tipos incompátiveis");
            return null;
        } else {
            int linha = A[0].length; //A.rows, numero de linhas da matriz A
            int coluna = B.length; //B.columns, numero de colunas da matriz B
            int C[][] = new int[linha][coluna];
            for (int i = 0; i < linha; i++) {
                for (int j = 0; j < coluna; j++) {
                    C[i][j] = 0;
                    for (int k = 0; k < A.length; k++) {
                        C[i][j] = C[i][j] + A[i][k] * B[k][j];
                    }
                }
            }
            return C;
        }
    }

    /**
     * Minimo Multiplicações Cadeia de Matrizes utilizando recursividade.
     * @param b Vetor com dimensòes das matrizes
     */
    public static int minimoMultiplicacoesRecursivo(int b[], int i, int j) {
        if (i == j) {
            return 0;
        } else {
            //Simula o infinito positivo
            m[i][j] = Integer.MAX_VALUE;
            for (int k = i; k <= j -1; k++) {                              
                 //Varios valores da árvore de recursão ja foram calculados e sao trazidos de m
                 //obtencao de m[i,j] por meio de solução de recursão: passo 2 pag.374 livro Cormen 
                int q = minimoMultiplicacoesRecursivo(b, i, k) + 
                        minimoMultiplicacoesRecursivo(b, k+1, j) + 
                        b[i] * b[k] * b[j];                
                if (q < m[i][j]) {
                    m[i][j] = q; //Obtenção de m ótimo para multiplicação da cadeia de matrizes de i a j
                    s[i][j] = k + 1; //Onde ocorreu a divisão otima da cadeia de matrizes de i a j
                }
            }
            return m[i][j];
        }
    }

    /**
     * Minimo Multiplicações Cadeia de Matrizes utilizando programação dinâmica.
     * Saída as tabelas m e s preenchidas
     * @param b Vetor com dimensòes das matrizes
     */
    public static void minimoMultiplicacoes(int b[]) {
        // numero de matrizes a serem multiplicadas
        int n = b.length - 1;
        //salva em m[i,i] o numero de operacoes com uma unica matriz = 0
        for (int i = 0; i <= n; i++) {
            m[i][i] = 0;
        }
        for (int u = 1; u <= n; u++) { //inicia com cadeia de duas matrizes: l=1 e vai aumentando
            for (int i = 0; i <= (n - u); i++) { 
                int j = i + u;
                //Simula o infinito positivo
                m[i][j] = Integer.MAX_VALUE;
                for (int k = i; k <= j-1; k++) {
                    //aqui entra programacao dinamica, pois varios valores da arvore de recursao ja foram calculados e sao trazidos de m                
                    int q = m[i][k] + 
                            m[k + 1][j] + 
                            b[i] * b[k] * b[j];

                    if (q < m[i][j]) {
                        m[i][j] = q; //Obtenção de m otimo para multiplicacao da cadeia de matrizes de i a j
                        s[i][j] = k + 1; //Onde ocorreu a divisão otima da cadeia de matrizes de i a j
                    }
                }
            }
        }        
    }

    /**
     * Imprime a parentização otima para multiplicação da cadeia de matrizes.
     * @param s Matriz da ordem da parentização
     * @param i Inicio da cadeia
     * @param j Quantidade de elementos da cadeia
     * @return  String com a parentização das matrizes
     */
    public static String imprimaParentizacaoOtima(int[][] s, int i, int j) {
        String retorno = "";
        if (i == j) {          
            return ("A" + (i + 1));
        } else {            
            retorno = retorno + "(";            
            retorno = retorno + imprimaParentizacaoOtima(s, i, s[i][j]-1);            
            retorno = retorno + imprimaParentizacaoOtima(s, s[i][j], j);            
            retorno = retorno + ")";
            return retorno;
        }
    }

    /**
     * Imprime na saida padrão a matriz 
     * @param X Uma matriz a ser exibida
     */
    public static void imprimir(int[][] X) {
        int ti = X.length; //Colunas
        int tj = X[0].length; //Linhas
        for (int i = 0; i < ti; i++) {
            for (int j = 0; j < tj; j++) {
                System.out.printf("\t %d \t", X[i][j]);
            }
            System.out.println();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        int[][] A = {{2, 3}, {4, 5}};
        int[][] B = {{6, 7}, {8, 9}};

        //imprimir(A);
        //imprimir(B);
        //int[][] C = multiplicacaoMatriz(A, B);
        //imprimir(C);
        int b[] = {35, 15, 5, 10, 20, 25};
        //int b[] = {200, 2, 30, 20, 5};
        int n = b.length;
        m = new int[n][n];
        s = new int[n][n];
        
        //((A1(A2A3))((A4A5)A6))
        //((A1(A2A3))((A4A5)A6))
        int i = 0;
        int j = n - 1;
        minimoMultiplicacoes(b);
        
        //minimoMultiplicacoesRecursivo(b, i, j);
        System.out.println(imprimaParentizacaoOtima(s, i, j));
        
        
        
        
    }

}
