
package serverpackage;

import java.io.*;
import java.net.*;

public class server {

    public static void main(String[] args) {
        int port = 8080;

        System.out.println("Serveur en attente de connexion sur le port " + port + "...");

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connecté : " + clientSocket.getInetAddress());

            try (
                BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream())
                );
                PrintWriter out = new PrintWriter(
                    clientSocket.getOutputStream(), true
                )
            ) {
                String operation;

                while ((operation = in.readLine()) != null) {
                    System.out.println("Opération reçue : " + operation);

                    try {
                        double resultat = calculer(operation);
                        out.println("Résultat = " + resultat);
                    } catch (IllegalArgumentException e) {
                        out.println("Erreur : " + e.getMessage());
                    }
                }

            }

            System.out.println("Client déconnecté.");

        } catch (IOException e) {
            System.err.println("Erreur serveur : " + e.getMessage());
        }
    }

    private static double calculer(String operation) {
        operation = operation.replaceAll("\\s+", "");

        String[] parts;
        double a;
        double b;

        try {
            if (operation.matches("-?\\d+(\\.\\d+)?[+*]-?\\d+(\\.\\d+)?")) {
                if (operation.contains("+")) {
                    parts = operation.split("\\+", 2);
                    return Double.parseDouble(parts[0])+ Double.parseDouble(parts[1]);
                }

                parts = operation.split("\\*", 2);
                return Double.parseDouble(parts[0])
                     * Double.parseDouble(parts[1]);
            }

            if (operation.matches("-?\\d+(\\.\\d+)?/-?\\d+(\\.\\d+)?")) {
                parts = operation.split("/", 2);
                a = Double.parseDouble(parts[0]);
                b = Double.parseDouble(parts[1]);

                if (b == 0) {
                    throw new IllegalArgumentException(
                        "Division par zéro impossible."
                    );
                }

                return a / b;
            }
            for (int i = 1; i < operation.length(); i++) {
                if (operation.charAt(i) == '-') {
                    a = Double.parseDouble(operation.substring(0, i));
                    b = Double.parseDouble(operation.substring(i + 1));
                    return a - b;
                }
            }

            throw new IllegalArgumentException(
                "Opération invalide. Exemples : 5+3, 8-2, 4*3, 10/2."
            );

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                "Veuillez saisir deux nombres et un opérateur valide."
            );
        }
    }
}
