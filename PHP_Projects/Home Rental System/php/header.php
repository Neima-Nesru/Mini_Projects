<?php
session_start();

include 'includes/includes.inc.php';

   
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="../css/styles.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css" />
    <title>Home Rental System</title>
</head>
<body>
<header>
  
<img class="logo" src="image/logo.png" alt="HRS">
<nav class="header__right homePage">
<ul>
    <li><a href="index.php">Home</a></li>
    <li><a href="about.php">About</a></li>
    <?php 
     $btn = '';
    if(isset($_SESSION["loggedin"])){
        $uname = $_SESSION["username"];
        $userid = $_SESSION["id"];
        $role = $_SESSION["role"];
       

        $userlink = "<li><a href='profile.php?id=$userid&username=$uname'>$uname</a></li>";
       
        echo $userlink,'<li><a href="logout.php">Logout</a></li>';
      
        if($role === 'renter'){
            $btn = "<button class='dynamicbtn'><a href='index.php?user=$userid#rent'>Rent Now</a></button>";
           // echo ' <li><a href="renter.php">R</a></li>';
        }
        else if($role === 'landlord'){
            $btn = "<button class='dynamicbtn'><a href='addprops.php?user=$userid'>Add Properties</a></button>";
           // echo ' <li><a href="landlord.php">L</a></li>',$btn;
        }
        else if($role === 'admin'){
            $btn = "<button class='dynamicbtn'><a href='admin.php?user=$userid'>Admin</a></button>";
        }
    }
    // <li><a href="#testimonials">Reviews</a></li>
    //<li><a href="#services">Features</a></li>
    else{
        echo '
        <li><a href="login.php">Login</a></li>
        <li><a href="signup.php">Signup</a></li>';
    }
    ?>
 <li><?php echo $btn;?></li>
       
</ul>
 </nav>
 <!-- <h1>X</h1>   -->
</header>

