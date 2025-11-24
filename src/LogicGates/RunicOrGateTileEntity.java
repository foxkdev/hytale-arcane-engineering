package com.tumi.hytale.arcana.tile.logic;

import com.tumi.hytale.arcana.power.IArcaneFlow;
import java.util.List;
import java.util.ArrayList;

/**
 * OR Gate - Outputs flow when ANY input has flow.
 * 
 * Configuration:
 * - Inputs: NORTH, EAST (2 inputs)
 * - Output: SOUTH
 * 
 * Logic: Output = Input1 OR Input2
 */
public class RunicOrGateTileEntity extends RunicLinkerTileEntity {

    @Override
    protected void readInputs() {
        inputFlows.clear();
        
        // TODO: Read actual flow from neighboring blocks
        // This is a placeholder - requires Hytale API
        // IArcaneFlow northNeighbor = getNeighbor(Direction.NORTH);
        // IArcaneFlow eastNeighbor = getNeighbor(Direction.EAST);
        // inputFlows.put(Direction.NORTH, northNeighbor != null ? northNeighbor.getCurrentFlow() : 0);
        // inputFlows.put(Direction.EAST, eastNeighbor != null ? eastNeighbor.getCurrentFlow() : 0);
        
        inputFlows.put(Direction.NORTH, 0);
        inputFlows.put(Direction.EAST, 0);
    }
    
    @Override
    protected boolean calculateLogic() {
        // OR logic: ANY input must be active
        int northFlow = inputFlows.getOrDefault(Direction.NORTH, 0);
        int eastFlow = inputFlows.getOrDefault(Direction.EAST, 0);
        
        return isFlowActive(northFlow) || isFlowActive(eastFlow);
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
