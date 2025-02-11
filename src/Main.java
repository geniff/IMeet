import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException
    {
        try (Socket socket = new Socket("localhost", 8080);
             //request
             DataOutputStream rqStream = new DataOutputStream(socket.getOutputStream());
             //response
             DataInputStream rpStream = new DataInputStream(socket.getInputStream());
             Scanner scanner = new Scanner(System.in))
        {
            while (scanner.hasNext())
            {
                String request = scanner.next();
                rqStream.writeUTF(request);
                String response = rpStream.readUTF();
                System.out.println(response);
            }
        }
    }
} 