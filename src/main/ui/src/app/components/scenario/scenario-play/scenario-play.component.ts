import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {HyperSeleniumService} from "../../../services/hyper-selenium.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Scenario} from "../../../domain/Scenario";
import {WebSocketService} from "../../../services/web-socket.service";
import {ServiceStatus} from "../../../domain/ServiceStatus";
import {faCircle, faEdit, faPlay, faStop, faWindowClose} from "@fortawesome/free-solid-svg-icons";
import {ProtocolLine} from "../../../domain/Protocol";
import {environment} from "../../../../environments/environment";
import {FormControl, FormGroup} from "@angular/forms";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-scenario-play',
  templateUrl: './scenario-play.component.html',
  styleUrls: ['./scenario-play.component.less']
})
export class ScenarioPlayComponent implements OnInit {

  @ViewChild('running') runningElement:ElementRef;
  @ViewChild('error') errorElement:ElementRef;

  scenarioName: string;
  scenario: Scenario;

  faEdit = faEdit;
  faPlay = faPlay;
  faStop = faStop;
  faCircle = faCircle;
  faWindowClose = faWindowClose;

  lastClickOnPlay = new Date();
  editLineNumber: number;
  editLineForm:FormGroup = new FormGroup({});

  constructor(
    private hyperSeleniumService: HyperSeleniumService,
    private webSocket: WebSocketService,
    private route: ActivatedRoute,
    private router: Router,
    private toastr:ToastrService
  ) {
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => this.loadScenario(params['name']));
  }

  /**
   * Load the scenario to be shown.
   * The source is a webSocket event
   * @param name
   */
  private loadScenario(name: string) {
    if (name == null) return;

    this.scenarioName = name;
    console.log("load " + name);

    this.hyperSeleniumService.loadScenario(name).subscribe(s => {

      this.scenario = s;

      this.webSocket.serviceStatus.subscribe(status => {

        let serviceStatus:ServiceStatus = <ServiceStatus> status;

        for (let i:number = 0; i<serviceStatus.scenarios.length; i++) {

          let scenario:Scenario = serviceStatus.scenarios[i];

          if (scenario.name == this.scenarioName) {

            this.scenario = scenario;

            if (this.runningElement) {
              this.runningElement.nativeElement
                .scrollIntoView({behavior: "smooth", block: "center"});
            }

            if (this.errorElement) {
              this.errorElement.nativeElement
                .scrollIntoView({behavior: "smooth", block: "center"});
            }

            break;
          }
        }

      })
    });
  }

  play(scenario: Scenario) {

    if (new Date().getTime() - this.lastClickOnPlay.getTime() > 2000) {
      this.hyperSeleniumService.play(this.scenarioName).subscribe(data => {
        this.router.navigate([`/play/${this.scenarioName}`]);
      });
    }

    this.lastClickOnPlay = new Date();
  }

  stop(scenario: Scenario) {
    this.hyperSeleniumService.stop(this.scenarioName).subscribe( data => {
      console.log(data);
      this.toastr.info("All running drivers closed!","HyperSelenium Service")
    });
  }

  close(scenario: Scenario) {
    this.hyperSeleniumService.close(this.scenarioName).subscribe( data => {
      console.log(data);
    });
  }

  editLine(line: ProtocolLine) {
    console.log(line);
    this.createEditLineForm();
    this.editLineForm.controls['script'].setValue(line.line);
    this.editLineNumber = line.lineNumber;
  }

  playFromLine(line: ProtocolLine) {
    this.hyperSeleniumService.continue(this.scenarioName, line.lineNumber).subscribe( data => {
      console.log(data);
    });
  }

  getScreenShotUrl(line: ProtocolLine) {
    let id = line.result.replace('screenshot ','');
    return `${environment.serverUrl}/api/scenario/screenshot/${id}`;
  }

  setBreakPoint(line: ProtocolLine) {
    this.hyperSeleniumService.updateBreakpoint(this.scenarioName, line.lineNumber).subscribe( scenario => {
      this.scenario.script.breakpoints = scenario.script.breakpoints;
    });
  }

  saveEditLine() {
    console.log(this.editLineForm.getRawValue().script);


    let oldScript:string[] = this.scenario.script.lines;
    let newScript:string[] = [];

    for (let i=0; i<oldScript.length; i++) {

      if (i == this.editLineNumber - 1) {
        let newLine:string = this.editLineForm.getRawValue().script;

        // add multiple lines
        if (newLine.includes("\n")) {

          let newLines:string[] = newLine.split("\n");

          for (let x=0; x<newLines.length; x++) {
            newScript.push(newLines[x]);
          }
        } else {
          // or just one single line
          newScript.push(this.editLineForm.getRawValue().script);
        }

      } else {
        // add all the rest
        newScript.push(oldScript[i]);
      }
    }

    this.editLineNumber = null;
    console.log(newScript);
    this.scenario.script.lines = newScript;

    this.hyperSeleniumService.updateScenario(this.scenario).subscribe( result => {
      this.loadScenario(this.scenarioName);
    });
  }

  cancelEditLine() {
    this.editLineNumber = null;
  }

  private createEditLineForm() {
    this.editLineForm = new FormGroup({
      script: new FormControl('')
    });
  }
}
