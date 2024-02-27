<?php
session_start();
if(isset($_SESSION["loggedin"])){
    header('Location: login.php');

    $_SESSION["loggedin"] = false;
    session_destroy();
    exit;
}
?>