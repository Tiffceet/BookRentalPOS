package my.edu.tarc.dco.bookrentalpos;

public class Book extends Entity {
    private Boolean rented;
    private Boolean reserved;
    private double rentalPrice;

    public Book(String name,double rentalPrice){
        setName(name);
        setRentalPrice(rentalPrice);
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