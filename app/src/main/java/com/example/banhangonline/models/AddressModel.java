package com.example.banhangonline.models;

import java.io.Serializable;

public class AddressModel implements Serializable {
    String userAddress;
    boolean isSelected;

    public AddressModel() {
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
