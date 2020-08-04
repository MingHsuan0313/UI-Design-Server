package com.selab.uidesignserver.service;

import org.springframework.stereotype.Service;

@Service
public class PositionTransformer {
    static int sourceHeight;
    static int sourceWidth;
    int sourceX;
    int sourceY;

    int targetHeight;
    int targetWidth;
    int targetX;
    int targetY;

    boolean isSet = false;

    public void setSourceHeight(int sourceHeight) {
        PositionTransformer.sourceHeight = sourceHeight;
    }

    public void setSourceWidth(int sourceWidth) {
        PositionTransformer.sourceWidth = sourceWidth;
    }

    public void setTargetHeight(int targetHeight) {
        this.targetHeight = targetHeight;
    }

    public void setTargetWidth(int targetWidth) {
        this.targetWidth = targetWidth;
    }

    public void transform(int srcx, int srcy){
        // percentage
        this.targetX = srcx*100/this.sourceWidth;
        this.targetY = srcy*100/this.sourceHeight;
    }


    public int getTargetHeight() {
        return this.targetY;
    }

    public int getTargetWidth() {
        return this.targetX;
    }

    public int getSourceHeight(){
        return PositionTransformer.sourceHeight;
    }

    public int getSourceWidth(){
        return PositionTransformer.sourceWidth;
    }
}
