export class Protocol {
  scriptName:string;
  lines:ProtocolLine[];
  status:string;
  currentLine:number;
}

export class ProtocolLine {
  lineNumber:number;
  type:string;
  status:string;
  message:string;
  line:string;
  result:string;
  subScriptProtocol:Protocol;
}
