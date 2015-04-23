<?php
$script = "/st1/cppopovich/Programming/CS495/col.pl";

$tbl = $_POST["tbl"];
$col = $_POST["col"];

$tbl = escapeshellarg($tbl);
$col = escapeshellarg($col);

$result = `perl $script $tbl $col`;
echo $result;
?>
