import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPResearcher {
    public static void main(String[] args) throws Exception {
        System.out.println("UDPResearcher Started.");

        //作为搜索方，让系统分配端口
        DatagramSocket ds = new DatagramSocket();

        //构建回送数据
        String requestData = "Hello World!";
        byte[] requestDataBytes = requestData.getBytes();
        //直接根据发送者构建回送信息
        DatagramPacket requestPacket = new DatagramPacket(
                requestDataBytes,
                requestDataBytes.length
        );
        requestPacket.setAddress(InetAddress.getLocalHost());
        requestPacket.setPort(20000);
        ds.send(requestPacket);

        //构建接收实体
        final byte[] buf = new byte[512];
        DatagramPacket receivePack = new DatagramPacket(buf, buf.length);

        //接收
        ds.receive(receivePack);

        //接收到的信息和发送者的信息
        String ip = receivePack.getAddress().getHostAddress();
        int port = receivePack.getPort();
        int dataLen = receivePack.getLength();
        String data = new String(receivePack.getData(), 0, dataLen);
        System.out.println("UDPResearcher receive from ip:" + ip + "\tport:" + port + "\tdata:" + data);


        //完成
        System.out.println("UDPResearcher Finished.");
        ds.close();
    }
}
