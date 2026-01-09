package com.noodlegamer76.shadered.client.util;

public class SkyboxTranslation {
    //Configured to typical skybox
    public SkyboxRotation frontRot = SkyboxRotation.ROTATE_90_CCW;
    public SkyboxRotation backRot = SkyboxRotation.ROTATE_90_CCW;
    public SkyboxRotation leftRot = SkyboxRotation.ROTATE_90_CCW;
    public SkyboxRotation rightRot = SkyboxRotation.ROTATE_90_CCW;
    public SkyboxRotation topRot = SkyboxRotation.NONE;
    public SkyboxRotation bottomRot = SkyboxRotation.ROTATE_180;

    public SkyboxFlip frontFlip = SkyboxFlip.HORIZONTAL;
    public SkyboxFlip backFlip = SkyboxFlip.HORIZONTAL;
    public SkyboxFlip leftFlip = SkyboxFlip.HORIZONTAL;
    public SkyboxFlip rightFlip = SkyboxFlip.HORIZONTAL;
    public SkyboxFlip topFlip = SkyboxFlip.HORIZONTAL;
    public SkyboxFlip bottomFlip = SkyboxFlip.HORIZONTAL;

    public SkyboxTranslation() {
    }

    public SkyboxTranslation setFrontRot(SkyboxRotation rot) {
        this.frontRot = rot;
        return this;
    }

    public SkyboxTranslation setBackRot(SkyboxRotation rot) {
        this.backRot = rot;
        return this;
    }

    public SkyboxTranslation setLeftRot(SkyboxRotation rot) {
        this.leftRot = rot;
        return this;
    }

    public SkyboxTranslation setRightRot(SkyboxRotation rot) {
        this.rightRot = rot;
        return this;
    }

    public SkyboxTranslation setTopRot(SkyboxRotation rot) {
        this.topRot = rot;
        return this;
    }

    public SkyboxTranslation setBottomRot(SkyboxRotation rot) {
        this.bottomRot = rot;
        return this;
    }

    public SkyboxTranslation setFrontFlip(SkyboxFlip flip) {
        this.frontFlip = flip;
        return this;
    }

    public SkyboxTranslation setBackFlip(SkyboxFlip flip) {
        this.backFlip = flip;
        return this;
    }

    public SkyboxTranslation setLeftFlip(SkyboxFlip flip) {
        this.leftFlip = flip;
        return this;
    }

    public SkyboxTranslation setRightFlip(SkyboxFlip flip) {
        this.rightFlip = flip;
        return this;
    }

    public SkyboxTranslation setTopFlip(SkyboxFlip flip) {
        this.topFlip = flip;
        return this;
    }

    public SkyboxTranslation setBottomFlip(SkyboxFlip flip) {
        this.bottomFlip = flip;
        return this;
    }

    public SkyboxTranslation setAllRot(SkyboxRotation rot) {
        this.frontRot = rot;
        this.backRot = rot;
        this.leftRot = rot;
        this.rightRot = rot;
        this.topRot = rot;
        this.bottomRot = rot;
        return this;
    }

    public SkyboxTranslation setSidesRot(SkyboxRotation rot) {
        this.frontRot = rot;
        this.backRot = rot;
        this.leftRot = rot;
        this.rightRot = rot;
        return this;
    }

    public SkyboxTranslation setTopBottomRot(SkyboxRotation rot) {
        this.topRot = rot;
        this.bottomRot = rot;
        return this;
    }

    public SkyboxTranslation setAllFlip(SkyboxFlip flip) {
        this.frontFlip = flip;
        this.backFlip = flip;
        this.leftFlip = flip;
        this.rightFlip = flip;
        this.topFlip = flip;
        this.bottomFlip = flip;
        return this;
    }

    public SkyboxTranslation setSidesFlip(SkyboxFlip flip) {
        this.frontFlip = flip;
        this.backFlip = flip;
        this.leftFlip = flip;
        this.rightFlip = flip;
        return this;
    }

    public SkyboxTranslation setTopBottomFlip(SkyboxFlip flip) {
        this.topFlip = flip;
        this.bottomFlip = flip;
        return this;
    }

    public SkyboxTranslation setFront(SkyboxRotation rot, SkyboxFlip flip) {
        this.frontRot = rot;
        this.frontFlip = flip;
        return this;
    }

    public SkyboxTranslation setBack(SkyboxRotation rot, SkyboxFlip flip) {
        this.backRot = rot;
        this.backFlip = flip;
        return this;
    }

    public SkyboxTranslation setLeft(SkyboxRotation rot, SkyboxFlip flip) {
        this.leftRot = rot;
        this.leftFlip = flip;
        return this;
    }

    public SkyboxTranslation setRight(SkyboxRotation rot, SkyboxFlip flip) {
        this.rightRot = rot;
        this.rightFlip = flip;
        return this;
    }

    public SkyboxTranslation setTop(SkyboxRotation rot, SkyboxFlip flip) {
        this.topRot = rot;
        this.topFlip = flip;
        return this;
    }

    public SkyboxTranslation setBottom(SkyboxRotation rot, SkyboxFlip flip) {
        this.bottomRot = rot;
        this.bottomFlip = flip;
        return this;
    }

    public enum SkyboxRotation {
        NONE,
        ROTATE_90_CW,
        ROTATE_180,
        ROTATE_90_CCW
    }

    public enum SkyboxFlip {
        NONE,
        HORIZONTAL,
        VERTICAL,
        BOTH
    }
}
