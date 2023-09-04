package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HotelManagementService {

//    @Autowired
    HotelManagementRepository hotelManagementRepository = new HotelManagementRepository();
    // Adding New hotel
    public String addhotel(Hotel hotel){
         return hotelManagementRepository.addHotel(hotel);
    }

    // new user add.
    public Integer adduser(User user){
       return  hotelManagementRepository.addUser(user);
    }

    // Hotel Name with Most Facilities.
    public String getHotelWithMostFacilities(){
        List<String> hotelnames = hotelManagementRepository.getHotelWithMostFacilities();
        if(hotelnames.isEmpty()) return "";
        String smaller = "";
        if(hotelnames.size() == 1){
            return hotelnames.get(0);
        }else {
            smaller = hotelnames.get(0);
           for(String name :  hotelnames){
                if(name.compareTo(smaller) > 0)
                   smaller  = name;
           }
       }
       return smaller;
    }
    // booking a hotel
    public int bookARoom(Booking booking){
        return hotelManagementRepository.bookARoom(booking);
    }

    //get booking by person
    public int getbookings(Integer aadharnumber){
       return hotelManagementRepository.getBookings(aadharnumber);
    }

    public Hotel updateFacilities(List<Facility> newFacilities,String hotelName){
        return hotelManagementRepository.updateFacilities(newFacilities,hotelName);
    }

    }
