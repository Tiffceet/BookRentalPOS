package my.edu.tarc.dco.bookrentalpos;

public class Book extends Entity {
    private Boolean rented;
    private Boolean reserved;
    private double rentalPrice;
    private int lastRentedBy;
    private int lastReservedBy;

    public int getLastRentedBy() {
        return this.lastRentedBy;
    }

    public void setLastRentedBy(int lastRentedBy) {
        this.lastRentedBy = lastRentedBy;
    }

    public int getLastReservedBy() {
        return this.lastReservedBy;
    }

    public void setLastReservedBy(int lastReservedBy) {
        this.lastReservedBy = lastReservedBy;
    }

    // For adding new book use
    public Book(String name,double rentalPrice){
        setName(name);
        setRentalPrice(rentalPrice);
    }

    public Book(String id, String name,String date,double rentalPrice, int lastRentedBy, int lastReservedBy){
       setID(id);
       setDateCreated(date);
       setName(name);
       setRentalPrice(rentalPrice);
       setLastRentedBy(lastRentedBy);
       setLastReservedBy(lastReservedBy);
    }

    public Boolean isRental(){
        return rented;
    }

    public Boolean isReserved(){
        return reserved;
    }

    public void setRentalPrice(double rentalPrice){
        this.rentalPrice = rentalPrice;
    }

    public double getRentalPrice(){
        return rentalPrice;
    }


}