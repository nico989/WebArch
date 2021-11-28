import { Routes } from "@angular/router";

import { ListMembersComponent } from './list-members/list-members.component';
import { CardMemberComponent } from './card-member/card-member.component';
import { ErrorComponent } from "./error/error.component";

export const appRoutes: Routes = [
  { path: "listParlaments", component: ListMembersComponent },
  { path: "cardParlament/:id", component: CardMemberComponent },
  { path: "", redirectTo: "listParlaments", pathMatch: "full" },
  { path: "**", component: ErrorComponent }
];
