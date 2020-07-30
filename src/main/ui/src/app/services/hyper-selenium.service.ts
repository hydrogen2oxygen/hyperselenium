import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {ServiceStatus} from "../domain/ServiceStatus";

@Injectable({
  providedIn: 'root'
})
export class HyperSeleniumService {

  public static url = `${environment.serverUrl}/api`;

  constructor(private http:HttpClient) { }

  getServiceStatus():Observable<ServiceStatus> {
    return this.http.get<ServiceStatus>(`${HyperSeleniumService.url}/serviceStatus`)
  }
}
