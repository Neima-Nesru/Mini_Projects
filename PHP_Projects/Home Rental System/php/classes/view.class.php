<?php

class View extends Model{
     
    public function signupView(){
      echo '<img id="banner" src="../images/banner.jpg">
            <div class="banner__info_in_registration">
            <h2>Sign Up for free if you are new for HRS</h2>
            <p>Join largest Airbnb community</p>
        </div>
    
        <div class="login signup">
            <fieldset>
                <legend>Signup</legend>
                <form method="POST" action="" enctype="multipart/form-data">
                    <div class="role">

                    <input type="radio" name="role" value="renter" id="renter" required> <label for="renter">Renter</label> 
                    <input type="radio" name="role" value="landlord" id="landlord" required> <label for="landlord">Landlord</label> 	
                    <input type="radio" name="role" value="admin" id="admin" required> <label for="admin">Admin</label>
                
                    </div>
                    <input type="text" name="username" placeholder="Username" required>
                    <div id="error"></div>
                    <input type="email" name="email" placeholder="Email" required>
                    <input type="number" name="phone" placeholder="Phone" required>
                    <input type="text" name="address" placeholder="Address" required>
                    <input type="password" name="password" placeholder="Password" required>
                    <input type="password" name="confirm" placeholder="Confirm Password" required>
                    <input type="file" name="profile">

                    <input type="submit" name="login" value="Signup">
                    <p class="signup">Already have an account? <a href="login.php">Login</a></p>      
          
                </form>
            </fieldset>
        </div>
       ';
       $profile = $san_username = $san_email = $san_password = $san_conf = $san_address = $san_role = '';
       $san_phone = 0;

       if($_SERVER["REQUEST_METHOD"] === "POST"){

        // sanitization
        $san_role = filter_var($_POST["role"], FILTER_SANITIZE_STRING);
        $san_username = filter_var($_POST["username"], FILTER_SANITIZE_STRING);
        $san_email = filter_var($_POST["email"], FILTER_SANITIZE_EMAIL);
        $san_phone = filter_var($_POST["phone"], FILTER_SANITIZE_STRING);
        $san_address = filter_var($_POST["address"], FILTER_SANITIZE_STRING);
        $san_password = filter_var($_POST["password"], FILTER_SANITIZE_STRING);
        $san_conf = filter_var($_POST["confirm"], FILTER_SANITIZE_STRING);
       

        $profile = $_FILES["profile"]["name"];
        $profileTmpName = $_FILES["profile"]["tmp_name"];

        $allowedExtension = array('jpg', 'jpeg', 'png', 'webp', 'jfif', 'PNG');
        $profileExtension = pathinfo($profile, PATHINFO_EXTENSION);

        $upload_directory = "image/profile/";
        $profilePath = $upload_directory.$profile;

        //validation
        if($san_password === $san_conf){

            if(!in_array($profileExtension, $allowedExtension)){
                ?>
                <script>
                    alert('Unsupported file extension!')
                </script>
                <?php
            }

            else{
                move_uploaded_file($profileTmpName, $profilePath);
                $data = $this->setUser($san_role, $san_username, $san_email, $san_password, $san_phone, $san_address, $profilePath);
              
                header("Location:login.php");
                return $data;
            }
           
        }
    }
}
    public function loginView(){
        echo '<img id="banner" src="../images/banner.jpg">
        <div class="banner__info_in_registration">
          <h2>Welcome back, You have been missed!</h2>
           <p>Login to your account to rent homes</p>
       </div>
       <div class="login">
           <fieldset>
           
           <form method="POST" action="">
               <legend>Login</legend>
               
               <input id="login-email" type="text" name="username"  placeholder="Enter your Username">
               <div id="error1"></div>
       
               <input id="login-password" type="password" name="password" placeholder="Enter your Password">
               <div id="error2"></div>
       
               <p class="forgot"><a href="">Forgot password?</a></p>
       
               <input type="submit" name="login" value="Login">
               <p class="signup">Don\'t have an account?<a href="signup.php">create new account.</a></p>
                   
           </form>
       </fieldset>
       </div>';
       if($_SERVER["REQUEST_METHOD"] === "POST"){
        $username = $_POST["username"];
        $password = $_POST["password"];
       
        //verification
        $data = $this->login($username);
        while($row = $data->fetch_assoc()){
            if($username === $row["username"] && 
                $password === $row["password"]){
               
                $_SESSION["username"] = $username;
                $_SESSION["id"] = $row["id"];
                $_SESSION["role"] = $row["role"];
                $_SESSION["profile"] = $row["profile"];
                $_SESSION["loggedin"] = TRUE;
                
                if ($_SESSION["role"] === "admin"){
                    header('Location:admin.php');
                    exit;
                }
                else{
                    header('Location:index.php');
                 
                    exit;
                }
                
            }
           
            else{
               ?><script>
                alert("Invalid Username or Password!");
               </script> <?php
            }
        }
    }
}
    public function indexView(){
       $userid = 0;
       $username  = '';
       
       $homeData = $this->getHomes();

       if(isset($_SESSION["id"])){
        $userid = $_SESSION["id"];
       }
       if(isset($_SESSION["username"])){
       $username = $_SESSION["username"];
       }

        while($homeRow = $homeData->fetch_assoc()){
            $homeid = $homeRow["id"];
            $img = $homeRow["image"];
            $title = $homeRow["title"];
            $location = $homeRow["location"];
            $price = $homeRow["price"];
    
           
            if($username){
                if(isset($_SESSION["role"])){
                   $role = $_SESSION["role"];
                   if($role === "renter"){
                    $btn = "<button class='bookbtn'><a href='book.php?home=$homeid&user=$userid'>Book Now</a></button>";
                   }
                   else if($role === "admin"){
                    $btn = "<button class='bookbtn'><a href='admin.php?home=$homeid&user=$userid'>Manage</a></button>";
                   }
                   else{
                    $btn = "";
                   }
                }

              
            }
            else{
                $btn = "<button class='bookbtn'><a href='login.php'>Book Now</a></button>";
           
            }

            echo '<div class="item">
            <img class="card-img-top" src="',$img,'" alt="',$title,'">
            <h2>',$title,'</h2>
            
            <p>Located at ',$location,'</p>
            <p>',$price,' ETB</p>
            
            <p>
                <a href="detail.php?home=',$title,'">See more</a>
            </p>

            ',$btn,'
            </div>';
        }
        return $homeData;

    }

