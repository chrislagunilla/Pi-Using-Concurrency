import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Pi{

    public static long hits = 0;

    public static void main(String [] args) throws Exception{
        long numThreads = Long.parseLong(args[0]);
        long numTimes = Long.parseLong(args[1]);

        final Object ref = new Object();
        ArrayList<Thread> threads = new ArrayList<>();

        for(int i = 0; i < numThreads; i++){
            Thread t = new Thread(() -> {
                for (long j = 0; j < numTimes/numThreads; j++) {
                    synchronized (ref) {
                        double x = ThreadLocalRandom.current().nextDouble(1);
                        double y = ThreadLocalRandom.current().nextDouble(1);
                        if((x*x)+(y*y) <= 1 ){
                            hits = hits + 1;
                        }
                    }
                }
            });
            threads.add(t);
        }

        for(Thread t : threads){
            t.start();
        }

        for(Thread t : threads){
            try {
                t.join();
            }
            catch (InterruptedException iex) {}
        }

        System.out.println("Total: " + numTimes);
        System.out.println("Hits: " + hits);
        System.out.println("Ratio: " + ((float)hits/(float)numTimes));
        System.out.println("Pi: " + ((float)hits/(float)numTimes) * (float)4);
    }
}
