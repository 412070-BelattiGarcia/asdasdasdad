import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Cliente, Videojuego, PuestoJuego, Reserva, ReservaRequest } from '../models/models';

const API_BASE_URL = 'http://localhost:8080/api/v1';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {
  private apiUrl = `${API_BASE_URL}/clientes`;

  constructor(private http: HttpClient) {}

  getClientes(): Observable<Cliente[]> {
    return this.http.get<Cliente[]>(this.apiUrl);
  }
}

@Injectable({
  providedIn: 'root'
})
export class VideojuegoService {
  private apiUrl = `${API_BASE_URL}/videojuegos`;

  constructor(private http: HttpClient) {}

  getVideojuegos(): Observable<Videojuego[]> {
    return this.http.get<Videojuego[]>(this.apiUrl);
  }
}

@Injectable({
  providedIn: 'root'
})
export class PuestoService {
  private apiUrl = `${API_BASE_URL}/puestos`;

  constructor(private http: HttpClient) {}

  getPuestos(): Observable<PuestoJuego[]> {
    return this.http.get<PuestoJuego[]>(this.apiUrl);
  }
}

@Injectable({
  providedIn: 'root'
})
export class ReservaService {
  private apiUrl = `${API_BASE_URL}/reservas`;

  constructor(private http: HttpClient) {}

  getReservas(filters?: {
    cliente_id?: number;
    videojuego_id?: number;
    puesto_id?: number;
    fecha_hora?: string;
  }): Observable<Reserva[]> {
    let params = new HttpParams();

    if (filters) {
      if (filters.cliente_id) params = params.set('cliente_id', filters.cliente_id.toString());
      if (filters.videojuego_id) params = params.set('videojuego_id', filters.videojuego_id.toString());
      if (filters.puesto_id) params = params.set('puesto_id', filters.puesto_id.toString());
      if (filters.fecha_hora) params = params.set('fecha_hora', filters.fecha_hora);
    }

    return this.http.get<Reserva[]>(this.apiUrl, { params });
  }

  createReserva(reserva: ReservaRequest): Observable<Reserva> {
    return this.http.post<Reserva>(this.apiUrl, reserva);
  }
}