    public function bookingView(){
        $homes = $this->getHomes();

        $checkin = $checkout = $status = "";
        $guests = 0;

       if(isset($_GET["home"])){
        $homeid = $_GET["home"];
       }
        $userid = $_GET["user"];
        
        while($home = $homes->fetch_assoc()){
           
            $title = $home["title"];
            $location = $home["location"];
            $amenities = $home["amenities"];
            $rooms = $home["rooms"];
          
            if($homeid === $home["id"]){
               echo '<div class="checkInOut">
               <fieldset>
                
               <form action="" method="POST" id="checkinout">
               <div class="center">
                 <h2 class="component">Booking ',$title,' House</h2>
                 <p>',$rooms,' rooms with ',$amenities,' and located at ',$location,'
                 </p>
                 <p>Please fillout this form to continue..</p>
               </div>

               <div>
               <label for="checkInDateInput">Checkin Date</label>
                 <input type="date" name="checkin" id="checkInDateInput" required>
               </div>

               <div>
                <label for="checkOutDate">Checkout Date</label>
                 <input type="date" name="checkout" id="checkOutDateInput" required>
               </div>

               <div>
                 <label for="guestsInput">Number of Guests</label>
                 <input min="1" max="4" name="guests" type="number" id="guestsInput" placeholder="Should be lessthan 5">
               </div>

               <br>
               <input type="submit" name="book" class="book" value="Book">
             </form>
             </fieldset>
               </div>';

             if ($_SERVER["REQUEST_METHOD"] === 'POST'){
              
                $checkin = $_POST["checkin"];
                $checkout = $_POST["checkout"];
                $guests = $_POST["guests"];
                $status = "confirm";
                $data = $this->setBooking($homeid, $userid, $guests, $checkin, $checkout, $status);
                header("Location:profile.php");
                return $data;
             }
        }
    }  
}
    
