package org.example.impl;

import org.example.impl.Direction;
import org.example.impl.ElevatorImpl;
import org.example.impl.FloorImpl;
import org.example.impl.PersonImpl;
import org.example.interfaces.Elevator;
import org.example.interfaces.ElevatorSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

public class FloorImplTest {

    private ElevatorSystem elevatorSystem;
    private FloorImpl floor;

    @BeforeEach
    public void setUp() {
        elevatorSystem = Mockito.mock(ElevatorSystem.class); // Mocking the ElevatorSystem
        floor = new FloorImpl(1);
        floor.addElevatorSystem(elevatorSystem);
    }

    @Test
    public void testPressUp() {
        floor.pressUp();
        assertTrue(floor.isUpPressed());
        verify(elevatorSystem, times(1)).pickup(1, Direction.UP);
    }

    @Test
    public void testPressDown() {
        floor.pressDown();
        assertTrue(floor.isDownPressed());
        verify(elevatorSystem, times(1)).pickup(1, Direction.DOWN);
    }

    @Test
    public void testAddPerson() {
        PersonImpl person = Mockito.mock(PersonImpl.class); // Mocking a Person
        floor.addPerson(person);
        assertEquals(1, floor.getPeople().size());
    }

}
