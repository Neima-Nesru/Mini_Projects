<?php

require 'header.php';
require 'banner.php';
require 'search.php';

$view = new View();

?>

<div class="gallery" id="rent">
    
<?php 
    $view->indexView();
?>

</div>

<?php

//require 'additionalInfo.php';
require 'footer.php';

?>