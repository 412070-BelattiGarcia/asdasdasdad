import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { ReservaService } from '../../services/services';
import { ClienteService } from '../../services/services';
import { VideojuegoService } from '../../services/services';
import { PuestoService } from '../../services/services';
import { Reserva, Cliente, Videojuego, PuestoJuego } from '../../models/models';

@Component({
  selector: 'app-reserva-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './reserva-list.html',
  styleUrl: './reserva-list.css'
})
export class ReservaList implements OnInit {
  reservas: Reserva[] = [];
  filteredReservas: Reserva[] = [];

  clientes: Cliente[] = [];
  videojuegos: Videojuego[] = [];
  puestos: PuestoJuego[] = [];

  filters = {
    cliente_id: 0,
    videojuego_id: 0,
    puesto_id: 0,
    fecha_hora: ''
  };

  loading = false;
  error: string | null = null;

  constructor(
    private reservaService: ReservaService,
    private clienteService: ClienteService,
    private videojuegoService: VideojuegoService,
    private puestoService: PuestoService
  ) {}

  ngOnInit(): void {
    this.loadData();
    this.loadReservas();
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

  loadReservas(): void {
    this.loading = true;
    this.error = null;

    const activeFilters: any = {};
    if (this.filters.cliente_id > 0) activeFilters.cliente_id = this.filters.cliente_id;
    if (this.filters.videojuego_id > 0) activeFilters.videojuego_id = this.filters.videojuego_id;
    if (this.filters.puesto_id > 0) activeFilters.puesto_id = this.filters.puesto_id;
    if (this.filters.fecha_hora) activeFilters.fecha_hora = this.filters.fecha_hora;

    this.reservaService.getReservas(Object.keys(activeFilters).length > 0 ? activeFilters : undefined).subscribe({
      next: (reservas) => {
        this.reservas = reservas;
        this.filteredReservas = [...reservas];
        this.loading = false;
      },
      error: (error) => {
        console.error('Error loading reservas:', error);
        this.error = 'Error al cargar las reservas';
        this.loading = false;
      }
    });
  }

  applyFilters(): void {
    this.loadReservas();
  }

  clearFilters(): void {
    this.filters = {
      cliente_id: 0,
      videojuego_id: 0,
      puesto_id: 0,
      fecha_hora: ''
    };
    this.loadReservas();
  }

  formatDateTime(dateTime: string): string {
    return new Date(dateTime).toLocaleString('es-ES', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
      hour: '2-digit',
      minute: '2-digit'
    });
  }

  formatDuration(minutes: number): string {
    const hours = Math.floor(minutes / 60);
    const remainingMinutes = minutes % 60;

    if (hours > 0) {
      return remainingMinutes > 0 ? `${hours}h ${remainingMinutes}m` : `${hours}h`;
    }
    return `${minutes}m`;
  }
}
