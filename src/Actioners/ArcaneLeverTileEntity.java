package com.tumi.hytale.arcana.tile;

import com.tumi.hytale.arcana.power.IArcaneFlow;
import java.util.List;
import java.util.ArrayList;

public class ArcaneLeverTileEntity extends ArcaneFlowBaseTileEntity {

    private boolean activated = false; // Lever state: true = ON, false = OFF
    
    public ArcaneLeverTileEntity() {
        // Minimal capacity, acts as a switch not a buffer
        super(IArcaneFlow.MAX_FLOW_CAPACITY_BASE); 
    }
    
    @Override
    public void tick() {
        // Generate passive flow of 1 when activated
        if (activated) {
            int spaceAvailable = this.getMaximumCapacity() - this.getCurrentFlow();
            if (spaceAvailable > 0) {
                this.currentFlow += 1; // Passive generation
                // this.markDirty();
            }
        }
        
        if (!activated || currentFlow == 0) {
            // If lever is OFF or no flow, don't propagate
            return;
        }

        // If lever is ON, propagate flow to neighbors like a channel
        List<IArcaneFlow> neighbors = getArcaneNeighbors(); 
        
        for (IArcaneFlow neighbor : neighbors) {
            if (currentFlow > neighbor.getCurrentFlow()) {
                 
                // Calculate transfer amount
                int flowDelta = (currentFlow - neighbor.getCurrentFlow()) / 2;
                int amountToTransfer = Math.min(flowDelta, currentFlow);

                // Transfer without decay (lever doesn't add decay, just passes through)
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
    
    // --- Lever Control Methods ---
    
    /**
     * Toggles the lever state (ON/OFF).
     * Called when player right-clicks the lever block.
     */
    public void toggle() {
        this.activated = !this.activated;
        // this.markDirty(); // Uncomment when Hytale API is available
        // Update block state visually in the world
    }
    
    /**
     * Sets the lever state explicitly.
     * @param active true to turn ON, false to turn OFF
     */
    public void setActivated(boolean active) {
        this.activated = active;
        // this.markDirty();
    }
    
    /**
     * Returns the current state of the lever.
     * @return true if ON, false if OFF
     */
    public boolean isActivated() {
        return this.activated;
    }
}
