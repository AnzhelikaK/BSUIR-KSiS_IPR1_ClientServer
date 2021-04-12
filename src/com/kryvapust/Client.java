package com.kryvapust;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {


    public static void main(String[] args) throws IOException {
        int port = 8080;
        Socket socket = new Socket("127.0.0.1", port); // создание сокета клиента на локальном хосту, работающего по протоколу TCP на IP
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream()); // поток записи
        BufferedWriter writer = new BufferedWriter(outputStreamWriter); // буфер потока записи
        System.out.println("<Client> BufferedWriter created");

        InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream()); // поток чтения
        BufferedReader reader = new BufferedReader(inputStreamReader); // буфер потока чтения
        System.out.println("<Client> BufferReader created");


        String request = inputArray(); // ввод вектора (массив чисел с плавающей точкой)
        System.out.println("Input array for sending: " + request);

        writer.write(request + "\n"); // запись введенного массива в буффер поток вывода
        writer.flush();  // немедленная отправка информации из буффера вывода
        System.out.println("Request is sent.");
        String result = reader.readLine(); // чтение ответа Сервера
        System.out.println("Received result from Server: " + result);

        // закрытие потоков чтение и записи и самого сокета
        reader.close();
        writer.close();
        socket.close();
    }

    private static String inputArray() {
        System.out.println("Input array use Enter after each number. Ctrl+D - Submit: ");
        Scanner scanner = new Scanner(System.in);
        ArrayList<Double> array = new ArrayList<>();
        while (scanner.hasNext()) {
            double v = scanner.nextDouble();
            array.add(v);
        }
        return array.toString(); // преобразование введенного массива в текстовый вид
    }
}
