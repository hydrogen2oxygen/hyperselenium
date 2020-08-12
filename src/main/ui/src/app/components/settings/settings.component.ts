import {Component, OnInit} from '@angular/core';
import {HyperSeleniumService} from "../../services/hyper-selenium.service";
import {Settings} from "../../domain/Settings";
import {KeyValue} from "../../domain/KeyValue";
import {FormControl, FormGroup} from "@angular/forms";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.less']
})
export class SettingsComponent implements OnInit {

  settings:Settings;
  reactiveForm: FormGroup;

  constructor(
    private hyperSeleniumService:HyperSeleniumService,
    private toastr:ToastrService
  ) { }

  ngOnInit(): void {
    this.hyperSeleniumService.getSettings().subscribe( settings => {

console.log(settings);

      this.settings = settings;

      this.reactiveForm = new FormGroup({});
      const group: any = {};

      for (let ob in this.settings.settings) {

        let keyValue:KeyValue = this.settings.settings[ob];
        console.log(keyValue);

        let keyValueControl:FormControl = new FormControl();
        keyValueControl.setValue(keyValue.value);
        group[keyValue.key] = keyValueControl;
      }

      this.reactiveForm = new FormGroup(group);

      console.log(this.settings);
    });
  }

  save() {

    for (let ob in this.settings.settings) {

      let keyValue:KeyValue = this.settings.settings[ob];
      keyValue.value = this.reactiveForm.controls[keyValue.key].value;
    }

    this.hyperSeleniumService.updateSettings(this.settings).subscribe( s => {
      console.log(s);
    })
  }

  closeAllDrivers() {
    this.hyperSeleniumService.closeAllDrivers().subscribe(commandResult => {
      if (commandResult.success) {
        this.toastr.info("All running drivers closed!","HyperSelenium Service")
      }
    });
  }
}
