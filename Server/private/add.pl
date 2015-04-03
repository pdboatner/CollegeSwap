#!/usr/bin/perl

use strict;
use warnings;

my $p = '/st1/cppopovich/Programming/CS495/';
my $tbl = $ARGV[0];
my $args = $ARGV[1];
my %h = map {s/\s+$//;$_} split /|/, $args;
open my $FILE, '+>>', $p.$tbl.'.csv' or die $!."\n";
my @n = map {s/\s+$//;$_} split /,/, <FILE>;
print $FILE, join(',',@h{@n});
print $FILE, "\n";
close($FILE);

SQL($tbl,$query);
