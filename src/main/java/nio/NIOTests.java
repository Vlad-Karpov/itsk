package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

public class NIOTests {
    public static class QQQ0001 {
        public static void main(String[] args) throws IOException {
            Path path = Paths.get(".");
            System.out.println(path.toAbsolutePath().toString());
            System.out.println(path.toAbsolutePath().normalize().toString());
            System.out.println(path.normalize().toUri().toString());
            System.out.println(path.toRealPath().toString());
            System.out.println(path.resolve("src").toString());
            System.out.println(path.resolve("qqqqqqq").toString());
            System.out.println(Paths.get(".").relativize(path.resolve("qqqqqqq").resolve("rrrrr")).toString());

            System.out.println(Files.exists(path.resolve("src")));

        }

    }

    public static class QQQ0002 {

        private static final String POISON_PILL = "POISON_PILL";

        SocketAddress addrYandex;
        SocketChannel sChannel;
        ByteBuffer buffer;

        public QQQ0002() throws IOException {
            addrYandex = new InetSocketAddress("yandex.ru", 80);
            //addrYandex = new InetSocketAddress("www.google.com", 80);
            sChannel = SocketChannel.open(addrYandex);
            buffer = ByteBuffer.allocate(16384);
        }

        public static void main(String[] args) throws IOException {
            QQQ0002 qq = new QQQ0002();
            qq.sendMessage("GET / HTTP/1.1\r\nUser-Agent: HTTPGrab\r\nAccept: text/*\r\nConnection: close\r\nHost: " + ((InetSocketAddress)qq.addrYandex).getHostName() + "\r\n" + "\r\n");
        }

        public String sendMessage(String msg) {
            System.out.println("request=");
            System.out.println(msg);
            buffer = ByteBuffer.wrap(msg.getBytes());
            String response = null;
            try {
                sChannel.write(buffer);
                buffer.clear();
                System.out.println("response=");
                while (sChannel.read(buffer) != -1) {
                    buffer.flip();
                    System.out.println(new String(buffer.array()));
                    buffer.clear();
                }
                buffer.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        static public class Server {

            Selector selector;
            ServerSocketChannel serverSocket;
            ByteBuffer buffer;

            public Server() throws IOException {
                selector = Selector.open();
                serverSocket = ServerSocketChannel.open();
                serverSocket.bind(new InetSocketAddress("localhost", 5454));
                serverSocket.configureBlocking(false);
                serverSocket.register(selector, SelectionKey.OP_ACCEPT);
                buffer = ByteBuffer.allocate(256);
            }

            public void process() throws IOException {
                while (true) {
                    int keyCount = selector.select();
                    System.out.println(keyCount);
                    if (keyCount > 0) {
                        Set<SelectionKey> selectedKeys = selector.selectedKeys();
                        Iterator<SelectionKey> iter = selectedKeys.iterator();
                        while (iter.hasNext()) {
                            SelectionKey key = iter.next();
                            if (key.isAcceptable()) {
                                register(selector, serverSocket);
                            }
                            if (key.isConnectable()) {
                                System.out.println("isConnectable()");
                            }
                            if (key.isReadable()) {
                                answerWithEcho(buffer, key);
                            }
                            if (key.isWritable()) {
                                System.out.println("isWritable");
                            }
                            iter.remove();
                        }
                    }
                }
            }

            private static void answerWithEcho(ByteBuffer buffer, SelectionKey key)
                    throws IOException {
                SocketChannel client = (SocketChannel) key.channel();
                client.read(buffer);
                if (new String(buffer.array()).trim().equals(POISON_PILL)) {
                    client.close();
                    System.out.println("Not accepting client messages anymore");
                }
                buffer.flip();
                System.out.println(new String(buffer.array()));
                client.write(buffer);
                buffer.clear();
            }

            private static void register(Selector selector, ServerSocketChannel serverSocket)
                    throws IOException {
                SocketChannel client = serverSocket.accept();
                client.configureBlocking(false);
                client.register(selector, SelectionKey.OP_READ);
            }

            public static void main(String[] args) throws IOException {
                Server server = new Server();
                server.process();
            }

        }

        static public class Client {
            private static SocketChannel client;
            private static ByteBuffer buffer;
            private static Client instance;

            public static Client start() {
                if (instance == null)
                    instance = new Client();

                return instance;
            }

            public static void stop() throws IOException {
                client.close();
                buffer = null;
            }

            private Client() {
                try {
                    client = SocketChannel.open(new InetSocketAddress("localhost", 5454));
                    buffer = ByteBuffer.allocate(256);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            public String sendMessage(String msg) {
                buffer = ByteBuffer.wrap(msg.getBytes());
                String response = null;
                try {
                    client.write(buffer);
                    buffer.clear();
                    client.read(buffer);
                    response = new String(buffer.array()).trim();
                    buffer.clear();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return response;
            }

            public static void main(String[] args) throws IOException {
                Client client = Client.start();
                String resp = client.sendMessage("Привет! " + UUID.randomUUID().toString());
                System.out.println(resp);
                client.stop();
            }

        }

    }


}
