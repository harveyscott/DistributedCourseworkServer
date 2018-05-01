import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;

public class server {
    static int port = 4444;

    private static String sortNumbers(int[] numbers){
        if (numbers == null) {
            return "";
        }
        ArrayList<Integer> numbersList = new ArrayList<Integer>();

        // Convert into arraylist
        for (int i = 0; i < numbers.length; i++) {
            numbersList.add(numbers[i]);
        }

        // Sort Numbers using the collection methods
        Collections.sort(numbersList);

        // Build String to return to users
        StringBuilder stringToRun = new StringBuilder("Here are the numbers in ascending order: ");
        for (int i = 0; i < numbersList.size(); i++) {
            stringToRun.append(" ");
            stringToRun.append(numbersList.get(i));
        }
        return stringToRun.toString();
    }

    public static void main(String[] args) {

        try {
            // Open a server socket to listen on port 4444
            ServerSocket serverSocket = new ServerSocket(port);
            try {
                // Wait to accept a connecting client
                Socket clientSocket = serverSocket.accept();
                // Create Input and Output streams to communicate with the client
                try (BufferedReader is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter os = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {
                    // Set up the server state
                    System.out.println("Server Waiting");
                    String inputLine, outputLine;
                    outputLine = sortNumbers(null);
                    os.println("Server connected");
                    os.flush();
                    while ((inputLine = is.readLine()) != null) {
                        String[] integersInString = inputLine.split(" ");
                        int integers[] = new int[integersInString.length];
                        for (int i = 0; i < integersInString.length; i++) {
                            try {
                                integers[i] = Integer.parseInt(integersInString[i]);
                            } catch (NumberFormatException ne) {
                                os.println("Please enter a valid number");
                            }

                        }
                        outputLine = sortNumbers(integers);
                        os.println(outputLine);
                        os.flush();
                    } // end while
                    clientSocket.close();
                    serverSocket.close();
                } catch (IOException e) {
                    System.out.println("Failed to create I/O streams " + e);
                    e.printStackTrace();
                }
            } catch (IOException e) {
                System.out.println("Accept failed on port: " + port + ", " + e);
                System.exit(1);
            }
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("Could not listen on port: " + port + ", " + e);
            System.exit(1);
        } // end catch
    } // end main

}
