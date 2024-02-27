<?php 
require 'header.php';
$view = new View();
$signupMsg = $view->signupView();
?>

<script>
    if($signupMsg !== ""){
        alert("<?php echo $signupMsg ;?>");
    }
  
</script>

<?php
require 'footer.php';
?>