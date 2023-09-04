package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Repository
public class HotelManagementRepository {
    // Hotel Database
    Map<String, Hotel> hotelDb = new HashMap<>();
    // User Database with Aadhar Number ad Primary Key
    Map<Integer, User> userDb  =  new HashMap<>();

    //Bookings done by users having aadhar number as primary key
    Map<String, Booking> bookingsDb =  new HashMap<>();
    Map<Integer, List<Booking>> userbookingsDb =  new HashMap<>();

    // Adding new Hotel
    public String addHotel(Hotel hotel){
        // if hotel name is null return failure
        //You need to add an hotel to the database
        //incase the hotelName is null or the hotel Object is null return an empty a FAILURE
        //Incase somebody is trying to add the duplicate hotelName return FAILURE
        //in all other cases return SUCCESS after successfully adding the hotel to the hotelDb.
        if(hotel.getHotelName() ==  null || hotel == null) return "FAILURE";
        if (hotel.getHotelName().equals(hotelDb.get(hotel.getHotelName()))) return "FAILURE";
        hotelDb.put(hotel.getHotelName(),  hotel);
        return "SUCCESS";
    }


    // Aadning new User and Returning Aadhar Number after Succesfull User Added
    public Integer addUser(User user){
        userDb.put(user.getaadharCardNo(), user); // Adding new User to db
       return user.getaadharCardNo(); // returning the Aadhar Number
    }

    // Returning the list facilities having maximum no of facilities.
    public List<String> getHotelWithMostFacilities(){
        List<String> hotelnames = new ArrayList<>();
        int max = Integer.MIN_VALUE;
        for (Map.Entry<String, Hotel> hotel: hotelDb.entrySet()){
            if(hotel.getValue().getFacilities().size() > max || hotel.getValue().getFacilities().size() == max){
                max = hotel.getValue().getFacilities().size();
                hotelnames.add(hotel.getKey());
            }
        }
        return hotelnames;
    }

    public int bookARoom(Booking booking){

        //The booking object coming from postman will have all the attributes except bookingId and amountToBePaid;
        //Have bookingId as a random UUID generated String
        //save the booking Entity and keep the bookingId as a primary key
        String bookingId =  String.valueOf(UUID.randomUUID());
        bookingsDb.put(bookingId, booking); // Saving booking.

        List<Booking> bookings = userbookingsDb.getOrDefault(booking.getBookingAadharCard(), new ArrayList<>());
        bookings.add(booking);
        userbookingsDb.put(booking.getBookingAadharCard(),bookings);

        //Calculate the total amount paid by the person based on no. of rooms booked and price of the room per night.
        int priceperNight = hotelDb.get(booking.getHotelName()).getPricePerNight();
        int noofBookings  = booking.getNoOfRooms();
        int totalAmount   =  priceperNight*noofBookings;

        //If there arent enough rooms available in the hotel that we are trying to book return -1

        if(hotelDb.get(booking.getHotelName()).getAvailableRooms() < noofBookings) return -1;
        //in other case return total amount paid

        return totalAmount;
    }
    // get bookings by person
    public int getBookings(Integer aadharCard){
        return userbookingsDb.get(aadharCard).size();
    }

    public Hotel updateFacilities(List<Facility> newFacilities,String hotelName){

        //We are having a new facilites that a hotel is planning to bring.
        //If the hotel is already having that facility ignore that facility otherwise add that facility in the hotelDb

        Hotel hotel = hotelDb.get(hotelName); // getting hotel by name
        List<Facility> existingFacilities = hotel.getFacilities();

        for (Facility facility: newFacilities ) {
            if(!existingFacilities.contains(facility)){
                existingFacilities.add(facility);
            }
        }
        hotel.setFacilities(existingFacilities);
        hotelDb.put(hotelName, hotel);
        return hotel;
        //return the final updated List of facilities and also update that in your hotelDb
        //Note that newFacilities can also have duplicate facilities possible

    }
}
