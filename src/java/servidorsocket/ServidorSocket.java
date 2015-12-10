/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorsocket;

import java.net.*;
import java.io.*;
/**
 *
 * @author Rudolfaraya
 */
public class ServidorSocket {
    
    public static void main(String[] args){
        ServerSocket serverSocket;
        Socket socketCliente;
        DataInputStream in; //Flujo de datos de entrada
        DataOutputStream out; //Flujo de datos de salida
        String mensaje;
        
        // VARIABLES PARA MODIFICAR EL TAMANO DE LA CACHE DINÁMICA //
        
        int tamanoCache = 10;
        int numeroParticiones = 4;
        
        //---------------------------------------------------------//
        
        Cache cache = new Cache(tamanoCache,numeroParticiones); //Se crea una nueva cache con los datos ingresados
        
        try{
            serverSocket = new ServerSocket(4444);
            System.out.print("Servidor activo a la espera de peticiones");
            
            //MIENTRAS PERMANEZCA ACTIVO EL SERVIDOR ESPERARÁ POR PETICIONES DE LOS CLIENTES
            while(true){
                socketCliente = serverSocket.accept();
                in = new DataInputStream(socketCliente.getInputStream()); //Entrada de los mensajes del cliente
                mensaje = in.readUTF(); //Leo el mensaje enviado por el cliente
                                 
                System.out.println("\nHe recibido del cliente: "+mensaje); //Muestro el mensaje recibido por el cliente
                int particionBuscada = seleccionarParticion(mensaje, tamanoCache, numeroParticiones); //Busco la partición
                double tamanoParticion = Math.ceil( (double)tamanoCache / (double)numeroParticiones);
                
                Thread hilo = new Hilo(mensaje,particionBuscada,cache.GetTable(),(int) tamanoParticion);
                hilo.start();
                
                //RESPUESTA DEL SERVIDOR AL CLIENTE
                    out = new DataOutputStream(socketCliente.getOutputStream());
                    String respuesta = "SOLICITUD RECIBIDA";
                    out.writeUTF(respuesta);
                    System.out.println("\nHe respondido al cliente: "+respuesta);
                    
                
                  //CIERRE DE LOS SOCKETS (FLUJOS DE DATOS)
                out.close();
                in.close();
                socketCliente.close();
            }      
        }catch(Exception e){
            System.out.print(e.getMessage());
        }
    }
    
    //Metodo que establece en qué partición se debe agregar la nueva entrada en la cache dinámica.
    //El criterio es la suma en valor ASCII del primer y último caracter de la palabara ingresada por el cliente
    public static int seleccionarParticion(String mensaje, int tamanoCache, int numeroParticiones){
        double entradasMaximasParticion;
        double entradasASCIIParticion;
        int sumaMaximaASCII = 400;
        int particionBuscada = 0;
        
        int ascii = (int) mensaje.charAt(0) + (int) mensaje.charAt(mensaje.length()-1); //Calcula la suma de los ascii de la primera y última letra de la palabra           
        //particionBuscada = tamanoCache / numeroParticiones;
        entradasMaximasParticion = Math.ceil( (double)tamanoCache / (double)numeroParticiones);
        //Ejemplo: 16 tamano  5 particiones = 4 entradas cada particion
        entradasASCIIParticion = Math.ceil( (double)sumaMaximaASCII / (double)numeroParticiones);
        //Ejemplo : 512 sumaMaxima / 5 particiones = 103 entradasASCII por cada partición
        for(int i=0;i<numeroParticiones;i++){ //Recorre cada partición para identificar donde escribir la nueva entrada
            if(ascii < entradasASCIIParticion*(i+1)){
                particionBuscada = i+1;
                break;
            }
        }
        System.out.println("\nPara la suma ASCII: "+ascii+" la partición buscada es: "+particionBuscada); //Muestro el resultado obtenido
        return particionBuscada;
    }
    
}
