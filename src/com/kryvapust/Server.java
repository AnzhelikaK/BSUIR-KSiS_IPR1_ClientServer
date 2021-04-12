package com.kryvapust;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        ServerSocket serverSocket = new ServerSocket(port); // создание класса сокета сервераб работающего по протоколу TCP
        System.out.printf("Server on port %d started.%n", port);
        while (true) {
            Socket clientSocket = serverSocket.accept(); // Лисенер подключения
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter((clientSocket.getOutputStream())); // поток записи
            BufferedWriter writer = new BufferedWriter(outputStreamWriter);// буфер потока записи
            System.out.println("<Server> BufferedWriter created");

            InputStreamReader inputStreamReader = new InputStreamReader(clientSocket.getInputStream()); // поток чтения
            BufferedReader reader = new BufferedReader(inputStreamReader); // буфер потока чтения
            System.out.println("<Server> BufferReader created");

            String request = reader.readLine(); // чтение запроса
            System.out.println("Received request: " + request);

            String response = makeTask(request); // вызов метода, выполняющего сортировку полученного массива
            System.out.println("Response to sent: " + response);

            writer.write(response + "\n"); // запись результата в буффер поток вывода
            writer.flush(); // немедленная отправкаинформации из буффера вывода

            // закрытие потоков чтение и записи и сокета подключенного клиента
            writer.close();
            reader.close();
            clientSocket.close();
        }
    }

    private static String makeTask(String request) {
        String[] split = request.replaceAll("[\\[\\]]", "").split(", ");
        float[] array = new float[split.length];
        for (int i = 0; i < split.length; i++) {
            array[i] = Float.parseFloat(split[i]);
        }
        // вызов метода быстрой сортировки
        qs(array, 0, array.length - 1);
        // преобразование массива в текст
        ArrayList<Float> floats = new ArrayList<>();
        for (float f : array) {
            floats.add(f);
        }
        return floats.toString();
    }

    // алгоритм быстрой сортировки
    private static void qs(float[] split, int left, int right) {
        int i, j;
        float x, y;
        i = left;
        j = right;
        x = split[(left + right) / 2];
        do {
            while ((split[i] < x) && (i < right)) i++;
            while ((x < split[j]) && (j > left)) j--;

            if (i <= j) {
                y = split[i];
                split[i] = split[j];
                split[j] = y;
                i++;
                j--;
            }
        } while (i <= j);

        if (left < j) qs(split, left, j);
        if (i < right) qs(split, i, right);
    }
}
