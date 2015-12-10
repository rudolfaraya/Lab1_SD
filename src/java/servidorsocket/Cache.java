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
public class Cache  {
    
    int tamaño;
    int particiones;
    int TamañoParticion;

    ArrayList<LinkedHashMap<String, String>> Cache= new ArrayList<LinkedHashMap<String, String>>();
    
    
    // Cache estatico se ingresa mediante string por aqui (por el momento)//
    static String[] Estatico = {"query 1", "query 2", "query 3", "query 4", "query 5", "query 6", "query 7", "query 8", "query 9", "query 10", "query 11", "query 12", "query 13", "query 14", "query 15", "query 16", "query 17", "query 18", "query 19", "query 20"};
    static String[] ResEstatico = {"answer 1", "answer 2", "answer 3", "answer 4", "answer 5", "answer 6", "answer 7", "answer 8", "answer 9", "answer 10", "answer 11", "answer 12", "answer 13", "answer 14", "answer 15", "answer 16", "answer 17", "answer 18", "answer 19", "answer 20"};
    
    public  ArrayList<LinkedHashMap<String, String>> GetTable(){
    
    return Cache;
    }
    
    public Cache(int tamaño,int particiones){  // Crea el cache estativo y las particiones vacias de cache dinamico//
    
        this.tamaño=tamaño;
        this.particiones=particiones;
        
        double x;     
        
        x = Math.ceil( (double) tamaño / (double)particiones ); // redondea hacia arriba el numero de cada particion 
        this.TamañoParticion = (int)x;
        System.out.print("Datos del Cache\nTamaño Solicitado ="+this.tamaño+"\nNumero Particiones = "+this.particiones+"\nTamaño de Cada Partcion ="+x+"\nTamaño Total ="+x*particiones+"\n");
        
        
        EstaticCache();
        DimanicCache();
    }

    
    public void EstaticCache(){  // crea la pparticion del cache estatico
        LinkedHashMap<String, String> ParticionAux = new LinkedHashMap <String,String>();
        Cache.add(ParticionAux);
        for (int i = 0; i < Estatico.length; i++) {
           
                Cache.get(0).put(Estatico[i], ResEstatico[i]);
               // System.out.println("===== Particion de Cache N°"+ParticionAux.values()+" =====");
                
        }
        //this.Cache.add(ParticionAux); // carga el hashMap en una particion del cache
        //System.out.println("===== Particion de Cache AQUIAQUI"+Cache.get(0).values()+" =====");
       // ParticionAux.clear(); // limpia el Hashmap aux
    }
    
    
    public void DimanicCache(){
         
        for (int i = 0; i < particiones; i++) {
           
                LinkedHashMap<String, String> ParticionAux = new LinkedHashMap <String,String>();
                Cache.add(ParticionAux);
        }
         // carga el hashMap en una particion del cache
     
    }

    public String Buscarentrada(String Busqueda,int Nparticion){
        
        String result = Cache.get(Nparticion).get(Busqueda);
        if(result != null) {
            Cache.get(Nparticion).remove(Busqueda);
            Cache.get(Nparticion).put(Busqueda, result);
        }
        return result;
    
    }
    
    public void AgregarEntrada(String Busqueda,int Nparticion){
       
        String Respuesta =  new StringBuffer(Busqueda).reverse().toString();
        
        
                if (Cache.get(Nparticion).containsKey(Busqueda)) { // HIT
            // Bring to front
            Cache.get(Nparticion).remove(Busqueda);
            Cache.get(Nparticion).put(Busqueda, Respuesta);
        } else { // MISS
            if(Cache.get(Nparticion).size() == this.TamañoParticion) {
                String first_element = Cache.get(Nparticion).entrySet().iterator().next().getKey();
                System.out.println("Removiendo: '" + first_element + "'");
                Cache.get(Nparticion).remove(first_element);
            }
            Cache.get(Nparticion).put(Busqueda, Respuesta);
        }            Cache.get(Nparticion).put(Busqueda, Respuesta);

        
    }
    
    public void ImprimirParticion(int Nparticion) {
        System.out.println("===== Particion de Cache N°"+Nparticion+" =====");
        System.out.println(" | "+Cache.get(Nparticion).keySet() + " | ");
            System.out.println(" | "+Cache.get(Nparticion).values() + " | ");
        System.out.println("========================");
    }
    
    public void ImprimirCache() {
        for(int i=0;i<Cache.size();i++){
            System.out.println("===== Particion de Cache N°"+i+" =====");
            System.out.println(" | "+Cache.get(i).keySet() + " | ");
            System.out.println(" | "+Cache.get(i).values() + " | ");
            System.out.println("========================");
        }
    }
    
    
    
}
    
    

