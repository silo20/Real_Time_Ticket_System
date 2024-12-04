import java.util.Scanner;

public class TicketingSystemCLI {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter total tickets:");
        int totalTickets = scanner.nextInt();

        System.out.println("Enter ticket release rate:");
        int ticketReleaseRate = scanner.nextInt();

        System.out.println("Enter customer retrieval rate:");
        int customerRetrievalRate = scanner.nextInt();

        TicketPool pool = new TicketPool(totalTickets);

        Thread vendorThread = new Thread(new Vendor(pool, ticketReleaseRate));
        Thread customerThread = new Thread(new Customer(pool, customerRetrievalRate));

        vendorThread.start();
        customerThread.start();
    }
}
