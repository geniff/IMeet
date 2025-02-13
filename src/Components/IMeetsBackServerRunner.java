package Components;

import BL.Controller.ClientController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class IMeetsBackServerRunner {
    private static final Map<Integer, ClientController> clients = new HashMap<>();
    private static int clientCounter = 0;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Сервер запущен и ожидает подключения...");
            System.out.println("Для того, чтобы отправить конкретному клиенту сообщение введите: " +
                    "send <clientID> <message>");

            // Запускаем поток для чтения сообщений от администратора
            // лямбда выражение, которое представляет реализацию интерфейса Runnable, который содержит метод run()
            new Thread(() -> {
                try (Scanner scanner = new Scanner(System.in))
                {
                    while (true) {
                        String adminMessage = scanner.nextLine();
                        if (adminMessage.startsWith("send"))
                        {
                            String[] parts = adminMessage.split(" ", 3); // ожидаемый формат ввода: send <clientID>, message
                            if (parts.length == 3)
                            {
                                int clientId = Integer.parseInt(parts[1]);
                                String message = parts[2];
                                sendMessageToClient(clientId, "Server: " + message);
                            }

                            else
                            {
                                System.out.println("Неверный формат команды. Используйте: send <clientId> <message>");
                            }
                        }

                        else
                        {
                            sendMessageToAllClients("Server: " + adminMessage);
                        }
                    }
                }
            }).start();

            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    clientCounter += 1;
                    System.out.println("Клиент " + clientCounter + " подключен: " + socket.getInetAddress());
                    ClientController clientHandler = new ClientController(socket, clientCounter);
                    clients.put(clientCounter, clientHandler);
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
    private static void sendMessageToAllClients(String message)
    {
        for (ClientController client : clients.values())
         {
            client.sendMessage(message);
        }
    }

    public static void sendMessageToClient(int clientId, String message)
    {
        ClientController client = clients.get(clientId);
        if (client != null)
        {
            client.sendMessage(message);
        }

        else
        {
            System.out.println("Клиент с ID " + clientId + " не найден.");
        }
    }
}