#!/usr/bin/perl

use strict;
use warnings;

my @tables = ('account','textbook','ticket','sublease');
my %T;
for (@tables){
    $T{$_} = [];
}
my %N;
for (@tables){
    $N{$_} = [];
}
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
        if($i =~ /=/){
            return 0 if $items[0] ne $items[1];
        }elsif($i =~ />/){
            return 0 if !($items[0] > $items[1]);
        }elsif($i =~ /</){
            return 0 if !($items[0] < $items[1]);
        }
    }
    return 1;
}

sub cmpNS{
    my ($a,$b) = @_;
    if($a=~/^-?\d+\.?\d*$/ && $b=~/^-?\d+\.?\d*$/){
        return $a <=> $b;
    }else{
        return $a cmp $b;
    }
}

sub SQL{
    my ($tbl,$q,$sortMethod) = @_;
    @whr = grep {/./} split /,/, $q;
    my @tbl = @{$T{$tbl}};
    my @result = ();
    for my $h_ref (@tbl){
        if(where($h_ref)){
            push @result, $h_ref;
        }
    }
    $sortMethod=~m/^[+-](.*)$/;
    my $s = $1;
    if($sortMethod =~ /^\+/){
        @result = sort {cmpNS($a->{$s},$b->{$s})} @result;
    }else{
        @result = sort {cmpNS($b->{$s},$a->{$s})} @result;
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
my $sortMethod = $ARGV[2];
SQL($tbl,$query,$sortMethod);
