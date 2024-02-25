package org.example.drawer;

import org.example.impl.Direction;

@FunctionalInterface
public
interface IntDirFunction {
    void call(int fromFloor, Direction direction);
}
