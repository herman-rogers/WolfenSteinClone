package com.base.engine;

/**
 * Created by Admin on 3/25/14.
 */
public class psPointLight {
    private psBaseLight baseLight;
    private psAttenuation attenuation;
    private Vector3f position;
    private float range;

    public psPointLight( psBaseLight baseLight, psAttenuation attenuation, Vector3f position, float range ){
        this.baseLight = baseLight;
        this.attenuation = attenuation;
        this.position = position;
        this.range = range;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public psBaseLight getBaseLight() {
        return baseLight;
    }

    public void setBaseLight(psBaseLight baseLight) {
        this.baseLight = baseLight;
    }

    public psAttenuation getAttenuation() {
        return attenuation;
    }

    public void setAttenuation(psAttenuation attenuation) {
        this.attenuation = attenuation;
    }

    public float getRange() {
        return range;
    }

    public void setRange(float range) {
        this.range = range;
    }
}
