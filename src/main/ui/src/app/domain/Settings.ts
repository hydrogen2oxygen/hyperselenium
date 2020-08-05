import {KeyValue} from "@angular/common";

export class Settings {

  name:string = "hyperSeleniumSettings";
  settings:Map<string,string> = new Map<string, string>();
  settingsArray:KeyValue<string,string>[] = [];
}
