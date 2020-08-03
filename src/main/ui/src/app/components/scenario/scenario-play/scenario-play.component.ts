import { Component, OnInit } from '@angular/core';
import {HyperSeleniumService} from "../../../services/hyper-selenium.service";
import {ActivatedRoute} from "@angular/router";
import {Scenario} from "../../../domain/Scenario";
import {WebSocketService} from "../../../services/web-socket.service";
import {Subject} from "rxjs";

@Component({
  selector: 'app-scenario-play',
  templateUrl: './scenario-play.component.html',
  styleUrls: ['./scenario-play.component.less']
})
export class ScenarioPlayComponent implements OnInit {

  scenarioName:string;
  scenario:Scenario;

  constructor(
    private hyperSeleniumService:HyperSeleniumService,
    private webSocket:WebSocketService,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe( params => this.loadScenario(params['name']));
  }

  /**
   * Load the scenario to be shown
   * @param name
   */
  private loadScenario(name: string) {
    if (name == null) return;

    this.scenarioName = name;
    console.log("load " + name);

    this.hyperSeleniumService.loadScenario(name).subscribe( s => {
      this.webSocket.serviceStatus.subscribe( status => {
        this.scenario = status.scenarioMap.get(this.scenarioName);
      })
    });
  }
}
