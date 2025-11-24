package com.tumi.hytale.arcana.tile.routing;

import com.tumi.hytale.arcana.power.IArcaneFlow;
import com.tumi.hytale.arcana.tile.ArcaneFlowBaseTileEntity;
import java.util.ArrayList;
import java.util.List;

/**
 * Arcane Clutch - Directional flow router.
 * 
 * Behavior:
 * - Has one active input side (configurable)
 * - Receives flow from the active input side
 * - Distributes flow to ALL other sides (output sides)
 * 
 * Configuration:
 * - Active input can be rotated: NORTH, SOUTH, EAST, WEST, UP, or DOWN
 * - All other sides become outputs
 * 
 * Example: If input is NORTH, outputs are SOUTH, EAST, WEST, UP, DOWN
 */
public class ArcaneClutchTileEntity extends ArcaneFlowBaseTileEntity {

    private Direction activeInputSide = Direction.NORTH; // Default input side
    
    public enum Direction {
        NORTH, SOUTH, EAST, WEST, UP, DOWN
    }
    
    public ArcaneClutchTileEntity() {
        // Higher capacity to handle distribution
        super(IArcaneFlow.MAX_FLOW_CAPACITY_BASE * 2);
    }
    
    @Override
    public void tick() {
        // 1. Read flow from active input side only
        int inputFlow = readInputFromActiveSide();
        
        // 2. Update internal flow
        this.currentFlow = inputFlow;
        
        // 3. Distribute to all output sides (all sides except input)
        if (currentFlow > 0) {
            distributeToOutputSides();
        }
    }
    
    /**
     * Reads flow only from the active input side.
     */
    private int readInputFromActiveSide() {
        // TODO: Implement with Hytale API
        // IArcaneFlow inputNeighbor = getNeighbor(activeInputSide);
        // return inputNeighbor != null ? inputNeighbor.getCurrentFlow() : 0;
        return 0;
    }
    
    /**
     * Distributes current flow to all sides EXCEPT the active input side.
     */
    private void distributeToOutputSides() {
        if (currentFlow == 0) return;
        
        List<Direction> outputSides = getOutputSides();
        
        for (Direction side : outputSides) {
            transferToSide(side);
        }
    }
    
    /**
     * Transfers flow to a specific side.
     */
    private void transferToSide(Direction side) {
        // TODO: Implement with Hytale API
        // IArcaneFlow neighbor = getNeighbor(side);
        // if (neighbor != null && currentFlow > neighbor.getCurrentFlow()) {
        //     int flowDelta = (currentFlow - neighbor.getCurrentFlow()) / 2;
        //     int amountToTransfer = Math.min(flowDelta, currentFlow);
        //     
        //     if (amountToTransfer > 0) {
        //         int remainder = neighbor.injectFlow(amountToTransfer, true);
        //         int accepted = amountToTransfer - remainder;
        //         
        //         if (accepted > 0) {
        //             this.extractFlow(accepted, false);
        //             neighbor.injectFlow(accepted, false);
        //         }
        //     }
        // }
    }
    
    /**
     * Gets all output sides (all sides except the active input).
     */
    private List<Direction> getOutputSides() {
        List<Direction> outputs = new ArrayList<>();
        for (Direction dir : Direction.values()) {
            if (dir != activeInputSide) {
                outputs.add(dir);
            }
        }
        return outputs;
    }
    
    // --- Configuration Methods ---
    
    /**
     * Cycles through input sides: NORTH -> EAST -> SOUTH -> WEST -> UP -> DOWN -> NORTH
     * Called when player right-clicks the block.
     */
    public void cycleInputSide() {
        switch (activeInputSide) {
            case NORTH:
                activeInputSide = Direction.EAST;
                break;
            case EAST:
                activeInputSide = Direction.SOUTH;
                break;
            case SOUTH:
                activeInputSide = Direction.WEST;
                break;
            case WEST:
                activeInputSide = Direction.UP;
                break;
            case UP:
                activeInputSide = Direction.DOWN;
                break;
            case DOWN:
                activeInputSide = Direction.NORTH;
                break;
        }
        // this.markDirty();
    }
    
    /**
     * Sets the active input side directly.
     * @param side The side to use as input
     */
    public void setInputSide(Direction side) {
        this.activeInputSide = side;
        // this.markDirty();
    }
    
    /**
     * Gets the current active input side.
     * @return The active input direction
     */
    public Direction getInputSide() {
        return this.activeInputSide;
    }
    
    /**
     * Gets all current output sides.
     * @return List of output directions
     */
    public List<Direction> getOutputSides() {
        return getOutputSides();
    }
}
