package server_client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class serverMain
{
    private int port;
    private String checkInput = "123145345623";
    private ServerSocket server;
    private Socket client;
    private DataInputStream input;
    private DataOutputStream output;

    public serverMain(int port) throws IOException
    {
        this.port = port;
        serverSettings();
    }

    public void serverSettings() throws IOException
    {
        server = new ServerSocket(port);
        server.setSoTimeout(60000);
        client = server.accept();
        System.out.println("Client connected.");
        input = new DataInputStream(client.getInputStream());
        output = new DataOutputStream(client.getOutputStream());
    }

    public String waiting() throws IOException
    {
       String message = input.readUTF();
       while(message == null || checkInput.equals(message))
       {
           message = input.readUTF();
       }
       checkInput = message;
       return message;
    }

    public void send(String message) throws IOException
    {
        output.writeUTF(message);
    }
}

