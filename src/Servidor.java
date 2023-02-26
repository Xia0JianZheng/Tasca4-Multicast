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
        String [] frases = {"palabra1","palabra1 palabra2","palabra1 palabra2 palabra3","palabra1 palabra2 palabra3 palabra4","palabra1 palabra2 palabra3 palabra4 palabra5 palabra6 palabra7 palabra8 palabra9","palabra1 palabra2 palabra3 palabra4 palabra5 palabra6 palabra7 palabra8 palabra9 parabla10","palabra1 palabra2 palabra3 palabra4 palabra5 palabra6 palabra7 palabra8 palabra9 palabra10 palabra11","palabra1 palabra2 palabra3 palabra4 palabra5 palabra6 palabra7 palabra8 palabra9 palabra10 palabra11 palabra12 palabra13 palabra14 palabra15"};

        public Servidor(int portValue, String strIp) throws IOException {
            socket = new MulticastSocket(portValue);
            multicastIP = InetAddress.getByName(strIp);
            port = portValue;
        }

        public void runServer() throws IOException{
            DatagramPacket packet;
            Random randomData = new Random();
            byte[] sendingData;

            while(continueRunning){
                sendingData = frases[randomData.nextInt(frases.length)].getBytes();
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
