$(window).on('scroll', function() {
    if (document.documentElement.scrollTop > 50) {
        $('.navbar').addClass('new-navbar');
        $('#tigi-logo').innerHtml('<h3>TiGi</h3>');
        $('.navbar').addClass('fixed-top');
    } else {
        $('.navbar').removeClass('new-navbar');
        $('#tigi-logo').innerHtml('<h1>TodayIGotIt</h1>');
        $('.navbar').removeClass('fixed-top');
    }
});

$(window).on('reset', function() {
    alert('reset');
})