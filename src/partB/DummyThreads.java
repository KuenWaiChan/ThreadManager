package partB;

import java.util.Random;

public class DummyThreads implements Runnable {

    private int lifeTime;

    DummyThreads() {
        Random RNG = new Random();

        // Potential lifetime is 8-25 seconds
        lifeTime = RNG.nextInt(18) + 8;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000 * lifeTime);
//            System.out.println("Thread done sleeping. End of Run()");
        } catch (InterruptedException e) {
//            System.out.println("\nThread safely interrupted.\n");
        }
    }
}
