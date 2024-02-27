<?php
class Controller extends Model{

    public function getUserData(){
        $data = $this->getUsers();
        return $data;
    }

    public function getHomesData(){
        $data = $this->getHomes();
        return $data;
    }

    public function getBookingData(){
        $data = $this->getBooking();
        return $data;
    }

    // public function getReviewData($userid, $homeid){
    //     $data = $this->getReview($userid, $homeid);
    //     return $data;
    // }

    public function retrieveHomes($title){
        $data = $this->searchHomes($title);
        return $data;
    }
}
?>