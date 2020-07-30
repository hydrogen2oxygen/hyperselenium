import { Injectable } from '@angular/core';

let SockJs = require("sockjs-client");
let Stomp = require("stompjs");

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {

  constructor() { }

  // Open connection with the back-end socket
  public connect() {
    let socket = new SockJs(`http://localhost/socket`);

    let stompClient = Stomp.over(socket);

    return stompClient;
  }
}