    public function addPropertiesView(){

    $userid = $_GET["user"];

           echo '<div class="login signup list-homes" id="listhomes"">
           <fieldset>
               <legend>Add Home</legend>
               <form method="POST" action="" enctype="multipart/form-data">
                     <input type="file" name="homeImg">
                   
                   <input type="text" name="title" placeholder="Title">
                  
                   <input type="text" name="desc" placeholder="Description">
                   <input type="text" name="location" placeholder="Location">
                   <input type="number" name="price" placeholder="Price">
                   
                   <input type="text" name="amenities" placeholder="Amenities">
                   <input type="number" name="rooms" placeholder="Number of rooms">
                   
                   <input type="submit" class="book" name="save" value="Save">
               </form>
           </fieldset>
                   </div>';

         if ($_SERVER["REQUEST_METHOD"] === 'POST'){
          
            $title = $_POST["title"];
            $desc = $_POST["desc"];
            $location = $_POST["location"];
            $price = $_POST["price"];
            $amenities = $_POST["amenities"];
            $rooms = $_POST["rooms"];

            if (isset($_FILES["homeImg"]))
            {
            $homeImg = $_FILES["homeImg"]["name"];
            $homeImgTmpName = $_FILES["homeImg"]["tmp_name"];

            $allowedExtension = array('jpg', 'jpeg', 'png', 'webp', 'jfif', 'PNG');
            $homeImgExtension = pathinfo($homeImg, PATHINFO_EXTENSION);

            $upload_directory = "image/home/";
            $homeImgPath = $upload_directory.$homeImg;

            if(!in_array($homeImgExtension, $allowedExtension)){
                ?>
                <script>
                    alert('Unsupported file extension!')
                </script>
                <?php
            }
           else{
            move_uploaded_file($homeImgTmpName, $homeImgPath);
            $data = $this->setHomes($userid, $homeImgPath, $title, $desc, $location, $price, $amenities, $rooms);
            header("Location: profile.php");
            return $data;
           }
         }
    }
}

    public function profileView($userid){
        $title = $description = $location = $price = $amenities = $rooms = '';
        $role = $_SESSION["role"];
       

        if($role === 'landlord'){
            $landlord = $this->getLandlordsHome($userid);
            if($landlord->num_rows > 0){
                while($row = $landlord->fetch_assoc()){
                     $homeid = $row["id"];
                     $img = $row["image"];
                     $title = $row["title"];
                     $description = $row["description"];
                     $location = $row["location"];
                     $price = $row["price"];
                     $amenities = $row["amenities"];
                     $rooms = $row["rooms"];


       echo "<div class='item'>
            <img class='card-img-top' src='$img' alt='$title'>
            <h2>$title</h2>
            
            <p>Located at $location</p>
            <p>$price ETB</p>
            
            
            <button class='editbtn'><a href='edit.php?edithome=$homeid'>Edit</a></button>
            <button class='deletbtn'><a href='delete.php?deletehome=$homeid'>Delete</a></button>
        </div>"; 
                     } 
     
            }
            else{
                echo "<h1>You haven't post any Home</h1>";
                return;
            }
        }   
        
           else if ($role === 'renter'){
            $renter = $this->getRentersHome($userid);
           if($renter->num_rows > 0){
            while($row = $renter->fetch_assoc()){
                $homeid = $row["homeid"];
                $img = $row["image"];
                 $title = $row["title"];
                 $description = $row["description"];
                 $location = $row["location"];
                 $price = $row["price"];
                 $amenities = $row["amenities"];
                 $rooms = $row["rooms"];
                 $checkin = $row["checkin"];
                 $checkout = $row["checkout"];
                 $guests = $row["guests"];
                 

        echo '<div class="item">
            <img class="card-img-top" src=',$img,' alt="',$title,'">
            <h2>',$title,'</h2>
            <p>Located at ',$location,'</p>
            <p>',$price,' ETB</p>
            
            <h5>Rented from ',$checkin,' to ',$checkout,' with ',$guests,' Guests.</h5>
          
            <a href="giveComment.php?homeid=',$homeid,'&userid=',$userid,'">Leave Comment</a>
            <br><br>
            <button class="deletbtn"><a href="delete.php?deletehome=',$homeid,'">Cancel</a></button>
        </div>'; 
            
                 }
                 
           }
           else{
            echo '<h1>You haven\'t rent any Home</h1>';
            return;
        }
     } 
        else{
            header("Location:admin.php");
            exit();
        }
      //  echo '<button style="color:white backgroundcolor:red border:1px solid red"><a href="delete.php?deleteuser=',$userid,'&homeid=',$homeid,'">Delete Account</a></button>';       
       
    }

