
// Java implementation for multithreaded chat client
// Save file as Client.java

import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatTCPClient
{

    public static void main (String args[]) {
        // arguments supply message and hostname
        Socket s = null;
        int serverPort = 7896;

        try{

            s = new Socket("localhost", serverPort);
            MyWrite w = new MyWrite(s);
            MyRead r = new MyRead(s);
            Thread t1 = new Thread(w);
            Thread t2 = new Thread(r);
            t1.start();
            t2.start();

            t1.join();

        }catch (UnknownHostException e){System.out.println("Socket:"+e.getMessage());
        }catch (EOFException e){System.out.println("EOF:"+e.getMessage());
        }catch (IOException e) {
            System.out.println("readline:" + e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {if(s!=null) try {s.close();}catch (IOException e){System.out.println("close:"+e.getMessage());}}
    }


}
class MyRead implements  Runnable {
    DataInputStream in ;
    Socket serverSocket;
    public MyRead(Socket aServerSocket){
        try{
            this.serverSocket = aServerSocket;
            this.in = new DataInputStream(serverSocket.getInputStream());
            //this.start();
        }catch (IOException e){System.out.println("Connection: "+e.getMessage());}
    }

    public void run(){
        String data;
        try {			                 // an echo server
            //While adicionado depois
            while(true){
                data = in.readUTF();
                System.out.println(data);
            }

        }catch (EOFException e){System.out.println("EOF:"+e.getMessage());
        } catch(IOException e) {System.out.println("readline:"+e.getMessage());
        } finally{ try {serverSocket.close();}catch (IOException e){/*close failed*/}}

    }
}

class MyWrite implements  Runnable {
    DataOutputStream out;
    Socket serverSocket;
    public MyWrite(Socket aServerSocket){
        try{
            this.serverSocket = aServerSocket;
            this.out = new DataOutputStream(serverSocket.getOutputStream());
            //this.start();
        }catch (IOException e){System.out.println("Connection: "+e.getMessage());}
    }

    public void run(){
        Scanner scn = new Scanner(System.in);
        String msg;
        try {			                 // an echo server
            //While adicionado depois
            while(true) {
                msg = scn.nextLine();
                out.writeUTF(msg);
            }

        }catch (EOFException e){System.out.println("EOF:"+e.getMessage());
        } catch(IOException e) {System.out.println("readline:"+e.getMessage());
        } finally{ try {serverSocket.close();}catch (IOException e){/*close failed*/}}

    }
}

