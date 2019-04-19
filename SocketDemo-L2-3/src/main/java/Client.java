import java.io.*;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();
        //超时时间
        socket.setSoTimeout(3000);
        //连接本地，端口2000，超时时间3000ms
        socket.connect(new InetSocketAddress(Inet4Address.getLocalHost(), 2000), 3000);
        System.out.println("已发起服务器连接，并进入后续流程");
        System.out.println("客户端信息:" + socket.getLocalAddress() + " p:" + socket.getLocalPort());
        System.out.println("服务端信息:" + socket.getInetAddress() + " p:" + socket.getPort());
        try {
            todo(socket);
        } catch (Exception e) {
            System.out.println("关闭异常");
        }
        socket.close();
        System.out.println("客户端退出");
    }

    private static void todo(Socket client) throws IOException {
        InputStream in = System.in;
        BufferedReader input = new BufferedReader(new InputStreamReader(in));

        OutputStream outputStream = client.getOutputStream();
        PrintStream socketPrintStream = new PrintStream(outputStream);

        InputStream inputStream = client.getInputStream();
        BufferedReader socketBufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        boolean flag = true;
        do {
            // 键盘读取一行
            String str = input.readLine();
            // 发送到服务器
            socketPrintStream.println(str);
            // 从服务器读取一行
            String echo = socketBufferedReader.readLine();
            if ("bye".equalsIgnoreCase(echo)) {
                flag = false;
            } else {
                System.out.println(echo);
            }
        } while (flag);

        // 资源释放
        socketPrintStream.close();
        socketBufferedReader.close();
    }
}
