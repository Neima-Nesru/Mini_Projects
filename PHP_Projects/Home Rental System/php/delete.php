<?php
include 'includes/includes.inc.php';
session_start();

$view = new View();
$role = $_SESSION["role"];

if (isset($_GET['deletehome'])){
    $homeid = $_GET['deletehome'];
    
    $result = $view->deleteHomeView($homeid, $role);
    
    $msg = "Home Deleted Successfully";
    if($role === 'renter' || $role === 'landlord'){
        header('Location: profile.php');
    }
    else if($role === 'admin'){
        header('Location: admin.php');
    }
}
else if (isset($_GET["deleteuser"])){
    $userid = $_GET["deleteuser"];

    $result = $view->deleteUserView($userid);
    $msg = "user Deleted Successfully";

    header('Location: admin.php');
}

else if (isset($_GET["updatebook"])){
    $bookid = $_GET["updatebook"];

    $result = $view->editBookView($bookid);
    $msg = "Booking updated Successfully";

    header('Location: admin.php');
}

else if (isset($_GET["deletebook"])){
    $bookid = $_GET["deletebook"];

    $result = $view->deleteBookView($bookid);
    $msg = "Booking deleted Successfully";

    header('Location: admin.php');
}

else if(isset($_GET["userid"])){
    $userid = $_GET["userid"];

    $result = $view->deleteUserView($userid);
    
    $msg = "Account deleted Successfully";

    session_destroy();
    header("Location: login.php");

}

else{
    echo 'can\'t perform  delete';
}
?>
<script language='javascript'>

alert('<?php echo $msg; ?>');
</script>