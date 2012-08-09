use ExtUtils::MakeMaker;

WriteMakefile(
    NAME          => 'GCMSender',
    AUTHOR        => 'Some Person <person@example.com>',
    VERSION_FROM  => 'lib/GCMSender.pm',
    PREREQ_PM     => {
        'Amon2'                           => '3.51',
        'Text::Xslate'                    => '1.5006',
        'Test::More'                      => '0.98',
        'HTML::FillInForm::Lite'          => '1.09',
        'JSON'                            => '2.50',
        'Plack::Middleware::ReverseProxy' => '0.09',
        'Test::WWW::Mechanize::PSGI'      => '0',
        'Config::Pit'                     => '0.04',
        'WWW::Google::Cloud::Messaging'   => '0.01',
        'LWP::UserAgent'                  => '0',
        'LWP::Protocol::https'            => '0',
    },
    MIN_PERL_VERSION => '5.008001',
    (-d 'xt' and $ENV{AUTOMATED_TESTING} || $ENV{RELEASE_TESTING}) ? (
        test => {
            TESTS => 't/*.t xt/*.t',
        },
    ) : (),
);
