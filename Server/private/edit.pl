#!/usr/bin/perl

use strict;
use warnings;

my $tbl = $ARGV[0];
my $key = $ARGV[1];
my $args = $ARGV[2];
my %newH = grep {/./} map {s/\s+$//;$_} split(/\|/,$args);
my @m;
my $p = '/st1/cppopovich/Programming/CS495/';

open my $in, '<', $p.$tbl.'.csv' or die $!."\n";
open my $out,'>', $p.$tbl.'.csv.new' or die $!."\n";
my $line = <$in>;
print {$out} $line;
my @n = map {s/\s+//;$_} split(/,/,$line);
while ($line = <$in>){
    @m = map {s/\s+//;$_} split(/,/, $line);
    my %h;
    for my $i (0 .. $#m){
        $h{$n[$i]}=$m[$i];
    }
    for my $i (@n){
        if(! defined $h{$i}){
            $h{$i} = '';
        }
    }
    if($tbl eq "account"){
        if($h{"name"} eq $key){
            for my $k (keys %newH){
                $h{$k} = $newH{$k};
            }
            print {$out} join(',',@h{@n})."\n";
            next;
        }
    }else{
        if($h{"id"} == $key){
            for my $k (keys %newH){
                $h{$k} = $newH{$k};
            }
            print {$out} join(',',@h{@n})."\n";
            next;
        }
    }
    print {$out} $line;
}
close($in);
close($out);

open my $inN, '<', $p.$tbl.'.csv.new' or die $!."\n";
open my $outN,'>', $p.$tbl.'.csv' or die $!."\n";
for (<$inN>){
    print {$outN} $_;
}
close($inN);
close($outN);
