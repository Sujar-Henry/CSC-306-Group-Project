# Dining Philosophers (CSC 306 Group Project)

## Overview
This project is a Java implementation of the classic **Dining Philosophers** concurrency problem. It demonstrates synchronization, deadlock avoidance, and resource sharing using semaphores and threads. The project is structured for use with NetBeans and Ant, but can be run from the command line as well.

### Overview of the Synchronization Problem
The Dining Philosophers problem is a classic synchronization problem that illustrates challenges in resource allocation and deadlock prevention. In this scenario, five philosophers sit at a circular table with a chopstick between each pair. Each philosopher must pick up both adjacent chopsticks to eat, and then put them down to think. The challenge is to design a solution that allows philosophers to eat without causing deadlock or starvation.

### Description of the Solution Approach
Our solution uses a combination of semaphores and a monitor lock to manage synchronization:

- **Semaphores:** Each philosopher has an associated semaphore initialized to 0. When a philosopher is hungry, they attempt to pick up both chopsticks. If the chopsticks are available, the philosopher's semaphore is released (via `self[id].release()`), allowing them to proceed. If not, the philosopher waits on their semaphore (via `self[id].acquire()`).

- **Monitor Lock:** A shared object (`lock`) is used to synchronize access to the shared state (philosopher states and chopstick statuses). This ensures that only one philosopher can change the state at a time, preventing race conditions.

### Prevention of Deadlock, Starvation, and Race Conditions
- **Deadlock Prevention:** The solution prevents deadlock by ensuring that a philosopher can only pick up both chopsticks if neither neighbor is eating. This is enforced by the `test()` method, which checks the states of the left and right neighbors before allowing a philosopher to eat.

- **Starvation Prevention:** By using semaphores and a fair scheduling mechanism, the solution ensures that all philosophers get a chance to eat. The `test()` method is called for both neighbors when a philosopher puts down their chopsticks, potentially allowing a waiting philosopher to eat.

- **Race Condition Prevention:** The use of a monitor lock (`synchronized (lock)`) ensures that the shared state is accessed in a thread-safe manner, preventing race conditions.

### Design Decisions and Implications
- **Use of Semaphores:** Semaphores were chosen for their simplicity and effectiveness in managing resource allocation. They allow for easy blocking and unblocking of threads based on resource availability.

- **Monitor Lock:** The monitor lock provides a straightforward way to ensure mutual exclusion when accessing shared state, simplifying the synchronization logic.

- **Performance Considerations:** The solution aims to minimize contention by only locking the critical sections necessary for state changes. This approach balances correctness with performance, ensuring that the system remains responsive even under load.


## Features
- Simulates five philosophers sitting at a table, alternating between thinking and eating.
- Uses semaphores and a monitor lock to manage access to shared chopsticks.
- Prevents deadlock and starvation.
- Console output visualizes the state of each philosopher and chopstick.

## Requirements
- Java JDK 24 or later
- (Optional) NetBeans IDE for project management
- Ant (for building from source)

## Getting Started

### Running the Pre-built JAR
1. Open a terminal and navigate to the `CSC-306-Group-Project/DiningPhilosophers/dist` directory.
2. Run the following command:
   ```sh
   java -jar DiningPhilosophers.jar
   ```

### Building from Source
1. Ensure you have Java JDK 24+ and Ant installed.
2. Navigate to the `CSC-306-Group-Project/DiningPhilosophers` directory.
3. Run:
   ```sh
   ant clean
   ant build
   ant run
   ```
   Or use NetBeans to open the project and use the IDE's build/run commands.

## Project Structure
```
CSC-306-Group-Project/
└── DiningPhilosophers/
    ├── src/
    │   └── diningphilosophers/
    │       └── DiningPhilosophers.java   # Main source file
    ├── dist/
    │   ├── DiningPhilosophers.jar       # Pre-built executable JAR
    │   └── README.TXT                   # Build output notes
    ├── build.xml                        # Ant build script
    ├── manifest.mf                      # JAR manifest
    └── nbproject/                       # NetBeans project files
```

## How It Works
- Each philosopher is represented by a thread.
- States: THINKING, HUNGRY, EATING.
- Semaphores are used to block philosophers when chopsticks are unavailable.
- A monitor lock ensures only one philosopher can change state at a time.
- Console output shows philosopher actions and chopstick status.


---
*CSC 306 Group Project – Dining Philosophers Simulation* 
