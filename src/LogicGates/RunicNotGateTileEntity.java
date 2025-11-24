package com.tumi.hytale.arcana.tile.logic;

import com.tumi.hytale.arcana.power.IArcaneFlow;
import java.util.List;
import java.util.ArrayList;

/**
 * NOT Gate - Inverts the input signal.
 * 
 * Configuration:
 * - Input: NORTH (1 input)
 * - Output: SOUTH
 * 
 * Logic: Output = NOT Input
 * 
 * Special: Generates passive flow of 10 when input is inactive.
 */
public class RunicNotGateTileEntity extends RunicLinkerTileEntity {

    private static final int NOT_OUTPUT_FLOW = 10; // Flow when inverting (no input)
    
    @Override
    protected void readInputs() {
        inputFlows.clear();
        
        // TODO: Read actual flow from neighboring blocks
        // This is a placeholder - requires Hytale API
        // IArcaneFlow northNeighbor = getNeighbor(Direction.NORTH);
        // inputFlows.put(Direction.NORTH, northNeighbor != null ? northNeighbor.getCurrentFlow() : 0);
        
        inputFlows.put(Direction.NORTH, 0);
    }
    
    @Override
    protected boolean calculateLogic() {
        // NOT logic: Invert the input
        int northFlow = inputFlows.getOrDefault(Direction.NORTH, 0);
        
        return !isFlowActive(northFlow);
    }
    
    @Override
    protected int getMaxInputFlow() {
        // NOT gate generates its own output flow when active
        return NOT_OUTPUT_FLOW;
    }
    
    @Override
    protected void propagateOutput() {
        if (currentFlow == 0) return;
        
        // TODO: Propagate to SOUTH neighbor
        // IArcaneFlow southNeighbor = getNeighbor(Direction.SOUTH);
        // if (southNeighbor != null) {
        //     int amountToTransfer = currentFlow;
        //     int remainder = southNeighbor.injectFlow(amountToTransfer, true);
        //     int accepted = amountToTransfer - remainder;
        //     if (accepted > 0) {
        //         this.extractFlow(accepted, false);
        //         southNeighbor.injectFlow(accepted, false);
        //     }
        // }
    }
}
