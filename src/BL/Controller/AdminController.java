package BL.Controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static Components.IMeetsBackServerRunner.clients;

public class AdminController extends Thread {
    public final Socket socket;
    public DataOutputStream dataOut;
    private DataInputStream dataIn;

    public AdminController(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            dataIn = new DataInputStream(socket.getInputStream());
            dataOut = new DataOutputStream(socket.getOutputStream());
            String request;
            while (true) {
                request = dataIn.readUTF();
                if ("stop".equals(request)) {
                    System.out.println("Админ клиент отключился");
                    break;
                }
                System.out.println("Получено от админа: " + request);
            }
        } catch (IOException e) {
            System.out.println("Ошибка при обработке админ-клиента: " + e.getMessage());
        } finally {
            closeSocket(); // Закрываем сокет в блоке finally
        }
    }

    // Метод для отправки сообщения всем клиентам
    public static void sendMessageToAllClients(String message) {
        for (ClientController client : clients.values()) {
            client.sendMessage(message);
        }
    }

    public void sendMessageToClient(int clientId, String message) {
        ClientController client = clients.get(clientId);
        if (client != null) {
            if (client.socket != null && !client.socket.isClosed()) {
                client.sendMessage("send " + clientId + " " + message);
            } else {
                System.out.println("Сокет клиента " + clientId + " закрыт.");
            }
        } else {
            System.out.println("Клиент с ID " + clientId + " не найден.");
        }
    }


    public void closeSocket() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            System.out.println("Ошибка при закрытии соединения: " + e.getMessage());
        }
    }
}
