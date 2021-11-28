import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatCardModule } from '@angular/material/card';

import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { ListMembersComponent } from './list-members/list-members.component';
import { CardMemberComponent } from './card-member/card-member.component';
import { ErrorComponent } from './error/error.component';
import { RouterModule } from "@angular/router";
import { appRoutes } from "./app.routes";
import { CustomDatePipe } from './pipe/custom-date.pipe';
import { DatePipe } from '@angular/common';
import { CacheService } from './services/cache.service';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    ListMembersComponent,
    CardMemberComponent,
    ErrorComponent,
    CustomDatePipe
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    MatGridListModule,
    MatCardModule,
    RouterModule.forRoot(appRoutes, {useHash: true})
  ],
  providers: [CacheService,DatePipe],
  bootstrap: [AppComponent]
})
export class AppModule { }

/*
Copyright Google LLC. All Rights Reserved.
Use of this source code is governed by an MIT-style license that
can be found in the LICENSE file at https://angular.io/license
*/
