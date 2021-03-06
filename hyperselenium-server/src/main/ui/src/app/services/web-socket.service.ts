import {Injectable} from "@angular/core";
import {ServiceStatus} from "../domain/ServiceStatus";
import {environment} from "../../environments/environment";
import {Subject} from "rxjs";

declare var SockJS;
declare var Stomp;

/**
 * WebSocket connection to the server.
 * It receives a ServiceStatus object with various information.
 */
@Injectable()
export class WebSocketService {

  constructor() {
    this.initializeWebSocketConnection();
  }

  public stompClient;
  serviceStatus:Subject<ServiceStatus> = new Subject<ServiceStatus>();

  initializeWebSocketConnection() {

    const serverUrl = `${environment.websocketUrl}`;
    const ws = new SockJS(serverUrl);
    this.stompClient = Stomp.over(ws);
    const that = this;

    this.stompClient.connect(function(w){
      console.log(w);
    }, function(frame) {
      that.stompClient.subscribe('/status', (message) => {
        if (message.body) {
          console.log("Received a websocket message");
          that.serviceStatus.next(JSON.parse(message.body));
        }
      });
    },() => { console.log("reconnect?"); this.initializeWebSocketConnection() });
  }

}
