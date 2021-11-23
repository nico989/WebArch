import { Routes } from "@angular/router";

import { ListParlamentsComponent } from "./list-parlaments/list-parlaments.component";
import { CardParlamentsComponent } from "./card-parlaments/card-parlaments.component";
import { ErrorComponent } from "./error/error.component";

export const appRoutes: Routes = [
  { path: "listParlaments", component: ListParlamentsComponent },
  { path: "cardParlament/:id", component: CardParlamentsComponent },
  { path: "", redirectTo: "listParlaments", pathMatch: "full" },
  { path: "**", component: ErrorComponent }
];
