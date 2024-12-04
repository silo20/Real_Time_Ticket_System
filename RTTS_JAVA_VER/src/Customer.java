public class Customer implements Runnable {
    private final TicketPool pool;
    private final int retrievalRate;

    public Customer(TicketPool pool, int retrievalRate) {
        this.pool = pool;
        this.retrievalRate = retrievalRate;
    }

    @Override
    public void run() {
        try {
            while (true) { // Infinite loop for continuous ticket consumption
                int ticket = pool.removeTicket(); // Remove a ticket from the pool
                System.out.println("Customer purchased ticket: " + ticket);
                Thread.sleep(retrievalRate * 1000); // Wait before trying to purchase the next ticket
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupted status
            System.err.println("Customer thread interrupted.");
        }
    }
}

