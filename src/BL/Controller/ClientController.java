package BL.Controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static Components.IMeetsBackServerRunner.clients;

// Класс для обработки клиента в отдельном потоке
public class ClientController extends Thread {
    public final Socket socket;
    public DataOutputStream outputStream;//поток для отправки данных клиенту
    private final int clientId;

    public ClientController(Socket socket, int clientId)
    {
        this.socket = socket;
        this.clientId = clientId;
    }

    //метод, который выполняется при запуске потока
    @Override
    public void run() {
        try (DataInputStream inputStream = new DataInputStream(socket.getInputStream()))//создаем поток для чтения данных из сокета
        {
            outputStream = new DataOutputStream(socket.getOutputStream());//инициализируем поток для отправки данных клиенту
            String request;
            while (true)
            {
                request = inputStream.readUTF();
                if ("stop".equals(request))
                {
                    System.out.println("Клиент " + clientId + " отключился: " + socket.getInetAddress());
                    break; // Выход из цикла при получении "stop"
                }
                System.out.println("Получено от клиента " + clientId + ": "  + request);
            }
        }
        catch (IOException e)
        {
            System.out.println("Ошибка при обработке клиента " + clientId + ": " + e.getMessage());
        }

        finally
        {
            clients.remove(clientId);
            try
            {
                socket.close(); // Закрытие сокета после завершения работы с клиентом
            }

            catch (IOException e)
            {
                System.out.println("Ошибка при закрытии сокета: " + e.getMessage());
            }
        }
    }
    // Метод для отправки сообщения клиенту
    public void sendMessage(String message)
    {
        try
        {
            outputStream.writeUTF(message);
        }

        catch (IOException e)
        {
            System.out.println("Ошибка при отправке сообщения клиенту: " + clientId + e.getMessage());
        }
    }
}
