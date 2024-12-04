import java.util.LinkedList;

public class TicketPool {
    private final LinkedList<Integer> tickets = new LinkedList<>();
    private final int capacity;

    public TicketPool(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void addTicket(int ticketId) throws InterruptedException {
        while (tickets.size() >= capacity) {
            wait();
        }
        tickets.add(ticketId);
        System.out.println("Ticket Added: " + ticketId);
        notifyAll();
    }

    public synchronized int removeTicket() throws InterruptedException {
        while (tickets.isEmpty()) {
            wait();
        }
        int ticket = tickets.removeFirst();
        System.out.println("Ticket Purchased: " + ticket);
        notifyAll();
        return ticket;
    }
}
