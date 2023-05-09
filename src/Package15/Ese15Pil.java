package Package15;

import java.util.concurrent.Semaphore;


public class Ese15Pil {
    private static final int MAX = 10;
    private static Semaphore semaforo = new Semaphore(2);
    private static int[] votes = new int[MAX];
    private static Semaphore mutex = new Semaphore(1);

    public static void main(String[] args) {
        for (int i = 0; i < MAX; i++) {    //Qua vengono istanziati i relativi Clienti,che sono dei Thread e ai quali viene assegnato un identificativo da 1 a 5
            new Thread(new Customer(i)).start();
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Classifica dei voti: ");
        for (int i = 1; i <= 5; i++) {
            int count = 1;
            for (int j = 0; j < MAX; j++) {
                if (votes[j] == i) {
                    count++;
                }
            }
            System.out.println("Il "+i+"° Cliente ha votato " + ": " + count);
        }
    }

    static class Customer implements Runnable {
        private int id;

        public Customer(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            try {
            	semaforo.acquire();
                System.out.println("Cliente " + id + " sta acquistando il biglietto");
                Thread.sleep((long) (Math.random() * 1000));
                System.out.println("Cliente " + id + " ha acquistato il biglietto");

                mutex.acquire();
                int vote = (int) (Math.random() * 5) + 1;
                votes[id] = vote;
                mutex.release();

                semaforo.release();
                System.out.println("Cliente " + id + " sta guardando il film");
                Thread.sleep((long) (Math.random() * 1000));
                System.out.println("Cliente " + id + " ha finito di guardare il film");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}