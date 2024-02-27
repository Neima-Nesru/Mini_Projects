<?php
class Db{
    private $hostname = 'localhost';
    private $username = 'HomeRentalSystem';
    private $password = 'incorrect';
    private $db = 'hrs';

    protected function conn(){
        $conn = new mysqli($this->hostname, $this->username, $this->password, $this->db);

        if ($conn->connect_errno){
            return "Connection Error";
        }
        else{
            return $conn;
        }
    }
}
?>