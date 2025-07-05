import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-reserva-container',
  standalone: true,
  imports: [CommonModule, RouterModule, RouterOutlet],
  template: `
    <div class="min-h-screen bg-gray-50">
      <!-- Header -->
      <header class="bg-white shadow-sm border-b">
        <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div class="flex justify-between items-center h-16">
            <div class="flex items-center">
              <h1 class="text-2xl font-bold text-gray-900">
                🎮 Sistema de Reservas
              </h1>
            </div>
            <nav class="flex space-x-4">
              <a
                routerLink="/reservas"
                routerLinkActive="bg-blue-100 text-blue-700"
                class="px-3 py-2 rounded-md text-sm font-medium text-gray-700 hover:text-blue-600 hover:bg-blue-50 transition-colors">
                📋 Ver Reservas
              </a>
              <a
                routerLink="/nueva-reserva"
                routerLinkActive="bg-green-100 text-green-700"
                class="px-3 py-2 rounded-md text-sm font-medium text-gray-700 hover:text-green-600 hover:bg-green-50 transition-colors">
                ➕ Nueva Reserva
              </a>
            </nav>
          </div>
        </div>
      </header>

      <!-- Main Content -->
      <main class="max-w-7xl mx-auto py-6 px-4 sm:px-6 lg:px-8">
        <router-outlet></router-outlet>
      </main>

      <!-- Footer -->
      <footer class="bg-white border-t mt-12">
        <div class="max-w-7xl mx-auto py-4 px-4 sm:px-6 lg:px-8">
          <p class="text-center text-sm text-gray-500">
            © 2025 Sistema de Reservas para Local de Videojuegos
          </p>
        </div>
      </footer>
    </div>
  `,
  styleUrl: './reserva-container.component.css'
})
export class ReservaContainerComponent {

}
