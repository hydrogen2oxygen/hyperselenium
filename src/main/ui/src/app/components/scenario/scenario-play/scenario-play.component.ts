import {Component, OnInit} from '@angular/core';
import {HyperSeleniumService} from "../../../services/hyper-selenium.service";
import {ActivatedRoute} from "@angular/router";
import {Scenario} from "../../../domain/Scenario";
import {WebSocketService} from "../../../services/web-socket.service";
import {Subject} from "rxjs";
import {ServiceStatus} from "../../../domain/ServiceStatus";

@Component({
  selector: 'app-scenario-play',
  templateUrl: './scenario-play.component.html',
  styleUrls: ['./scenario-play.component.less']
})
export class ScenarioPlayComponent implements OnInit {

  scenarioName: string;
  scenario: Scenario;

  constructor(
    private hyperSeleniumService: HyperSeleniumService,
    private webSocket: WebSocketService,
    private route: ActivatedRoute
  ) {
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => this.loadScenario(params['name']));
  }

  /**
   * Load the scenario to be shown
   * @param name
   */
  private loadScenario(name: string) {
    if (name == null) return;

    this.scenarioName = name;
    console.log("load " + name);

    this.hyperSeleniumService.loadScenario(name).subscribe(s => {

      this.scenario = s;

      this.webSocket.serviceStatus.subscribe(status => {
        console.log("status changed");

        let serviceStatus:ServiceStatus = <ServiceStatus> status;

        console.log(serviceStatus);

        for (let i:number = 0; i<serviceStatus.scenarios.length; i++) {
          let scenario:Scenario = serviceStatus.scenarios[i];
          if (scenario.name == this.scenarioName) {
            this.scenario = scenario;
            console.log("READING SUCCESS");
            break;
          }
        }

      })
    });
  }
}
