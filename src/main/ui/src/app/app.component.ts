import {Component, OnInit} from '@angular/core';
import {WebSocketService} from "./services/web-socket.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.less']
})
export class AppComponent implements OnInit {
  title = 'ui';

  constructor(private webSocketService:WebSocketService){}

  ngOnInit(): void {
    this.webSocketService.listen('status').subscribe(data => {
      console.log("Data from websocket!");
      console.log(data);
    });

    console.log("websocket registered");
  }
}
