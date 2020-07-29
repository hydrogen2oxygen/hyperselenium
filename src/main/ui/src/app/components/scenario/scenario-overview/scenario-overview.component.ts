import { Component, OnInit } from '@angular/core';
import {Scenario} from "../../../domain/Scenario";
import { faEdit, faTrash, faPlay, faStop } from '@fortawesome/free-solid-svg-icons';

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

  constructor() { }

  ngOnInit(): void {
    let testScenario = new Scenario();
    testScenario.name = 'TestScenario';
    testScenario.description = 'just a small test';
    this.scenarios.push(testScenario);
  }

}
