import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.io.IOException;

public class AsyncFileWriterHandler {
    public static void main(String[] args) {

        try {
            // Открытие асинхронного файла для записи
            AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(Paths.get("file2.txt"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);
            ByteBuffer buffer = ByteBuffer.wrap("Hello world from AsyncFileWriterHandler!".getBytes());

            // Запуск асинхронной записи с использованием CompletionHandler
            fileChannel.write(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer attachment) {
                    System.out.println("Байтов записано: " + result);
                    try {
                        fileChannel.close();
                    } catch (IOException e) {
                        System.out.println("Внезапная ошибка при закрытии FileChannel!");
                    }
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    System.out.println("Ошибка записи!");
                    try {
                        fileChannel.close();
                    } catch (IOException e) {
                        System.out.println("Внезапная ошибка при закрытии FileChannel!");
                    }
                }
            });

            // Делаем что-то другое пока данные записываются в файл (полезная программа)

        } catch (IOException e) {
            System.out.println("Внезапная ошибка!");
        }
    }
}
