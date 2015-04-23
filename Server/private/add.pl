#!/usr/bin/perl

use strict;
use warnings;

my $p = '/st1/cppopovich/Programming/CS495/';
my $tbl = $ARGV[0];
my $args = $ARGV[1];
$args =~ s/,//g;
my %h = grep {/./} map {s/\s+$//;$_} split(/\|/, $args);
open my $FILE, '+>>', $p.$tbl.'.csv' or die $!."\n";
my @n = map {s/\s+$//;$_} split /,/, <$FILE>;
if($tbl ne "account"){
    my $id = 1;
    while (<$FILE>){
        if(/\S/){
            ($id) = split /,/;
        }
    }
    $h{"id"} = $id + 1;
    $h{"del"} = 0;
}
for my $key (@n){
    if(!defined $h{$key}){
        $h{$key} = '';
    }
}
print {$FILE} join(',',@h{@n});
print {$FILE} "\n";
close($FILE);
