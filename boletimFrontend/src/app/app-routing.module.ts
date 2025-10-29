import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { BoletimComponent } from "./pages/boletim/boletim.component";


const routes: Routes = [
  { path: '', component: BoletimComponent },
  { path: '**', redirectTo: '', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { scrollPositionRestoration: 'enabled' })],
  exports: [RouterModule]
})
export class AppRoutingModule {}