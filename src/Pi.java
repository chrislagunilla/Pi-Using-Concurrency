import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Pi{

    public static long hits = 0;

    public static void main(String [] args) throws Exception{
        // read in command line args
        long numThreads = Long.parseLong(args[0]);
        long numTimes = Long.parseLong(args[1]);

        // create arraylist
        final Object ref = new Object();
        ArrayList<Thread> threads = new ArrayList<>();

        // generate threads
        for(int i = 0; i < numThreads; i++){
            Thread t = new Thread(() -> {
                for (long j = 0; j < numTimes/numThreads; j++) {
                    synchronized (ref) {

                        // see if random point is a "hit"
                        double x = ThreadLocalRandom.current().nextDouble(1);
                        double y = ThreadLocalRandom.current().nextDouble(1);
                        if((x*x)+(y*y) <= 1 ){
                            hits = hits + 1;
                        }
                    }
                }
            });

            // add the threads to the thread array
            threads.add(t);
        }

        // start all the threads
        for(Thread t : threads){
            t.start();
        }

        // when the threads are complete, join the threads to stop them
        for(Thread t : threads){
            try {
                t.join();
            }
            catch (InterruptedException iex) {}
        }

        // print results
        System.out.println("Total: " + numTimes);
        System.out.println("Hits: " + hits);
        System.out.println("Ratio: " + ((float)hits/(float)numTimes));
        System.out.println("Pi: " + ((float)hits/(float)numTimes) * (float)4);
    }
}
