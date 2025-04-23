/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package diningphilosophers;

/**
 *
 * @author ehcor
 */

import java.util.Arrays;
import java.util.concurrent.Semaphore;

class DiningPhilosophers {

    // making a thing where we initialize the 3 important states: thinking, hungry, eating (similar to one of the solutions discussed in class)
    private enum State { THINKING, HUNGRY, EATING }

    // initialize everything
    private final State[] philosopherStates = new State[5];         // stores the current states of each philosopher.
    private final boolean[] chopsticks = new boolean[5];            // true if taken, false if on the table. for visual purposes.
    private final Semaphore[] self = new Semaphore[5];              // each philosopher has their own semaphore, which lets us make them wait for any reason (i.e. no chopsticks available)
    private final Object lock = new Object();                       // a monitor object which protects shared data (i.e. the states and chopsticks).

    // constructor / initialization
    public DiningPhilosophers() {
        
        // all philosphers are set to THINKING initially
        Arrays.fill(philosopherStates, State.THINKING);
        
        // for loop
        for (int i = 0; i < 5; i++) {
            // initialize each semaphore to 0, so they all must wait.
            self[i] = new Semaphore(0);
        }
    }

    // function to allow a philosopher to pick up chopsticks
    public void pickup(int id) throws InterruptedException {
        // so we only get one process in at a time
        synchronized (lock) {
            // sets philosopher state to hungry
            philosopherStates[id] = State.HUNGRY;
            
            // visualizes state of said philosopher
            printState(id, "is HUNGRY");
            
            // checks if the philosopher can ACTUALLY eat (neighbors aren't using either of the chopsticks).
            test(id);
        }
        
        // philosopher must wait until signaled to eat
        self[id].acquire();
        
        // if allowed to proceed, then the philosopher is allowed to pick up their nearby chopsticks and can begin eating
        synchronized (lock) {
            chopsticks[id] = true;                     // left
            chopsticks[(id + 1) % 5] = true;           // right
            printState(id, "picked up both chopsticks, now EATING");
        }
    }

    // function to allow a philosopher to put down their chopsticks
    public void putdown(int id) {
        synchronized (lock) {
            // reset the state of the current philosopher who stopped eating to now be thinking
            philosopherStates[id] = State.THINKING;
            
            // puts down both of the chopsticks, resetting them
            chopsticks[id] = false;
            chopsticks[(id + 1) % 5] = false;
            
            // visualization
            printState(id, "puts down chopsticks and goes back to THINKING");
            
            // test the neighbors again because this could potentially unblock a neighbor and (hopefully) allow them to eat
            test((id + 4) % 5); // left
            test((id + 1) % 5); // right
        }
    }

    // function to test if one's action can be valid
    private void test(int id) {
        int left = (id + 4) % 5;
        int right = (id + 1) % 5;
        
        // checks if the neighbors arent eating and if the philosopher is hungry.
        if (philosopherStates[id] == State.HUNGRY &&
            philosopherStates[left] != State.EATING &&
            philosopherStates[right] != State.EATING) {
            philosopherStates[id] = State.EATING;
            self[id].release(); // lets the philosopher eat the food (important), unblocks
        }
    }

    // function for visualizing stuff, a snapshot of the current statuses of the philosophers and chopsticks.
    private void printState(int id, String action) {
        System.out.println("Philosopher " + id + " " + action);
        System.out.println("States: " + Arrays.toString(philosopherStates));
        System.out.print("Chopsticks: ");
        for (int i = 0; i < 5; i++) {
            System.out.print((chopsticks[i] ? "[X]" : "[ ]") + " ");
        }
        System.out.println("\n");
    }

    // --- Main Simulation ---

    // where shit happens
    public static void main(String[] args) {
        DiningPhilosophers dp = new DiningPhilosophers();

        // creates the philosophers
        for (int i = 0; i < 5; i++) {
            int id = i;
            // use a thread for this
            new Thread(() -> {
                try {
                    while (true) {
                        // Each philosopher i invokes the operations pickup() and
                        // putdown() in this specified sequence on slide 20, lecture 6 notes:
                        Thread.sleep((long) (Math.random() * 2000)); // Thinking
                        dp.pickup(id);
                        Thread.sleep((long) (Math.random() * 1000)); // Eating
                        dp.putdown(id);
                        // there is no deadlock or starvation possible here i think
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }
    }
}
