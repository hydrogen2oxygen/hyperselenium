import { Component, OnInit } from '@angular/core';
import {HyperSeleniumService} from "../../../services/hyper-selenium.service";
import {Scenario} from "../../../domain/Scenario";
import {FormArray, FormBuilder, FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-scenario-edit',
  templateUrl: './scenario-edit.component.html',
  styleUrls: ['./scenario-edit.component.less']
})
export class ScenarioEditComponent implements OnInit {

  scenarioForm:FormGroup;

  constructor(
    private hyperSeleniumService:HyperSeleniumService
  ) {
    this.scenarioForm = new FormGroup({
      name: new FormControl(''),
      description: new FormControl('')
    });
  }

  ngOnInit(): void {
  }

  save() {

    let scenario:Scenario = new Scenario();
    scenario.name = this.scenarioForm.getRawValue().name;
    scenario.description = this.scenarioForm.getRawValue().description;

    this.hyperSeleniumService.saveScenario(scenario).subscribe( result => {
      console.log(result);
      // TODO Toast
    })
  }
}
