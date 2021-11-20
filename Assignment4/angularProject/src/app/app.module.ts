// imports
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { ItemDirective } from './item.directive';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { ListParlamentsComponent } from './list-parlaments/list-parlaments.component';
import { CardParlamentsComponent } from './card-parlaments/card-parlaments.component';
import { ErrorComponent } from './error/error.component';
import { RouterModule } from "@angular/router";
import { appRoutes } from "./app.routes";

// @NgModule decorator with its metadata
@NgModule({
  declarations: [
    AppComponent,
    ItemDirective,
    HeaderComponent,
    FooterComponent,
    ListParlamentsComponent,
    CardParlamentsComponent,
    ErrorComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes)
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }



/*
Copyright Google LLC. All Rights Reserved.
Use of this source code is governed by an MIT-style license that
can be found in the LICENSE file at https://angular.io/license
*/
