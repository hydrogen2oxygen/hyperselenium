import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.less']
})
export class NavbarComponent implements OnInit {

  collapsed = true;

  links = [
    { title: 'Home', link: '', fragment: 'home' },
    { title: 'New Scenario', link: 'scenario', fragment: 'scenario' },
  ];

  constructor(public route: ActivatedRoute) {
  }

  ngOnInit(): void {
  }

}
