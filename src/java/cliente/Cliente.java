/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.net.*;
import java.io.*;
import java.util.Scanner; //Sólo se usa para el ejemplo de leer datos por teclado (después lo eliminas)



/**
 *
 * @author Rudolfaraya
 */
public class Cliente{
    public Cliente(String palabraBuscada) {
        Socket miSocket;
        DataOutputStream out;
        DataInputStream in;
        
        try{
           
            miSocket = new Socket("127.0.0.1",4444);
            out = new DataOutputStream(miSocket.getOutputStream());
            
            //EJEMPLO POR INGRESO DE TECLADO
            /*
            System.out.println ("Por favor introduzca una cadena por teclado:");
            String entradaTeclado = "";
            Scanner entradaEscaner = new Scanner (System.in); //Creación de un objeto Scanner
            entradaTeclado = entradaEscaner.nextLine (); //Invocamos un método sobre un objeto Scanner
            out.writeUTF(entradaTeclado);
            System.out.println("He enviado al servidor: "+entradaTeclado);
            */    
            
            
            //EJEMPLO STRING YA ESTABLECIDO
            //String mensaje = "query gatos";
            
            out.writeUTF(palabraBuscada);
            System.out.println("He enviado al servidor: "+palabraBuscada);
            
            
            //LEER RESPUESTA DEL SERVIDOR
            in = new DataInputStream(miSocket.getInputStream());
            String mensajeRecibido = in.readUTF();
            System.out.println("He recibido del servidor: "+mensajeRecibido);
            
            //IMPORTANTE CERRAR LOS SOCKETS (FLUJOS DE DATOS)
            in.close();
            miSocket.close();
            out.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
               
    }
    public static void main(String[] args) {
        
        
    }
    
}
