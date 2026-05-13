import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { resolve } from 'path'
import compression from 'vite-plugin-compression'

export default defineConfig({
  plugins: [
    vue(),
    compression({ threshold: 500 })
  ],
  resolve: {
    alias: {
      '@': resolve(__dirname, 'src')
    },
    extensions: ['.mjs', '.js', '.ts', '.jsx', '.tsx', '.json', '.vue']
  },
  base: process.env.NODE_ENV === 'production' ? '/client/eframe/' : '/',
  server: {
    proxy: {
      '/api': 'http://localhost:8080',
      '/domain': 'http://localhost:8080',
      '/flexType': 'http://localhost:8080'
    }
  },
  build: {
    rollupOptions: {
      input: {
        index: resolve(__dirname, 'index.html'),
        flexType: resolve(__dirname, 'flexType.html')
      }
    }
  }
})
