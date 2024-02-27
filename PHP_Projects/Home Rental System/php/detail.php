<?php 
require 'header.php';
$view = new View();
$homeDtlMsg = $view->homeDetail();
?>

<script>
    if($homeDtlMsg !== ""){
        alert("<?php echo $homeDtlMsg ;?>");
    }
 
</script>

<?php
require 'footer.php';
?>