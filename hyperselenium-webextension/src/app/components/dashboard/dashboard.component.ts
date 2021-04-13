import {Component, OnInit} from '@angular/core';
import {BackgroundService} from "../services/background.service";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  messages: any[] = [];

  constructor(private backgroundService:BackgroundService) {
  }

  ngOnInit(): void {
    window.addEventListener('message',ev => {
      console.log(ev);
      this.messages.push("OK");
    }, false);

    this.backgroundService.messages.subscribe( m => this.messages = m);
  }

}
