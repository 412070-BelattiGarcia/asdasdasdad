import { Routes } from '@angular/router';
import {ReservaList} from './components/reserva-list/reserva-list';
import {ReservaFormComponent} from './components/reserva-form/reserva-form.component';

export const routes: Routes = [
  { path: '', redirectTo: '/reservas', pathMatch: 'full' },
  { path: 'reservas', component: ReservaList },
  { path: 'nueva-reserva', component: ReservaFormComponent }
];
