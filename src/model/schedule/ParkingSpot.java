package model.schedule;

/**
 *
 * @author David
 */
public class ParkingSpot {

    private String name;

    public ParkingSpot(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
    	return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ParkingSpot))
            return false;
        ParkingSpot parkingSpot = (ParkingSpot)o;
        return parkingSpot.toString().equals(this.name);
    }
}
