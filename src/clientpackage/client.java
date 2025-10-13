package clientpackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class client {
    public static void main(String[] args) {
        String host = "localhost"; 
        int port = 8080;

        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner sc = new Scanner(System.in)) {

            System.out.println("Connecté au serveur " + host + ":" + port);

            while (true) {
                System.out.println("Entrez une opération (ex: 55 * 25 ou 'exit' pour quitter) : ");
                String operation = sc.nextLine();

                if (operation.equalsIgnoreCase("exit")) break;

                
                if (!operation.matches("\\d+\\s*[\\+\\-\\*/]\\s*\\d+")) {
                    System.out.println(" Syntaxe incorrecte. Réessayez !");
                    continue;
                }

              
                out.println(operation);

                
                String response = in.readLine();
                System.out.println("réponse correct " + response);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
