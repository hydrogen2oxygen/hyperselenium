import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, Subject} from "rxjs";
import {environment} from "../../environments/environment";
import {ServiceStatus} from "../domain/ServiceStatus";
import {Title} from "@angular/platform-browser";
import {Scenario} from "../domain/Scenario";
import {Settings} from "../domain/Settings";
import {ToastrService} from "ngx-toastr";
import {catchError} from "rxjs/operators";
import {Command} from "../domain/Command";
import {CommandResult} from "../domain/CommandResult";

@Injectable({
  providedIn: 'root'
})
export class HyperSeleniumService {

  public static url = `${environment.serverUrl}/api`;

  constructor(
    private http:HttpClient,
    private titleService: Title,
    private toastr: ToastrService
  ) {
  }

  getServiceStatus():Observable<ServiceStatus> {
    return this.http.get<ServiceStatus>(`${HyperSeleniumService.url}/serviceStatus`)
  }

  saveScenario(scenario:Scenario):Observable<Scenario> {
    console.log(scenario);
    return this.http.post<Scenario>(`${HyperSeleniumService.url}/scenario`, scenario);
  }

  getSettings():Observable<Settings> {
    return this.http.get<Settings>(`${HyperSeleniumService.url}/settings`);
  }

  getAllScenarios() {
    return this.http.get<Scenario[]>(`${HyperSeleniumService.url}/scenario`)
  }

  loadCommands() {
    return this.http.get<Command[]>(`${HyperSeleniumService.url}/commands`)
  }

  /**
   * Set the title inside the index.html
   * @param title
   */
  setTitle(title: string) {
    console.log(title);
    this.titleService.setTitle(title);
  }

  loadScenario(name: string):Observable<Scenario> {
    return this.http.get<Scenario>(`${HyperSeleniumService.url}/scenario/${name}`);
  }

  updateScenario(scenario: Scenario):Observable<Scenario> {
    return this.http.put<Scenario>(`${HyperSeleniumService.url}/scenario`, scenario);
  }

  updateSettings(settings:Settings):Observable<Settings> {
    // @ts-ignore
    return this.http.put<Settings>(`${HyperSeleniumService.url}/settings`, settings)
      .pipe(
        catchError((error:any) => this.handleError(error))
      );
  }

  play(name: string):Observable<Scenario> {
    return this.http.post<Scenario>(`${HyperSeleniumService.url}/play/${name}`, null);
  }

  stop(name: string):Observable<Scenario> {
    return this.http.put<Scenario>(`${HyperSeleniumService.url}/stop/${name}`, null);
  }

  close(name: string):Observable<Scenario> {
    return this.http.put<Scenario>(`${HyperSeleniumService.url}/close/${name}`, null);
  }

  closeAllDrivers():Observable<CommandResult> {
    return this.http.put<CommandResult>(`${HyperSeleniumService.url}/closeAll`, null);
  }

  private handleError(error: any) {
    console.log(error);
    this.toastr.error(error.message);
    return undefined;
  }
}
