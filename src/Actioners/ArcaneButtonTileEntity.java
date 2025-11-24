package com.tumi.hytale.arcana.tile;

import com.tumi.hytale.arcana.power.IArcaneFlow;
import java.util.List;
import java.util.ArrayList;

public class ArcaneButtonTileEntity extends ArcaneFlowBaseTileEntity {

    private static final int ACTIVE_DURATION = 10; // Active for 10 ticks
    private int remainingActiveTicks = 0; // Countdown timer
    
    public ArcaneButtonTileEntity() {
        // Minimal capacity, acts as a temporary switch
        super(IArcaneFlow.MAX_FLOW_CAPACITY_BASE); 
    }
    
    @Override
    public void tick() {
        // Countdown timer
        if (remainingActiveTicks > 0) {
            remainingActiveTicks--;
            // this.markDirty(); // Uncomment when Hytale API is available
            
            // Generate passive flow of 1 when active
            int spaceAvailable = this.getMaximumCapacity() - this.getCurrentFlow();
            if (spaceAvailable > 0) {
                this.currentFlow += 1; // Passive generation
                // this.markDirty();
            }
        }
        
        // Only propagate if button is active and has flow
        if (remainingActiveTicks == 0 || currentFlow == 0) {
            return;
        }

        // If button is active, propagate flow to neighbors
        List<IArcaneFlow> neighbors = getArcaneNeighbors(); 
        
        for (IArcaneFlow neighbor : neighbors) {
            if (currentFlow > neighbor.getCurrentFlow()) {
                
                // Calculate transfer amount
                int flowDelta = (currentFlow - neighbor.getCurrentFlow()) / 2;
                int amountToTransfer = Math.min(flowDelta, currentFlow);

                // Transfer without decay (button doesn't add decay, just passes through)
                if (amountToTransfer > 0) {
                    // Simulate acceptance
                    int neighborRemainder = neighbor.injectFlow(amountToTransfer, true);
                    int flowAcceptedByNeighbor = amountToTransfer - neighborRemainder;
                    
                    // Real Transfer
                    if (flowAcceptedByNeighbor > 0) {
                        this.extractFlow(flowAcceptedByNeighbor, false);
                        neighbor.injectFlow(flowAcceptedByNeighbor, false);
                    }
                }
            }
        }
    }

    // Simulated method - Requires Hytale API access to fetch adjacent Tile Entities.
    private List<IArcaneFlow> getArcaneNeighbors() {
        // Logic: Scan 6 directions (or specific output direction), 
        // check if Tile Entity implements IArcaneFlow.
        return new ArrayList<>(); 
    }
    
    // --- Button Control Methods ---
    
    /**
     * Activates the button for 10 ticks.
     * Called when player right-clicks the button block.
     */
    public void press() {
        this.remainingActiveTicks = ACTIVE_DURATION;
        // this.markDirty(); // Uncomment when Hytale API is available
        // Update block state visually in the world
    }
    
    /**
     * Returns whether the button is currently active.
     * @return true if active, false if inactive
     */
    public boolean isActive() {
        return this.remainingActiveTicks > 0;
    }
    
    /**
     * Returns the remaining active ticks.
     * @return number of ticks remaining
     */
    public int getRemainingTicks() {
        return this.remainingActiveTicks;
    }
}
