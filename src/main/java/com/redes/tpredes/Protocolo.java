package com.redes.tpredes;

import java.util.ArrayList;
import java.util.Scanner;


//Bytes bandera con relleno de bytes
public class Protocolo {

    String flag = "11111111";
    String fill = "00000000";
    char parity = 0;
    public ArrayList<String> bytes = new ArrayList();
    ArrayList<String> bytesParid = new ArrayList<>();
    ArrayList<String> bytesBuffer = new ArrayList<>();
    ArrayList<String> bufferFinal = new ArrayList<>();
    ArrayList<String> bufferFinal2 = new ArrayList<>();
    int counter = 0;


    public void send() {

        System.out.println("-------------------------------------------------------");
        System.out.println("------------------------EMISOR-------------------------");
        System.out.println("-------------------------------------------------------");

        System.out.print("Ingrese la cantidad de bytes que desea enviar: ");
        Scanner cantBytes = new Scanner(System.in);
        int cantBytes2 = cantBytes.nextInt();

        //se le solicita al usuario la cantidad de bytes que desea enviar y se agregan al array Bytes
        for (int i = 0; i < cantBytes2; i++){
            Scanner stringIn1 = new Scanner(System.in);
            String stringIn2 = stringIn1.nextLine();
            bytes.add(stringIn2);
        }

        //agregamos al array BytesParid todos los bytes que les pasa el usuario con su bit de paridad correspondiente
        for (int i = 0; i < bytes.size(); i++){
            counter = 0;
            for (int j = 0; j < bytes.get(i).length();j++){
                if (bytes.get(i).charAt(j) == flag.charAt(1)){
                    counter++;
                }
            }
            if (counter % 2 == 1 ){
                bytesParid.add(bytes.get(i) + 1);
            }else{
                bytesParid.add(bytes.get(i) + 0);
            }
        }

        //se muesetran todos los bytes que el usario desea enviar, con su bit de paridad correspondiente
        for (int i = 0; i < bytesParid.size(); i++){
            System.out.print(bytesParid.get(i) + ";");
        }

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("-------------------------------------------------------");
        System.out.println("------------------------BUFFER-------------------------");
        System.out.println("-------------------------------------------------------");


        //en el caso que se detecte que dentro de cada byte enviado hay un flag, lo que hacemos en meterle un fill luego de que el flag termina
        for (int i = 0; i < bytes.size(); i++){
            counter = 0;
            int counterFill = 0;

            for (int j = 0; j < bytes.get(i).length(); j++) {
                if (bytes.get(i).charAt(j) == flag.charAt(1)) {
                    counter++;
                    if (counter == 8) {
                        String byteFlag = flag + bytesParid.get(i).substring(0,j+1) + fill + bytesParid.get(i).substring(j+1) + flag;
                        bytesBuffer.add(byteFlag);
                    }
                }else{
                    counterFill++;
                    if (counterFill == 8) {
                        String byteFill = flag + bytesParid.get(i).substring(0,j+1) + fill + bytesParid.get(i).substring(j+1) + flag;
                        bytesBuffer.add(byteFill);
                    }
                }
            }
            if (counter < 8 && counterFill < 8){

                String byteNormal = flag + bytesParid.get(i) + flag;
                bytesBuffer.add(byteNormal);
            }
        }
        System.out.println(bytesBuffer);


        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("-------------------------------------------------------");
        System.out.println("------------------------RECEPTOR-----------------------");
        System.out.println("-------------------------------------------------------");


        //Se le saca al contenido del buffer los flags y los fill, para que el receptor solo tenga lo que el emisor le envió
        for (int i = 0; i < bytesBuffer.size(); i++){
            int counterFill = 0;
            String bytesFinal = bytesBuffer.get(i).substring(8, bytesBuffer.get(i).length() - 9);
            for(int j = 0; j < bytesFinal.length(); j++){
                if(bytesFinal.charAt(j) == fill.charAt(1)){
                    counterFill++;
                    if(counterFill == 8){
                        String bytesfinal2 = bytesFinal.substring(0, j - 7) + bytesFinal.substring(j + 1);
                        bufferFinal.add(bytesfinal2);
                    }
                }
            }
            if (counterFill<8){
                String byteFinal3 = bytesBuffer.get(i).substring(8, bytesBuffer.get(i).length() - 8);
                bufferFinal.add(byteFinal3);
            }
        }

        //Se modifica un caracter random de cada byte enviado para generar un error
        for(int i = 0; i < bytesParid.size();i++){
            int numero = (int)(Math.random()*bytesParid.get(i).length());
            int bitError = (int)(Math.random()+1);
            char byteBufferF = bytesParid.get(i).charAt(numero);

            if(byteBufferF == '0' || byteBufferF == '1'){
                byteBufferF = ((char) (bitError + 48));
            }
            String bufferRecibido = bytesParid.get(i).substring(0, numero) +  byteBufferF + bytesParid.get(i).substring(numero + 1);
            bufferFinal2.add(bufferRecibido);

        }

        //Se comprueba si el bit de paridad es correcto o no, si no es correcto tiene error
        for (int i = 0; i < bufferFinal2.size(); i++){
            counter = 0;
            for (int j = 0; j < bufferFinal2.get(i).length();j++){
                if (bufferFinal2.get(i).charAt(j) == flag.charAt(1)){
                    counter++;
                }
            }
            if (counter % 2 == 1 ){
                System.out.println(bufferFinal2.get(i) + "  Tiene Error");
            }else{
                System.out.println(bufferFinal2.get(i) + "  Recibido Correctamente");
            }
        }
    }
}
