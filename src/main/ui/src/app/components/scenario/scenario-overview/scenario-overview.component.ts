import {Component, OnInit, Type} from '@angular/core';
import {Scenario} from "../../../domain/Scenario";
import { faEdit, faTrash, faPlay, faStop, faArchive } from '@fortawesome/free-solid-svg-icons';
import {HyperSeleniumService} from "../../../services/hyper-selenium.service";
import {ActivatedRoute, Router} from "@angular/router";
import {ToastrService} from "ngx-toastr";
import {NgbActiveModal, NgbModal, NgbModalRef} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'modal-delete-scenario-confirm',
  template: `
    <div class="modal-header">
      <h4 class="modal-title" id="modal-title">Scenario deletion</h4>
      <button type="button" class="close" aria-describedby="modal-title" (click)="modal.dismiss('cross click')">
        <span aria-hidden="true">&times;</span>
      </button>
    </div>
    <div class="modal-body">
      <p><strong>Are you sure you want to delete <span class="text-primary">"{{scenario.name}}"</span> scenario?</strong></p>
      <p>The scenario and associated script will be permanently deleted.
        <span class="text-danger">This operation can not be undone.</span>
      </p>
    </div>
    <div class="modal-footer">
      <button type="button" class="btn btn-outline-secondary" (click)="modal.dismiss('cancel click')">Cancel</button>
      <button type="button" class="btn btn-danger" (click)="modal.close('delete')">Delete</button>
    </div>
  `
})
export class ModalDeleteScenarioConfirm {
  constructor(public modal: NgbActiveModal) {}
}

const MODALS: {[name: string]: Type<any>} = {
  deleteConfirmationModal: ModalDeleteScenarioConfirm
};

@Component({
  selector: 'app-scenario-overview',
  templateUrl: './scenario-overview.component.html',
  styleUrls: ['./scenario-overview.component.less']
})
export class ScenarioOverviewComponent implements OnInit {
  scenarios: Scenario[] = [];
  faEdit = faEdit;
  faTrash = faTrash;
  faPlay = faPlay;
  faStop = faStop;
  faArchive = faArchive;

  constructor(
    private hyperSeleniumService:HyperSeleniumService,
    private route: ActivatedRoute,
    private router: Router,
    private toastr: ToastrService,
    private _modalService: NgbModal
  ) { }

  ngOnInit(): void {

    this.loadAllScenarios();
  }

  private loadAllScenarios() {
    this.hyperSeleniumService.getAllScenarios().subscribe(data => {
      this.scenarios = data;
    });
  }

  play(scenario: Scenario) {
    this.hyperSeleniumService.play(scenario.name).subscribe( data => {
      this.router.navigate([`/play/${scenario.name}`]);
    });
  }

  stop(scenario: Scenario) {
    // TODO
  }

  delete(scenario: Scenario) {

    let modalRef:NgbModalRef = this._modalService.open(MODALS['deleteConfirmationModal']);
    modalRef.componentInstance.scenario = scenario;

    modalRef.result.then((result) => {
        if (result == 'delete') {
          this.hyperSeleniumService.delete(scenario.name).subscribe( data => {
            this.toastr.warning(`Script ${data.name} deleted.`,'Scripts');
            this.loadAllScenarios();
          });
        }
    }, (reason) => {
      console.log(reason)
    });
  }

  export(scenario: Scenario) {
    this.hyperSeleniumService.export(scenario.name).subscribe( data => {
      this.toastr.info(`Script ${data.name} exported.`,'Scripts')
    });
  }

}