    public function giveCommentView($homeid, $userid){
        echo '<div class="login signup">
        <fieldset>
            <legend>Give Comment</legend>
        <form method="POST" action="">
            <input type="number" name="rate" min="1" max="5" placeholder="Rate from 1 to 5">
            <input type="text" name="comment" placeholder="Leave Comment">
                
            <input type="submit" value="Submit">
        </form>
        </fieldset>
        </div>';

        $rate = 0;
        $comment = '';

        if($_SERVER["REQUEST_METHOD"] === "POST"){
            $rate = $_POST["rate"];
            $comment = $_POST["comment"];

            $data = $this->setReview($homeid, $userid, $rate, $comment);
      
            header("Location:profile.php");
            return $data;
        
    }
}


    public function homeDetail(){
      $homes = $this->getHomes();
      $homeTitle = $_GET["home"];

     while($row = $homes->fetch_assoc()){
        if($homeTitle === $row["title"]){
        $img = $row["image"];
        $title = $row["title"];
        $desc = $row["description"];
        $price = $row["price"];
        $rooms = $row["rooms"];
        $location = $row["location"];
        $total = $price * $rooms;
        $rate = 0;
        for($i=0;$i<$rooms;$i++){
            $rate++;
        }
     //    <img src="" alt="',$rate,'">
       echo '<div class="searchResult">
       <img src=',$img,' alt="">
       <img src="../images/heartIcon.png" alt="" class="searchResult__heart">
       
       <div class="searchResult__info">
           <div class="searchResult__infoTop">
               <p>',$location,'</p>
               <h2>',$title,'</h2>
               <p>_______</p>
               <p>',$desc,' with ',$rooms,' rooms</p>
           </div>
   
           <div class="searchResult__infoBottom">
               <div class="searchResult__stars">
                   <p>
                       <img src="../images/starIcon.png" alt="star">
                       <strong class="searchresult__heart strong">',$rate,'</strong>
                   </p>
               </div>
               
               <div class="searchResult__price">
                   <h2>',$price,' ETB / night</h2>
                   <p>',$total,' ETB total</p>
                  
               </div>
              
           </div>
          <a href="index.php">Back</a>
       </div>
       
     </div>


    ';
        }
       
    }

}

// admin view
    public function userMgmt(){
        echo '<h2 class="component">User Management</h2>
        <tr>
        <th>ID</th>
        <th>Role</th>
        <th>Username</th>
        <th>Email</th>
        <th>Phone</th>
        <th>Address</th>
        <th>Profile</th>
        <th>UPDATE</th>
        <th>DELETE</th>
        </tr>';
        $controller = new Controller();
        $usersData = $controller->getUserData();
        
        while($row = $usersData->fetch_assoc()){
            $id = $row["id"];
            $role = $row["role"];
            $username = $row["username"];
            $email = $row["email"];
            $phone = $row["phone"];
            $address = $row["address"];
            $profile = $row["profile"];
        
            //<img class='ppl' src='$profile' alt='$username'>
            echo "<tr>
            <td>$id</td>
            <td>$role</td>
            <td>$username</td>
            <td>$email</td>
            <td>$phone</td>
            <td>$address</td>
            <td>$profile</td>
            <td>
                <button class='editbtn'><a href='edit.php?updateuser=$id'>update</a></button>
            </td>
            <td>
                <button class='deletbtn'><a href='delete.php?deleteuser=$id'>delete</a></button>
            </td>
            </tr>";
        }

    }
    public function homeMgmt(){
        echo '<h2 class="component">Home Management</h2>
        <tr>
        <th>ID</th>
        <th>Image</th>
        <th>Title</th>
        <th>Description</th>
        <th>Price</th>
        <th>Amenities</th>
        <th>Rooms</th>
        <th>Location</th>
        <th>UPDATE</th>
        <th>DELETE</th>
        </tr>';
        $controller = new Controller();
        $homesData = $controller->getHomesData();
        
        while($row = $homesData->fetch_assoc()){
            $id = $row["id"];
            $image = $row["image"];
            $title = $row["title"];
            $description = $row["description"];
            $price = $row["price"];
            $amenities = $row["amenities"];
            $rooms = $row["rooms"];
            $location = $row["location"];
        
           // <img class='ppl' src='$image' alt='$title'>
            echo "<tr>
            <td>$id</td>
            <td>$image</td>
            <td>$title</td>
            <td>$description</td>
            <td>$price</td>
            <td>$amenities</td>
            <td>$rooms</td>
            <td>$location</td>
            <td>
            <button class='editbtn'><a href='edit.php?updatehome=$id'>update</a></button>
            </td>
            <td>
            <button class='deletbtn'><a href='delete.php?deletehome=$id'>delete</a></button>
            </td>
            </tr>";
        }

    }
    public function bookingMgmt(){
        echo '<h2 class="component">Booking Management</h2>
        <tr>
            <th>ID</th>
            <th>Home ID</th>
            <th>User ID</th>
            <th>Guests</th>
            <th>Checkin</th>
            <th>Checkout</th>
            <th>UPDATE</th>
            <th>DELETE</th>
        </tr>';
        $controller = new Controller();
        $bookingData = $controller->getBookingData();
        
        while($row = $bookingData->fetch_assoc()){
            $id = $row["id"];
            $homeid = $row["homeid"];
            $userid = $row["userid"];
            $guests = $row["guests"];
            $checkin = $row["checkin"];
            $checkout = $row["checkout"];
        
            echo "<tr>
            <td>$id</td>
            <td>$homeid</td>
            <td>$userid</th>
            <td>$guests</td>
            <td>$checkin</td>
            <td>$checkout</td>
            <td>
            <button class='editbtn'><a href='edit.php?updatebook=$id'>update</a></button>
            </td>
            <td>
            <button class='deletbtn'><a href='delete.php?deletebook=$id'>delete</a></button>
            </td>
            </tr>";
        }

    }

