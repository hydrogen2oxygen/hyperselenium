import {Script} from "./Script";
import {Protocol} from "./Protocol";

export class Scenario {
  uuid:string;
  name:string;
  description:string;
  script:Script;
  protocol:Protocol;
}
