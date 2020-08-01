import { Component, OnInit } from '@angular/core';
import {HyperSeleniumService} from "../../../services/hyper-selenium.service";
import {Scenario} from "../../../domain/Scenario";
import {FormArray, FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {ActivatedRoute} from "@angular/router";
import {Script} from "../../../domain/Script";

@Component({
  selector: 'app-scenario-edit',
  templateUrl: './scenario-edit.component.html',
  styleUrls: ['./scenario-edit.component.less']
})
export class ScenarioEditComponent implements OnInit {

  scenarioForm:FormGroup = new FormGroup({});
  editMode:boolean = false;

  constructor(
    private hyperSeleniumService:HyperSeleniumService,
    private route: ActivatedRoute
  ) {

    this.scenarioForm = new FormGroup({
      name: new FormControl(''),
      description: new FormControl(''),
      script: new FormControl('')
    });

    this.route.params.subscribe( params => this.load(params['name']));
  }

  ngOnInit(): void {
  }

  save() {

    let scenario:Scenario = new Scenario();
    scenario.name = this.scenarioForm.getRawValue().name;
    scenario.description = this.scenarioForm.getRawValue().description;
    let script = new Script();
    script.name = scenario.name;
    script.lines = this.scenarioForm.getRawValue().script.split("\n");
    scenario.script = script;

    this.hyperSeleniumService.saveScenario(scenario).subscribe( result => {
      console.log("scenario saved!");
      console.log(result);
      // TODO Toast
    })
  }

  private load(name: string) {
    if (name == null) return;
    this.editMode = true;
    console.log(name);
    this.hyperSeleniumService.loadScenario(name).subscribe( s => {
      console.log(s);
    });
  }

  udate() {

  }
}
