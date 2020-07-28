import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ScenarioOverviewComponent} from "./components/scenario/scenario-overview/scenario-overview.component";

const routes: Routes = [
  {path:'', component: ScenarioOverviewComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
