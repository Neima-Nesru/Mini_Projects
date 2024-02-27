<?php
$to = 'neimanesru34@gmail.com';
$subject = 'Test';
$msg = 'This is to test if email sending works';
$header = 'From: me@example.com';

if(mail($to, $subject, $msg, $header)){
    echo 'Email sent!';
}
else{
    echo 'Error occurred!';
}
?>