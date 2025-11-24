package com.tumi.hytale.arcana.tile;

import com.tumi.hytale.arcana.power.IArcaneFlow;
import java.util.List;
import java.util.ArrayList;

public class ArcaneChannelTileEntity extends ArcaneFlowBaseTileEntity {

    private static final int DECAY_PER_BLOCK = 1; // 1 flow lost per block
    private static final int MAX_RANGE = 32; // Maximum 32 blocks (0 flow at block 33)
    private static final int MAX_TRANSFER_PER_TICK = 100; // Increased for better flow
    
    public ArcaneChannelTileEntity() {
        // Channels have double capacity to smooth flow.
        super(IArcaneFlow.MAX_FLOW_CAPACITY_BASE * 2); 
    }
    
    // --- Flow Propagation Logic ---

    @Override
    public void tick() {
        if (currentFlow == 0) return;

        List<IArcaneFlow> neighbors = getArcaneNeighbors(); 
        
        for (IArcaneFlow neighbor : neighbors) {
            if (currentFlow > neighbor.getCurrentFlow() + DECAY_PER_BLOCK) {
                
                // 1. Calculate desired transfer (half the difference, limited by MAX_TRANSFER)
                int flowDelta = (currentFlow - neighbor.getCurrentFlow()) / 2;
                int amountToTransfer = Math.min(flowDelta, MAX_TRANSFER_PER_TICK);

                // 2. Apply Decay: Subtract 1 flow per block transfer
                int flowWithDecay = amountToTransfer - DECAY_PER_BLOCK;
                
                // Only transfer if there's flow left after decay
                if (flowWithDecay > 0) {
                    // 3. Simulate acceptance
                    int neighborRemainder = neighbor.injectFlow(flowWithDecay, true);
                    int flowAcceptedByNeighbor = flowWithDecay - neighborRemainder;
                    
                    // 4. Real Transfer
                    if (flowAcceptedByNeighbor > 0) {
                        // Extract the original amount (includes the decay loss)
                        int totalFlowToExtract = flowAcceptedByNeighbor + DECAY_PER_BLOCK;
                        
                        this.extractFlow(totalFlowToExtract, false);
                        neighbor.injectFlow(flowAcceptedByNeighbor, false);
                    }
                }
            }
        }
    }

    // Simulated method - Requires Hytale API access to fetch adjacent Tile Entities.
    private List<IArcaneFlow> getArcaneNeighbors() {
        // Logic: Scan 6 directions, check if Tile Entity implements IArcaneFlow.
        return new ArrayList<>(); 
    }
}