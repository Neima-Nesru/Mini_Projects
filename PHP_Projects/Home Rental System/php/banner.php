<?php
    if(isset($_SESSION["loggedin"])){
        $username = $_SESSION["username"];
    }
    else{
        $username = "to HRS";
        echo '<div class="banner">
        <div class="banner__info">
        <h1>
            Get out and stretch your imagination
        </h1>

        <h5>
            Plan a different kind of gateway to uncover the hidden gems near you.
        </h5>

        <button><a href="login.php">Get started</a></button>
    </div>
        </div>';
    }
 ?>
<h2 class="component">Welcome <?php echo $username; ?></h2>
<p class="section-description">
      Welcome to HRS, the Ethipia's first online marketplace for lodging, homestays, and vacation rentals! <br>
      Our mission is to create a world where you can belong anywhere, and our platform allows you to easily 
      discover and book unique accommodations all around the globe.
</p>