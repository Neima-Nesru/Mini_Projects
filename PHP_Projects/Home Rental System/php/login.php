<?php 
require 'header.php';
$view = new View();
$loginMsg = $view->loginView();
?>

<script>
    if($loginMsg !== ""){
        alert("<?php echo $loginMsg ;?>");
    }
  
</script>

<?php
require 'footer.php';
?>