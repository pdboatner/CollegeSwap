#!/usr/bin/perl

use strict;
use warnings;

my $p = '/st1/cppopovich/Programming/CS495/';
my $f = 'account.csv';
my $name = $ARGV[0];
my $pass = $ARGV[1];

open my $FILE, '<', $p.$f or die $!."\n";
my @n = map {s/\s+$//;$_} split(/,/,<$FILE>);
my $n_index = -1;
my $p_index = -1;
for my $i (0 .. $#n){
    if($n[$i] eq 'name'){
        $n_index = $i;
    }
    if($n[$i] eq 'password'){
        $p_index = $i;
    }
}
my @m;
while ($_ = <$FILE>){
    @m = map {s/\s+$//;$_} split(/,/);
    if($m[$n_index] eq $name){
        if($m[$p_index] eq $pass){
            close($FILE);
            print 'success';
            exit;
        }
    }
}
close($FILE);
print 'failure';
