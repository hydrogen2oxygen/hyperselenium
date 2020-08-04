import {Component, OnInit} from '@angular/core';
import {HyperSeleniumService} from "../../../services/hyper-selenium.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Scenario} from "../../../domain/Scenario";
import {WebSocketService} from "../../../services/web-socket.service";
import {ServiceStatus} from "../../../domain/ServiceStatus";
import {faEdit, faPlay, faStop} from "@fortawesome/free-solid-svg-icons";
import {ProtocolLine} from "../../../domain/Protocol";

@Component({
  selector: 'app-scenario-play',
  templateUrl: './scenario-play.component.html',
  styleUrls: ['./scenario-play.component.less']
})
export class ScenarioPlayComponent implements OnInit {

  scenarioName: string;
  scenario: Scenario;

  faEdit = faEdit;
  faPlay = faPlay;
  faStop = faStop;

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
    // TODO
  }

  editLine(line: ProtocolLine) {
    console.log(line);
  }
}
