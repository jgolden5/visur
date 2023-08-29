document.addEventListener('keydown', (event) => {
  var eb = new EventBus('http://localhost:8888/eventbus');
  eb.onopen = function() {
    var svc = new BrowserInputService(eb, "browserInput");
    svc.keyPress(event.key);
  }
})
