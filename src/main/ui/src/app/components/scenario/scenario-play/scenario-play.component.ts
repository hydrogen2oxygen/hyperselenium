import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {HyperSeleniumService} from "../../../services/hyper-selenium.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Scenario} from "../../../domain/Scenario";
import {WebSocketService} from "../../../services/web-socket.service";
import {ServiceStatus} from "../../../domain/ServiceStatus";
import {faEdit, faPlay, faStop, faCircle, faWindowClose} from "@fortawesome/free-solid-svg-icons";
import {ProtocolLine} from "../../../domain/Protocol";
import {environment} from "../../../../environments/environment";

@Component({
  selector: 'app-scenario-play',
  templateUrl: './scenario-play.component.html',
  styleUrls: ['./scenario-play.component.less']
})
export class ScenarioPlayComponent implements OnInit {

  @ViewChild('running') runningElement:ElementRef;

  scenarioName: string;
  scenario: Scenario;

  faEdit = faEdit;
  faPlay = faPlay;
  faStop = faStop;
  faCircle = faCircle;
  faWindowClose = faWindowClose;

  constructor(
    private hyperSeleniumService: HyperSeleniumService,
    private webSocket: WebSocketService,
    private route: ActivatedRoute,
    private router: Router
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
                .scrollIntoView({behavior: "smooth", block: "start"});
            }

            break;
          }
        }

      })
    });
  }

  play(scenario: Scenario) {
    this.hyperSeleniumService.play(this.scenarioName).subscribe( data => {
      this.router.navigate([`/play/${this.scenarioName}`]);
    });
  }

  stop(scenario: Scenario) {
    this.hyperSeleniumService.stop(this.scenarioName).subscribe( data => {
      console.log(data);
    });
  }

  close(scenario: Scenario) {
    this.hyperSeleniumService.close(this.scenarioName).subscribe( data => {
      console.log(data);
    });
  }

  editLine(line: ProtocolLine) {
    console.log(line);
  }

  playFromLine(line: ProtocolLine) {
    console.log(line);
  }

  getScreenShotUrl(line: ProtocolLine) {
    let id = line.result.replace('screenshot ','');
    return `${environment.serverUrl}/api/scenario/screenshot/${id}`;
  }

  setBreakPoint(line: ProtocolLine) {

  }


}
