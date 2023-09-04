package com.driver.controllers;

import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        String smaller = hotelnames.get(0);
        if(hotelnames.size() == 0){
            return "";
        }else if(hotelnames.size() == 1){
            return smaller;
        }else if(hotelnames.size()>1){

           for(String name :  hotelnames){
                if(name.compareTo(smaller) < 0)
                   smaller  = name;
           }
       }
       return smaller;
    }

    //get booking by person
    public int getbookings(Integer aadharnumber){
       return hotelManagementRepository.getBookings(aadharnumber);
    }
}
