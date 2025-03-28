# Elevator
Project to simulate a basic elevator system. The main entrypoint is in App.java, where we get an instance of the ElevatorManager and run 4 sample test elevator service requests.

The ElevatorManager handles calls for pick-up, dispatch routing, and moving the elevator to its destination once a floor has been selected.

Test logging runs to logs/elevator.log, configured in by LoggerConfig.java

> Mar 28, 2025 8:12:58 AM main.java.com.github.elevator.App main<br>
> INFO: Creating a new Elevator System with 4 elevators and 14 floors.<br>
> Mar 28, 2025 8:12:58 AM main.java.com.github.elevator.manager.ElevatorManager callElevator<br>
> INFO: Service request from floor 1 going up<br>
> Mar 28, 2025 8:12:58 AM main.java.com.github.elevator.manager.AuditManager auditEvent<br>
> INFO: Audit event: Service request from floor 1 going up<br>
> Mar 28, 2025 8:12:58 AM main.java.com.github.elevator.manager.ElevatorManager callElevator<br>
> INFO: Elevator 4 dispatched to request on floor 1<br>
> Mar 28, 2025 8:12:58 AM main.java.com.github.elevator.component.environment.Elevator pressFloorButton<br>
> INFO: Service requested from floor 1 to 6<br>
> Mar 28, 2025 8:12:58 AM main.java.com.github.elevator.manager.AuditManager auditEvent<br>
> INFO: Audit event: Service requested from floor 1 to 6<br>
> Mar 28, 2025 8:12:58 AM main.java.com.github.elevator.manager.ElevatorManager travel<br>
> INFO: Elevator 4 in transit from floor 1 to 6<br>
> Mar 28, 2025 8:12:58 AM main.java.com.github.elevator.manager.AuditManager auditEvent<br>
> INFO: Audit event: Elevator 4 in transit from floor 1 to 6
Mar 28, 2025 8:12:58 AM main.java.com.github.elevator.manager.ElevatorManager travel<br>
> INFO: Elevator 4 has completed its trip and is now idle.<br>
> Mar 28, 2025 8:12:58 AM main.java.com.github.elevator.App main<br>
> INFO: ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

## Dispatch
This simulator will use a simple method of dispatching the closest elevator, regardless of direction. Enhancements would include more detailed information like load weight and other adaptive traffic data. It would also add some kind of queuing for multiple stops along the way, picking up passengers going the same direction and dropping each along the way to the final stop.

In order to more quickly find a matching elevator, the floors are divided into buckets of 5 floors. The dispatcher will check for an available elevator in the bucket of incoming service calls, and work its way out from there. So if a request comes in for pickup on the 3rd floor, the dispatcher would check bucket 0 first, then move on to bucket 1 and finally 2.

- For example, a building with 12 floors would be divided into 3 buckets:
    - Bucket 0: floors 1-5
    - Bucket 1: floors 5-10
    - Bucket 2: floors 10-12

In the provided test cases, the logs will show the following trips on a building with 14 floors and 4 elevators in service:
    - Elevator 4 takes a passenger UP from floor 1 to 6
    - Elevator 3 takes a passenger DOWN from floor 2 to 1
    - Elevator 4 is closest to a call on floor 8, taking a passenger from 8 down to 1
    - Invalid floor input on a call request

## State of the Simulator

### Max Values
The following maximums are declared in Constants.java
- Maximum floors: 250
- Maximum size of a group (bank) of elevators: 80
- Maximum operating weight: 2,270kg/5,000Ibs.

### Floor Components
Each floor has doors and an external elevator panel to call an elevator.

## Internal Elevator Components
The simulator includes many internal components that would be required in a fully operational ecosystem, though currently only the core basic components are implemented.
- **Standard Panel**
    - **<u>Floor Number Button</u>** - Numbered buttons that correspond to each floor landing the elevator services.
    - Open Door Button
    - Close Door Button
    - Hold Door Button - Holds the elevator and doors open for a prolonged period of time, often for freight loading or other accessibility reasons.
    - Alarm Button - Starts an audible alarm that signals an emergency, and will also send a signal to security or monitoring personnel.
    - Emergency Stop Button - Brings the elevator to an immediate stop, doors remain closed.
    - Call Button - Connects to emergency or security personnel via a dedicated two-way speaker call system.
- **Firefighter** Panel
    - Keyhole - Allows elevator to be put into phase 2 emergency mode, which alters door open/close functionality. Removal of the key returns the elevator to the main egress floor and back to Phase 1 emergency setting
    - Open Door Button - Press and hold until the door is fully open to stay open, else door will automatically close for safety
    - Close Door Button
    - Push/Pull Button - Force stop or force the car to run
- **Floor Display Panel** - Small lit panel that displays the floor number and which direction the elevator is currently heading.
- **Keycard Reader** - Requires users to scan their keycard (i.e. hotel room keycard) in order to use the elevator or access special floors, depending on configuration.
    - The AuthenticationManager would pull from this card reader in order to determine access level, if security mode were active.
- **Audio System** - Speaker system that plays overhead in the elevator.
Music
    - Announcements/Instructions for accessibility, providing audio cues such as the current floor, direction of the elevator, emergency directions, or other important info about the elevator’s operational state.
- **Scale** - Elevators have a maximum operational weight capacity. This would trip a failure state and not allow the elevator to proceed while overloaded.

While all of the above components are included in the Elevator and its internal components, only the floor press button is actually implemented in the elevator tests.

State changes of components are all contained in just a few action methods for this simulator, but in a full-scale implementation it would be better to have cleaner separation of the actions, since there would be things like emergency buttons, open/close/hold doors, or multiple floor stops interspersed.

A real elevator would also have travel and load times where these buttons could be pressed, unlike this simulator.