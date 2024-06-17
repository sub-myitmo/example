package withSocket;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class AsyncClientHandler {
    public static void main(String[] args) {
        try {
            AsynchronousSocketChannel client = AsynchronousSocketChannel.open();
            InetSocketAddress hostAddress = new InetSocketAddress("localhost", 1234);
            client.connect(hostAddress, null, new CompletionHandler<Void, Void>() {
                @Override
                public void completed(Void result, Void attachment) {
                    String message = "Hello world from AsyncClientHandler!";
                    ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
                    client.write(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                        @Override
                        public void completed(Integer result, ByteBuffer attachment) {
                            attachment.flip();
                            client.read(attachment, attachment, new CompletionHandler<Integer, ByteBuffer>() {

                                @Override
                                public void completed(Integer result, ByteBuffer attachment) {
                                    System.out.println("Получено от сервера: " + new String(attachment.array()));
                                    try {
                                        client.close();
                                    } catch (Exception e) {
                                        System.out.println("Внезапная ошибка при закрытии SocketChannel!");
                                    }
                                }

                                @Override
                                public void failed(Throwable e, ByteBuffer attachment) {
                                    System.out.println("Ошибка чтения!");
                                }
                            });
                        }

                        @Override
                        public void failed(Throwable e, ByteBuffer attachment) {
                            System.out.println("Ошибка записи!");
                        }
                    });
                }

                @Override
                public void failed(Throwable exc, Void attachment) {
                    System.out.println("Ошибка соединения!");
                }
            });

            // Делаем что-то другое пока выполняются операции при подключении (полезная программа)

        } catch (Exception e) {
            System.out.println("Внезапная ошибка!");
        }
    }
}