    /*
    public function reviewMgmt($userid, $homeid){
        
        echo '<h2 class="component">Review Management</h2>
        <tr><th>ID</th>
        
        <th>Home ID</th>
        <th>User ID</th>
        <th>Rate</th>
        <th>Comment</th>
        <th>UPDATE</th>
        <th>DELETE</th>
        </tr>';
        $controller = new Controller();
       // $reviewData = $controller->getReviewData($userid, $homeid);
        
        while($row = $reviewData->fetch_assoc()){
            $id = $row["id"];
            $homeid = $row["homeid"];
            $userid = $row["userid"];
            $rate = $row["rate"];
            $comment = $row["comment"];
        
            echo "<tr>
            <th>$id</th>
            <th>$homeid</th>
            <th>$userid</th>
            <th>$rate</th>
            <th>$comment</th>
            <th><a href='edit.php?updatereview=$id'>update</a></th>
            <th><a href='delete.php?updatereview=$id'>delete</a></th>
            </tr>";
        }

    }
    */

    public function deleteHomeView($id, $role){
        $delete = $this->deleteHome($id, $role);
        return $delete;
}
    
    public function deleteUserView($id){
        $delete = $this->deleteUser($id);
        return $delete;
}

    public function deleteBookView($id){
        $delete = $this->deleteBooking($id);
        return $delete;
}
    
