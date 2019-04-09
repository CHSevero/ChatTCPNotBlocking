import java.net.*;
import java.io.*;
import java.util.Scanner;

public class ChatTCPServer {
    public static void main (String args[]) {
        try{
            int serverPort = 7896; // the server port
            ServerSocket listenSocket = new ServerSocket(serverPort);
            while(true) {
                Socket clientSocket = listenSocket.accept();
                Connection c = new Connection(clientSocket);
            }
        } catch(IOException e) {System.out.println("Listen socket:"+e.getMessage());}
    }
}
class Connection extends Thread {
    public Connection (Socket aClientSocket) {
        try{


            MyWrite w = new MyWrite(aClientSocket);
            MyRead r = new MyRead(aClientSocket);
            Thread t1 = new Thread(w);
            Thread t2 = new Thread(r);
            t1.start();
            t2.start();

            t1.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {if(aClientSocket!=null) try {aClientSocket.close();}catch (IOException e){System.out.println("close:"+e.getMessage());}}
    }


}


class MyRead implements  Runnable {
    DataInputStream in ;
    Socket serverSocket;
    public MyRead(Socket aServerSocket){
        try{
            this.serverSocket = aServerSocket;
            this.in = new DataInputStream(serverSocket.getInputStream());

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

        }catch (EOFException e){
            System.out.println("EOF:"+e.getMessage());
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

