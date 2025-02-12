package Components;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IMeetsBackServerRunner {
    private static final List<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Сервер запущен и ожидает подключения...");

            // Запускаем поток для чтения сообщений от администратора
            // лямбда выражение, которое представляет реализацию интерфейса Runnable, который содержит метод run()
            new Thread(() -> {
                try (Scanner scanner = new Scanner(System.in)) {
                    while (true) {
                        String adminMessage = scanner.nextLine();
                        sendMessageToAllClients("Server: " + adminMessage);
                    }
                }
            }).start();

            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    System.out.println("Клиент подключен: " + socket.getInetAddress());
                    ClientHandler clientHandler = new ClientHandler(socket);
                    clients.add(clientHandler);
                    clientHandler.start();
                } catch (IOException e) {
                    System.out.println("Ошибка при подключении клиента: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод для отправки сообщения всем клиентам
    private static void sendMessageToAllClients(String message) {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }
}

// Класс для обработки клиента в отдельном потоке
class ClientHandler extends Thread {
    private final Socket socket;
    private DataOutputStream outputStream;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (DataInputStream inputStream = new DataInputStream(socket.getInputStream())) {
            outputStream = new DataOutputStream(socket.getOutputStream());
            String request;
            while (true) {
                request = inputStream.readUTF();
                if ("stop".equals(request)) {
                    System.out.println("Клиент отключился: " + socket.getInetAddress());
                    break; // Выход из цикла при получении "stop"
                }
                System.out.println("Получено от клиента: " + request);
                outputStream.writeUTF("Hi from server: " + request); // Эхо запроса
            }
        } catch (IOException e) {
            System.out.println("Ошибка при обработке клиента: " + e.getMessage());
        } finally {
            try {
                socket.close(); // Закрытие сокета после завершения работы с клиентом
            } catch (IOException e) {
                System.out.println("Ошибка при закрытии сокета: " + e.getMessage());
            }
        }
    }

    // Метод для отправки сообщения клиенту
    public void sendMessage(String message) {
        try {
            outputStream.writeUTF(message);
        } catch (IOException e) {
            System.out.println("Ошибка при отправке сообщения клиенту: " + e.getMessage());
        }
    }
}