     public function editUserView($id){
        $username = $email = $address = '';
        $phone = 0;
        $retrieveUserInfo = $this->getUser($id);
        while($row = $retrieveUserInfo->fetch_assoc()){
            $username = $row["username"];
            $email = $row["email"];
            $phone = $row["phone"];
            $address = $row["address"];
            $role = $row["role"];
            }

        echo   '<div class="login signup">
                    <fieldset>
                        <legend>Edit Your Profile</legend>
                        <form method="POST" action="">
                        <input type="text" name="newUsername" value="',$username,'">
                        <div id="error"></div>
                        <input type="email" name="newEmail" value="',$email,'">
                        <input type="number" name="newPhone" value="',$phone,'">
                        <input type="text" name="newAddress" value="',$address,'">
           
                        <input type="submit" name="login" value="Update"> 
   
                        </form>
                    </fieldset>
                </div>
                ';

            $newUsername = $newEmail = $newAddress = '';
            $newPhone = 0;

            if($_SERVER["REQUEST_METHOD"] === "POST"){
                $newUsername = $_POST["newUsername"];
                $newEmail = $_POST["newEmail"];
                $newPhone = $_POST["newPhone"];
                $newAddress = $_POST["newAddress"];

                $this->editUser($id, $newUsername, $newEmail, $newPhone, $newAddress);
               
                
                if ($role === 'renter' || $role === 'landlord'){
             
                    $_SESSION["username"] = $newUsername;
                    header("Location:profile.php");
                }
                else if ($role === 'admin'){
                    
                    $_SESSION["username"] = $username;
                    header("Location:admin.php");
                }
               
            }
    }
    
    public function editHomeView($id){
        $title = $desc = $location = $amenities = '';
        $price = $rooms = 0;
        $homeInfo = $this->getHome($id);
        
        while($row = $homeInfo->fetch_assoc()){
            $title = $row["title"];
            $desc = $row["description"];
            $location = $row["location"];
            $amenities = $row["amenities"];
            $price = $row["price"];
            $rooms = $row["rooms"];
        
            }
       echo   '<div class="login signup">
                    <fieldset>
                        <legend>Edit Home Details</legend>
                        <form method="POST" action="">
                        <input type="text" name="newTitle" value="',$title,'">
                        <div id="error"></div>
                        <input type="text" name="newDesc" value="',$desc,'">
                        <input type="text" name="newLocation" value="',$location,'">
                        <input type="number" name="newPrice" value="',$price,'">
                        <input type="text" name="newAmenities" value="',$amenities,'">
                        <input type="number" name="newRooms" value="',$rooms,'">
                       
                        <input type="submit" name="login" value="Update"> 
   
                        </form>
                    </fieldset>
                </div>
                ';

            $newTitle = $newDesc = $newLocation  = $newAmenities = '';
            $newPrice = $newRooms = 0;

            if($_SERVER["REQUEST_METHOD"] === "POST"){
                $newTitle = $_POST["newTitle"];
                $newDesc = $_POST["newDesc"];
                $newLocation = $_POST["newLocation"];
                $newPrice = $_POST["newPrice"];
                $newAmenities = $_POST["newAmenities"];
                $newRooms = $_POST["newRooms"];

                $this->editHome($id, $newTitle, $newDesc, $newLocation, $newPrice, $newAmenities, $newRooms);

                header("Location:profile.php");
            
            }
    }

    public function editBookView($id){
        $checkin = $checkout  = '';
        $guests = 0;
        $retrieveUserInfo = $this->getBooking($id);
        while($row = $retrieveUserInfo->fetch_assoc()){
            $checkin = $row["checkin"];
            $checkout = $row["checkout"];
            $guests = $row["guests"];
            }

        echo   '<div class="login signup">
                    <fieldset>
                        <legend>Edit Booking Info</legend>
                        <form method="POST" action="">
                        <input type="date" name="newCheckin" value="',$checkin,'">
                        <div id="error"></div>
                        <input type="date" name="newCheckout" value="',$checkout,'">
                        <input type="number" name="newGuests" value="',$guests,'">
           
                        <input type="submit" name="login" value="Update"> 
   
                        </form>
                    </fieldset>
                </div>
                ';

            $newCheckin = $newCheckout = '';
            $newGuests = 0;

            if($_SERVER["REQUEST_METHOD"] === "POST"){
                $newCheckin = $_POST["newCheckin"];
                $newCheckout = $_POST["newCheckout"];
                $newGuests = $_POST["newGuests"];

                $this->editBooking($id, $newCheckin, $newCheckout, $newGuests);
               
                    
                    header("Location:admin.php");
              
               
            }
    }
  
}
?>