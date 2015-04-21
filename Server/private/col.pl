#!/usr/bin/perl

use strict;
use warnings;

my $p = '/st1/cppopovich/Programming/CS495/';

my $tbl = $ARGV[0];
my $col = $ARGV[1];
my @result = ();
open my $FILE, '<', $p.$tbl.'.csv' or die $!."\n";
my @n = map {s/\s+//;$_} split(/,/,<$FILE>);
my ($index) = grep {$n[$_] eq $col} (0 .. $#n);
my @m;
while ($_ = <$FILE>){
    @m = map {s/\s+//;$_} split(/,/);
    push(@result, $m[$index]);
}
close($FILE);

sub cmpNS{
    my ($a,$b) = @_;
    if($a=~/^-?\d+\.?\d*$/ && $b=~/^-?\d+\.?\d*$/){
        return $a <=> $b;
    }else{
        return $a cmp $b;
    }
}

@result = do{ my %seen; grep { !$seen{$_}++ } @result };
@result = sort {cmpNS($a,$b)} @result;
print(join(',',@result));
