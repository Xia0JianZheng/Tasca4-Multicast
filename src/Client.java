import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
public class Client {

        /* Client afegit al grup multicast SrvVelocitats.java que representa un velocímetre */

        private boolean continueRunning = true;
        private MulticastSocket socket;
        private InetAddress multicastIP;
        private int port;
        private NetworkInterface netIf;
        private InetSocketAddress group;


        public Client(int portValue, String strIp) throws IOException {
            multicastIP = InetAddress.getByName(strIp);
            port = portValue;
            socket = new MulticastSocket(port);
            netIf = socket.getNetworkInterface();
            group = new InetSocketAddress(multicastIP,port);
        }

        public void runClient() throws IOException{
            DatagramPacket packet;
            byte [] receivedData = new byte[1024];

            socket.joinGroup(group, netIf);
            System.out.printf("Connectat a %s:%d%n", group.getAddress(), group.getPort());

            while(continueRunning){
                packet = new DatagramPacket(receivedData, receivedData.length);
                socket.setSoTimeout(5000);
                try{
                    socket.receive(packet);
                    continueRunning = getData(packet.getData(),packet.getLength());
                }catch(SocketTimeoutException e){
                    System.out.println("S'ha perdut la connexió amb el servidor.");
                    continueRunning = false;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            socket.close();
        }

        protected  boolean getData(byte[] data,int length) {
            boolean ret = true;
            String msg = new String(data,0, length);

            String[] countWord = msg.trim().split(" ");
            int contador = countWord.length;

            if(contador > 8){
                System.out.println(msg);
            }
            return ret;
        }

        public static void main(String[] args) throws IOException {
            Client cvel = new Client(5557, "224.0.11.111");
            cvel.runClient();
            System.out.println("Parat!");

        }


}
