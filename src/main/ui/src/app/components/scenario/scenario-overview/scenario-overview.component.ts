import { Component, OnInit } from '@angular/core';
import {Scenario} from "../../../domain/Scenario";
import { faEdit, faTrash, faPlay, faStop } from '@fortawesome/free-solid-svg-icons';
import {HyperSeleniumService} from "../../../services/hyper-selenium.service";

@Component({
  selector: 'app-scenario-overview',
  templateUrl: './scenario-overview.component.html',
  styleUrls: ['./scenario-overview.component.less']
})
export class ScenarioOverviewComponent implements OnInit {
  scenarios: Scenario[] = [];
  faEdit = faEdit;
  faTrash = faTrash;
  faPlay = faPlay;
  faStop = faStop;

  constructor(private hyperSeleniumService:HyperSeleniumService) { }

  ngOnInit(): void {

    this.hyperSeleniumService.getAllScenarios().subscribe( data => {
      this.scenarios = data;
    });
  }

  play(scenario: Scenario) {

  }

  stop(scenario: Scenario) {

  }

  delete(scenario: Scenario) {

  }
}
