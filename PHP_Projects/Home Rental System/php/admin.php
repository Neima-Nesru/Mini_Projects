<?php
 /*
user mgmt
home mgmt 
book mgmt 
reviews and rating
security controls
 */
require 'header.php';
$view = new View();
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin</title>
    <!-- <link rel="stylesheet" type="text/css" href="../css/styles.css"> -->
</head>
<body>

<!-- usr mgmt -->
<table border="1">
    
<?php
$view->userMgmt();
?>

</table>

<!-- home mgmt -->
<table border="1">

<?php
$view->homeMgmt();

?>

</table>

<!-- booking mgmt -->
<table border="1">

<?php
$view->bookingMgmt();

?>

</table>

<!-- review mgmt -->
<table border="1">

<?php
$userid = $homeid = '';
//$view->reviewMgmt($userid, $homeid);

?>

</table>

   <script>
    alert("<?php echo $message; ?>")
   </script>
</body>
</html>

<!-- 
    index.php
    homes.php(to insert home detail) -> insert update delete (all 3 are d/t pages
    
    how to host,how to do mvc model)

 -->