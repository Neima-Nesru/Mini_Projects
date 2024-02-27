<?php
require 'header.php';
$view = new View();

if(isset($_GET["homeid"]) && isset($_GET["userid"])){
    $homeid = $_GET["homeid"];
    $userid = $_GET["userid"];

    $view->giveCommentView($homeid, $userid);

}
?>

