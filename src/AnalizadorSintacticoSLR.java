
import java.awt.Point;
import java.util.HashMap;

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
    
    private Token token;
    private AnalizadorLexico al;
    private HashMap<Punto, Accion> acciones;
    private HashMap<Punto, Integer> destinos;
    
    public AnalizadorSintacticoSLR(AnalizadorLexico al)
    {
        this.al = al;
    }
    
    public void InicializaAcciones()
    {
        acciones = new HashMap();
        
        acciones.put(new Punto(0,11), new Accion(Accion.DESPLAZAR, 6));
        acciones.put(new Punto(0, 12), new Accion(Accion.DESPLAZAR, 5));
        acciones.put(new Punto(2, 13), new Accion(Accion.DESPLAZAR, 7));
        acciones.put(new Punto(3, 11), new Accion(Accion.DESPLAZAR, 6));
        acciones.put(new Punto(3, 12), new Accion(Accion.DESPLAZAR, 5));
        acciones.put(new Punto(4, 15), new Accion(Accion.DESPLAZAR, 9));
        acciones.put(new Punto(7, 0), new Accion(Accion.DESPLAZAR, 10));
        acciones.put(new Punto(9, 0), new Accion(Accion.DESPLAZAR, 11));
        acciones.put(new Punto(10, 1), new Accion(Accion.DESPLAZAR, 12));
        acciones.put(new Punto(11, 1), new Accion(Accion.DESPLAZAR, 13));
        acciones.put(new Punto(12, 7), new Accion(Accion.DESPLAZAR, 15));
        acciones.put(new Punto(13, 7), new Accion(Accion.DESPLAZAR, 15));
        acciones.put(new Punto(15, 7), new Accion(Accion.DESPLAZAR, 15));
        acciones.put(new Punto(15, 11), new Accion(Accion.DESPLAZAR, 6));
        acciones.put(new Punto(15, 12), new Accion(Accion.DESPLAZAR, 5));
        acciones.put(new Punto(15, 15), new Accion(Accion.DESPLAZAR, 21));
        acciones.put(new Punto(17, 8), new Accion(Accion.DESPLAZAR, 24));
        acciones.put(new Punto(18, 7), new Accion(Accion.DESPLAZAR, 15));
        acciones.put(new Punto(18, 11), new Accion(Accion.DESPLAZAR, 6));
        acciones.put(new Punto(18, 12), new Accion(Accion.DESPLAZAR, 5));
        acciones.put(new Punto(18, 15), new Accion(Accion.DESPLAZAR, 21));
        acciones.put(new Punto(19, 7), new Accion(Accion.DESPLAZAR, 15));
        acciones.put(new Punto(19, 11), new Accion(Accion.DESPLAZAR, 6));
        acciones.put(new Punto(19, 12), new Accion(Accion.DESPLAZAR, 5));
        acciones.put(new Punto(19, 15), new Accion(Accion.DESPLAZAR, 21));
        acciones.put(new Punto(20, 4), new Accion(Accion.DESPLAZAR, 27));
        acciones.put(new Punto(20, 5), new Accion(Accion.DESPLAZAR, 28));
        acciones.put(new Punto(21, 6), new Accion(Accion.DESPLAZAR, 29));
        acciones.put(new Punto(23, 15), new Accion(Accion.DESPLAZAR, 31));
        acciones.put(new Punto(28, 15), new Accion(Accion.DESPLAZAR, 31));
        acciones.put(new Punto(29, 14), new Accion(Accion.DESPLAZAR, 37));
        acciones.put(new Punto(29, 15), new Accion(Accion.DESPLAZAR, 38));
        acciones.put(new Punto(29, 16), new Accion(Accion.DESPLAZAR, 36));
        acciones.put(new Punto(30, 9), new Accion(Accion.DESPLAZAR, 39));
        acciones.put(new Punto(32, 9), new Accion(Accion.DESPLAZAR, 39));
        acciones.put(new Punto(33, 3), new Accion(Accion.DESPLAZAR, 41));
        acciones.put(new Punto(33, 4), new Accion(Accion.DESPLAZAR, 40));
        acciones.put(new Punto(34, 2), new Accion(Accion.DESPLAZAR, 42));
        acciones.put(new Punto(39, 14), new Accion(Accion.DESPLAZAR, 43));
        acciones.put(new Punto(41, 14), new Accion(Accion.DESPLAZAR, 37));
        acciones.put(new Punto(41, 15), new Accion(Accion.DESPLAZAR, 38));
        acciones.put(new Punto(41, 16), new Accion(Accion.DESPLAZAR, 36));
        acciones.put(new Punto(42, 14), new Accion(Accion.DESPLAZAR, 37));
        acciones.put(new Punto(42, 15), new Accion(Accion.DESPLAZAR, 38));
        acciones.put(new Punto(42, 16), new Accion(Accion.DESPLAZAR, 36));
        acciones.put(new Punto(43, 10), new Accion(Accion.DESPLAZAR, 46));
        acciones.put(new Punto(44, 2), new Accion(Accion.DESPLAZAR, 42));
        
    }
    
    public void inicializaDestinos()
    {
        destinos = new HashMap();
        //acciones.put(new Punto(0, 13), new Accion(Accion.REDUCIR, 3));
    }
}
