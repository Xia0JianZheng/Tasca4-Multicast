import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Servidor {
        /* Servidor Multicast que ens comunica la velocitat que porta d'un objecte */
        MulticastSocket socket;
        InetAddress multicastIP;
        int port;
        boolean continueRunning = true;
        Velocitat simulator;

        public Servidor(int portValue, String strIp) throws IOException {
            socket = new MulticastSocket(portValue);
            multicastIP = InetAddress.getByName(strIp);
            port = portValue;
            simulator = new Velocitat(200);
        }

        public void runServer() throws IOException{
            DatagramPacket packet;

            byte[] sendingData;

            String [] frases = {"1","1 2","1 2 3 4 5 6 7 8","8 7 6 5 4 3 2 1","0 2 3","1 1 1 1 1 1 1 1 1 1 1 1 1"};

            while(continueRunning){
                sendingData = frases[(int)(Math.random() * 6)].getBytes(StandardCharsets.UTF_8);
                packet = new DatagramPacket(sendingData, sendingData.length,multicastIP, port);
                socket.send(packet);

                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    ex.getMessage();
                }
            }
            socket.close();
        }

        public static void main(String[] args) throws IOException {
            //Canvieu la X.X per un número per formar un IP.
            //Que no sigui la mateixa que la d'un altre company
            Servidor srvVel = new Servidor(5557, "224.0.11.111");
            srvVel.runServer();
            System.out.println("Parat!");

        }

}
