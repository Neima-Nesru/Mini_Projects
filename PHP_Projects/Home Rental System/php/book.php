<?php 
require 'header.php';
$view = new View();
$bkMsg = $view->bookingView();
?>

<script>
    if($bkMsg !== ""){
        alert("<?php echo $bkMsg ;?>");
    }
  
</script>

<?php
//require 'footer.php';
?>