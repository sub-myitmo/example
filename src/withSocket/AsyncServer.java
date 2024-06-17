package withSocket;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class AsyncServer {
    public static void main(String[] args) {
        try {
            final AsynchronousServerSocketChannel serverChannel = AsynchronousServerSocketChannel.open();
            InetSocketAddress hostAddress = new InetSocketAddress("localhost", 1234);
            serverChannel.bind(hostAddress);

            System.out.println("Порт прослушивания: " + hostAddress);

            serverChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {
                @Override
                public void completed(AsynchronousSocketChannel result, Void attachment) {
                    serverChannel.accept(null, this);  // Accept the next connection
                    handleClient(result);
                }

                @Override
                public void failed(Throwable exc, Void attachment) {
                    System.out.println("Не удалось установить соединение!");
                }
            });

            // Чтобы сервер не завершился немедленно
            while (true) {
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            System.out.println("Внезапная ошибка!");
        }
    }

    private static void handleClient(AsynchronousSocketChannel clientChannel) {
        ByteBuffer buffer = ByteBuffer.allocate(4096);
        clientChannel.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                attachment.flip();
                String message = new String(attachment.array()).trim();
                System.out.println("Получено от клиента: " + message);

                // Отправить сообщение обратно клиенту
                clientChannel.write(ByteBuffer.wrap(("Сервер принял ваше сообщение").getBytes()));
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                System.out.println("Ошибка при чтении от клиента!");
            }
        });
    }
}

