import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
public class Client {

        /* Client afegit al grup multicast SrvVelocitats.java que representa un velocímetre */

        private boolean continueRunning = true;
        private MulticastSocket socket;
        private InetAddress multicastIP;
        private int port;


        public Client(int portValue, String strIp) throws IOException {
            multicastIP = InetAddress.getByName(strIp);
            port = portValue;
            socket = new MulticastSocket(port);
            //netIf = NetworkInterface.getByName("enp1s0");
        }

        public void runClient() throws IOException{
            DatagramPacket packet;
            byte [] receivedData = new byte[1024];

            while(continueRunning){
                packet = new DatagramPacket(receivedData, 1024);
                socket.setSoTimeout(5000);
                try{
                    socket.receive(packet);
                    continueRunning = getData(packet.getData());
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

        protected  boolean getData(byte[] data) {
            boolean ret=true;
            //if (v==1) ret=false;
            return ret;
        }

        public static void main(String[] args) throws IOException {
            Client cvel = new Client(5557, "224.0.11.111");
            cvel.runClient();
            System.out.println("Parat!");

        }
}
