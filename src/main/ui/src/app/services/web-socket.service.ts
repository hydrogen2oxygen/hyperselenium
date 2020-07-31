import {Injectable} from "@angular/core";
import {Observable} from 'rxjs';
import * as io from 'socket.io-client';
import {environment} from "../../environments/environment";

@Injectable()
export class WebSocketService {

  socket: any;

  constructor() {
    this.socket = io(environment.websocketUrl);
  }

  listen(eventName: string) {
    return new Observable(subscriber => {
      console.log("listen event triggered");
      this.socket.on(eventName, data => {
        console.log("on socket");
        console.log(data);
        subscriber.next(data)
      })
    })
  }

  emit(eventName: string, data: any) {
    console.log("emit triggered");
    this.socket.emit(eventName, data);
  }

}
