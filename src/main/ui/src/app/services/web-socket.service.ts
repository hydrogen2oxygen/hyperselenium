import {Injectable} from "@angular/core";
import {ServiceStatus} from "../domain/ServiceStatus";

declare var SockJS;
declare var Stomp;

@Injectable()
export class WebSocketService {

  constructor() {
    this.initializeWebSocketConnection();
  }

  public stompClient;
  serviceStatus:ServiceStatus;

  initializeWebSocketConnection() {

    const serverUrl = 'http://localhost:80/socket';
    const ws = new SockJS(serverUrl);
    this.stompClient = Stomp.over(ws);
    const that = this;

    this.stompClient.connect({}, function(frame) {
      that.stompClient.subscribe('/status', (message) => {
        if (message.body) {
          console.log("Received a websocket message");
          console.log(message.body);
          that.serviceStatus = message.body;
          console.log(that.serviceStatus)
        }
      });
    });
  }

}
