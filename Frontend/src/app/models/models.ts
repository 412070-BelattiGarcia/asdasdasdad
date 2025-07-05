export interface Cliente {
  id: number;
  nombreCompleto: string;
  email: string;
}

export interface Videojuego {
  id: number;
  titulo: string;
  genero: string;
}

export interface PuestoJuego {
  id: number;
  nombre: string;
  tipo: string;
}

export interface Reserva {
  id: number;
  cliente: Cliente;
  videojuego: Videojuego;
  puesto: PuestoJuego;
  fechaHora: string;
  duracionMinutos: number;
  observaciones?: string;
}

export interface ReservaRequest {
  cliente_id: number;
  videojuego_id: number;
  puesto_id: number;
  fechaHora: string;
  duracionMinutos: number;
  observaciones?: string;
}
