package vtb.tst;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Что неправлиьно в данном коде сервиса, который возвращает список всех клиентов и менеджеров
 *
 *          данные код правильный
 *          необходимо использовать synchronizedList(clients)
 *          необходимо использовать блокировку вокруг блоков .append
 *          необходимо использовать StreamAPI вмосто forEach
 *
 *
 * Вообще говоря, тут ВСЕ неправильно...
 * 1) private ClientRepository repository не инстантиирован в оригинале
 * 2) 2 потока никогда не вызовуться, потому что им задан только метод run, но сами потоки не стартованы
 * 3) Даже если потоки стартуют, программа закончиться ДО их выполнения, и результат будет... неопределен...
 *
 * только после решения этих трех проблемм уместно сказать:
 *      данный код НЕ правильный
 *      необязательно использовать synchronizedList(clients)
 *      да, необходимо использовать блокировку вокруг блоков .append
 *      бообще без разницы будет это просто forEch, или stream().forEach
 */
public class Tst016 {

    static class Client {
        String clientName;
        Client manager = null;

        public Client() {
            this.clientName = "client";
        }

        public Client(String clientName) {
            this.clientName = clientName;
        }

        String getName() {
            return clientName;
        };
        Client getManager() {
            if (manager == null) {
                manager = new Client("manager");
            }
            return manager;
        };
    }

    static class ClientRepository {
        List<Client> getAll() {
            List<Client> result = new ArrayList<Client>();
            result.add(new Client());
            return result;
        }
    }

    static class ClientService {

        //private ClientRepository repository;
        private ClientRepository repository = new ClientRepository();

        public String allClientAndManagersCsv() {
            StringBuilder clientsAndManagers = new StringBuilder();
            List<Client> clients = repository.getAll();
            new Thread(() -> {
                clients.forEach(client -> {
                    clientsAndManagers
                            .append(client.getName())
                            .append(";\n");
                });
            });     //}).start();
            new Thread(() -> {
                clients.stream().forEach(client -> clientsAndManagers
                        .append(client.getManager().getName())
                        .append(";\n"));
//                clients.forEach(client -> {
//                    clientsAndManagers
//                            .append(client.getManager().getName())
//                            .append(";\n");
//                });
            });     //}).start();
            return clientsAndManagers.toString();
        }

    }

    @Test
    public void tst() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        ClientService service1 = vtb.tst.Tst016.ClientService.class.newInstance();
        Class<ClientService> clientServiceClass = (Class<ClientService>) Class.forName("vtb.tst.Tst016$ClientService");
        ClientService service2 = clientServiceClass.newInstance();
        ClientService service3 = new ClientService();
        String str = service1.allClientAndManagersCsv();
        System.out.println(str);
    }

}
