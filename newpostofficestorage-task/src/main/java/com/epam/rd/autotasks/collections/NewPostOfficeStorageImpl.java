package com.epam.rd.autotasks.collections;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Predicate;

public class NewPostOfficeStorageImpl implements NewPostOfficeStorage {
    private List<Box> parcels;

    /**
     * Creates internal storage for becoming parcels
     */
    public NewPostOfficeStorageImpl() {
        parcels = new LinkedList<>();
    }

    /**
     * Creates own storage and appends all parcels into own storage.
     * It must add either all the parcels or nothing, if an exception occurs.
     *
     * @param boxes a collection of parcels.
     * @throws NullPointerException if the parameter is {@code null}
     *                              or contains {@code null} values.
     */
    public NewPostOfficeStorageImpl(Collection<Box> boxes) {
        if (parcels==null){
           this.parcels = new LinkedList<>();
        }
       if (checkIfNotNull(boxes).isPresent()){
           for (Box b : boxes) {
               parcels.add(b);
           }
       }
        }
    private Optional<Collection<Box>> checkIfNotNull(Collection<Box> boxes) {
        for (Box b : boxes) {
            if (b == null) {
                //return Optional.ofNullable(null);
                throw new NullPointerException();
            }
        }
       return Optional.ofNullable(boxes);
    }

    @Override
    public boolean acceptBox(Box box) {
        if (checkIfBoxNotIsNull(box).isPresent() && checkIfParcelsNotNull(parcels).isPresent()){
            parcels.add(box);
            return true;
        }else {
            throw new NullPointerException();
        }
    }
    private Optional<Box> checkIfBoxNotIsNull(Box b) {
        return Optional.ofNullable(b);
    }

    private Optional<List<Box>> checkIfParcelsNotNull(List<Box> prcl){
       if (prcl==null){
           this.parcels = new LinkedList<>();
           prcl = parcels;
       }
        for (Box b : prcl) {
            if (b == null) {
               // return Optional.ofNullable(null);
               throw new NullPointerException();
            }
        }
        return Optional.ofNullable(prcl);
    }

    @Override
    public boolean acceptAllBoxes(Collection<Box> boxes) {
        Collection<Box> boxes1 = checkIfNotNull(boxes).get();
        if (!boxes1.isEmpty() && checkIfParcelsNotNull(parcels).isPresent()){
            parcels.addAll(boxes1);
            return true;
        }
        return false;
    }

    @Override
    public boolean carryOutBoxes(Collection<Box> boxes) {
        if (checkIfNotNull(boxes).isPresent() && checkIfParcelsNotNull(parcels).isPresent()){
            if(parcels.removeAll(boxes)){
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public List<Box> carryOutBoxes(Predicate<Box> predicate) {
        if (predicate==null){throw new NullPointerException();}
    Predicate<Box> pred = new Predicate<Box>() {
        @Override
        public boolean test(Box box) {
            return box.getOfficeNumber() != 1;
        }
    };
    predicate = pred;

        List<Box> boxes = searchBoxes(predicate);
        Set<Box> boxSet = new LinkedHashSet<>();
        boxSet.addAll(boxes);
        parcels.removeAll(boxSet);
        List<Box> toReturn = new LinkedList<>(boxSet);
        toReturn.addAll(boxSet);
        System.out.println(toReturn);
        return toReturn;
    }

    @Override
    public List<Box> getAllWeightLessThan(double weight) {
        if (weight <= 0.0) {
            throw new IllegalArgumentException();
        }
        Predicate<Box> pred = new Predicate<Box>() {
            @Override
            public boolean test(Box box) {
                if (checkIfBoxNotIsNull(box).isPresent()) {
                    boolean bool = box.getWeight() < weight;
                    return bool;
                }
                return false;
            }
        };
        List<Box> boxes = searchBoxes(pred);
        if (boxes == null){
            throw new NullPointerException();
        }
        return boxes;
    }

    @Override
    public List<Box> getAllCostGreaterThan(BigDecimal cost) {
        if (cost.equals(0) || cost.compareTo(BigDecimal.ZERO)<0){
            throw new IllegalArgumentException();
}
        Predicate<Box> pred = new Predicate<Box>() {
            @Override
            public boolean test(Box box) {
                if (checkIfBoxNotIsNull(box).isPresent()) {
                    int i = box.getCost().compareTo(cost);
                    if (i>0){
                        return true;
                    }
                    return false;
                }
                return false;
            }
        };
        List<Box> boxes = searchBoxes(pred);
        if (boxes == null){
            throw new NullPointerException();
        }
        return boxes;
        }



    @Override
    public List<Box> getAllVolumeGreaterOrEqual(double volume) {
        if (volume<0.0){
            throw new IllegalArgumentException();
        }
        Predicate<Box> pred = new Predicate<Box>() {
            @Override
            public boolean test(Box box) {
                if (checkIfBoxNotIsNull(box).isPresent()) {
                    boolean bool = box.getVolume() >= volume;
                    return bool;
                }
                return false;
            }
        };
        List<Box> boxes = searchBoxes(pred);
        if (boxes == null){
            throw new IllegalArgumentException();
        }
        return boxes;

    }


    @Override
    public List<Box> searchBoxes(Predicate<Box> predicate) {
        if (predicate == null) {
            throw new NullPointerException();
        }
        if (checkIfParcelsNotNull(parcels).isPresent()) {
            List<Box> response = new ArrayList<>();
            for (Box b : parcels) {
                if (predicate.test(b)) {
                    response.add(b);
                }
            }
            return response;
        }
        throw new NullPointerException();
    }

    @Override
    public void updateOfficeNumber(Predicate<Box> predicate, int newOfficeNumber) {
        if (predicate == null){
            throw new NullPointerException();
        }
        if (checkIfParcelsNotNull(parcels).isPresent()) {
            for (Box b : parcels) {
                if (predicate.test(b)) {
                    b.setOfficeNumber(newOfficeNumber);
                }
            }
        }
    }
}
