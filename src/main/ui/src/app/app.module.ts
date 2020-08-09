import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ScenarioOverviewComponent } from './components/scenario/scenario-overview/scenario-overview.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { NavbarComponent } from './components/navbar/navbar.component';
import { ScenarioEditComponent } from './components/scenario/scenario-edit/scenario-edit.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { DocumentationComponent } from './components/documentation/documentation.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {WebSocketService} from "./services/web-socket.service";
import {ReactiveFormsModule} from "@angular/forms";
import { ScenarioPlayComponent } from './components/scenario/scenario-play/scenario-play.component';
import { SettingsComponent } from './components/settings/settings.component';
import {ToastrModule} from "ngx-toastr";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {HttpErrorInterceptor} from "./services/http-error.interceptor";

@NgModule({
  declarations: [
    AppComponent,
    ScenarioOverviewComponent,
    NavbarComponent,
    ScenarioEditComponent,
    PageNotFoundComponent,
    DocumentationComponent,
    ScenarioPlayComponent,
    SettingsComponent,
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        NgbModule,
        FontAwesomeModule,
        HttpClientModule,
        ReactiveFormsModule,
        ToastrModule.forRoot(),
        BrowserAnimationsModule
    ],
  providers: [
    WebSocketService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpErrorInterceptor,
      multi: true
    }
    ],
  bootstrap: [AppComponent]
})
export class AppModule { }
