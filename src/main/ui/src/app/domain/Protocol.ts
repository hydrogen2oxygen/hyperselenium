export class Protocol {
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
}
