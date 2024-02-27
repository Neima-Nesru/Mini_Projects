<?php 
require 'header.php';
$view = new View();

$username = $_SESSION["username"];
$userid = $_SESSION["id"];
$role = $_SESSION["role"];
$profile = $_SESSION["profile"];

if($role === "renter"){
    $tag = "Renter";
}
else if($role === "landlord"){
    $tag = "Landlord";
}

?>

<div class="profile-header">
    <header>
        <div class="pro-pic">
            <img class="pro-pic" src="<?php echo $profile?>" alt="profile-pic">
        </div>

        <div>
            <h2><?php echo $username ?> - <?php echo $tag ?></h2>
            <a href="edit.php?edituser=<?php echo $userid ?>">Edit Profile</a> 
            | 
            <a style="color: red;" href="delete.php?userid=<?php echo $userid ?>">Delete Account</a>   
        </div>
    </header>
</div>

<div class="gallery">
<?php 
   $view->profileView($userid);
?>
</div>

<script>
    if($profileMsg !== ""){
        alert("<?php echo $profileMsg ;?>");
    }
</script>