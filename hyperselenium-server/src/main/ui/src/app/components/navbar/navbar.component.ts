import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {HyperSeleniumService} from "../../services/hyper-selenium.service";
import {ServiceStatus} from "../../domain/ServiceStatus";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.less']
})
export class NavbarComponent implements OnInit {

  collapsed = true;
  serviceStatus: ServiceStatus;

  links = [
    {title: 'Home', link: '', fragment: 'home'},
    {title: 'New Scenario', link: 'scenario', fragment: 'scenario'},
    {title: 'Environments', link: 'environments', fragment: 'environments'},
    {title: 'Settings', link: 'settings', fragment: 'settings'},
    {title: 'Documentation', link: 'documentation', fragment: 'documentation'},
  ];

  constructor(
    private route: ActivatedRoute,
    private hyperSeleniumService: HyperSeleniumService
  ) {
  }

  ngOnInit(): void {
    this.hyperSeleniumService.getServiceStatus().subscribe(result => {
      this.serviceStatus = result;
      this.hyperSeleniumService.setTitle(`HyperSelenium v${result.buildVersion}`)
    });
  }

}
