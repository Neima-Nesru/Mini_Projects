<?php
require 'header.php';
$view = new View();
/*
select * from landlord 
fill the values of input with the existing value inth db 
edit and submit 
*/

// for user
if(isset($_GET['edituser'])){
    $id = $_GET['edituser'];
    $view->editUserView($id);
}

else if(isset($_GET['edithome'])){
    $id = $_GET['edithome'];
    $view->editHomeView($id);
    
}

//for admin
else if(isset($_GET['updateuser'])){
    $id = $_GET['updateuser'];
    $view->editUserView($id);
}

else if(isset($_GET['updatebook'])){
    $id = $_GET['updatebook'];
    $view->editBookView($id);
}

else if(isset($_GET['updatehome'])){
    $id = $_GET['updatehome'];
    $view->editHomeView($id);
}

else if(isset($_GET['profilepic'])){
    $id = $_GET['profilepic'];
    //$view->editProPicView($id);
}
?>
