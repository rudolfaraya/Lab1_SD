/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorsocket;

import java.util.*;
/**
 *
 * @author Hairon
 */
public class Hilo extends Thread{
    //Se declarna las variables a utilizar
    String Buscar; 
    int Nparticion;
    ArrayList<LinkedHashMap<String, String>> Tabla;
    int TamañoParticion;
    String Resultado;
    
    public Hilo(String Busca,int Nparticion,ArrayList<LinkedHashMap<String, String>> tabla,int TamañoParticion){ //Los recibe del servidor

        //Luego se instancian
        this.Buscar=Busca;
        this.Tabla=tabla;
        this.Nparticion=Nparticion;
        this.TamañoParticion=TamañoParticion;
        
    }
    
    //El hilo de ejecución
    public void run() {
      //System.out.println("LLEGUE AL RUN");
      
      this.Resultado = Buscarentrada(Buscar,0); //Busca la query en la cache estatica
      

      if(Resultado == null)Resultado = Buscarentrada(Buscar,Nparticion); //Si no la encuentra, busca en la partición dinámica que corresponde
      if(Resultado != null){
          ImprimirCache();// Si la encuentra en la partición dinámica, Imprime la cache con todas sus particiones
      }else { //Si no
          AgregarEntrada(Buscar,Nparticion); //agrega la entrada en la cache en la partición correspodiente
          ImprimirCache(); //Imprime la cache con todas sus particiones
      }
      
    }

public String Buscarentrada(String Busqueda,int Nparticion){
        
        String result = Tabla.get(Nparticion).get(Busqueda);
        if(result != null) {
            Tabla.get(Nparticion).remove(Busqueda);
            Tabla.get(Nparticion).put(Busqueda, result);
        }
        return result;
    
    }
    
    public void AgregarEntrada(String Busqueda,int Nparticion){
       
        String Respuesta =  new StringBuffer(Busqueda).reverse().toString();
        
        
            if (Tabla.get(Nparticion).containsKey(Busqueda)) { // HIT
            // Bring to front
                Tabla.get(Nparticion).remove(Busqueda);
                Tabla.get(Nparticion).put(Busqueda, Respuesta);
            } else { // MISS
            if(Tabla.get(Nparticion).size() == this.TamañoParticion) {
                String first_element = Tabla.get(Nparticion).entrySet().iterator().next().getKey();
                System.out.println("Removiendo: '" + first_element + "'");
                Tabla.get(Nparticion).remove(first_element);
            }
            Tabla.get(Nparticion).put(Busqueda, Respuesta);
            } Tabla.get(Nparticion).put(Busqueda, Respuesta);

        
    }
    
    public void ImprimirParticion(int Nparticion) {
        System.out.println("===== Particion de Cache N°"+Nparticion+" =====");
        System.out.println(" | "+Tabla.get(Nparticion).keySet() + " | ");
            System.out.println(" | "+Tabla.get(Nparticion).values() + " | ");
        System.out.println("========================");
    }
    
    public void ImprimirCache() {
        for(int i=0;i<Tabla.size();i++){
            System.out.println("===== Particion de Cache N°"+i+" =====");
            System.out.println(" | "+Tabla.get(i).keySet() + " | ");
            System.out.println(" | "+Tabla.get(i).values() + " | ");
            System.out.println("========================");
        }
    }
    public String getResultado(){
        return Resultado;
    }


    
}
