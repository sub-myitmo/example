import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;

public class AsyncFileReader {
    public static void main(String[] args) {
        try {
            // ��������� ���� � ����� � �������� ������ (�� ������)
            AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(Paths.get("file.txt"), StandardOpenOption.READ);

            ByteBuffer buffer = ByteBuffer.allocate(4096);
            Future<Integer> result = fileChannel.read(buffer, 0);

            // ������ ���-�� ������ ���� �������� ������ �� ����� (�������� ���������)
            int k = 0;
            for (int i=0; i < 1000; i++){
                k++;
            }

            // ���� ���������� ������
            Integer bytesRead = result.get();
            System.out.println("��������� ������: " + bytesRead);

            // �������������� �����, ����� ��� ���������
            buffer.flip();
            // ����, ���� � ������ ���� ��������
            while (buffer.hasRemaining()) {
                System.out.print((char) buffer.get());
            }
            fileChannel.close();
        } catch (Exception e) {
            System.out.println("��������� ������!");
        }
    }
}

