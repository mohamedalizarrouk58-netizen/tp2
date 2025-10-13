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

            
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            String operation;
            while ((operation = in.readLine()) != null) {
                System.out.println("Opération reçue : " + operation);

                double resultat = calculer(operation);
                out.println("Résultat = " + resultat);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    private static double calculer(String operation) {
        try {
            operation = operation.replaceAll("\\s+", ""); 

            if (operation.contains("+")) {
                String[] parts = operation.split("\\+");
                return Double.parseDouble(parts[0]) + Double.parseDouble(parts[1]);
            } else if (operation.contains("-")) {
                String[] parts = operation.split("\\-");
                return Double.parseDouble(parts[0]) - Double.parseDouble(parts[1]);
            } else if (operation.contains("*")) {
                String[] parts = operation.split("\\*");
                return Double.parseDouble(parts[0]) * Double.parseDouble(parts[1]);
            } else if (operation.contains("/")) {
                String[] parts = operation.split("\\/");
                double a = Double.parseDouble(parts[0]);
                double b = Double.parseDouble(parts[1]);
                if (b == 0) return Double.NaN;
                return a / b;
            } else {
                return Double.NaN;
            }
        } catch (Exception e) {
            System.out.println("Erreur d'analyse !");
            return Double.NaN;
        }
    }
}

    

