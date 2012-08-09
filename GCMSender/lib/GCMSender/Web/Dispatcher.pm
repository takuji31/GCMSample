package GCMSender::Web::Dispatcher;
use strict;
use warnings;
use utf8;
use Amon2::Web::Dispatcher::Lite;
use 5.014_001;

use Config::Pit;
use JSON;
use WWW::Google::Cloud::Messaging;

my $REGISTER_ID = "APA91bEsBEGJe_qs0ukK0EckRGW54hAf5LciXOgxwjeZRQovnl75VhwFtCsB-egtsox0XqJCXxBTMwHanWMdIp26EqHEBJeExT98CCX4BUb1fchTLTBXWx4DGeg13ArkxGCq3mvlFnB3US4fI-1ybcst3Aps1Kwp0g";

my $CONFIG = pit_get('gcmsample', require => {sender_id => 'GCM Sender id', api_key => 'Server API key'});
my $gcm = WWW::Google::Cloud::Messaging->new(api_key => $CONFIG->{api_key});

get '/' => sub {
    my ($c) = @_;
    $c->render('index.tt');
};

post '/send' => sub {
    my ($c, ) = @_;
    my $title = $c->req->param('title');
    my $msg = $c->req->param('message');

    if (defined $msg) {
        my $res = $gcm->send(
            {
                registration_ids => [$REGISTER_ID],
                data => {
                    title => "$title",
                    message => "$msg",
                },
            }
        );
        die $res->error unless $res->is_success;

        my $results = $res->results;
        while (my $result = $results->next) {
            my $reg_id = $result->target_reg_id;
            if ($result->is_success) {
                say sprintf 'message_id: %s, reg_id: %s',
                    $result->message_id, $reg_id;
            }
            else {
                warn sprintf 'error: %s, reg_id: %s',
                    $result->error, $reg_id;
            }

            if ($result->has_canonical_id) {
                say sprintf 'reg_id %s is old! refreshed reg_id is %s',
                    $reg_id, $result->registration_id;
            }
        }
    }
    $c->redirect('/');
};

1;
