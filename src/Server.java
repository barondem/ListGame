import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private final static boolean IS_TEST = true;

    private final static int PORT=3000;
    private final static String address="localhost";

    private List<Utente> utenti;

    ExecutorService executorService;
    ServerSocket serverSocket;

    public static void main(String [] args) {

        Server s = new Server();

    }

    public Server() {

        System.out.println("Server avviato");

        utenti = new ArrayList<>();

        if (IS_TEST) {
            utenti.add(new Utente("alessia", "ciao", 12));
            utenti.add(new Utente("giuseppe", "boh", 40/4));
            utenti.add(new Utente("federico", "shish", 23));
        }

        try {
            esegui();
        } catch (RuntimeException re) {
            re.printStackTrace();
        }


    }

    private void esegui() {

        if(setup()) {
            manageConnections();
        }
        executorService.shutdown();

    }

    private void manageConnections() {

        Socket socket;

        while (true) {
            try {
                socket = serverSocket.accept();
                executorService.submit(new ConnectionHandler(socket));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private boolean setup() {

        System.out.println("Setup del server in corso");

        executorService = Executors.newCachedThreadPool();

        try {
            serverSocket = new ServerSocket(PORT);

            System.out.println("Server avviato correttamente");
            return true;

        } catch (IOException e) {
            System.out.println("Errore nell'avvio del server");
            e.printStackTrace();
            return false;
        }


    }

    public class ConnectionHandler implements Runnable {

        Socket socket;
        PrintWriter out;
        BufferedReader in;

        public ConnectionHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {

            try {

                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String username = null;

                while (true) {
                    boolean exit = false;
                    String line = in.readLine();
                    switch (line) {

                        case "login": {

                            System.out.println("Trying to log user");

                            username = in.readLine();
                            String password = in.readLine();
                            out.println(login(username, password));

                            break;
                        }

                        case "partita": {

                            boolean succeeded = true;
                            int length = 5;

                            while (succeeded) {

                                String list = genList(length);
                                out.println(list);
                                succeeded = list.equals(in.readLine().trim());
                                out.println(succeeded);
                                length++;

                            }

                            for (int i = 0; i < utenti.size(); i++) {
                                Utente u = utenti.get(i);
                                if (u.getUsername().equals(username)) {
                                    if (length > 6 && length - 2  > u.getRecord()) {
                                        utenti.set(i, new Utente(username, u.getPassword(), length - 2));
                                        break;
                                    }
                                }
                            }

                            break;
                        }

                        case "record": {

                            printRecord();

                            break;
                        }

                        case "fine": {

                            exit = true;
                            break;
                        }

                        default: {

                            break;

                        }

                    }

                    if (exit) {
                        break;
                    }

                }

                out.close();
                in.close();
                socket.close();

            } catch (IOException ioe) {
                ioe.printStackTrace();

            }

        }

        private boolean checkList(String list, String s) {
            return false;
        }

        private String genList(int length) {

            StringBuilder stringBuilder = new StringBuilder();
            Random random = new Random();

            for (int i = 0; i < length; i++) {

                stringBuilder.append(random.nextInt(50 * length) + " ");

            }

            return stringBuilder.substring(0, stringBuilder.toString().length() - 1);

        }

        private void printRecord() {

            out.println(utenti.size());
            for (Utente u: utenti) {
                out.println(u.getUsername() + ": " + u.getRecord());
            }

        }

        private boolean login(String username, String password) {

            System.out.println("Attempting login with username " +  username + " and password " + password);

            for (Utente u: utenti) {
                if (u.getUsername().equals(username)) {
                    return u.getPassword().equals(password);
                }
            }
            utenti.add(new Utente(username, password));
            System.out.println("Utente aggiunto");

            return true;

        }
    }

    public class Utente {

        String username;
        String password;
        int record;

        public Utente(String username, String password, int record) {
            this.username = username;
            this.password = password;
            this.record = record;
        }

        public Utente(String username, String password) {
            this(username, password, 0);
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getRecord() {
            return record;
        }

        public void setRecord(int record) {
            this.record = record;
        }
    }

}
