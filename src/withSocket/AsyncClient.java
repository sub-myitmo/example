package withSocket;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.Future;

public class AsyncClient {
    public static void main(String[] args) {
        try {
            AsynchronousSocketChannel client = AsynchronousSocketChannel.open();
            InetSocketAddress hostAddress = new InetSocketAddress("localhost", 1234);
            Future<Void> future = client.connect(hostAddress);
            future.get();  // Ожидаем подключения

            String message = "Hello world from AsyncClient!";
            ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
            Future<Integer> writeResult = client.write(buffer);
            writeResult.get();  // Ожидаем завершения записи

            buffer.flip();
            Future<Integer> readResult = client.read(buffer);
            readResult.get();  // Ожидаем завершения чтения

            System.out.println("Получено от сервера: " + new String(buffer.array()));

            client.close();
        } catch (Exception e) {
            System.out.println("Внезапная ошибка!");
        }
    }
}

