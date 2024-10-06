import dao.ClientDAO;
import models.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ClientDAO clientdao = new ClientDAO();
        List<Client> clients;
        List<Integer> existingIds = new ArrayList<Integer>();
        Scanner sc = new Scanner(System.in);
        boolean pass;
        int counter;
        char op = '0';

        while (op != '5') {
            System.out.println("Client Registries Administrator\n");
            System.out.println("1. Add new client");
            System.out.println("2. See current clients");
            System.out.println("3. Update client");
            System.out.println("4. Delete client");
            System.out.println("5. Exit\n");
            System.out.print("Choose an option: ");
            op = sc.next().charAt(0);

            switch (op) {
                case '1':
                    System.out.println("\n1. Add new client\n");
                    clients = clientdao.getClients();
                    counter = 1;
                    existingIds.clear();
                    if (!clients.isEmpty()) {
                        System.out.println("List of existing id's");
                        for (Client client : clients) {
                            counter++;
                            System.out.print("{ " + client.getId() + "} ");
                            if (counter % 5 == 0) {
                                System.out.println();
                            }
                            existingIds.add(client.getId());
                        }
                    }
                    Client newClient = new Client();
                    pass = false;
                    while (!pass) {
                        System.out.print("Insert the client's unique id: ");
                        newClient.setId(Integer.parseInt(sc.next()));
                        sc.nextLine();
                        pass = true;
                        for (int id : existingIds) {
                            if (newClient.getId() == id) {
                                pass = false;
                                System.out.println("You cannot use an already existing id!\n");
                            }
                        }
                    }
                    System.out.print("Insert the client's name: ");
                    newClient.setName(sc.nextLine());
                    System.out.print("Insert the client's RUC number: ");
                    newClient.setRuc(sc.nextLine());
                    System.out.print("Insert the client's department: ");
                    newClient.setDepartment(sc.nextLine());
                    System.out.print("Insert the client's city: ");
                    newClient.setCity(sc.nextLine());
                    System.out.print("Insert the client's website url: ");
                    newClient.setWebsite(sc.next());
                    newClient.setClientState(true);
                    clientdao.save(newClient);
                    System.out.println("\nThe client was successfully added to the database.\n");
                    sc.nextLine();
                    break;

                case '2':
                    clients = clientdao.getClients();
                    System.out.println("\n2. See current clients\n");
                    if (clients.isEmpty()) {
                        System.out.println("There are no clients in the database.\n");
                    } else {
                        for (Client client : clients) {
                            System.out.println(client);
                        }
                    }
                    System.out.println("\n");
                    sc.nextLine();
                    break;

                case '3':
                    System.out.println("\n3. Update a client register\n");
                    clients = clientdao.getClients();
                    counter = 1;
                    if (!clients.isEmpty()) {
                        System.out.println("List of existing id's");
                        existingIds.clear();
                        for (Client client : clients) {
                            counter++;
                            System.out.print("{ " + client.getId() + "} ");
                            if (counter % 5 == 0) {
                                System.out.println();
                            }
                            existingIds.add(client.getId());
                        }
                    } else {
                        System.out.println("There are no clients to update in the database.\n");
                        sc.nextLine();
                        break;
                    }
                    Client updatedClient = new Client();
                    pass = false;
                    while (!pass) {
                        System.out.print("\n\nInsert the client's unique id: ");
                        updatedClient.setId(Integer.parseInt(sc.next()));
                        sc.nextLine();
                        for (int id : existingIds) {
                            if (updatedClient.getId() == id) {
                                pass = true;
                            }
                        }
                        if (!pass) {
                            System.out.println("You have to insert an existing id!\n");
                        }
                    }
                    System.out.print("Insert the client's name: ");
                    updatedClient.setName(sc.nextLine());
                    System.out.print("Insert the client's RUC number: ");
                    updatedClient.setRuc(sc.nextLine());
                    System.out.print("Insert the client's department: ");
                    updatedClient.setDepartment(sc.nextLine());
                    System.out.print("Insert the client's city: ");
                    updatedClient.setCity(sc.nextLine());
                    System.out.print("Insert the client's website url: ");
                    updatedClient.setWebsite(sc.next());
                    System.out.print("Insert the client's state (true/false): ");
                    updatedClient.setClientState(sc.nextBoolean());
                    clientdao.update(updatedClient);
                    System.out.print("\nThe client was successfully updated in the database.\n");
                    break;

                case '4':
                    System.out.println("\n4. Delete a client register\n");
                    clients = clientdao.getClients();
                    counter = 1;
                    if (!clients.isEmpty()) {
                        System.out.println("List of existing id's");
                        existingIds.clear();
                        for (Client client : clients) {
                            counter++;
                            System.out.print("{ " + client.getId() + "} ");
                            if (counter % 5 == 0) {
                                System.out.println();
                            }
                            existingIds.add(client.getId());
                        }
                    } else {
                        System.out.println("\nThere are no clients in the database.\n");
                        break;
                    }
                    int removeId = 0;
                    pass = false;
                    while (!pass) {
                        System.out.print("\n\nInsert the id of the client you want to delete: ");
                        removeId = Integer.parseInt(sc.next());
                        sc.nextLine();
                        for (int id : existingIds) {
                            if (removeId == id) {
                                pass = true;
                                break;
                            }
                        }
                        if (!pass) {
                            System.out.println("You have to insert an existing id!\n");
                        }
                    }
                    Client deletedClient = clientdao.findById(removeId);
                    clientdao.delete(deletedClient);
                    System.out.println("\nThe client was successfully deleted from the database.\n");
                    break;

                case '5':
                    System.out.println("\nThank you for using the program.\n");
                    break;

                default:
                    System.out.println("You have entered an invalid option! Please choose an option between 1 and 5.\n");
                    break;
            }
        }
    }
}
