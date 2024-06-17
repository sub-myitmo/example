import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;

public class AsyncFileReader {
    public static void main(String[] args) {
        try {
            // Указываем путь к файлу и параметр канала (на чтение)
            AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(Paths.get("file.txt"), StandardOpenOption.READ);

            ByteBuffer buffer = ByteBuffer.allocate(4096);
            Future<Integer> result = fileChannel.read(buffer, 0);

            // Делаем что-то другое пока читаются данные из файла (полезная программа)
            int k = 0;
            for (int i=0; i < 1000; i++){
                k++;
            }

            // Ждем завершения чтения
            Integer bytesRead = result.get();
            System.out.println("Прочитано байтов: " + bytesRead);

            // переворачиваем буфер, чтобы его прочитать
            buffer.flip();
            // цикл, пока в буфере есть элементы
            while (buffer.hasRemaining()) {
                System.out.print((char) buffer.get());
            }
            fileChannel.close();
        } catch (Exception e) {
            System.out.println("Внезапная ошибка!");
        }
    }
}

