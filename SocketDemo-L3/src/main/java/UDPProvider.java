import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPProvider {
    public static void main(String[] args) throws Exception {
        System.out.println("UDPProvider Started.");

        //作为接收者，指定一个端口用于数据接收
        DatagramSocket ds = new DatagramSocket(20000);

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
        System.out.println("UDPProvider receive from ip:" + ip + "\tport:" + port + "\tdata:" + data);

        //构建回送数据
        String responseData = "Receive data with len:" + dataLen;
        byte[] responseDataBytes = responseData.getBytes();
        //直接根据发送者构建回送信息
        DatagramPacket responsePacket = new DatagramPacket(
                responseDataBytes,
                responseDataBytes.length,
                receivePack.getAddress(),
                receivePack.getPort()
        );
        ds.send(responsePacket);

        //完成
        System.out.println("UDPProvider Finished.");
        ds.close();
    }
}
