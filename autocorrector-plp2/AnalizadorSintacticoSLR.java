
import java.awt.Point;
import java.util.HashMap;
import java.util.Stack;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gacel
 */
public class AnalizadorSintacticoSLR 
{
    // Estructura utilizada para las acciones
    public class Accion
    {
        public static final int REDUCIR = 0, DESPLAZAR = 1, ACEPTAR = 0;
        
        private int estado;
        private int tipo;
        
        public Accion(int estado, int tipo)
        {
            this.estado = estado;
            this.tipo = tipo;
        }
    }
    
    public class Punto
    {
        private int x, y;
        
        public Punto(int x, int y)
        {
            this.x = x;
            this.y = y;
        }
    }
    private static HashMap<Punto, Accion> acciones;
    private static HashMap<Punto, Integer> destinos;
    private static int[] longParteDerecha;
    private static int[] parteIzquierda;
    private static final int numTerminales = 17;
    
    public StringBuilder reglas;
    private Stack<Integer> stackReglas;
    private Token token;
    private AnalizadorLexico al;
    
    public AnalizadorSintacticoSLR(AnalizadorLexico al)
    {
        this.al = al;
        inicializaDestinos();
        inicializaAcciones();
        inicializaParteDerecha();
        stackReglas = new Stack();
        reglas = new StringBuilder();
        
    }
    
    public void errorSintaxis(int estado)
    {
        String s = new String();
        for(int i = 0; i < numTerminales; i++)
        {
            if(acciones.get(new Punto(estado, i)) != null)
                s += " "+Token.getLabel(i);
        }
        if(token.tipo == Token.EOF)
            System.err.println("Error sintactico: encontrado fin de fichero, esperaba"+s);
        else
        {
            System.err.println("Error sintactico ("+token.fila+","+token.columna+"): encontrado '"+token.lexema+"', esperaba"+s);
        
        }
            
        System.exit(-1);
    }
    
    public void analizar()
    {
        if(!stackReglas.empty())
            stackReglas.clear();
        
        Accion accion;
        int estado;
        stackReglas.push(0);
        token = al.siguienteToken();
        
        while(true)
        {
            estado = stackReglas.peek();
            accion = acciones.get(new Point(estado, token.tipo));
            
            if(accion == null)
            {
                errorSintaxis(estado);
            }
            else if(accion.tipo == Accion.DESPLAZAR)
            {
                stackReglas.push(accion.estado);
                token = al.siguienteToken();
            }
            else if(accion.tipo == Accion.REDUCIR)
            {
                for(int i = 0; i < longParteDerecha[accion.estado]; i++)
                    stackReglas.pop();
                stackReglas.push(destinos.get(new Punto(stackReglas.peek(), parteIzquierda[accion.estado])));
            }
            else if(accion.tipo == Accion.ACEPTAR)
                break;
            
        }
        // Construye la sucesion de reglas realizadas invirtiendo la pila
        for(int i = 0; i < stackReglas.size(); i++)
        {
            reglas.append(stackReglas.pop() + " ");            
        }
        System.out.println(reglas);
    }
    
    private void inicializaParteIzquierda()
    {
        parteIzquierda = new int[25];
        
        //parteIzquierda[0] = 5;
        parteIzquierda[1] = 1;
        parteIzquierda[2] = 2;
        parteIzquierda[3] = 2;
        parteIzquierda[4] = 3;
        parteIzquierda[5] = 4;
        parteIzquierda[6] = 4;
        parteIzquierda[7] = 5;
        parteIzquierda[8] = 6;
        parteIzquierda[9] = 6;
        parteIzquierda[10] = 6;
        parteIzquierda[11] = 7;
        parteIzquierda[12] = 8;
        parteIzquierda[13] = 8;
        parteIzquierda[14] = 9;
        parteIzquierda[15] = 9;
        parteIzquierda[16] = 10;
        parteIzquierda[17] = 10;
        parteIzquierda[18] = 11;
        parteIzquierda[19] = 11;
        parteIzquierda[20] = 12;
        parteIzquierda[21] = 12;
        parteIzquierda[22] = 13;
        parteIzquierda[23] = 13;
        parteIzquierda[24] = 13;
    }
    
