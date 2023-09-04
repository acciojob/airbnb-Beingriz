package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class HotelManagementRepository {
    // Hotel Database
    Map<String, Hotel> hotelDb = new HashMap<>();
    // User Database with Aadhar Number ad Primary Key
    Map<Integer, User> userDb  =  new HashMap<>();

    //Bookings done by users having aadhar number as primary key
    Map<Integer, List<Booking>> userBookingsDb =  new HashMap<>();

    // Adding new Hotel
    public String addHotel(Hotel hotel){
        // if hotel name is null return failure
        if(hotel.getHotelName() ==  null) return "FAILURE";
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
    // get bookings by person
    public int getBookings(Integer aadharCard){
        return userBookingsDb.get(aadharCard).size();
    }

}
