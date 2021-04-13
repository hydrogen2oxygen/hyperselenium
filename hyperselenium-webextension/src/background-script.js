// background-script.js

function notify(message) {
  console.log('receiving message...');
  console.log(message);
  browser.notifications.create({
    "type": "basic",
    "iconUrl": browser.extension.getURL("link.png"),
    "title": "You clicked a link!",
    "message": message.url
  });
  try {
    window.postMessage("HELLO", "http://example.com");
    browser.notifications.create({
      "type": "basic",
      "title": "SEND POSTMESSAGE SUCCESS",
      "message": "OK!"
    });
  } catch (e) {
    browser.notifications.create({
      "type": "basic",
      "title": "ERROR TRYING TO SEND POSTMESSAGE",
      "message": e.message
    });
  }
}

browser.runtime.onMessage.addListener(notify);