    private void inicializaParteDerecha()
    {
        longParteDerecha = new int[25];
        
        longParteDerecha[1] = 5;
        longParteDerecha[2] = 2;
        longParteDerecha[3] = 0;
        longParteDerecha[4] = 5;
        longParteDerecha[5] = 1;
        longParteDerecha[6] = 1;
        longParteDerecha[7] = 3;
        longParteDerecha[8] = 2;
        longParteDerecha[9] = 2;
        longParteDerecha[10] = 0;
        longParteDerecha[11] = 2;
        longParteDerecha[12] = 2;
        longParteDerecha[13] = 3;
        longParteDerecha[14] = 4;
        longParteDerecha[15] = 1;
        longParteDerecha[16] = 4;
        longParteDerecha[17] = 1;
        longParteDerecha[18] = 3;
        longParteDerecha[19] = 1;
        longParteDerecha[20] = 3;
        longParteDerecha[21] = 1;
        longParteDerecha[22] = 1;
        longParteDerecha[23] = 1;
        longParteDerecha[24] = 1;
    }
    
    private void inicializaAcciones()
    {
        acciones = new HashMap();
        /*
        acciones.put(new Punto(0,11), new Accion(Accion.DESPLAZAR, 6));
        acciones.put(new Punto(0, 12), new Accion(Accion.DESPLAZAR, 5));
        acciones.put(new Punto(0, 13), new Accion(Accion.REDUCIR, 3));
        acciones.put(new Punto(2, 13), new Accion(Accion.DESPLAZAR, 7));
        acciones.put(new Punto(3, 11), new Accion(Accion.DESPLAZAR, 6));
        acciones.put(new Punto(3, 12), new Accion(Accion.DESPLAZAR, 5));
        acciones.put(new Punto(3, 13), new Accion(Accion.REDUCIR, 3));
        acciones.put(new Punto(4, 15), new Accion(Accion.DESPLAZAR, 9));
        acciones.put(new Punto(5, 15), new Accion(Accion.REDUCIR, 5));
        acciones.put(new Punto(6, 15), new Accion(Accion.REDUCIR, 6));
        acciones.put(new Punto(7, Token.PARI), new Accion(Accion.DESPLAZAR, 10));
        acciones.put(new Punto(8, 13), new Accion(Accion.REDUCIR, 2));
        acciones.put(new Punto(9, Token.PARI), new Accion(Accion.DESPLAZAR, 11));
        acciones.put(new Punto(10, Token.PARD), new Accion(Accion.DESPLAZAR, 12));
        acciones.put(new Punto(11, Token.PARD), new Accion(Accion.DESPLAZAR, 13));
        acciones.put(new Punto(12, 7), new Accion(Accion.DESPLAZAR, 15));
        acciones.put(new Punto(13, 7), new Accion(Accion.DESPLAZAR, 15));
        acciones.put(new Punto(14, Token.EOF), new Accion(Accion.REDUCIR, 1));
        acciones.put(new Punto(15, 7), new Accion(Accion.DESPLAZAR, 15));
        acciones.put(new Punto(15, 11), new Accion(Accion.DESPLAZAR, 6));
        acciones.put(new Punto(15, 8), new Accion(Accion.REDUCIR, 10));
        acciones.put(new Punto(15, 12), new Accion(Accion.DESPLAZAR, 5));
        acciones.put(new Punto(15, 15), new Accion(Accion.DESPLAZAR, 21));
        acciones.put(new Punto(16, 11), new Accion(Accion.REDUCIR, 4));
        acciones.put(new Punto(16, 12), new Accion(Accion.REDUCIR, 4));
        acciones.put(new Punto(16, 13), new Accion(Accion.REDUCIR, 4));
        acciones.put(new Punto(17, 8), new Accion(Accion.DESPLAZAR, 24));
        acciones.put(new Punto(18, 7), new Accion(Accion.DESPLAZAR, 15));        
        acciones.put(new Punto(18, 8), new Accion(Accion.REDUCIR, 10));
        acciones.put(new Punto(18, 11), new Accion(Accion.DESPLAZAR, 6));
        acciones.put(new Punto(18, 12), new Accion(Accion.DESPLAZAR, 5));
        acciones.put(new Punto(18, 15), new Accion(Accion.DESPLAZAR, 21));
        acciones.put(new Punto(19, 7), new Accion(Accion.DESPLAZAR, 15));
        acciones.put(new Punto(19, 8), new Accion(Accion.REDUCIR, 10));
        acciones.put(new Punto(19, 11), new Accion(Accion.DESPLAZAR, 6));
        acciones.put(new Punto(19, 12), new Accion(Accion.DESPLAZAR, 5));
        acciones.put(new Punto(19, 15), new Accion(Accion.DESPLAZAR, 21));
        acciones.put(new Punto(20, 4), new Accion(Accion.DESPLAZAR, 27));
        acciones.put(new Punto(20, 5), new Accion(Accion.DESPLAZAR, 28));
        acciones.put(new Punto(21, 6), new Accion(Accion.DESPLAZAR, 29));
        acciones.put(new Punto(22, 7), new Accion(Accion.REDUCIR, 17));
        acciones.put(new Punto(22, 8), new Accion(Accion.REDUCIR, 17));
        acciones.put(new Punto(22, 11), new Accion(Accion.REDUCIR, 17));
        acciones.put(new Punto(22, 12), new Accion(Accion.REDUCIR, 17));
        acciones.put(new Punto(22, 15), new Accion(Accion.REDUCIR, 17));
        acciones.put(new Punto(23, 15), new Accion(Accion.DESPLAZAR, 31));
        acciones.put(new Punto(24, 7), new Accion(Accion.REDUCIR, 17));
        acciones.put(new Punto(24, 8), new Accion(Accion.REDUCIR, 17));
        acciones.put(new Punto(24, 11), new Accion(Accion.REDUCIR, 17));
        acciones.put(new Punto(24, 12), new Accion(Accion.REDUCIR, 17));
        acciones.put(new Punto(24, 13), new Accion(Accion.REDUCIR, 17));
        acciones.put(new Punto(24, 15), new Accion(Accion.REDUCIR, 17));
        acciones.put(new Punto(25, 8), new Accion(Accion.REDUCIR, 8));
        acciones.put(new Punto(26, 8), new Accion(Accion.REDUCIR, 9));
        acciones.put(new Punto(27, 7), new Accion(Accion.REDUCIR, 11));
        acciones.put(new Punto(27, 8), new Accion(Accion.REDUCIR, 11));
        acciones.put(new Punto(27, 11), new Accion(Accion.REDUCIR, 11));
        acciones.put(new Punto(27, 12), new Accion(Accion.REDUCIR, 11));
        acciones.put(new Punto(27, 15), new Accion(Accion.REDUCIR, 11));
        acciones.put(new Punto(28, 15), new Accion(Accion.DESPLAZAR, 31));
        acciones.put(new Punto(29, 14), new Accion(Accion.DESPLAZAR, 37));
        acciones.put(new Punto(29, 15), new Accion(Accion.DESPLAZAR, 38));
        acciones.put(new Punto(29, 16), new Accion(Accion.DESPLAZAR, 36));
        acciones.put(new Punto(30, 9), new Accion(Accion.DESPLAZAR, 39));
        acciones.put(new Punto(30, 4), new Accion(Accion.REDUCIR, 12));
        acciones.put(new Punto(30, 5), new Accion(Accion.REDUCIR, 12));
        acciones.put(new Punto(31, 4), new Accion(Accion.REDUCIR, 39));
        acciones.put(new Punto(31, 5), new Accion(Accion.REDUCIR, 39));
        acciones.put(new Punto(31, 9), new Accion(Accion.REDUCIR, 39));
        acciones.put(new Punto(32, 4), new Accion(Accion.REDUCIR, 13));
        acciones.put(new Punto(32, 5), new Accion(Accion.REDUCIR, 13));
        acciones.put(new Punto(32, 9), new Accion(Accion.DESPLAZAR, 39));
        acciones.put(new Punto(33, 3), new Accion(Accion.DESPLAZAR, 41));
        acciones.put(new Punto(33, 4), new Accion(Accion.DESPLAZAR, 40));
        acciones.put(new Punto(34, 3), new Accion(Accion.REDUCIR, 19));
        acciones.put(new Punto(34, 4), new Accion(Accion.REDUCIR, 19));
        acciones.put(new Punto(34, 2), new Accion(Accion.DESPLAZAR, 42));
        acciones.put(new Punto(35, 2), new Accion(Accion.REDUCIR, 21));
        acciones.put(new Punto(35, 3), new Accion(Accion.REDUCIR, 21));
        acciones.put(new Punto(35, 4), new Accion(Accion.REDUCIR, 21));
        acciones.put(new Punto(36, 2), new Accion(Accion.REDUCIR, 22));
        acciones.put(new Punto(36, 3), new Accion(Accion.REDUCIR, 22));
        acciones.put(new Punto(36, 4), new Accion(Accion.REDUCIR, 22));
        acciones.put(new Punto(37, 2), new Accion(Accion.REDUCIR, 23));
        acciones.put(new Punto(37, 3), new Accion(Accion.REDUCIR, 23));
        acciones.put(new Punto(37, 4), new Accion(Accion.REDUCIR, 23));
        acciones.put(new Punto(38, 2), new Accion(Accion.REDUCIR, 24));
        acciones.put(new Punto(38, 3), new Accion(Accion.REDUCIR, 24));
        acciones.put(new Punto(38, 4), new Accion(Accion.REDUCIR, 24));
        acciones.put(new Punto(39, 14), new Accion(Accion.DESPLAZAR, 43));
        acciones.put(new Punto(40, 7), new Accion(Accion.REDUCIR, 16));
        acciones.put(new Punto(40, 8), new Accion(Accion.REDUCIR, 16));
        acciones.put(new Punto(40, 11), new Accion(Accion.REDUCIR, 16));
        acciones.put(new Punto(40, 12), new Accion(Accion.REDUCIR, 16));
        acciones.put(new Punto(40, 15), new Accion(Accion.REDUCIR, 16));
        acciones.put(new Punto(41, 14), new Accion(Accion.DESPLAZAR, 37));
        acciones.put(new Punto(41, 15), new Accion(Accion.DESPLAZAR, 38));
        acciones.put(new Punto(41, 16), new Accion(Accion.DESPLAZAR, 36));
        acciones.put(new Punto(42, 14), new Accion(Accion.DESPLAZAR, 37));
        acciones.put(new Punto(42, 15), new Accion(Accion.DESPLAZAR, 38));
        acciones.put(new Punto(42, 16), new Accion(Accion.DESPLAZAR, 36));
        acciones.put(new Punto(43, 10), new Accion(Accion.DESPLAZAR, 46));
        acciones.put(new Punto(44, 2), new Accion(Accion.DESPLAZAR, 42));
        acciones.put(new Punto(44, 3), new Accion(Accion.REDUCIR, 18));
        acciones.put(new Punto(44, 4), new Accion(Accion.REDUCIR, 18));
        acciones.put(new Punto(45, 2), new Accion(Accion.REDUCIR, 20));
        acciones.put(new Punto(45, 3), new Accion(Accion.REDUCIR, 20));
        acciones.put(new Punto(45, 4), new Accion(Accion.REDUCIR, 20));
        acciones.put(new Punto(46, 4), new Accion(Accion.REDUCIR, 14));
        acciones.put(new Punto(46, 5), new Accion(Accion.REDUCIR, 14));
        acciones.put(new Punto(46, 9), new Accion(Accion.REDUCIR, 14));*/
        acciones.put(new Punto(0, Token.DOUBLE), new Accion(Accion.DESPLAZAR, 6));
        acciones.put(new Punto(0, Token.INT), new Accion(Accion.DESPLAZAR, 5));
        acciones.put(new Punto(0, Token.MAIN), new Accion(Accion.REDUCIR, 3));
        acciones.put(new Punto(1, Token.EOF), new Accion(Accion.ACEPTAR, 0));
        acciones.put(new Punto(2, Token.MAIN), new Accion(Accion.DESPLAZAR, 7));
        acciones.put(new Punto(3, Token.DOUBLE), new Accion(Accion.DESPLAZAR, 6));
        acciones.put(new Punto(3, Token.INT), new Accion(Accion.DESPLAZAR, 5));
        acciones.put(new Punto(3, Token.MAIN), new Accion(Accion.REDUCIR, 3));
        acciones.put(new Punto(4, Token.ID), new Accion(Accion.DESPLAZAR, 9));
        acciones.put(new Punto(5, Token.ID), new Accion(Accion.REDUCIR, 5));
        acciones.put(new Punto(6, Token.ID), new Accion(Accion.REDUCIR, 6));
        acciones.put(new Punto(7, Token.PARI), new Accion(Accion.DESPLAZAR, 10));
        acciones.put(new Punto(8, Token.MAIN), new Accion(Accion.REDUCIR, 2));
        acciones.put(new Punto(9, Token.PARI), new Accion(Accion.DESPLAZAR, 11));
        acciones.put(new Punto(10, Token.PARD), new Accion(Accion.DESPLAZAR, 12));
        acciones.put(new Punto(11, Token.PARD), new Accion(Accion.DESPLAZAR, 13));
        acciones.put(new Punto(12, Token.LLAVEI), new Accion(Accion.DESPLAZAR, 15));
        acciones.put(new Punto(13, Token.LLAVEI), new Accion(Accion.DESPLAZAR, 15));
        acciones.put(new Punto(14, Token.EOF), new Accion(Accion.REDUCIR, 1));
        acciones.put(new Punto(15, Token.LLAVEI), new Accion(Accion.DESPLAZAR, 15));
        acciones.put(new Punto(15, Token.DOUBLE), new Accion(Accion.DESPLAZAR, 6));
        acciones.put(new Punto(15, Token.LLAVED), new Accion(Accion.REDUCIR, 10));
        acciones.put(new Punto(15, Token.INT), new Accion(Accion.DESPLAZAR, 5));
        acciones.put(new Punto(15, Token.ID), new Accion(Accion.DESPLAZAR, 21));
        acciones.put(new Punto(16, Token.DOUBLE), new Accion(Accion.REDUCIR, 4));
        acciones.put(new Punto(16, Token.INT), new Accion(Accion.REDUCIR, 4));
        acciones.put(new Punto(16, Token.MAIN), new Accion(Accion.REDUCIR, 4));
        acciones.put(new Punto(17, Token.LLAVED), new Accion(Accion.DESPLAZAR, 24));
        acciones.put(new Punto(18, Token.LLAVEI), new Accion(Accion.DESPLAZAR, 15));        
        acciones.put(new Punto(18, Token.LLAVED), new Accion(Accion.REDUCIR, 10));
        acciones.put(new Punto(18, Token.DOUBLE), new Accion(Accion.DESPLAZAR, 6));
        acciones.put(new Punto(18, Token.INT), new Accion(Accion.DESPLAZAR, 5));
        acciones.put(new Punto(18, Token.ID), new Accion(Accion.DESPLAZAR, 21));
        acciones.put(new Punto(19, Token.LLAVEI), new Accion(Accion.DESPLAZAR, 15));
        acciones.put(new Punto(19, Token.LLAVED), new Accion(Accion.REDUCIR, 10));
        acciones.put(new Punto(19, Token.DOUBLE), new Accion(Accion.DESPLAZAR, 6));
        acciones.put(new Punto(19, Token.INT), new Accion(Accion.DESPLAZAR, 5));
        acciones.put(new Punto(19, Token.ID), new Accion(Accion.DESPLAZAR, 21));
        acciones.put(new Punto(20, Token.PYC), new Accion(Accion.DESPLAZAR, 27));
        acciones.put(new Punto(20, Token.COMA), new Accion(Accion.DESPLAZAR, 28));
        acciones.put(new Punto(21, Token.ASIG), new Accion(Accion.DESPLAZAR, 29));
        acciones.put(new Punto(22, Token.LLAVEI), new Accion(Accion.REDUCIR, 17));
        acciones.put(new Punto(22, Token.LLAVED), new Accion(Accion.REDUCIR, 17));
        acciones.put(new Punto(22, Token.DOUBLE), new Accion(Accion.REDUCIR, 17));
        acciones.put(new Punto(22, Token.INT), new Accion(Accion.REDUCIR, 17));
        acciones.put(new Punto(22, Token.ID), new Accion(Accion.REDUCIR, 17));
        acciones.put(new Punto(23, Token.ID), new Accion(Accion.DESPLAZAR, 31));
        acciones.put(new Punto(24, Token.LLAVEI), new Accion(Accion.REDUCIR, 17));
        acciones.put(new Punto(24, Token.LLAVED), new Accion(Accion.REDUCIR, 17));
        acciones.put(new Punto(24, Token.DOUBLE), new Accion(Accion.REDUCIR, 17));
        acciones.put(new Punto(24, Token.INT), new Accion(Accion.REDUCIR, 17));
        acciones.put(new Punto(24, Token.MAIN), new Accion(Accion.REDUCIR, 17));
        acciones.put(new Punto(24, Token.ID), new Accion(Accion.REDUCIR, 17));
        acciones.put(new Punto(24, Token.EOF), new Accion(Accion.REDUCIR, 7));
        acciones.put(new Punto(25, Token.LLAVED), new Accion(Accion.REDUCIR, 8));
        acciones.put(new Punto(26, Token.LLAVED), new Accion(Accion.REDUCIR, 9));
        acciones.put(new Punto(27, Token.LLAVEI), new Accion(Accion.REDUCIR, 11));
        acciones.put(new Punto(27, Token.LLAVED), new Accion(Accion.REDUCIR, 11));
        acciones.put(new Punto(27, Token.DOUBLE), new Accion(Accion.REDUCIR, 11));
        acciones.put(new Punto(27, Token.INT), new Accion(Accion.REDUCIR, 11));
        acciones.put(new Punto(27, Token.ID), new Accion(Accion.REDUCIR, 11));
        acciones.put(new Punto(28, Token.ID), new Accion(Accion.DESPLAZAR, 31));
        acciones.put(new Punto(29, Token.ENTERO), new Accion(Accion.DESPLAZAR, 37));
        acciones.put(new Punto(29, Token.ID), new Accion(Accion.DESPLAZAR, 38));
        acciones.put(new Punto(29, Token.REAL), new Accion(Accion.DESPLAZAR, 36));
        acciones.put(new Punto(30, Token.CORI), new Accion(Accion.DESPLAZAR, 39));
        acciones.put(new Punto(30, Token.PYC), new Accion(Accion.REDUCIR, 12));
        acciones.put(new Punto(30, Token.COMA), new Accion(Accion.REDUCIR, 12));
        acciones.put(new Punto(31, Token.PYC), new Accion(Accion.REDUCIR, 39));
        acciones.put(new Punto(31, Token.COMA), new Accion(Accion.REDUCIR, 39));
        acciones.put(new Punto(31, Token.CORI), new Accion(Accion.REDUCIR, 39));
        acciones.put(new Punto(32, Token.PYC), new Accion(Accion.REDUCIR, 13));
        acciones.put(new Punto(32, Token.COMA), new Accion(Accion.REDUCIR, 13));
        acciones.put(new Punto(32, Token.CORI), new Accion(Accion.DESPLAZAR, 39));
        acciones.put(new Punto(33, Token.ADDOP), new Accion(Accion.DESPLAZAR, 41));
        acciones.put(new Punto(33, Token.PYC), new Accion(Accion.DESPLAZAR, 40));
        acciones.put(new Punto(34, Token.ADDOP), new Accion(Accion.REDUCIR, 19));
        acciones.put(new Punto(34, Token.PYC), new Accion(Accion.REDUCIR, 19));
        acciones.put(new Punto(34, Token.MULOP), new Accion(Accion.DESPLAZAR, 42));
        acciones.put(new Punto(35, Token.MULOP), new Accion(Accion.REDUCIR, 21));
        acciones.put(new Punto(35, Token.ADDOP), new Accion(Accion.REDUCIR, 21));
        acciones.put(new Punto(35, Token.PYC), new Accion(Accion.REDUCIR, 21));
        acciones.put(new Punto(36, Token.MULOP), new Accion(Accion.REDUCIR, 22));
        acciones.put(new Punto(36, Token.ADDOP), new Accion(Accion.REDUCIR, 22));
        acciones.put(new Punto(36, Token.PYC), new Accion(Accion.REDUCIR, 22));
        acciones.put(new Punto(37, Token.MULOP), new Accion(Accion.REDUCIR, 23));
        acciones.put(new Punto(37, Token.ADDOP), new Accion(Accion.REDUCIR, 23));
        acciones.put(new Punto(37, Token.PYC), new Accion(Accion.REDUCIR, 23));
        acciones.put(new Punto(38, Token.MULOP), new Accion(Accion.REDUCIR, 24));
        acciones.put(new Punto(38, Token.ADDOP), new Accion(Accion.REDUCIR, 24));
        acciones.put(new Punto(38, Token.PYC), new Accion(Accion.REDUCIR, 24));
        acciones.put(new Punto(39, Token.ENTERO), new Accion(Accion.DESPLAZAR, 43));
        acciones.put(new Punto(40, Token.LLAVEI), new Accion(Accion.REDUCIR, 16));
        acciones.put(new Punto(40, Token.LLAVED), new Accion(Accion.REDUCIR, 16));
        acciones.put(new Punto(40, Token.DOUBLE), new Accion(Accion.REDUCIR, 16));
        acciones.put(new Punto(40, Token.INT), new Accion(Accion.REDUCIR, 16));
        acciones.put(new Punto(40, Token.ID), new Accion(Accion.REDUCIR, 16));
        acciones.put(new Punto(41, Token.ENTERO), new Accion(Accion.DESPLAZAR, 37));
        acciones.put(new Punto(41, Token.ID), new Accion(Accion.DESPLAZAR, 38));
        acciones.put(new Punto(41, Token.REAL), new Accion(Accion.DESPLAZAR, 36));
        acciones.put(new Punto(42, Token.ENTERO), new Accion(Accion.DESPLAZAR, 37));
        acciones.put(new Punto(42, Token.ID), new Accion(Accion.DESPLAZAR, 38));
        acciones.put(new Punto(42, Token.REAL), new Accion(Accion.DESPLAZAR, 36));
        acciones.put(new Punto(43, Token.CORD), new Accion(Accion.DESPLAZAR, 46));
        acciones.put(new Punto(44, Token.MULOP), new Accion(Accion.DESPLAZAR, 42));
        acciones.put(new Punto(44, Token.ADDOP), new Accion(Accion.REDUCIR, 18));
        acciones.put(new Punto(44, Token.PYC), new Accion(Accion.REDUCIR, 18));
        acciones.put(new Punto(45, Token.MULOP), new Accion(Accion.REDUCIR, 20));
        acciones.put(new Punto(45, Token.ADDOP), new Accion(Accion.REDUCIR, 20));
        acciones.put(new Punto(45, Token.PYC), new Accion(Accion.REDUCIR, 20));
        acciones.put(new Punto(46, Token.PYC), new Accion(Accion.REDUCIR, 14));
        acciones.put(new Punto(46, Token.COMA), new Accion(Accion.REDUCIR, 14));
        acciones.put(new Punto(46, Token.CORI), new Accion(Accion.REDUCIR, 14));
        
    }
    
