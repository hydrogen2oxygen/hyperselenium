import { Component, OnInit } from '@angular/core';
import {HyperSeleniumService} from "../../../services/hyper-selenium.service";
import {ActivatedRoute} from "@angular/router";
import {Scenario} from "../../../domain/Scenario";

@Component({
  selector: 'app-scenario-play',
  templateUrl: './scenario-play.component.html',
  styleUrls: ['./scenario-play.component.less']
})
export class ScenarioPlayComponent implements OnInit {

  scenario:Scenario;

  constructor(
    private hyperSeleniumService:HyperSeleniumService,
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

    console.log("load " + name);

    this.hyperSeleniumService.loadScenario(name).subscribe( s => {
      this.scenario = s;
    });
  }
}
