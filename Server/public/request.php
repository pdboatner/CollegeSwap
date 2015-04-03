<?php
$script = "/st1/cppopovich/Programming/CS495/sql.pl";

//TODO remove defaults?
if(isset($_POST["tbl"])){
$tbl = $_POST["tbl"];
$args = $_POST["args"];
$sort = $_POST["sort"];
}else{
$tbl = 'textbook';
$args = 'subject=CS';
$sort = '';
}

$tbl = escapeshellarg($tbl);
$args = escapeshellarg($args);
$sort = escapeshellarg($sort);

$result = `perl $script $tbl $args $sort`;
echo $result;
?>
