<?php

class Model extends Db{

    protected function login($username){
        $stmt = "select * from users where username = '$username'";
        $result = $this->conn()->query($stmt);
        return $result;

    }

    protected function getUsers(){
        $stmt = "select * from users";
        $result = $this->conn()->query($stmt);
        return $result;

    }

    protected function getUser($userid){
        $stmt = "select * from users where id = '$userid'";
        $result = $this->conn()->query($stmt);
        return $result;

    }


    protected function setUser($role, $username, $email, $password, $phone, $address, $profile){
        $stmt = "insert into users (role, username, email, password, phone, address, profile) 
                    values ('$role', '$username', '$email', '$password', '$phone', '$address', '$profile')";
        
        $this->conn()->query($stmt);
        return "Signup Successfully!";

    }

    protected function editUser($id, $newUsername, $newEmail, $newPhone, $newAddress){
        $stmt = "update users set username = '$newUsername', email = '$newEmail', phone = '$newPhone', address = '$newAddress' WHERE users.id = $id;";
        $this->conn()->query($stmt);
        return "Profile updated Successfully!";
    }

    protected function deleteUser($userid){
        $delete = "delete from users where id = '$userid'";
        $result = $this->conn()->query($delete);
        return $result;
    }

    protected function setHomes($userid, $homeImg,  $title, $desc, $location, $price, $amenities, $rooms){
        $stmt = "insert into landlord (userid, image, title, description, location, price, amenities, rooms) values ('$userid', '$homeImg', '$title', '$desc', '$location', '$price', '$amenities', '$rooms')";
         $this->conn()->query($stmt);
        return "Home added Successfully!";

    }

    protected function getLandlordsHome($userid){
        $stmt = "select * from landlord where userid = '$userid'";
        $data = $this->conn()->query($stmt);
        return $data;

    }

    // get home to edit
    protected function getHome($homeid){
        $stmt = "select * from landlord where id = '$homeid'";
        $data = $this->conn()->query($stmt);
        return $data;

    }

    protected function editHome($id, $newTitle, $newDesc, $newLocation, $newPrice, $newAmenities, $newRooms){
        $stmt = "update landlord set title = '$newTitle', description = '$newDesc', location = '$newLocation', price = '$newPrice', amenities = '$newAmenities', rooms = '$newRooms' WHERE landlord.id = $id";
        $this->conn()->query($stmt);
        return "Home detail updated Successfully!";
    }

    protected function getRentersHome($userid){
        $stmt = "select * from landlord join booking on landlord.id = booking.homeid where booking.userid = '$userid'";
        $data = $this->conn()->query($stmt);
        return $data;

    }


    protected function getHomes(){
        $data = "select * from landlord";
        $result = $this->conn()->query($data);
        return $result;
    }

    protected function searchHomes($title){
        $data = "select * from landlord where title = '$title'";
        $result = $this->conn()->query($data);
        return $result;
    }

    protected function deleteHome($homeid, $role){
        if ($role === 'renter'){
            $delete = "delete from booking where homeid = '$homeid'";
        }
        else if($role === 'landlord' || $role === 'admin'){
            $delete = "delete from landlord where id = '$homeid'";
        }

        $result = $this->conn()->query($delete);
        return $result;
    }

    protected function setBooking($homeid, $userid, $guests, $checkin, $checkout, $status){
        $data = "insert into booking (homeid, userid, guests, checkin, checkout, status) values ('$homeid', '$userid', '$guests', '$checkin', '$checkout', '$status')";
        $this->conn()->query($data);
        return "Booked Successfully!";
    }

    protected function getBooking(){
        $data = "select * from booking";
        $result = $this->conn()->query($data);
        return $result;
    }

    protected function editBooking($id, $newCheckin, $newCheckout, $newGuests){
        $stmt = "update booking set checkin = '$newCheckin', checkout = '$newCheckout', guests = '$newGuests' WHERE booking.id = $id";
        $this->conn()->query($stmt);
        return "Booking detail updated Successfully!";
    }


    protected function deleteBooking($id){
        $delete = "delete from booking where id = '$id'";
        $result = $this->conn()->query($delete);
        return $result;
    }

    protected function setReview($homeid, $userid, $rate, $comment){
        $data = "insert into review (homeid, userid, rate, comment) values ('$homeid', '$userid', '$rate', '$comment')";
        $this->conn()->query($data);
        return "Reviewed Successfully!";
    }

   /*
    protected function getReview($userid, $homeid){
        $stmt = "select * from review 
                inner join landlord on review.homeid = landlord.id 
                where review.homeid = '$homeid' 
                and review.userid = '$userid'";
        $data = $this->conn()->query($stmt);
        return $data;

    }

    protected function getReview($homeid){
        $data = "select * from review where homeid = '$homeid'";
        $result = $this->conn()->query($data);
        return $result;
    }
    

    
    protected function deleteAcct($acctid, $role){
      if($role === 'renter'){

        $deleteHome = "delete from booking where userid = '$acctid'";
        $deleteUser = "delete from users where id = '$acctid'";
        
        $result = $this->conn()->query($deleteHome);
        $result = $this->conn()->query($deleteUser);

        return $result;
      } 

      else if ($role === 'landlord'){
        $deleteUser = "delete from users where id = '$acctid'";
        $result = $this->conn()->query($deleteUser);
        return $result;
      }  
    }*/
}
?>