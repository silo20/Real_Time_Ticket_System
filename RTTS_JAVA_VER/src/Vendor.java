public class Vendor implements Runnable {
    private final TicketPool pool;
    private final int releaseRate;

    public Vendor(TicketPool pool, int releaseRate) {
        this.pool = pool;
        this.releaseRate = releaseRate;
    }

    @Override
    public void run() {
        for (int i = 1; ; i++) { // Infinite loop for continuous ticket production
            try {
                pool.addTicket(i); // Add ticket to the pool
                Thread.sleep(releaseRate * 1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Vendor added ticket: " + i);
        }

    }
}

