import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {ServiceStatus} from "../domain/ServiceStatus";
import {Title} from "@angular/platform-browser";

@Injectable({
  providedIn: 'root'
})
export class HyperSeleniumService {

  public static url = `${environment.serverUrl}/api`;

  constructor(
    private http:HttpClient,
    private titleService: Title
  ) { }

  getServiceStatus():Observable<ServiceStatus> {
    return this.http.get<ServiceStatus>(`${HyperSeleniumService.url}/serviceStatus`)
  }

  /**
   * Set the title inside the index.html
   * @param title
   */
  setTitle(title: string) {
    console.log(title);
    this.titleService.setTitle(title);
  }
}
