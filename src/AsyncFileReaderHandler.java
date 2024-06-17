import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class AsyncFileReaderHandler {
    public static void main(String[] args) {
        try {
            AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(Paths.get("file.txt"), StandardOpenOption.READ);

            ByteBuffer buffer = ByteBuffer.allocate(4096);
            fileChannel.read(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer attachment) {
                    System.out.println("Прочитано байтов: " + result);
                    attachment.flip();
                    while (attachment.hasRemaining()) {
                        System.out.print((char) attachment.get());
                    }
                    try {
                        fileChannel.close();
                    } catch (Exception e) {
                        System.out.println("Внезапная ошибка при закрытии FileChannel!");
                    }
                }

                @Override
                public void failed(Throwable e, ByteBuffer attachment) {
                    System.out.println("Ошибка чтения!");
                }
            });

            // Делаем что-то другое пока читаются данные из файла (полезная программа)

        } catch (Exception e) {
            System.out.println("Внезапная ошибка!");
        }
    }
}

