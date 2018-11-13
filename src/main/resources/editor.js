console.log('Initializing Hyperselenium Editor ...');

var editor = {};
var websiteId;
var baseUrl;

// Never remove this line of comment!
//SETVARIABLES

editor.init = function () {

    var h = '<div id="hyperseleniumEditor" style="position: absolute; bottom: 100px; left: 10px">' +
        '<button id="buttonCloseWebsite" class="btn btn-danger">Close</button>' +
        '</div>';
    $('div').last().append(h);
    
    $('#buttonCloseWebsite').click(function () {
        $.ajax({
            url: baseUrl + "/closeWebsite/" + websiteId
        })
    });
};

editor.checkJquery = function () {
    // check if jquery has been loaded and load it if false
    if (!window.jQuery) {
        console.log('No jquery found. I will load my own into the page!');
        editor.loadJS('https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js');
    }
}

editor.loadJS = function (url) {
    var script = document.createElement('script');
    script.type = "text/javascript";
    script.src = url;
    (document.getElementsByTagName('head')[0] || document.getElementsByTagName('script')[0]).appendChild(script);
}

editor.loadCss = function (url) {
    var head = document.getElementsByTagName('head')[0];
    var link = document.createElement('link');
    link.rel = 'stylesheet';
    link.type = 'text/css';
    link.href = url;
    link.media = 'all';
    head.appendChild(link);
}

editor.checkJquery();
//editor.loadJS('https://code.jquery.com/jquery-3.3.1.slim.min.js');
//editor.loadJS('https://code.jquery.com/ui/1.12.1/jquery-ui.min.js');
editor.loadCss('https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css');
//editor.loadCss('https://code.jquery.com/ui/1.12.1/themes/dot-luv/jquery-ui.css');

setTimeout(function () {
    editor.init();
}, 3000);