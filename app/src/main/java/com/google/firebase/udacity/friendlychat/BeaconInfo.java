package com.google.firebase.udacity.friendlychat;

import org.altbeacon.beacon.Beacon;

/**
 * Created by X220 on 2017/5/15.
 */

public  class BeaconInfo extends Beacon{
    private String combinedUUID;
    private int defDisatnce;
    private String name;
    private String description;
    private String entryTimeId;

    public BeaconInfo(String combinedUUID,String name, String description,int defDisatnce ,String entryTimeId){
        setCombinedUUID(combinedUUID);
        setName(name);
        setDescription(description);
        setDefDisatnce(defDisatnce);
        setEntryTimeId(entryTimeId);
    }

    public String getEntryTimeId() {
        return entryTimeId;
    }

    public void setEntryTimeId(String entryTimeId) {
        this.entryTimeId = entryTimeId;
    }

    public String getCombinedUUID() {
        return combinedUUID;
    }

    public void setCombinedUUID(String combinedUUID) {
        this.combinedUUID = combinedUUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDefDisatnce(int difDisatnce) {
        this.defDisatnce = difDisatnce;
    }

    public int getDefDisatnce() {
        return defDisatnce;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }


}
