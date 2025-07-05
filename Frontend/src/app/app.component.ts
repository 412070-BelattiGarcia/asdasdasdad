import { Component } from '@angular/core';
import {ReservaContainerComponent} from './components/reserva-container/reserva-container.component';

@Component({
  selector: 'app-root',
  imports: [ReservaContainerComponent],
  template:'<app-reserva-container></app-reserva-container>',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'Frontend';
}
