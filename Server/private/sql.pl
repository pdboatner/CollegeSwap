#!/usr/bin/perl

use strict;
use warnings;

my %T = ('account'=>[],'textbook'=>[]);
my %N = ('account'=>[],'textbook'=>[]);
my @m;
my $p = '/st1/cppopovich/Programming/CS495/';
for my $f ('account','textbook'){;
    open my $FILE, '<', $p.$f.'.csv' or die $!."\n";
    my @n = map {s/\s+//;$_} split(/,/,<$FILE>);
    @{$N{$f}} = @n;
    while ($_ = <$FILE>){
        @m = map {s/\s+//;$_} split(/,/);
        my %h;
        for my $i (0 .. $#m){
            $h{$n[$i]}=$m[$i];
        }
        push(@{$T{$f}},\%h);
    }
    close($FILE);
}

my @whr;

sub where{
    my ($h_ref) = @_;
    my %h = %$h_ref;
    my @items = (0,0);
    for my $i (@whr){
        @items = split /[=><]/, $i;
        $items[0] = $h{$items[0]};
        if($items[0] ne $items[1]){
            return 0;
        }
    }
    return 1;
}

sub SQL{
    my ($tbl,$q) = @_;
    @whr = grep {/./} split /,/, $q;
    my @tbl = @{$T{$tbl}};
    my @result = ();
    for my $h_ref (@tbl){
        if(where($h_ref)){
            push @result, $h_ref;
        }
    }

    print '{"'.$tbl.'":[';
    for my $i (0 .. $#result){
        if($i>0){print ','};
        print '{';
        my %h = %{$result[$i]};
        my @k = @{$N{$tbl}};
        for my $j (0 .. $#k){
            if($j>0){print ','};
            print '"'.$k[$j].'":"'.$h{$k[$j]}.'"';
        }
        print '}';
    }
    print ']}';
    return;
}

my $tbl = $ARGV[0];
my $query = $ARGV[1];
SQL($tbl,$query);
