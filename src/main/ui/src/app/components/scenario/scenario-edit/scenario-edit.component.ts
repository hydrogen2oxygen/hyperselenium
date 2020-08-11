import {Component, OnInit} from '@angular/core';
import {HyperSeleniumService} from "../../../services/hyper-selenium.service";
import {Scenario} from "../../../domain/Scenario";
import {FormControl, FormGroup} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {Script} from "../../../domain/Script";
import {Command} from "../../../domain/Command";
import {ToastrService} from "ngx-toastr";
import {tokenReference} from "@angular/compiler";

@Component({
  selector: 'app-scenario-edit',
  templateUrl: './scenario-edit.component.html',
  styleUrls: ['./scenario-edit.component.less']
})
export class ScenarioEditComponent implements OnInit {

  scenarioForm:FormGroup = new FormGroup({});
  editMode:boolean = false;
  commands:Command[];

  constructor(
    private hyperSeleniumService:HyperSeleniumService,
    private route: ActivatedRoute,
    private router: Router,
    private toastr: ToastrService
  ) {
  }

  ngOnInit(): void {
    this.createForm.call(this);
    this.route.params.subscribe( params => this.load(params['name']));
    this.hyperSeleniumService.loadCommands().subscribe( commands => this.commands = commands);
  }

  save() {

    let scenario = this.generateScenarioFromForm();

    this.hyperSeleniumService.saveScenario(scenario).subscribe( result => {
      console.log("scenario saved!");
      console.log(result);
      this.toastr.success("Saved successfully",`Scenario: ${scenario.name}`);
      this.editMode = true;
    })
  }

  private generateScenarioFromForm():Scenario {
    let scenario: Scenario = new Scenario();
    scenario.name = this.scenarioForm.getRawValue().name;
    scenario.description = this.scenarioForm.getRawValue().description;
    let script = new Script();
    script.name = scenario.name;
    script.lines = this.scenarioForm.getRawValue().script.split("\n");
    scenario.script = script;
    return scenario;
  }

  private load(name: string) {
    if (name == null) return;

    console.log("load " + name);
    this.editMode = true;

    this.hyperSeleniumService.loadScenario(name).subscribe( s => {
      console.log(s);
      this.scenarioForm.controls['name'].setValue(s.name);
      this.scenarioForm.controls['description'].setValue(s.description);

      if (s.script == null || s.script.lines == null) return;

      let lines = "";

      for (let i = 0; i < s.script.lines.length; i++) {
        if (lines.length > 0) lines += "\n";
        lines += s.script.lines[i];
      }

      this.scenarioForm.controls['script'].setValue(lines);
    });
  }

  udate() {
    console.log("update");
    let scenario = this.generateScenarioFromForm();
    this.hyperSeleniumService.updateScenario(scenario).subscribe( result => {
      console.log(result);
      this.toastr.success("Saved successfully",`Scenario: ${scenario.name}`);
    });
  }

  play() {
    let scenario = this.generateScenarioFromForm();
    this.hyperSeleniumService.play(scenario.name).subscribe( data => {
      this.router.navigate([`/play/${scenario.name}`]);
    });
  }

  private createForm() {
    this.scenarioForm = new FormGroup({
      name: new FormControl(''),
      description: new FormControl(''),
      script: new FormControl('')
    });
  }

  copyCommandToClipBoard(command: Command) {
    const selBox = document.createElement('textarea');
    selBox.style.position = 'fixed';
    selBox.style.left = '0';
    selBox.style.top = '0';
    selBox.style.opacity = '0';
    selBox.value = command.name;
    document.body.appendChild(selBox);
    selBox.focus();
    selBox.select();
    document.execCommand('copy');
    document.body.removeChild(selBox);
  }

  insertTab() {
    // TODO crazy javascript stuff
    //const textarea:any = document.getElementById('#scenarioScript');
    //console.log(textarea);
    //let currentposition = textarea.selectionStart;
    //console.log(currentposition);

  }
}
