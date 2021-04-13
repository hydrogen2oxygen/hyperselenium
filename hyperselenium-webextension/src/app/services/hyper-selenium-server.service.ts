import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Scenario} from "../domain/Scenario";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class HyperSeleniumServerService {

  public static url = `${environment.serverUrl}/api`;

  constructor(private http:HttpClient) { }

  getScenarios():Observable<any> {
    return this.http.get<Scenario[]>(`${HyperSeleniumServerService.url}/scenario`)
  }
}
