import {Scenario} from "./Scenario";

export interface ServiceStatus {
  buildVersion:string;
  scenarioMap:Map<string,Scenario>;
}
