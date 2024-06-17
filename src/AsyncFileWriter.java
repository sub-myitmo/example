import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;

public class AsyncFileWriter  {
    public static void main(String[] args) {
        try {
            // Указываем путь к файлу и параметр канала (запись и создание)
            AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(Paths.get("file2.txt"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);

            ByteBuffer buffer = ByteBuffer.wrap("Hello world from AsyncFileWriter!".getBytes());
            Future<Integer> result = fileChannel.write(buffer, 0);

            // Делаем что-то другое пока данные записываются в файл (полезная программа)
            int k = 0;
            for (int i=0; i < 1000; i++){
                k++;
            }

            // Ждем завершения записи
            Integer bytesWritten = result.get();
            System.out.println("Записано байтов: " + bytesWritten);

            fileChannel.close();
        } catch (Exception e) {
            System.out.println("Внезапная ошибка!");
        }
    }
}

