# Dining Philosophers (CSC 306 Group Project)

## Overview
This project is a Java implementation of the classic **Dining Philosophers** concurrency problem. It demonstrates synchronization, deadlock avoidance, and resource sharing using semaphores and threads. The project is structured for use with NetBeans and Ant, but can be run from the command line as well.

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
