<?php
$script = "/st1/cppopovich/Programming/CS495/sql.pl";

//TODO remove defaults?
if(isset($_POST["tbl"])){
$tbl = $_POST["tbl"];
$args = $_POST["args"];
//$sort =
}else{
$tbl = 'textbook';
$args = 'subject=CS';
}

$result = `perl $script $tbl $args`;
echo $result;
?>
