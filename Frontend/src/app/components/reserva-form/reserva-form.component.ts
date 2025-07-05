import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { ReservaService } from '../../services/services';
import { ClienteService } from '../../services/services';
import { VideojuegoService } from '../../services/services';
import { PuestoService } from '../../services/services';
import { Cliente, Videojuego, PuestoJuego, ReservaRequest } from '../../models/models';

@Component({
  selector: 'app-reserva-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './reserva-form.component.html',
  styleUrl: './reserva-form.component.css'
})
export class ReservaFormComponent implements OnInit {
  clientes: Cliente[] = [];
  videojuegos: Videojuego[] = [];
  puestos: PuestoJuego[] = [];

  reservaForm: ReservaRequest = {
    cliente_id: 0,
    videojuego_id: 0,
    puesto_id: 0,
    fechaHora: '',
    duracionMinutos: 30,
    observaciones: ''
  };

  fechaInput: string = '';
  horaInput: string = '';

  duracionesDisponibles = [30, 60, 90, 120];

  loading = false;
  error: string | null = null;

  constructor(
    private reservaService: ReservaService,
    private clienteService: ClienteService,
    private videojuegoService: VideojuegoService,
    private puestoService: PuestoService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadData();
  }

  loadData(): void {
    this.clienteService.getClientes().subscribe({
      next: (clientes) => this.clientes = clientes,
      error: (error) => console.error('Error loading clientes:', error)
    });

    this.videojuegoService.getVideojuegos().subscribe({
      next: (videojuegos) => this.videojuegos = videojuegos,
      error: (error) => console.error('Error loading videojuegos:', error)
    });

    this.puestoService.getPuestos().subscribe({
      next: (puestos) => this.puestos = puestos,
      error: (error) => console.error('Error loading puestos:', error)
    });
  }

  onSubmit(): void {
    if (!this.isFormValid()) {
      return;
    }

    this.loading = true;
    this.error = null;

    // Combinar fecha y hora
    this.reservaForm.fechaHora = `${this.fechaInput}T${this.horaInput}:00`;

    this.reservaService.createReserva(this.reservaForm).subscribe({
      next: (reserva) => {
        console.log('Reserva created:', reserva);
        this.router.navigate(['/reservas']);
      },
      error: (error) => {
        console.error('Error creating reserva:', error);
        this.error = error.error?.message || 'Error al crear la reserva';
        this.loading = false;
      }
    });
  }

  isFormValid(): boolean {
    return (
      this.reservaForm.cliente_id > 0 &&
      this.reservaForm.videojuego_id > 0 &&
      this.reservaForm.puesto_id > 0 &&
      this.fechaInput.length > 0 &&
      this.horaInput.length > 0 &&
      this.reservaForm.duracionMinutos > 0
    );
  }

  resetForm(): void {
    this.reservaForm = {
      cliente_id: 0,
      videojuego_id: 0,
      puesto_id: 0,
      fechaHora: '',
      duracionMinutos: 30,
      observaciones: ''
    };
    this.fechaInput = '';
    this.horaInput = '';
    this.error = null;
  }
}
