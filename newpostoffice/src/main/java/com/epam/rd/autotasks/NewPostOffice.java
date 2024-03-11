package com.epam.rd.autotasks;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

public class NewPostOffice {
    private final Collection<Box> listBox;
    private static final int COST_KILOGRAM = 5;
    private static final int COST_CUBIC_METER = 100;
    private static final double COEFFICIENT = 0.5;

    public NewPostOffice() {
        listBox = new ArrayList<>();
    }

    public Collection<Box> getListBox() {
        return (Collection<Box>) ((ArrayList<Box>) listBox).clone();
    }

    static BigDecimal calculateCostOfBox(double weight, double volume, int value) {
        BigDecimal costWeight = BigDecimal.valueOf(weight)
                .multiply(BigDecimal.valueOf(COST_KILOGRAM), MathContext.DECIMAL64);
        BigDecimal costVolume = BigDecimal.valueOf(volume)
                .multiply(BigDecimal.valueOf(COST_CUBIC_METER), MathContext.DECIMAL64);
        return costVolume.add(costWeight)
                .add(BigDecimal.valueOf(COEFFICIENT * value), MathContext.DECIMAL64);
    }

    // implements student
    public boolean addBox(String addresser, String recipient, double weight, double volume, int value) {
        if (checkWeight(addresser, recipient, weight, volume, value)) {
            Box boxToAdd = new Box(addresser, recipient, weight, volume);
            boxToAdd.setCost(calculateCostOfBox(weight,volume,value));
            listBox.add(boxToAdd);
            return true;
        }
        return false;
    }

    // implements student
    public Collection<Box> deliveryBoxToRecipient(String recipient) {
        if (recipient == null){
            return null;
        }
         Collection<Box> response = new ArrayList<>();
        Iterator<Box> iterator = listBox.iterator();
        while (iterator.hasNext()){
            Box boxElement = iterator.next();
            if (boxElement.getRecipient().equals(recipient)){
                response.add(boxElement);
                iterator.remove();
            }
        }
        return response;
    }

    public void declineCostOfBox(double percent) {
        int sub = 1;
        Iterator<Box> iterator = listBox.iterator();
        while (iterator.hasNext()) {
            Box boxElement = iterator.next();
            BigDecimal multiply = boxElement.getCost().multiply(BigDecimal.valueOf((100.0 - percent) / 100.0), MathContext.DECIMAL64);
            BigDecimal cost = multiply.setScale(12, RoundingMode.DOWN);
            boxElement.setCost(cost);
        }
    }
    private boolean checkWeight(String ad, String rec, double heft, double vol,int val) {
        if (ad == null || rec == null || ad.isBlank() || rec.isBlank()){
            throw new IllegalArgumentException();
        }
        if (heft < 0.5 || heft > 20.0){
            throw new IllegalArgumentException();
        }
        if (vol <= 0.0 || vol > 0.25){
            throw new IllegalArgumentException();
        }
        if (val <= 0.0){
            throw new IllegalArgumentException();
        }

        return true;
    }

}
