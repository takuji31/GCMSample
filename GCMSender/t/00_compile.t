use strict;
use warnings;
use utf8;
use Test::More;

use_ok $_ for qw(
    GCMSender
    GCMSender::Web
    GCMSender::Web::Dispatcher
);

done_testing;
