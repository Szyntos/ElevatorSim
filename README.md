# ElevatorSim

Elevator Simulation done in Java. Uses Java Processing library for the visualisation and Maven for the dependencies.

## User Manual
1. Running the simulation
   - to start the simulation simply run Main.java
2. Simulation Controls
   - Simulations Controls are explained in the following picture
   ![Info](info.jpg?raw=true "Simulation Screen")
   - The simulation screen is divided into the following sections:
      - Main body of the simulation
       - Displays all the floors with waiting people and all the elevators.
     - Elevator Control Panel
       - Allows spawning people to a given floor and calling the elevator up or down.
     - Setup Panel
       - Sets up the values of FloorCount, ElevatorCount, and ElevatorCapacity.
     - Elevator Information Panel
       - Provides information about the number of passengers in a given elevator, its current floor, and desired floor.
     - Floor Information Panel
       - Provides information about how many people are waiting on a given floor, and the state of Up and Down buttons.
## How does it work?

1. Each floor stores information about all the elevators' positions. It requests each elevator for its Estimated Time of Arrival (ETA) to reach a specified floor, considering its current state and direction, and applies penalties (twice the sum of the number of floors already in the queue and the distance to the last stop) if necessary. Then, it calls the elevator with the smallest ETA.
2. When the elevator is called, the information of the pickup floor is added to a pickup list. If the elevator is stationary, it adds any pickups to a toVisit list. However, if the elevator is moving, it checks if it's traveling in the direction of the pickup. If it is, it adds the floor index to the toVisit list. Otherwise, the elevator continues its journey to the previously set destination, and only after the elevator changes direction will this pickup be transferred to the toVisit list. This creates an elevator behavior where the elevator won't take any passengers in the opposite direction of their travel. It will always move in the direction of travel that is favorable to its passengers.
3. Each elevator will pick up passengers only on the floors that are in the toVisit list, which includes floors where passengers want to be dropped off and floors that called said elevator.
4. This approach differs from the simplest First Come, First Served approach as it prioritizes the fact that people shouldn't be traveling in the opposite direction to their intended travel direction. It also maximizes the number of people served in a given run because each elevator will pick up passengers on the floors that the elevator would already visit. This policy also ensures that the work is distributed evenly across the elevators.