package org.example.impl;

import org.example.interfaces.Floor;
import org.example.interfaces.Person;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class ElevatorImplTest {
    @Mock
    private Floor mockFloor;
    @Mock
    private Person mockPerson;

    public ElevatorImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    private Floor[] createFloors(int number){
        Floor[] floors = new Floor[number];
        for(int i = 0; i < number; i++){
            floors[i] = new FloorImpl(i);
        }
        return floors;
    }

    @Test
    public void testAddPassengerSuccess() {
        ElevatorImpl elevator = new ElevatorImpl(0, 2, new Floor[]{mockFloor});
        elevator.addPassenger(mockPerson);
        assertEquals(1, elevator.getPassengersCount());
    }

    @Test
    public void testAddPassengerExceedCapacity() {
        ElevatorImpl elevator = new ElevatorImpl(0, 1, new Floor[]{mockFloor});
        elevator.addPassenger(mockPerson);
        elevator.addPassenger(mockPerson);
        assertFalse(elevator.canAddPassenger());
        assertEquals(1, elevator.getPassengersCount());
    }

    @Test
    public void testMove() {
        ElevatorImpl elevator = new ElevatorImpl(0, 1, new Floor[]{mockFloor, mockFloor});
        elevator.setDesiredFloor(1);
        elevator.update();
        assertEquals(1, elevator.getCurrentFloor());
    }

    @Test
    public void testDropOffPassengers() {
        ElevatorImpl elevator = new ElevatorImpl(0, 1, new Floor[]{mockFloor, mockFloor});
        when(mockPerson.getDesiredFloor()).thenReturn(mockFloor);
        when(mockFloor.getID()).thenReturn(1);
        elevator.setDesiredFloor(1);
        elevator.setCurrentFloor(0);
        elevator.addPassenger(mockPerson);
        elevator.update();
        assertEquals(1, elevator.getPassengersCount());
        elevator.setCurrentFloor(1);
        elevator.update();
        assertEquals(0, elevator.getPassengersCount());
    }

    @Test
    public void addDelFloor() {
        ElevatorImpl elevator = new ElevatorImpl(0, 1, new Floor[]{mockFloor, mockFloor, mockFloor});
        assertEquals(3, elevator.getFloorButtonsPressed().length);
        elevator.addFloor();
        assertEquals(4, elevator.getFloorButtonsPressed().length);
        elevator.delFloor();
        assertEquals(3, elevator.getFloorButtonsPressed().length);
    }
    @Test
    public void testUpdateFloorsToVisit() {
        ElevatorImpl elevator = new ElevatorImpl(1, 5, createFloors(10));
        elevator.call(3, Direction.UP);
        elevator.updateFloorsToVisit();

        assertTrue(elevator.isFloorToBeVisited(3));
    }

    @Test
    public void testCheckDirectionToNextStop() {
        boolean[] floors = new boolean[10];
        floors[3] = true;

        ElevatorImpl elevator = new ElevatorImpl(1, 5, createFloors(10));
        Direction direction = elevator.checkDirectionToNextStop(floors);

        assertEquals(Direction.UP, direction);
    }

    @Test
    public void testSetNewDesiredFloor() {
        ElevatorImpl elevator = new ElevatorImpl(1, 5, createFloors(10));
        elevator.pushFloorButton(3);
        elevator.setNewDesiredFloor();

        assertEquals(3, elevator.getDesiredFloor());
    }
}