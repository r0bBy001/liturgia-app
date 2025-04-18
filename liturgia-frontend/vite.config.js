import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
  plugins: [
    react(),
  ],
  build: {
    // Reducir el tamaño del chunk que genera advertencia
    chunkSizeWarningLimit: 1000,
    // Minimizar para producción
    minify: 'terser',
    terserOptions: {
      compress: {
        drop_console: true,
        drop_debugger: true,
      },
    },
    // Configurar los chunks manualmente para mejor división
    rollupOptions: {
      output: {
        manualChunks: {
          // Separa React y ReactDOM en un chunk separado
          'react-vendor': ['react', 'react-dom', 'react-router-dom'],
          // Otras librerías grandes en chunks separados
          'jwt': ['jwt-decode'],
          // UI y componentes
          'ui-libs': ['@heroicons/react', 'react-icons'],
          // Utilidades de fechas
          'date-utils': ['date-fns', 'react-date-range'],
        },
        // Estrategia para nombres de archivos consistentes para mejor caching
        assetFileNames: 'assets/[name]-[hash][extname]',
        chunkFileNames: 'assets/[name]-[hash].js',
        entryFileNames: 'assets/[name]-[hash].js',
      },
    },
    // Desactivar sourcemaps en producción para reducir tamaño
    sourcemap: false,
    // Comprimir con brotli para resultados óptimos
    brotliSize: true,
  },
})
