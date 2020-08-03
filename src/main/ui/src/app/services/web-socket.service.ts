import {Injectable} from "@angular/core";
import {ServiceStatus} from "../domain/ServiceStatus";
import {environment} from "../../environments/environment";
import {Subject} from "rxjs";

declare var SockJS;
declare var Stomp;

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

    this.stompClient.connect({}, function(frame) {
      that.stompClient.subscribe('/status', (message) => {
        if (message.body) {
          console.log("Received a websocket message");
          console.log(JSON.parse(message.body).scenarioMap.test1);
          //that.serviceStatus.next(message.body);
        }
      });
    });
  }

}