    private void inicializaDestinos()
    {
        destinos = new HashMap();
        
        destinos.put(new Punto(0, 1), 1);
        destinos.put(new Punto(0, 2), 2);
        destinos.put(new Punto(0, 3), 3);
        destinos.put(new Punto(0, 4), 4);
        destinos.put(new Punto(3, 2), 8);
        destinos.put(new Punto(3, 3), 3);
        destinos.put(new Punto(3, 4), 4);
        destinos.put(new Punto(12, 5), 14);
        destinos.put(new Punto(13, 5), 16);
        destinos.put(new Punto(15, 4), 23);
        destinos.put(new Punto(15, 5), 22);
        destinos.put(new Punto(15, 6), 17);
        destinos.put(new Punto(15, 7), 18);
        destinos.put(new Punto(15, 8), 20);
        destinos.put(new Punto(15, 10), 19);
        destinos.put(new Punto(18, 4), 23);
        destinos.put(new Punto(18, 5), 22);
        destinos.put(new Punto(18, 6), 25);
        destinos.put(new Punto(18, 7), 18);
        destinos.put(new Punto(18, 8), 20);
        destinos.put(new Punto(18, 10), 19);
        destinos.put(new Punto(19, 4), 23);
        destinos.put(new Punto(19, 5), 22);
        destinos.put(new Punto(19, 6), 26);
        destinos.put(new Punto(19, 7), 18);
        destinos.put(new Punto(19, 8), 20);
        destinos.put(new Punto(19, 10), 19);
        destinos.put(new Punto(23, 9), 30);
        destinos.put(new Punto(28, 9), 32);
        destinos.put(new Punto(29, 11), 33);
        destinos.put(new Punto(29, 12), 34);
        destinos.put(new Punto(29, 13), 35);
        destinos.put(new Punto(41, 12), 44);
        destinos.put(new Punto(41, 13), 35);
        destinos.put(new Punto(42, 13), 45);
    }
}
