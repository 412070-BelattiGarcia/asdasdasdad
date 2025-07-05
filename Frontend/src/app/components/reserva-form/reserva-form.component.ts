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
  success: string | null = null;

  constructor(
    private reservaService: ReservaService,
    private clienteService: ClienteService,
    private videojuegoService: VideojuegoService,
    private puestoService: PuestoService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadData();
    this.setMinDate();
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

  setMinDate(): void {
    const today = new Date();
    const year = today.getFullYear();
    const month = String(today.getMonth() + 1).padStart(2, '0');
    const day = String(today.getDate()).padStart(2, '0');

    const minDate = `${year}-${month}-${day}`;
    const fechaInput = document.getElementById('fecha') as HTMLInputElement;
    if (fechaInput) {
      fechaInput.min = minDate;
    }
  }

  onSubmit(): void {
    if (!this.isFormValid()) {
      this.error = 'Por favor, complete todos los campos obligatorios';
      return;
    }

    if (!this.isValidTime()) {
      this.error = 'El horario debe estar entre las 10:00 y 22:00';
      return;
    }

    this.loading = true;
    this.error = null;
    this.success = null;

    // Combinar fecha y hora
    this.reservaForm.fechaHora = `${this.fechaInput}T${this.horaInput}:00`;

    this.reservaService.createReserva(this.reservaForm).subscribe({
      next: (reserva) => {
        console.log('Reserva created:', reserva);
        this.success = 'Reserva creada exitosamente';

        // Redirigir después de 2 segundos
        setTimeout(() => {
          this.router.navigate(['/reservas']);
        }, 2000);
      },
      error: (error) => {
        console.error('Error creating reserva:', error);

        if (error.status === 409) {
          this.error = 'Conflicto: Ya existe una reserva para este puesto en el horario seleccionado, o el cliente ya tiene una reserva para este día.';
        } else if (error.status === 400) {
          this.error = error.error?.message || 'Datos de la reserva inválidos';
        } else {
          this.error = 'Error al crear la reserva. Intente nuevamente.';
        }

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

  isValidTime(): boolean {
    if (!this.horaInput) return false;

    const [hours, minutes] = this.horaInput.split(':').map(Number);
    const timeInMinutes = hours * 60 + minutes;

    // Entre 10:00 (600 minutos) y 22:00 (1320 minutos)
    return timeInMinutes >= 600 && timeInMinutes <= 1320;
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
    this.success = null;
  }

  getSelectedClienteName(): string {
    if (this.reservaForm.cliente_id === 0) return '';
    const cliente = this.clientes.find(c => c.id === this.reservaForm.cliente_id);
    return cliente ? cliente.nombreCompleto : '';
  }

  getSelectedVideojuegoName(): string {
    if (this.reservaForm.videojuego_id === 0) return '';
    const videojuego = this.videojuegos.find(v => v.id === this.reservaForm.videojuego_id);
    return videojuego ? videojuego.titulo : '';
  }

  getSelectedPuestoName(): string {
    if (this.reservaForm.puesto_id === 0) return '';
    const puesto = this.puestos.find(p => p.id === this.reservaForm.puesto_id);
    return puesto ? puesto.nombre : '';
  }
}
