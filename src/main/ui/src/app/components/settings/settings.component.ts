import {Component, OnInit} from '@angular/core';
import {HyperSeleniumService} from "../../services/hyper-selenium.service";
import {Settings} from "../../domain/Settings";
import {KeyValue} from "../../domain/KeyValue";
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.less']
})
export class SettingsComponent implements OnInit {

  settings:Settings;
  reactiveForm: FormGroup;

  constructor(private hyperSeleniumService:HyperSeleniumService) { }

  ngOnInit(): void {
    this.hyperSeleniumService.getSettings().subscribe( settings => {


      let array = Object.keys(settings.settings);

      this.settings = new Settings();

      this.reactiveForm = new FormGroup({});
      const group: any = {};

      for (let ob in array) {
        let key:string = array[ob];
        let value:string = <string> Object.values(settings.settings)[ob];
        this.settings.settings.set(key,value);
        this.settings.settingsArray.push(new KeyValue(key, value));

        let keyValueControl:FormControl = new FormControl();
        keyValueControl.setValue(value);
        group[key] = keyValueControl;
      }

      this.reactiveForm = new FormGroup(group);

      console.log(this.settings);
    });
  }

  save() {
    for (let index in this.settings.settingsArray) {
      let key:string = this.settings.settingsArray[index].key;
      let value:string = this.reactiveForm.get(key).value;
      console.log(value)
    }
  }
}
