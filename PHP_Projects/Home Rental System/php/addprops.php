<?php 
require 'header.php';
$view = new View();

$addpropsMsg = $view->addPropertiesView();
?>

<script>
    if($addpropsMsg !== ""){
        alert("<?php echo $addpropsMsg ;?>");
    }
  
</script>

<?php
// require 'footer.php';
?>