function statusChangeCallback(response) {
    console.log('statusChangeCallback');
    console.log(response);
    if (response.status === 'connected') {
        FB.api('/me', function(response) {
            // var fbname = $();
            // fbname.val(response.name);
            $('#logged-name').html(response.name);
            console.log(response);
        });

        $('#user-logged').css('display', 'block');
        $('#user-author').css('display', 'none');
        $('#admin-logged').css('display', 'none');
    } else {
        $('#user-logged').css('display', 'none');
        $('#user-author').css('display', 'block');
        $('#admin-logged').css('display', 'none');

        window.location.href = "#/home";
    }
}

function checkLoginState() {
    FB.getLoginStatus(function(response) {
        statusChangeCallback(response);
    });
}

function out() {
    FB.logout(function(response) {
        statusChangeCallback(response);
    });
}

window.fbAsyncInit = function() {
    FB.init({
        appId: '1125397304274954',
        autoLogAppEvents: true,
        xfbml: true,
        version: 'v3.0'
    });

    FB.getLoginStatus(function(response) {
        statusChangeCallback(response);
    });

    FB.AppEvents.logPageView();

};

(function(d, s, id) {
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id)) {
        return;
    }
    js = d.createElement(s);
    js.id = id;
    //                js.src = "https://connect.facebook.net/en_US/sdk.js";
    js.src = 'https://connect.facebook.net/en_US/sdk.js#xfbml=1&version=v3.0&appId=1125397304274954&autoLogAppEvents=1'
    fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));

FB.login(function(response) {
    if (response.authResponse) {
        console.log('Welcome!  Fetching your information.... ');
        FB.api('/me', function(response) {
            console.log('Good to see you, ' + response.name + '.');
        });
    } else {
        console.log('User cancelled login or did not fully authorize.');
    }
});

function testAPI() {
    console.log('Welcome!  Fetching your information.... ');
    FB.api('/me', function(response) {
        console.log('Successful login for: ' + response.name);
    });
}