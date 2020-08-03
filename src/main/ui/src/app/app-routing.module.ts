import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ScenarioOverviewComponent} from "./components/scenario/scenario-overview/scenario-overview.component";
import {ScenarioEditComponent} from "./components/scenario/scenario-edit/scenario-edit.component";
import {PageNotFoundComponent} from "./components/page-not-found/page-not-found.component";
import {DocumentationComponent} from "./components/documentation/documentation.component";
import {ScenarioPlayComponent} from "./components/scenario/scenario-play/scenario-play.component";

const routes: Routes = [
  {path:'', component: ScenarioOverviewComponent},
  {path:'home', redirectTo: '', pathMatch: 'full'},
  {path:'documentation', component: DocumentationComponent},
  {path:'scenario', component: ScenarioEditComponent},
  {path:'scenario/:name', component: ScenarioEditComponent},
  {path:'play/:name', component: ScenarioPlayComponent},
  {path: '**', component: PageNotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
