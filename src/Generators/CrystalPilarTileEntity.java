package com.tumi.hytale.arcana.tile;

import com.tumi.hytale.arcana.power.IArcaneFlow;
import java.util.List;
import java.util.ArrayList;

/**
 * Crystal Pillar - Arcane Flow Generator
 * Generates flow passively and distributes it to connected blocks.
 */
public class CrystalPillarTileEntity extends ArcaneFlowBaseTileEntity {

    private static final int BASE_GENERATION_RATE = 5; 
    private static final int DECAY_PER_BLOCK = 32; // 1 flow lost per block transfer
    private static final int MAX_TRANSFER_PER_TICK = 100;
    private int currentGenerationRate = BASE_GENERATION_RATE; 

    public CrystalPillarTileEntity() {
        // Higher capacity to act as a primary energy buffer.
        super(IArcaneFlow.MAX_FLOW_CAPACITY_  BASE * 5); 
    }
    
    @Override
    public void tick() {
        // 1. Generation Logic - ALWAYS generate (up to capacity):
        int spaceAvailable = this.getMaximumCapacity() - this.getCurrentFlow();
        int flowToGenerate = Math.min(currentGenerationRate, spaceAvailable);
        
        if (flowToGenerate > 0) {
            this.currentFlow += flowToGenerate;
            // this.markDirty();
        }
        
        // 2. Propagation to connected blocks (only if we have flow):
        // This will drain flow as neighbors request it
        if (currentFlow > 0) {
            propagateToNeighbors();
        }
        
        // 3. Update generation rate based on world conditions
        updateGenerationRate();
    }
    
    /**
     * Propagates flow to all neighboring arcane blocks.
     * Will drain from the internal buffer as neighbors accept flow.
     */
    private void propagateToNeighbors() {
        List<IArcaneFlow> neighbors = getArcaneNeighbors();
        
        for (IArcaneFlow neighbor : neighbors) {
            // Only transfer if we have more flow than neighbor ( accounting for decay cost)
            if (currentFlow > neighbor.getCurrentFlow() + DECAY_PER_BLOCK) {
                
                // Calculate how much to transfer (balance towards equilibrium)
                int flowDelta = (currentFlow - neighbor.getCurrentFlow()) / 2;
                int amountToTransfer = Math.min(flowDelta, MAX_TRANSFER_PER_TICK);
                
                // Don't transfer more than we have
                amountToTransfer = Math.min(amountToTransfer, currentFlow);

                // Apply decay (1 flow lost per block)
                int flowWithDecay = amountToTransfer - DECAY_PER_BLOCK;
                
                if (flowWithDecay > 0) {
                    // Simulate acceptance to see how much neighbor can take
                    int neighborRemainder = neighbor.injectFlow(flowWithDecay, true);
                    int flowAcceptedByNeighbor = flowWithDecay - neighborRemainder;
                    
                    // Real Transfer - drain from our buffer
                    if (flowAcceptedByNeighbor > 0) {
                        // Total cost = what neighbor gets + decay loss
                        int totalFlowToExtract = flowAcceptedByNeighbor + DECAY_PER_BLOCK;
                        this.extractFlow(totalFlowToExtract, false);
                        neighbor.injectFlow(flowAcceptedByNeighbor, false);
                    }
                }
            }
        }
    }
    
    /**
     * Gets all neighboring blocks that implement IArcaneFlow.
     */
    private List<IArcaneFlow> getArcaneNeighbors() {
        // TODO: Scan 6 directions, check if Tile Entity implements IArcaneFlow.
        return new ArrayList<>(); 
    }
    
    /**
     * Updates generation rate based on world conditions.
     */
    private void updateGenerationRate() {
        // TODO: Check world conditions (e.g., proximity to Mana Nexus, time of day, biome).
        // Example:
        // if (isNearManaNexus()) {
        //     this.currentGenerationRate = BASE_GENERATION_RATE * 2;
        // } else {
        //     this.currentGenerationRate = BASE_GENERATION_RATE;
        // }
    }
    
    /**
     * Gets the current generation rate.
     */
    public int getGenerationRate() {
        return this.currentGenerationRate;
    }
}