function notifyExtension(e) {
  console.log('click ' + e.target.tagName);
  if (e.target.tagName != "A") {
    return;
  }
  console.log('sending message ...');
  browser.runtime.sendMessage({"url": e.target.href}).then(r => console.log(r));
}

window.addEventListener("click", notifyExtension);
