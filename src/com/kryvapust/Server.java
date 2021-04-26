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
        int port = 8081;
        // сокет сервера
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.printf("Server on port %d started.%n", port);
        while (true) {
            // активация листенера сервера
            Socket clientSocket = serverSocket.accept();

            // поток для записи
            BufferedWriter writer = getBufferedWriter(clientSocket);
            System.out.println("<Server>  BufferedWriter is created.");
            // поток для чтения
            BufferedReader reader = getBufferedReader(clientSocket);
            System.out.println("<Server> BufferReader is created.");

            // чтение запроса
            String request = reader.readLine();
            System.out.println("Received request: " + request);

            // вызов метода, выполняющего задание
            String response = makeTask(request);
            System.out.println("Response to sent: " + response);

            // запись результата в поток вывода
            writer.write(response + "\n");

            // отправка ответа
            writer.flush();

            // закрытие потоков чтение и записи и сокета подключенного клиента
            writer.close();
            reader.close();
            clientSocket.close();
        }
    }

    private static BufferedReader getBufferedReader(Socket clientSocket) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
        BufferedReader reader = new BufferedReader(inputStreamReader);
        return reader;
    }

    private static BufferedWriter getBufferedWriter(Socket clientSocket) throws IOException {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter((clientSocket.getOutputStream()));
        BufferedWriter writer = new BufferedWriter(outputStreamWriter);
        return writer;
    }

    /**
     * метода, реализующий задание по сортировке полученного массива.
     * преобразовывает текстовую строку в массив текстовых значений ->
     * затем преобразовывает из в числа ->
     * вызовает метод сортировки
     */
    private static String makeTask(String request) {
        String[] split = request.replaceAll("[\\[\\]]", "").split(", ");
        double[] array = new double[split.length];
        for (int i = 0; i < split.length; i++) {
            array[i] = Double.parseDouble(split[i]);
        }
        // вызов метода быстрой сортировки
        quickSort(array, 0, array.length - 1);

        // преобразование массива чисел в текст
        ArrayList<Double> numbers = new ArrayList<>();
        for (double f : array) {
            numbers.add(f);
        }
        return numbers.toString();
    }

    /**
     * алгоритм быстрой сортировки
     */
    private static void quickSort(double[] split, int left, int right) {
        int i, j;
        double x, y;
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

        if (left < j) quickSort(split, left, j);
        if (i < right) quickSort(split, i, right);
    }
}
