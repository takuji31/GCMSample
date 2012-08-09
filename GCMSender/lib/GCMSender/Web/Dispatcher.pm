package GCMSender::Web::Dispatcher;
use strict;
use warnings;
use utf8;
use Amon2::Web::Dispatcher::Lite;

use Config::Pit;

my $REGISTER_ID = "APA91bEsBEGJe_qs0ukK0EckRGW54hAf5LciXOgxwjeZRQovnl75VhwFtCsB-egtsox0XqJCXxBTMwHanWMdIp26EqHEBJeExT98CCX4BUb1fchTLTBXWx4DGeg13ArkxGCq3mvlFnB3US4fI-1ybcst3Aps1Kwp0g";

my $SENDER_ID = pit_get('gcmsample', require => {sender_id => 'GCM Sender id'});

get '/' => sub {
    my ($c) = @_;
    $c->render('index.tt');
};

post '/' => sub {
    my ($c, ) = @_;
    
    $c->render('index.tt');
};

1;
