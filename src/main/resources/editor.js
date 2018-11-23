console.log('Initializing Hyperselenium Editor ...');

var editor = {};
var websiteUUID;
var baseUrl;


// Never remove this line of comment!
//SETVARIABLES

editor.init = function () {

    let h = '<div id="editorBar" style="position: absolute; width: 100%; bottom: 100px; color: white; background-color: #4e555b"><b>EDITOR</b><p><button>TEST</button></p></div>' +
        '<header style="color:white"><navbar class="navbar navbar-expand-md navbar-dark fixed-bottom bg-dark" id="hyperseleniumEditor">'+
        '<a class="navbar-brand">HyperSelenium Editor</a> ' +
        '<div class="navbar-collapse">' +
        '<ul class="navbar-nav mr-auto">' +
        '<li class="nav-item active"><a id="buttonCloseWebsite" class="nav-link" href="#">Close</a></li>' +
        '</ul></div></navbar></header>';
    $('body').append(h);

    $('#buttonCloseWebsite').click(function () {

        let website = { WebSite:{uuid: websiteUUID}};
        let websiteData = JSON.stringify(website);

        let request = $.ajax({
            url: baseUrl + "closeWebsite",
            type: 'POST',
            data: websiteData,
            dataType: "json",
            processData: false,
            contentType : 'application/json'
        });

        request.done(function( data ) {
            console.log(data);
        });

        request.fail(function( jqXHR, textStatus ) {
            alert( "Request failed: " + textStatus );
        });
    });


};

editor.checkJquery = function () {
    // check if jquery has been loaded and load it if false
    if (!window.jQuery) {
        console.log('No jquery found. I will load my own into the page!');
        editor.loadJS('https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js');

        for (let i = 0; i<100; i++) {
            if (window.jQuery) break;
            editor.sleep(100);
        }
    }
};

editor.sleep = function(milliseconds) {
    let start = new Date().getTime();
    for (let i = 0; i < 1e7; i++) {
        if ((new Date().getTime() - start) > milliseconds){
            break;
        }
    }
};

editor.loadJS = function (url) {
    let script = document.createElement('script');
    script.type = "text/javascript";
    script.src = url;
    (document.getElementsByTagName('head')[0] || document.getElementsByTagName('script')[0]).appendChild(script);
};

editor.loadCss = function (url) {
    let head = document.getElementsByTagName('head')[0];
    let link = document.createElement('link');
    link.rel = 'stylesheet';
    link.type = 'text/css';
    link.href = url;
    link.media = 'all';
    head.appendChild(link);
};

editor.loadCss('https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css');
editor.checkJquery();
editor.init();